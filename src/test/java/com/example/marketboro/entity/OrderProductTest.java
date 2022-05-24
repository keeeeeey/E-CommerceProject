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
    public void createOrderProduct() {

        // given
        UserRequestDto.JoinRequestDto userRequestDto = new UserRequestDto.JoinRequestDto(
                username,
                password,
                passwordCheck,
                name,
                nickname
        );

        ProductRequestDto.CreateProduct productRequestDto = new ProductRequestDto.CreateProduct(
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

        Order order = Order.builder()
                .user(user)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productCount(5)
                .build();

        //then
        assertEquals(username, orderProduct.getOrder().getUser().getUsername());
        assertEquals(password, orderProduct.getOrder().getUser().getPassword());
        assertEquals(name, orderProduct.getOrder().getUser().getName());
        assertEquals(nickname, orderProduct.getOrder().getUser().getNickname());
        assertEquals(UserRoleEnum.USER, orderProduct.getOrder().getUser().getRole());
        assertEquals(productName, orderProduct.getProduct().getProductName());
        assertEquals(productInfo, orderProduct.getProduct().getProductInfo());
        assertEquals(productPrice, orderProduct.getProduct().getProductPrice());
        assertEquals(leftProduct, orderProduct.getProduct().getLeftProduct());
        assertEquals(productEnum, orderProduct.getProduct().getProductEnum());
        assertEquals(5, orderProduct.getProductCount());
    }
}
