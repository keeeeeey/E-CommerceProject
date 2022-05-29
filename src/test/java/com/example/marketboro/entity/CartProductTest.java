package com.example.marketboro.entity;

import com.example.marketboro.dto.request.ProductRequestDto;
import com.example.marketboro.dto.request.ProductRequestDto.CreateProduct;
import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartProductTest {

    @Test
    @DisplayName("정상 케이스")
    public void createCartProduct() {

        // given
        JoinRequestDto userRequestDto = joinRequestDto();

        CreateProduct productRequestDto = createProduct();

        User user = user();

        Product product = product();

        //when
        Cart cart = Cart.builder()
                .user(user)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .productCount(5)
                .build();

        //then
        assertEquals(user.getUsername(), cartProduct.getCart().getUser().getUsername());
        assertEquals(user.getPassword(), cartProduct.getCart().getUser().getPassword());
        assertEquals(user.getName(), cartProduct.getCart().getUser().getName());
        assertEquals(user.getNickname(), cartProduct.getCart().getUser().getNickname());
        assertEquals(UserRoleEnum.USER, cartProduct.getCart().getUser().getRole());
        assertEquals(product.getProductName(), cartProduct.getProduct().getProductName());
        assertEquals(product.getProductInfo(), cartProduct.getProduct().getProductInfo());
        assertEquals(product.getProductPrice(), cartProduct.getProduct().getProductPrice());
        assertEquals(product.getLeftProduct(), cartProduct.getProduct().getLeftProduct());
        assertEquals(ProductEnum.SELLING, cartProduct.getProduct().getProductEnum());
        assertEquals(5, cartProduct.getProductCount());
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

    private Product product() {
        return Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();
    }

    private JoinRequestDto joinRequestDto() {
        return new JoinRequestDto(
                "sseioul@naver.com",
                "1234",
                "1234",
                "김기윤",
                "key"
        );
    }

    private CreateProduct createProduct() {
        return new CreateProduct(
                "당근",
                "신선한 당근",
                5000,
                100
        );
    }

}
