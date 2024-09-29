package com.antoniosousa.ecommerce.domain.repositories;

import com.antoniosousa.ecommerce.domain.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Transactional(readOnly = true)
    Optional<Cart> findByUser_Id(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.id = :cartItemId AND ci.cart.id = :cartId")
    void deleteCartItemByCartIdAndItemId(@Param("cartId") Long cartId, @Param("cartItemId") Long cartItemId);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Cart c JOIN c.items ci WHERE c.id = :cartId AND ci.id = :cartItemId")
    Optional<Cart> findCartByIdAndCartItemId(@Param("cartId") Long cartId, @Param("cartItemId") Long cartItemId);

}
