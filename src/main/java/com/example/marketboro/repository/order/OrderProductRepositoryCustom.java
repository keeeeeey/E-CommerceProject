package com.example.marketboro.repository.order;

import com.example.marketboro.dto.response.OrderProductResponseDto;

import java.util.List;

public interface OrderProductRepositoryCustom {
    List<OrderProductResponseDto> findOrderProductByOrderId(Long orderId);
}
