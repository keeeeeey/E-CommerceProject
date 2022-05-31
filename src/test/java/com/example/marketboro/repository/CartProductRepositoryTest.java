package com.example.marketboro.repository;

import com.example.marketboro.dto.response.CartProductResponseDto;
import com.example.marketboro.entity.*;
import com.example.marketboro.repository.cart.CartProductRepository;
import com.example.marketboro.repository.cart.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CartProductRepositoryTest {

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("saveCartProduct")
    public void saveCartProductTest() {
        // given
        User user = user();
        Product product = product();
        Cart cart = Cart.builder()
                .user(user)
                .build();
        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .productCount(10)
                .build();
        userRepository.save(user);
        productRepository.save(product);
        cartRepository.save(cart);

        // when
        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);

        // then
        assertEquals(cartProduct.getProduct().getProductName(), savedCartProduct.getProduct().getProductName());

    }

    @Test
    @DisplayName("findCartProductByCartId")
    public void findCartProductByCartIdTest() {
        // given
        User user = user();
        Product product = product();
        Cart cart = Cart.builder()
                .user(user)
                .build();
        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .productCount(10)
                .build();
        userRepository.save(user);
        productRepository.save(product);
        Cart savedCart = cartRepository.save(cart);
        cartProductRepository.save(cartProduct);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<CartProductResponseDto> responseDtoList = cartProductRepository
                .findCartProductByCartId(savedCart.getId(), pageable);

        // then
        assertEquals(1, responseDtoList.size());
        assertEquals(cartProduct.getProduct().getProductName(), responseDtoList.get(0).getProductName());
        assertEquals(cartProduct.getProductCount(), responseDtoList.get(0).getProductCount());

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
