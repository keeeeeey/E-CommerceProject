package com.example.marketboro.service;

import com.example.marketboro.dto.request.CartRequestDto.AddCartDto;
import com.example.marketboro.dto.request.CartRequestDto.UpdateCartDto;
import com.example.marketboro.entity.*;
import com.example.marketboro.repository.ProductRepository;
import com.example.marketboro.repository.cart.CartProductRepository;
import com.example.marketboro.repository.cart.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    CartProductRepository cartProductRepository;

    @Test
    @DisplayName("장바구니 담기")
    public void addCartTest() {

        // given
        Long userId = 100L;
        Long productId = 100L;

        User user = User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();

        Cart cart = Cart.builder()
                .user(user)
                .build();

        Product product = Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .product(product)
                .cart(cart)
                .productCount(5)
                .build();

        AddCartDto requestDto = new AddCartDto(productId, 5);

        CartService cartService = new CartService(cartRepository, productRepository, cartProductRepository);
        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(cartProductRepository.save(any()))
                .thenReturn(cartProduct);

        // when
        CartProduct saveCartProduct = cartService.addCart(userId, requestDto);

        // then
        assertEquals(cartProduct.getProduct().getProductName(), saveCartProduct.getProduct().getProductName());
        assertEquals(cartProduct.getCart().getUser().getId(), saveCartProduct.getCart().getUser().getId());
        assertEquals(cartProduct.getProductCount(), saveCartProduct.getProductCount());
    }

    @Test
    @DisplayName("장바구니 수정")
    public void updateCartTest() {

        // given
        Long cartProductId = 100L;

        User user = User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();

        Cart cart = Cart.builder()
                .user(user)
                .build();

        Product product = Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .product(product)
                .cart(cart)
                .productCount(5)
                .build();

        UpdateCartDto requestDto = new UpdateCartDto(cartProductId, 10);

        CartService cartService = new CartService(cartRepository, productRepository, cartProductRepository);
        when(cartProductRepository.findById(cartProductId))
                .thenReturn(Optional.of(cartProduct));

        // when
        CartProduct saveCartProduct = cartService.updateCart(requestDto);

        // then
        assertEquals(10, saveCartProduct.getProductCount());
    }
}
