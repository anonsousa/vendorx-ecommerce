package com.antoniosousa.ecommerce.domain.dtos.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDto {
    private Long id;
    private Long userId; // Apenas o ID do usuário, se preferir.
    private List<CartItemResponseDto> items;
    private BigDecimal totalAmount;
}
