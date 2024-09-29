package com.antoniosousa.ecommerce.controller;

import com.antoniosousa.ecommerce.domain.dtos.cart.CartItemRequestDto;
import com.antoniosousa.ecommerce.domain.dtos.cart.CartResponseDto;
import com.antoniosousa.ecommerce.domain.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartResponseDto> addItem(@RequestParam("userId") Long userId,
                                                   @RequestBody @Valid CartItemRequestDto cartItemRequestDto) {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.addItemtoCart(userId, cartItemRequestDto));
    }
}
