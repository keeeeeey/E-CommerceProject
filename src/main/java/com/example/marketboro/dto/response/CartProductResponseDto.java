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
    private String productname;
    private String productinfo;
    private int productprice;
    private int productcount;

    @Builder
    public CartProductResponseDto(final Long cartProductId, final Long productId, final String productname,
                                  final String productinfo, final int productprice, final int productcount) {
        this.cartProductId = cartProductId;
        this.productId = productId;
        this.productname = productname;
        this.productinfo = productinfo;
        this.productprice = productprice;
        this.productcount = productcount;
    }

}
