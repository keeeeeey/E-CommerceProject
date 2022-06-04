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
    public OrderProductResponseDto(OrderProduct orderProduct) {
        this.orderProductId = orderProduct.getId();
        this.productId = orderProduct.getProduct().getId();
        this.productName = orderProduct.getProduct().getProductName();
        this.productInfo = orderProduct.getProduct().getProductInfo();
        this.productPrice = orderProduct.getProduct().getProductPrice();
        this.productCount = orderProduct.getProductCount();
        this.orderStatus = orderProduct.getOrderStatus();
    }
}
