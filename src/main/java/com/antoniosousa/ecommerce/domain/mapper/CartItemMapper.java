package com.antoniosousa.ecommerce.domain.mapper;

import com.antoniosousa.ecommerce.domain.dtos.cart.CartItemRequestDto;
import com.antoniosousa.ecommerce.domain.dtos.cart.CartItemResponseDto;
import com.antoniosousa.ecommerce.domain.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem toCartItemEntity(CartItemRequestDto cartItemRequestDto);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);
}
