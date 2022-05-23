package com.example.marketboro.entity;

import com.example.marketboro.dto.request.ProductRequestDto;
import com.example.marketboro.dto.request.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderProductTest {

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
    public void createOrderProduct() {

        // given
        UserRequestDto.JoinRequestDto userRequestDto = new UserRequestDto.JoinRequestDto(
                username,
                password,
                passwordcheck,
                name,
                nickname
        );

        ProductRequestDto.CreateProduct productRequestDto = new ProductRequestDto.CreateProduct(
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

        Order order = Order.builder()
                .user(user)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productcount(5)
                .build();

        //then
        assertEquals(username, orderProduct.getOrder().getUser().getUsername());
        assertEquals(password, orderProduct.getOrder().getUser().getPassword());
        assertEquals(name, orderProduct.getOrder().getUser().getName());
        assertEquals(nickname, orderProduct.getOrder().getUser().getNickname());
        assertEquals(UserRoleEnum.USER, orderProduct.getOrder().getUser().getRole());
        assertEquals(productname, orderProduct.getProduct().getProductname());
        assertEquals(productinfo, orderProduct.getProduct().getProductinfo());
        assertEquals(productprice, orderProduct.getProduct().getProductprice());
        assertEquals(leftproduct, orderProduct.getProduct().getLeftproduct());
        assertEquals(productEnum, orderProduct.getProduct().getProductEnum());
        assertEquals(5, orderProduct.getProductcount());
    }
}
