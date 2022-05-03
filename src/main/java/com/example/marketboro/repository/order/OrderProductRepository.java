package com.example.marketboro.repository.order;

import com.example.marketboro.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {
    List<OrderProduct> findAllByOrderId(Long id);
}
