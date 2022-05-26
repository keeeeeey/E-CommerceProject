package com.example.marketboro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProduct {

        @NotEmpty(message = "상품명을 입력해주세요.")
        private String productName;

        private String productInfo;

        @NotNull(message = "상품의 가격을 입력해주세요.")
        private int productPrice;

        @NotNull(message = "상품의 재고를 입력해주세요.")
        private int leftProduct;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateProduct {

        @NotEmpty(message = "상품명을 입력해주세요.")
        private String productName;

        private String productInfo;

        @NotNull(message = "상품의 가격을 입력해주세요.")
        private int productPrice;

        @NotNull(message = "상품의 재고를 입력해주세요.")
        private int leftProduct;

        @NotEmpty(message = "상품의 재고상태를 입력해주세요.")
        private String productEnum;
    }
}
