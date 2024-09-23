package com.antoniosousa.ecommerce.domain.services;

import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterResponseDto;
import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationRabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public NotificationRabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notify(VerificationToken verificationToken, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", verificationToken);
    }
}
