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

    @Autowired
    EntityManager em;

    // user
    private String username;
    private String password;
    private String passwordCheck;
    private String name;
    private String nickname;

    // product
    private String productName;
    private String productInfo;
    private int productPrice;
    private int leftProduct;
    private ProductEnum productEnum;

    @BeforeEach
    public void setUp() {
        // user
        username = "username@username.com";
        password = "password";
        passwordCheck = "password";
        name = "name";
        nickname = "nickname";

        // product
        productName = "당근";
        productInfo = "신선한 당근";
        productPrice = 5000;
        leftProduct = 100;
        productEnum = ProductEnum.SELLING;
    }

    @Test
    @DisplayName("정상 케이스")
    public void createCartProduct() {

        // given
        JoinRequestDto userRequestDto = new JoinRequestDto(
                username,
                password,
                passwordCheck,
                name,
                nickname
        );

        CreateProduct productRequestDto = new CreateProduct(
                productName,
                productInfo,
                productPrice,
                leftProduct
        );

        //when
        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(userRequestDto.getPassword())
                .name(userRequestDto.getName())
                .nickname(userRequestDto.getNickname())
                .role(UserRoleEnum.USER)
                .build();

        Product product = Product.builder()
                .productName(productRequestDto.getProductName())
                .productInfo(productRequestDto.getProductInfo())
                .productPrice(productRequestDto.getProductPrice())
                .leftProduct(productRequestDto.getLeftProduct())
                .productEnum(productEnum)
                .build();

        Cart cart = Cart.builder()
                .user(user)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .productCount(5)
                .build();

        //then
        assertEquals(username, cartProduct.getCart().getUser().getUsername());
        assertEquals(password, cartProduct.getCart().getUser().getPassword());
        assertEquals(name, cartProduct.getCart().getUser().getName());
        assertEquals(nickname, cartProduct.getCart().getUser().getNickname());
        assertEquals(UserRoleEnum.USER, cartProduct.getCart().getUser().getRole());
        assertEquals(productName, cartProduct.getProduct().getProductName());
        assertEquals(productInfo, cartProduct.getProduct().getProductInfo());
        assertEquals(productPrice, cartProduct.getProduct().getProductPrice());
        assertEquals(leftProduct, cartProduct.getProduct().getLeftProduct());
        assertEquals(productEnum, cartProduct.getProduct().getProductEnum());
        assertEquals(5, cartProduct.getProductCount());
    }


}
