package com.example.marketboro.repository;

import com.example.marketboro.entity.Cart;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.repository.cart.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("saveCart")
    public void saveCartTest() {
        // given
        User user = user();
        Cart cart = Cart.builder()
                .user(user)
                .build();
        userRepository.save(user);

        // when
        Cart savedCart = cartRepository.save(cart);

        // then
        assertEquals(cart.getUser().getUsername(), savedCart.getUser().getUsername());
    }

    @Test
    @DisplayName("findByUserId")
    public void findByUserIdTest() {
        // given
        User user = user();
        Cart cart = Cart.builder()
                .user(user)
                .build();
        userRepository.save(user);
        cartRepository.save(cart);

        // when
        Optional<Cart> findCart = cartRepository.findByUserId(user.getId());

        // then
        assertEquals(cart.getUser().getUsername(), findCart.get().getUser().getUsername());
    }

    private User user() {
        return User.builder()
                .userId(1L)
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();
    }
}
