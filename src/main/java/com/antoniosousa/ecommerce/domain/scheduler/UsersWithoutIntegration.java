package com.antoniosousa.ecommerce.domain.scheduler;

import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterResponseDto;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import com.antoniosousa.ecommerce.domain.mapper.UserMapper;
import com.antoniosousa.ecommerce.domain.repositories.UserRepository;
import com.antoniosousa.ecommerce.domain.repositories.VerificationTokenRepository;
import com.antoniosousa.ecommerce.domain.services.NotificationRabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
public class UsersWithoutIntegration {

    private final UserRepository userRepository;
    private final NotificationRabbitMQService notificationRabbitMQService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final String exchange;

    private static final Logger logger = LoggerFactory.getLogger(UsersWithoutIntegration.class);


    public UsersWithoutIntegration(UserRepository userRepository,
                                   NotificationRabbitMQService notificationRabbitMQService, VerificationTokenRepository verificationTokenRepository,
                                   @Value("${rabbitmq.notification.exchange}") String exchange) {
        this.userRepository = userRepository;
        this.notificationRabbitMQService = notificationRabbitMQService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void searchUsersWithoutIntegration() {
        logger.info("Searching users without integration and trying to integrate'em");

        /*
         * First I just search users with Integrated: False
         */
        List<UserRegisterResponseDto> usersDtoList = userRepository.findByIntegratedFalse().stream()
                .map(UserMapper.INSTANCE::toUserRegisterResponseDto)
                .collect(Collectors.toList());

        /*
         * Search all users without integrated on verificationToken repo.
         */
        List<VerificationToken> verificationTokens = verificationTokenRepository.findAllByUserIds(
                usersDtoList.stream()
                        .map(UserRegisterResponseDto::getId)
                        .collect(Collectors.toList())
        );

        /*
         * I map all users on Map List to easy access, without need to go on
         * database on each user, reducing integration to database
         * less memory to consume.
         */
        Map<Long, VerificationToken> tokenMap = verificationTokens.stream()
                .collect(Collectors.toMap(token -> token.getUser().getId(),
                                          token -> token));

        usersDtoList.forEach(user -> {
            try {
                VerificationToken verificationToken = tokenMap.get(user.getId());
                if (verificationToken != null) {
                    notificationRabbitMQService.notify(verificationToken, exchange);
                }
                userRepository.updateIntegratedById(user.getId(), true);
            } catch (RuntimeException e) {
                userRepository.updateIntegratedById(user.getId(), false);
                logger.error(e.getMessage());
            }
        });
    }
}
