package com.example.marketboro.repository;

import com.example.marketboro.entity.Product;
import com.example.marketboro.entity.ProductEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("saveProduct")
    public void saveProduct() {
        // given
        Product product = product();

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductInfo(), savedProduct.getProductInfo());
        assertEquals(product.getProductPrice(), savedProduct.getProductPrice());
        assertEquals(product.getLeftProduct(), savedProduct.getLeftProduct());
        assertEquals(product.getProductEnum(), savedProduct.getProductEnum());
    }

    @Test
    @DisplayName("findAllByOrderByModifiedAtDesc")
    public void findAllByOrderByModifiedAtDescTest() {
        // given
        Product product = product();
        Product product2 = product2();
        productRepository.save(product);
        productRepository.save(product2);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<Product> findProductList = productRepository.findAllByOrderByModifiedAtDesc(pageable);

        // then
        assertEquals(2, findProductList.size());
    }

    private Product product() {
        return Product.builder()
                .productId(1L)
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();
    }

    private Product product2() {
        return Product.builder()
                .productId(2L)
                .productName("배추")
                .productInfo("신선한 배추")
                .productPrice(10000)
                .leftProduct(50)
                .productEnum(ProductEnum.SELLING)
                .build();
    }
}
