package com.example.marketboro.service;

import com.example.marketboro.dto.request.CommonDto.IdDto;
import com.example.marketboro.dto.request.CommonDto.OrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CancelOrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CreateOrderDto;
import com.example.marketboro.entity.*;
import com.example.marketboro.repository.OrderProductRepository;
import com.example.marketboro.repository.OrderRepository;
import com.example.marketboro.repository.ProductRepository;
import com.example.marketboro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public List<Long> createOrder(Long userId, CreateOrderDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        Order order = Order.builder()
                .user(user)
                .build();
        Order saveOrder = orderRepository.save(order);
        List<OrderDto> orderDtoList = requestDto.getOrderList();
        List<Long> orderProductIdList = new ArrayList<>();
        orderDtoList.forEach((orderDto) -> {
            Product product = productRepository.findById(orderDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

            if (product.getLeftproduct() < orderDto.getProductcount()) {
                throw new RuntimeException("재고가 부족합니다.");
            }

            OrderProduct orderProduct = OrderProduct.builder()
                    .order(saveOrder)
                    .product(product)
                    .productcount(orderDto.getProductcount())
                    .orderStatus(OrderStatus.주문접수)
                    .build();

            OrderProduct saveOrderProduct = orderProductRepository.save(orderProduct);
            product.minusLeftProduct(orderDto.getProductcount());

            orderProductIdList.add(saveOrderProduct.getId());
            log.info(saveOrderProduct.getId() + "번 상품 주문 접수");
        });

        return orderProductIdList;
    }

    @Transactional
    public List<Long> cancelOrder(CancelOrderDto requestDto) {
        List<Long> cancelOrderProductIdList = new ArrayList<>();
        List<IdDto> cancelOrderList = requestDto.getCancelOrderList();
        cancelOrderList.forEach((cancelOrder) -> {
            OrderProduct orderProduct = orderProductRepository.findById(cancelOrder.getId())
                    .orElseThrow(() -> new RuntimeException("이미 취소된 주문입니다."));

            if (orderProduct.getOrderStatus().equals(OrderStatus.배송완료)) {
                throw new RuntimeException("이미 배송이 완료된 상품입니다. 주문을 취소할 수 없습니다.");
            } else if (orderProduct.getOrderStatus().equals(OrderStatus.주문취소)) {
                throw new RuntimeException("이미 취소된 상품입니다. 주문을 취소할 수 없습니다.");
            }

            orderProduct.changeOrderStatus(OrderStatus.주문취소);
            orderProduct.getProduct().plusLeftProduct(orderProduct.getProductcount());

            cancelOrderProductIdList.add(orderProduct.getId());
            log.info(orderProduct.getId() + "번 주문 취소");
        });
        return cancelOrderProductIdList;
    }

    @Transactional
    public Long finishDelivery(Long orderProductId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new RuntimeException("주문하신 상품이 아닙니다."));
        orderProduct.changeOrderStatus(OrderStatus.배송완료);
        log.info(orderProduct.getId() + "번 주문 배송 완료");
        return orderProduct.getId();
    }

}
