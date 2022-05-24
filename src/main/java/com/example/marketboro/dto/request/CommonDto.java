package com.example.marketboro.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommonDto {

    @Getter
    @NoArgsConstructor
    public static class IdDto {
        private Long id;

        public IdDto(final Long id) {
            this.id = id;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class OrderDto {
        private Long productId;
        private Long cartProductId;
        private int productCount;

        public OrderDto(final Long productId, final Long cartProductId, final int productCount) {
            this.productId = productId;
            this.cartProductId = cartProductId;
            this.productCount = productCount;
        }
    }
}
