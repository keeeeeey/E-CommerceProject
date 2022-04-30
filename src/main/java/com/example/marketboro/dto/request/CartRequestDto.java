package com.example.marketboro.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartRequestDto {

    @Getter
    @NoArgsConstructor
    public static class AddOrUpdateCartDto {
        private Long productId;
        private int productcount;
    }

    @Getter
    @NoArgsConstructor
    public static class DeleteCartDto {
        private Long productId;
    }

}
