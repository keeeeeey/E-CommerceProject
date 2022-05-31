package com.example.marketboro.repository;

import com.example.marketboro.entity.Cart;
import com.example.marketboro.entity.Order;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("saveOrder")
    public void saveOrderTest() {
        // given
        User user = user();
        Order order = Order.builder()
                .user(user)
                .build();
        userRepository.save(user);

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        assertEquals(order.getUser().getUsername(), savedOrder.getUser().getUsername());
    }

    @Test
    @DisplayName("findByUserId")
    public void findByUserIdTest() {
        // given
        User user = user();
        Order order = Order.builder()
                .user(user)
                .build();
        User savedUser = userRepository.save(user);
        orderRepository.save(order);

        // when
        List<Order> findOrderList = orderRepository.findAllByUserId(savedUser.getId());

        // then
        assertEquals(1, findOrderList.size());
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
}
