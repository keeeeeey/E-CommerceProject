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

    @Test
    @DisplayName("product 생성")
    public void createProductTest() {
        // given
        CreateProduct requestDto = createProduct();

        Product product = product();

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
    public void updateProductTest() {
        // given
        Long productId = 100L;

        UpdateProduct requestDto = updateProduct();

        Product product = product();

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

    private UpdateProduct updateProduct() {
        return new UpdateProduct(
                "배추",
                "신선한 배추",
                10000,
                50,
                "SELLING"
        );
    }

}
