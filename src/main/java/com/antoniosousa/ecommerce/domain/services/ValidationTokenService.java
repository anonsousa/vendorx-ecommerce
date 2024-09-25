package com.antoniosousa.ecommerce.domain.services;

import com.antoniosousa.ecommerce.domain.dtos.token.ValidationToken;
import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import com.antoniosousa.ecommerce.domain.entities.enums.AccountStatus;
import com.antoniosousa.ecommerce.domain.repositories.UserRepository;
import com.antoniosousa.ecommerce.domain.repositories.VerificationTokenRepository;
import com.antoniosousa.ecommerce.domain.services.strategy.ValidateToken;
import com.antoniosousa.ecommerce.infra.exceptions.StrategyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ValidationTokenService {

    private final List<ValidateToken> validateTokenList;

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public ValidationTokenService(List<ValidateToken> validateTokenList,
                                  VerificationTokenRepository verificationTokenRepository,
                                  UserRepository userRepository) {
        this.validateTokenList = validateTokenList;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
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
                log.error("Validation token failed", e);
            }
        }
        throw new StrategyException("Validation token failed");
    }





}
