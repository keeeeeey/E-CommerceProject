package com.example.marketboro.repository;

import com.example.marketboro.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findAllByCartId(Long id);
}
