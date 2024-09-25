package com.antoniosousa.ecommerce.domain.services.strategy;

import com.antoniosousa.ecommerce.domain.dtos.token.ValidationToken;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;

public interface ValidateToken {

    boolean validate(ValidationToken validationToken, VerificationToken verificationToken);
}
