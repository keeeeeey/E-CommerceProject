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
    private String passwordcheck;
    private String name;
    private String nickname;

    // product
    private String productname;
    private String productinfo;
    private int productprice;
    private int leftproduct;
    private ProductEnum productEnum;

    @BeforeEach
    public void setUp() {
        // user
        username = "username@username.com";
        password = "password";
        passwordcheck = "password";
        name = "name";
        nickname = "nickname";

        // product
        productname = "당근";
        productinfo = "신선한 당근";
        productprice = 5000;
        leftproduct = 100;
        productEnum = ProductEnum.SELLING;
    }

    @Test
    @DisplayName("정상 케이스")
    public void createCartProduct() {

        // given
        JoinRequestDto userRequestDto = new JoinRequestDto(
                username,
                password,
                passwordcheck,
                name,
                nickname
        );

        CreateProduct productRequestDto = new CreateProduct(
                productname,
                productinfo,
                productprice,
                leftproduct
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
                .productname(productRequestDto.getProductname())
                .productinfo(productRequestDto.getProductinfo())
                .productprice(productRequestDto.getProductprice())
                .leftproduct(productRequestDto.getLeftproduct())
                .productEnum(productEnum)
                .build();

        Cart cart = Cart.builder()
                .user(user)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .productcount(5)
                .build();

        //then
        assertEquals(username, cartProduct.getCart().getUser().getUsername());
        assertEquals(password, cartProduct.getCart().getUser().getPassword());
        assertEquals(name, cartProduct.getCart().getUser().getName());
        assertEquals(nickname, cartProduct.getCart().getUser().getNickname());
        assertEquals(UserRoleEnum.USER, cartProduct.getCart().getUser().getRole());
        assertEquals(productname, cartProduct.getProduct().getProductname());
        assertEquals(productinfo, cartProduct.getProduct().getProductinfo());
        assertEquals(productprice, cartProduct.getProduct().getProductprice());
        assertEquals(leftproduct, cartProduct.getProduct().getLeftproduct());
        assertEquals(productEnum, cartProduct.getProduct().getProductEnum());
        assertEquals(5, cartProduct.getProductcount());
    }


}
