package com.example.marketboro.service;

import com.example.marketboro.dto.request.ProductRequestDto.CreateProduct;
import com.example.marketboro.dto.request.ProductRequestDto.UpdateProduct;
import com.example.marketboro.entity.Product;
import com.example.marketboro.entity.ProductEnum;
import com.example.marketboro.repository.ProductRepository;
import com.example.marketboro.repository.cart.CartProductRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CartProductRepository cartProductRepository;

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
    @DisplayName("product 생성")
    public void createProduct() {
        // given
        CreateProduct requestDto = new CreateProduct(
                productName,
                productInfo,
                productPrice,
                leftProduct
        );

        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .productInfo(requestDto.getProductInfo())
                .productPrice(requestDto.getProductPrice())
                .leftProduct(requestDto.getLeftProduct())
                .productEnum(productEnum)
                .build();

        ProductService productService = new ProductService(productRepository, cartProductRepository);
        when(productRepository.save(any()))
                .thenReturn(product);

        // when
        Product saveProduct = productService.createProduct(requestDto);

        // then
        assertEquals(product.getProductName(), saveProduct.getProductName());
        assertEquals(product.getProductInfo(), saveProduct.getProductInfo());
        assertEquals(product.getProductPrice(), saveProduct.getProductPrice());
        assertEquals(product.getLeftProduct(), saveProduct.getLeftProduct());
        assertEquals(product.getProductEnum(), saveProduct.getProductEnum());

    }

    @Test
    @DisplayName("product 업데이트")
    public void updateProduct() {
        // given
        Long productId = 100L;

        UpdateProduct requestDto = new UpdateProduct(
                "배추",
                "신선한 배추",
                10000,
                50,
                "SELLING"
        );

        Product product = Product.builder()
                .productName(productName)
                .productInfo(productInfo)
                .productPrice(productPrice)
                .leftProduct(leftProduct)
                .productEnum(productEnum)
                .build();

        ProductService productService = new ProductService(productRepository, cartProductRepository);
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        Product updateProduct = productService.updateProduct(requestDto, productId);

        // then
        assertEquals(requestDto.getProductName(), updateProduct.getProductName());
        assertEquals(requestDto.getProductInfo(), updateProduct.getProductInfo());
        assertEquals(requestDto.getProductPrice(), updateProduct.getProductPrice());
        assertEquals(requestDto.getLeftProduct(), updateProduct.getLeftProduct());
    }

}
