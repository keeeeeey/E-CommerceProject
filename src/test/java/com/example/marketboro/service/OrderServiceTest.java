package com.example.marketboro.service;

import com.example.marketboro.dto.request.CommonDto;
import com.example.marketboro.dto.request.CommonDto.IdDto;
import com.example.marketboro.dto.request.CommonDto.OrderDto;
import com.example.marketboro.dto.request.OrderRequestDto;
import com.example.marketboro.dto.request.OrderRequestDto.CancelOrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CreateOrderDto;
import com.example.marketboro.dto.response.OrderProductResponseDto;
import com.example.marketboro.entity.*;
import com.example.marketboro.repository.ProductRepository;
import com.example.marketboro.repository.UserRepository;
import com.example.marketboro.repository.cart.CartProductRepository;
import com.example.marketboro.repository.order.OrderProductRepository;
import com.example.marketboro.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartProductRepository cartProductRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderProductRepository orderProductRepository;

    MockHttpServletRequest request = new MockHttpServletRequest();

    @Test
    @DisplayName("주문하기")
    public void createOrderTest() {
        // given
        Long userId = 100L;
        Long productId = 100L;
        Long cartProductId = 100L;

        User user = User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();

        Order order = Order.builder()
                .user(user)
                .build();

        Product product = Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productCount(10)
                .orderStatus(OrderStatus.주문접수)
                .build();

        OrderDto orderDto = new OrderDto(productId, cartProductId, 10);
        List<OrderDto> requestDto = new ArrayList<>();
        requestDto.add(orderDto);

        CreateOrderDto requestDtoList = new CreateOrderDto(requestDto);

        OrderService orderService = new OrderService(
                userRepository,
                productRepository,
                cartProductRepository,
                orderRepository,
                orderProductRepository
        );
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(orderRepository.save(any())).thenReturn(order);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderProductRepository.save(any())).thenReturn(orderProduct);

        // when
        List<OrderProductResponseDto> responseDtoList = orderService.createOrder(userId, requestDtoList, request);

        // then
        assertEquals(orderProduct.getProduct().getProductName(), responseDtoList.get(0).getProductName());
    }

    @Test
    @DisplayName("주문취소")
    public void cancelOrderTest() {
        // given
        Long orderProductId = 100L;

        User user = User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();

        Order order = Order.builder()
                .user(user)
                .build();

        Product product = Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productCount(10)
                .orderStatus(OrderStatus.주문접수)
                .build();

        IdDto idDto = new IdDto(orderProductId);
        List<IdDto> requestDto = new ArrayList<>();
        requestDto.add(idDto);

        CancelOrderDto requestDtoList = new CancelOrderDto(requestDto);

        OrderService orderService = new OrderService(
                userRepository,
                productRepository,
                cartProductRepository,
                orderRepository,
                orderProductRepository
        );
        when(orderProductRepository.findById(orderProductId))
                .thenReturn(Optional.of(orderProduct));

        // when
        List<OrderProductResponseDto> responseDtoList = orderService.cancelOrder(requestDtoList);

        // then
        assertEquals(OrderStatus.주문취소, responseDtoList.get(0).getOrderStatus());
    }

    @Test
    @DisplayName("주문완료")
    public void finishDeliveryTest() {
        // given
        Long orderProductId = 100L;

        User user = User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();

        Order order = Order.builder()
                .user(user)
                .build();

        Product product = Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productCount(10)
                .orderStatus(OrderStatus.주문접수)
                .build();

        OrderService orderService = new OrderService(
                userRepository,
                productRepository,
                cartProductRepository,
                orderRepository,
                orderProductRepository
        );
        when(orderProductRepository.findById(orderProductId))
                .thenReturn(Optional.of(orderProduct));

        // when
        OrderProductResponseDto responseDto = orderService.finishDelivery(orderProductId);

        // then
        assertEquals(OrderStatus.배송완료, responseDto.getOrderStatus());
    }
}
