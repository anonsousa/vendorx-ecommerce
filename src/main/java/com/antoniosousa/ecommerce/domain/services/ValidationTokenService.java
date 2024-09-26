package com.antoniosousa.ecommerce.domain.services;

import com.antoniosousa.ecommerce.domain.dtos.token.ValidationToken;
import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import com.antoniosousa.ecommerce.domain.entities.enums.AccountStatus;
import com.antoniosousa.ecommerce.domain.repositories.UserRepository;
import com.antoniosousa.ecommerce.domain.repositories.VerificationTokenRepository;
import com.antoniosousa.ecommerce.domain.services.strategy.ValidateToken;
import com.antoniosousa.ecommerce.infra.exceptions.BadRequestException;
import com.antoniosousa.ecommerce.infra.exceptions.ItemNotFoundException;
import com.antoniosousa.ecommerce.infra.exceptions.StrategyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ValidationTokenService {

    private final List<ValidateToken> validateTokenList;

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final NotificationRabbitMQService notificationRabbitMQService;
    private final String exchange;

    public ValidationTokenService(List<ValidateToken> validateTokenList,
                                  VerificationTokenRepository verificationTokenRepository,
                                  UserRepository userRepository,
                                  NotificationRabbitMQService notificationRabbitMQService,
                                  @Value("${rabbitmq.notification.exchange}")String exchange) {
        this.validateTokenList = validateTokenList;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
        this.notificationRabbitMQService = notificationRabbitMQService;
        this.exchange = exchange;
    }

    @Transactional
    public VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        String name = user.getUsername();
        String email = user.getEmail();
        VerificationToken verificationToken = new VerificationToken(user, name, email, token);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Transactional
    public boolean validateToken(ValidationToken validationToken) {
        Optional<VerificationToken> token = verificationTokenRepository.findByToken(validationToken.token());

        if (token.isPresent()) {
            try {
                boolean allValidationsPassed = validateTokenList.stream()
                        .allMatch(impl -> impl.validate(validationToken, token.get()));

                if(allValidationsPassed){
                    userRepository.updateAccountStatus(token.get().getEmail(), AccountStatus.ACTIVE);
                    return true;

                }
            } catch (StrategyException e) {

                throw new StrategyException(e.getMessage());
            }
        }
        throw new ItemNotFoundException("Token not found");
    }

    @Transactional
    public void generateNewToken(String oldToken) {
        Optional<VerificationToken> oldTokenEntity = verificationTokenRepository.findByToken(oldToken);

        /*
         * First thing here its check if we have one token by the uuid
         * if present we check the token (if token still valid).
         * or token expired we set another value & expiry date follow business rules
         * and manage customized exceptions if something goes wrong
         */
        oldTokenEntity.ifPresentOrElse(verificationToken -> {
            long daysBetween = Duration.between(LocalDateTime.now(), verificationToken.getExpiryDate()).toDays();
            if(daysBetween > 2) {
                verificationToken.setToken(UUID.randomUUID().toString());
                verificationToken.setExpiryDate(LocalDateTime.now().plusDays(2));
                verificationTokenRepository.save(verificationToken);

                notificationRabbitMQService.notify(verificationToken, exchange);
            } else {

                throw new BadRequestException("Token still valid");
            }
        },
                () -> {
                    throw new ItemNotFoundException("Token not found");
            }
        );
    }
}
