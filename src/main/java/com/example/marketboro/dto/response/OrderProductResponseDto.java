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
    public OrderProductResponseDto(OrderProduct orderProduct) {
        this.orderProductId = orderProduct.getId();
        this.productId = orderProduct.getProduct().getId();
        this.productname = orderProduct.getProduct().getProductname();
        this.productinfo = orderProduct.getProduct().getProductinfo();
        this.productprice = orderProduct.getProduct().getProductprice();
        this.productcount = orderProduct.getProductcount();
        this.orderStatus = orderProduct.getOrderStatus();
    }
}
