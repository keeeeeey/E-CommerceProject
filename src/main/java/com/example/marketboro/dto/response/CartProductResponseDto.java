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
    public CartProductResponseDto(final Long cartProductId, final Long productId, final String productName,
                                  final String productInfo, final int productPrice, final int productCount) {
        this.cartProductId = cartProductId;
        this.productId = productId;
        this.productName = productName;
        this.productInfo = productInfo;
        this.productPrice = productPrice;
        this.productCount = productCount;
    }

}
