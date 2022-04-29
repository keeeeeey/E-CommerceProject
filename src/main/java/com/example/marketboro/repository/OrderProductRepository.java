package com.example.marketboro.repository;

import com.example.marketboro.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    void deleteByProductId(Long productId);
}
