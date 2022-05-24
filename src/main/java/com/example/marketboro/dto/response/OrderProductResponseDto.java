package com.example.marketboro.dto.response;

import com.example.marketboro.entity.OrderProduct;
import com.example.marketboro.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductResponseDto {

    private Long orderProductId;
    private Long productId;
    private String productName;
    private String productInfo;
    private int productPrice;
    private int productCount;
    private OrderStatus orderStatus;

    @Builder
    public OrderProductResponseDto(final Long orderProductId, final Long productId, final String productName,
                                   final String productInfo, final int productPrice, final int productCount,
                                   final OrderStatus orderStatus) {
        this.orderProductId = orderProductId;
        this.productId = productId;
        this.productName = productName;
        this.productInfo = productInfo;
        this.productPrice = productPrice;
        this.productCount = productCount;
        this.orderStatus = orderStatus;
    }
}
