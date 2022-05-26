package com.example.marketboro.service;

import com.example.marketboro.entity.Order;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
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

    @Test
    @DisplayName("주문하기")
    public void createOrderTest() {
        // given
        Long userId = 100L;

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

    }
}
