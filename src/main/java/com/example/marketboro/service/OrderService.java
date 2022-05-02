package com.example.marketboro.service;

import com.example.marketboro.repository.OrderProductRepository;
import com.example.marketboro.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Long createOrder() {
        return null;
    }
}
