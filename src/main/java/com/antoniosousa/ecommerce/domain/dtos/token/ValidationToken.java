package com.antoniosousa.ecommerce.domain.dtos.token;

public record ValidationToken(
    String email,
    String token
) { }
