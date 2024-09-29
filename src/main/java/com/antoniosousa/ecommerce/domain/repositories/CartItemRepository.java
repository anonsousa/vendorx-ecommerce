package com.antoniosousa.ecommerce.domain.repositories;

import com.antoniosousa.ecommerce.domain.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
