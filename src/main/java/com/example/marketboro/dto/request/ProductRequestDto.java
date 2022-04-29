package com.example.marketboro.dto.request;

import com.example.marketboro.entity.ProductEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductRequestDto {

    @Getter
    @NoArgsConstructor
    public static class CreateProduct {
        private String productname;
        private String productinfo;
        private int productprice;
        private int leftproduct;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateProduct {
        private String productname;
        private String productinfo;
        private int productprice;
        private int leftproduct;
        private String productEnum;
    }
}
