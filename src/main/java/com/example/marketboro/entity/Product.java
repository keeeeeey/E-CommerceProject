package com.example.marketboro.entity;

import com.example.marketboro.dto.request.ProductRequestDto.UpdateProduct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String productName;

    private String productInfo;

    @Column(nullable = false)
    private int productPrice;

    @Column(nullable = false)
    private int leftProduct;

    @Enumerated(EnumType.STRING)
    private ProductEnum productEnum;

    @Builder
    public Product(final String productName, final String productInfo,
                   final int productPrice, final int leftProduct, final ProductEnum productEnum) {
        this.productName = productName;
        this.productInfo = productInfo;
        this.productPrice = productPrice;
        this.leftProduct = leftProduct;
        this.productEnum = productEnum;
    }

    public void updateProduct(UpdateProduct requestDto) {
        this.productName = requestDto.getProductName();
        this.productInfo = requestDto.getProductInfo();
        this.productPrice = requestDto.getProductPrice();
        this.leftProduct = requestDto.getLeftProduct();
        this.productEnum = ProductEnum.valueOf(requestDto.getProductEnum());
    }

    public void plusLeftProduct(int plusLeftProduct) {
        this.leftProduct += plusLeftProduct;
    }

    public void minusLeftProduct(int minusLeftProduct) {
        this.leftProduct -= minusLeftProduct;
    }

}
