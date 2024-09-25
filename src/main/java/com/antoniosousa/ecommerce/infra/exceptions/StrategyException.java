package com.antoniosousa.ecommerce.infra.exceptions;

public class StrategyException extends RuntimeException {
    public StrategyException(String message) {
        super(message);
    }
}
