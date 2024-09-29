package com.antoniosousa.ecommerce.domain.dtos.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemRequestDto {

    @NotBlank
    private String productName;
    @NotNull
    @Positive
    private Integer quantity;
    @NotNull
    @Positive
    private BigDecimal price;

}
