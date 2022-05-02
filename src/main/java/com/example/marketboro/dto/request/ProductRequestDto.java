package com.example.marketboro.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class ProductRequestDto {

    @Getter
    @NoArgsConstructor
    public static class CreateProduct {

        @NotEmpty(message = "상품명을 입력해주세요.")
        private String productname;

        private String productinfo;

        @NotEmpty(message = "상품의 가격을 입력해주세요.")
        private int productprice;

        @NotEmpty(message = "상품의 재고를 입력해주세요.")
        private int leftproduct;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateProduct {

        @NotEmpty(message = "상품명을 입력해주세요.")
        private String productname;

        private String productinfo;

        @NotEmpty(message = "상품의 가격을 입력해주세요.")
        private int productprice;

        @NotEmpty(message = "상품의 재고를 입력해주세요.")
        private int leftproduct;

        @NotEmpty(message = "상품의 재고상태를 입력해주세요.")
        private String productEnum;
    }
}
