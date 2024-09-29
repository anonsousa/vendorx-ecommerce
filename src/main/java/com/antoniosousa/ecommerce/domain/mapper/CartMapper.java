package com.antoniosousa.ecommerce.domain.mapper;

import com.antoniosousa.ecommerce.domain.dtos.cart.CartResponseDto;
import com.antoniosousa.ecommerce.domain.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "user.id", target = "userId")
    CartResponseDto cartoToDto(Cart cart);
}
