package com.example.marketboro.entity;

import com.example.marketboro.dto.request.ProductRequestDto;
import com.example.marketboro.dto.request.ProductRequestDto.CreateProduct;
import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Autowired
    EntityManager em;

    private String productName;
    private String productInfo;
    private int productPrice;
    private int leftProduct;
    private ProductEnum productEnum;

    @BeforeEach
    public void setUp() {
        productName = "당근";
        productInfo = "신선한 당근";
        productPrice = 5000;
        leftProduct = 100;
        productEnum = ProductEnum.SELLING;
    }

    @Test
    @DisplayName("정상 케이스")
    public void createProduct() {

        // given
        CreateProduct requestDto = new CreateProduct(
                productName,
                productInfo,
                productPrice,
                leftProduct
        );

        //when
        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .productInfo(requestDto.getProductInfo())
                .productPrice(requestDto.getProductPrice())
                .leftProduct(requestDto.getLeftProduct())
                .productEnum(productEnum)
                .build();

        //then
        assertEquals(productName, product.getProductName());
        assertEquals(productInfo, product.getProductInfo());
        assertEquals(productPrice, product.getProductPrice());
        assertEquals(leftProduct, product.getLeftProduct());
        assertEquals(productEnum, product.getProductEnum());
    }
}
