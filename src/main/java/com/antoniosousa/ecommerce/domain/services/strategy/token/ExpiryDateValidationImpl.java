package com.antoniosousa.ecommerce.domain.services.strategy.token;

import com.antoniosousa.ecommerce.domain.dtos.token.ValidationToken;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Order(1)
public class ExpiryDateValidationImpl implements ValidateToken {

    @Override
    public boolean validate(ValidationToken validationToken, VerificationToken verificationToken) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = verificationToken.getExpiryDate();

        return !expiryDate.isBefore(now) && ChronoUnit.DAYS.between(now, expiryDate) <= 2;
    }
}
