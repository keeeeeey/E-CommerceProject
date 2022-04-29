package com.example.marketboro.repository;

import com.example.marketboro.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    void deleteByProductId(Long productId);
}
