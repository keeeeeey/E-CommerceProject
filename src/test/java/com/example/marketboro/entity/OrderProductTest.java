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

public class OrderProductTest {

    @Test
    @DisplayName("정상 케이스")
    public void createOrderProduct() {

        // given
        JoinRequestDto userRequestDto = joinRequestDto();

        CreateProduct productRequestDto = createProduct();

        User user = user();

        Product product = product();

        //when
        Order order = Order.builder()
                .user(user)
                .build();

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .productCount(5)
                .build();

        //then
        assertEquals(userRequestDto.getUsername(), orderProduct.getOrder().getUser().getUsername());
        assertEquals(userRequestDto.getPassword(), orderProduct.getOrder().getUser().getPassword());
        assertEquals(userRequestDto.getName(), orderProduct.getOrder().getUser().getName());
        assertEquals(userRequestDto.getNickname(), orderProduct.getOrder().getUser().getNickname());
        assertEquals(UserRoleEnum.USER, orderProduct.getOrder().getUser().getRole());
        assertEquals(productRequestDto.getProductName(), orderProduct.getProduct().getProductName());
        assertEquals(productRequestDto.getProductInfo(), orderProduct.getProduct().getProductInfo());
        assertEquals(productRequestDto.getProductPrice(), orderProduct.getProduct().getProductPrice());
        assertEquals(productRequestDto.getLeftProduct(), orderProduct.getProduct().getLeftProduct());
        assertEquals(ProductEnum.SELLING, orderProduct.getProduct().getProductEnum());
        assertEquals(5, orderProduct.getProductCount());
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
