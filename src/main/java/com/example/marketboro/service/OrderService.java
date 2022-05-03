package com.example.marketboro.service;

import com.example.marketboro.dto.request.CommonDto.IdDto;
import com.example.marketboro.dto.request.CommonDto.OrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CancelOrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CreateOrderDto;
import com.example.marketboro.dto.response.OrderProductResponseDto;
import com.example.marketboro.entity.*;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.order.OrderProductRepository;
import com.example.marketboro.repository.order.OrderRepository;
import com.example.marketboro.repository.ProductRepository;
import com.example.marketboro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));
        Order order = Order.builder()
                .user(user)
                .build();
        Order saveOrder = orderRepository.save(order);
        List<OrderDto> orderDtoList = requestDto.getOrderList();
        List<Long> orderProductIdList = new ArrayList<>();
        orderDtoList.forEach((orderDto) -> {
            Product product = productRepository.findById(orderDto.getProductId())
                    .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_EXISTENCE_ERROR));

            if (product.getLeftproduct() < orderDto.getProductcount()) {
                throw new ErrorCustomException(ErrorCode.SHORTAGE_PRODUCT_ERROR);
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
                    .orElseThrow(() -> new ErrorCustomException(ErrorCode.ALREADY_CANCELORDER_ERROR));

            if (orderProduct.getOrderStatus().equals(OrderStatus.배송완료)) {
                throw new ErrorCustomException(ErrorCode.ALREADY_DELIVERED_ERROR);
            } else if (orderProduct.getOrderStatus().equals(OrderStatus.주문취소)) {
                throw new ErrorCustomException(ErrorCode.ALREADY_CANCELORDER_ERROR);
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
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_ORDERED_ERROR));
        orderProduct.changeOrderStatus(OrderStatus.배송완료);
        log.info(orderProduct.getId() + "번 주문 배송 완료");
        return orderProduct.getId();
    }

    @Transactional(readOnly = true)
    public List<OrderProductResponseDto> getAllOrder(Long userId) {
        List<Order> orderList = orderRepository.findAllByUserId(userId);
        List<OrderProductResponseDto> responseDto = new ArrayList<>();
        orderList.forEach((order) -> {
            List<OrderProductResponseDto> findOrderProductList = orderProductRepository.findOrderProductByOrderId(order.getId());
            findOrderProductList.forEach((findOrderProduct) -> {
                responseDto.add(findOrderProduct);
            });
        });
        return responseDto;
    }

}
