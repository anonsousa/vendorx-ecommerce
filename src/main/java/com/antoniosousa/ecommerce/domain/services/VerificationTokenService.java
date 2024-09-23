package com.antoniosousa.ecommerce.domain.services;

import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import com.antoniosousa.ecommerce.domain.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        String name = user.getUsername();
        String email = user.getEmail();
        VerificationToken verificationToken = new VerificationToken(user, name, email, token);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

}
