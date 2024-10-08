package com.antoniosousa.ecommerce.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StrategyException extends RuntimeException {
    public StrategyException(String message) {
        super(message);
    }
}
