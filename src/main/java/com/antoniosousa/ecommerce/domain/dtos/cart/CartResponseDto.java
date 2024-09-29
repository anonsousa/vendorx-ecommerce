package com.antoniosousa.ecommerce.domain.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> items;
    private BigDecimal totalAmount;
}
