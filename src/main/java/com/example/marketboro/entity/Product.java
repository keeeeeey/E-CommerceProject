package com.example.marketboro.entity;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String productname;

    @Column(nullable = false)
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

}
