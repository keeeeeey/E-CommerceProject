package com.example.marketboro.repository;

import com.example.marketboro.dto.response.OrderProductResponseDto;
import com.example.marketboro.entity.*;
import com.example.marketboro.repository.cart.CartProductRepository;
import com.example.marketboro.repository.cart.CartRepository;
import com.example.marketboro.repository.order.OrderProductRepository;
import com.example.marketboro.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class OrderProductRepositoryTest {

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("saveOrderProduct")
    public void saveOrderProductTest() {
        // given
        User user = user();
        Product product = product();
        Order order = Order.builder()
                .user(user)
                .build();
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productCount(10)
                .build();
        userRepository.save(user);
        productRepository.save(product);
        orderRepository.save(order);

        // when
        OrderProduct savedOrderProduct = orderProductRepository.save(orderProduct);

        // then
        assertEquals(orderProduct.getProduct().getProductName(), savedOrderProduct.getProduct().getProductName());
    }

    @Test
    @DisplayName("findOrderProductByOrderId")
    public void findOrderProductByOrderIdTest() {
        // given
        User user = user();
        Product product = product();
        Order order = Order.builder()
                .user(user)
                .build();
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productCount(10)
                .build();
        userRepository.save(user);
        productRepository.save(product);
        Order savedOrder = orderRepository.save(order);
        orderProductRepository.save(orderProduct);

        // when
        List<OrderProductResponseDto> responseDtoList = orderProductRepository.findOrderProductByOrderId(savedOrder.getId());

        // then
        assertEquals(1, responseDtoList.size());
        assertEquals(orderProduct.getProduct().getProductName(), responseDtoList.get(0).getProductName());
        assertEquals(orderProduct.getProductCount(), responseDtoList.get(0).getProductCount());
    }

    private User user() {
        return User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();
    }

    private Product product() {
        return Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();
    }
}
