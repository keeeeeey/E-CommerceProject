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

    @Test
    @DisplayName("정상 케이스")
    public void createProductTest() {

        // given
        CreateProduct requestDto = createProduct();

        //when
        Product product = product();

        //then
        assertEquals(requestDto.getProductName(), product.getProductName());
        assertEquals(requestDto.getProductInfo(), product.getProductInfo());
        assertEquals(requestDto.getProductPrice(), product.getProductPrice());
        assertEquals(requestDto.getLeftProduct(), product.getLeftProduct());
        assertEquals(ProductEnum.SELLING, product.getProductEnum());
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

    private CreateProduct createProduct() {
        return new CreateProduct(
                "당근",
                "신선한 당근",
                5000,
                100
        );
    }
}
