package com.antoniosousa.ecommerce.domain.services.strategy.token;

import com.antoniosousa.ecommerce.domain.dtos.token.ValidationToken;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class TokenValidationImpl implements ValidateToken {

    @Override
    public boolean validate(ValidationToken validationToken, VerificationToken verificationToken) {
        return validationToken.token().equals(verificationToken.getToken());
    }
}
