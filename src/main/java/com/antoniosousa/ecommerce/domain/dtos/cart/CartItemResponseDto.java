package com.antoniosousa.ecommerce.domain.dtos.cart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDto {

    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

}
