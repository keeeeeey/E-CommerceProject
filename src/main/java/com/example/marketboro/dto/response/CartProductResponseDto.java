package com.example.marketboro.dto.response;

import com.example.marketboro.entity.CartProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartProductResponseDto {
    private Long cartProductId;
    private Long productId;
    private String productName;
    private String productInfo;
    private int productPrice;
    private int productCount;

    @Builder
    public CartProductResponseDto(final CartProduct cartProduct) {
        this.cartProductId = cartProduct.getId();
        this.productId = cartProduct.getProduct().getId();
        this.productName = cartProduct.getProduct().getProductName();
        this.productInfo = cartProduct.getProduct().getProductInfo();
        this.productPrice = cartProduct.getProduct().getProductPrice();
        this.productCount = cartProduct.getProductCount();
    }

}
