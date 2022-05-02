package com.example.marketboro.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommonDto {

    @Getter
    @NoArgsConstructor
    public static class CartProductIdDto {
        private Long cartProductId;

        public CartProductIdDto(final Long cartProductId) {
            this.cartProductId = cartProductId;
        }
    }
}
