package com.antoniosousa.ecommerce.controller;

import com.antoniosousa.ecommerce.domain.dtos.token.ValidationToken;
import com.antoniosousa.ecommerce.domain.services.ValidationTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/validation/token")
public class ValidationTokenController {

    private final ValidationTokenService validationTokenService;

    public ValidationTokenController(ValidationTokenService validationTokenService) {
        this.validationTokenService = validationTokenService;
    }

    @PostMapping
    public ResponseEntity<Boolean> validateToken(@RequestParam("email") String email,
                                                 @RequestParam("token") String token) {

        ValidationToken validationToken = new ValidationToken(email, token);
        boolean valid = validationTokenService.validateToken(validationToken);

        if (valid) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
