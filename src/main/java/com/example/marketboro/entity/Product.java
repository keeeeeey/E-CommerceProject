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
    private String productname;

    private String productinfo;

    @Column(nullable = false)
    private int productprice;

    @Column(nullable = false)
    private int leftproduct;

    @Enumerated(EnumType.STRING)
    private ProductEnum productEnum;

    @Builder
    public Product(final String productname, final String productinfo, final int productprice,
                   final int leftproduct, final ProductEnum productEnum) {
        this.productname = productname;
        this.productinfo = productinfo;
        this.productprice = productprice;
        this.leftproduct = leftproduct;
        this.productEnum = productEnum;
    }

    public void updateProduct(UpdateProduct requestDto) {
        this.productname = requestDto.getProductname();
        this.productinfo = requestDto.getProductinfo();
        this.productprice = requestDto.getProductprice();
        this.leftproduct = requestDto.getLeftproduct();
        this.productEnum = ProductEnum.valueOf(requestDto.getProductEnum());
    }

    public void plusLeftProduct(int plusLeftproduct) {
        this.leftproduct += plusLeftproduct;
    }

    public void minusLeftProduct(int minusLeftproduct) {
        this.leftproduct -= minusLeftproduct;
    }

}
