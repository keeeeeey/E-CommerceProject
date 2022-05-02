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
        private int productcount;

        public OrderDto(final Long productId, final int productcount) {
            this.productId = productId;
            this.productcount = productcount;
        }
    }
}
