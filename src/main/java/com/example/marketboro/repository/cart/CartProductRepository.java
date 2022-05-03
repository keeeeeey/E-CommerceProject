package com.example.marketboro.repository.cart;

import com.example.marketboro.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, CartProductRepositoryCustom {
}
