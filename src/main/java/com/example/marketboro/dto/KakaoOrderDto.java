package com.example.marketboro.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoOrderDto {
    private Long orderId;
    private Long userId;
    private int totalPrice;
    private int totalQty;

    @Builder
    public KakaoOrderDto(final Long orderId, final Long userId, final int totalPrice, final int totalQty) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.totalQty = totalQty;
    }
}
