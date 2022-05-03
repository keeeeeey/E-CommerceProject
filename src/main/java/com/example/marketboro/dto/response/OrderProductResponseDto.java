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
    private String productname;
    private String productinfo;
    private int productprice;
    private int productcount;
    private OrderStatus orderStatus;

    @Builder
    public OrderProductResponseDto(final Long orderProductId, final Long productId, final String productname,
                                   final String productinfo, final int productprice, final int productcount,
                                   final OrderStatus orderStatus) {
        this.orderProductId = orderProductId;
        this.productId = productId;
        this.productname = productname;
        this.productinfo = productinfo;
        this.productprice = productprice;
        this.productcount = productcount;
        this.orderStatus = orderStatus;
    }
}
