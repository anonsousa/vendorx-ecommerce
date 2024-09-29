package com.antoniosousa.ecommerce.domain.services;

import com.antoniosousa.ecommerce.domain.dtos.cart.CartItemRequestDto;
import com.antoniosousa.ecommerce.domain.dtos.cart.CartResponseDto;
import com.antoniosousa.ecommerce.domain.entities.Cart;
import com.antoniosousa.ecommerce.domain.entities.CartItem;
import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.mapper.CartItemMapper;
import com.antoniosousa.ecommerce.domain.mapper.CartMapper;
import com.antoniosousa.ecommerce.domain.repositories.CartItemRepository;
import com.antoniosousa.ecommerce.domain.repositories.CartRepository;
import com.antoniosousa.ecommerce.domain.repositories.UserRepository;
import com.antoniosousa.ecommerce.infra.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public CartResponseDto addItemtoCart(Long userId, CartItemRequestDto cartItemRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        /*
         * If this is the first item for the user,
         * create new cart to him, else just continue the operation
         */
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        CartItem cartItem = CartItemMapper.INSTANCE.toCartItemEntity(cartItemRequestDto);
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);
        cart.setTotalAmount(cart.getTotalAmount().add(cartItem.getPrice()));

        Cart cartSaved = cartRepository.save(cart);

        return CartMapper.INSTANCE.cartoToDto(cartSaved);
    }

    @Transactional(readOnly = true)
    public CartResponseDto findCartByUserId(Long userId) {

        return cartRepository.findByUser_Id(userId)
                .map(CartMapper.INSTANCE::cartoToDto)
                .orElseThrow(() -> new ItemNotFoundException("Cart not found!"));
    }

    @Transactional
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        Optional<Cart> cart = cartRepository.findCartByIdAndCartItemId(cartId, cartItemId);

        cart.ifPresentOrElse(c -> cartRepository.deleteCartItemByCartIdAndItemId(c.getId(), cartItemId),
                () -> {throw new ItemNotFoundException("Item not found!");}
        );
    }





}
