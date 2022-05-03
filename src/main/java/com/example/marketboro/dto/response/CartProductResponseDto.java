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
    public CartProductResponseDto(CartProduct cartProduct) {
        this.cartProductId = cartProduct.getId();
        this.productId = cartProduct.getProduct().getId();
        this.productname = cartProduct.getProduct().getProductname();
        this.productinfo = cartProduct.getProduct().getProductinfo();
        this.productprice = cartProduct.getProduct().getProductprice();
        this.productcount = cartProduct.getProductcount();
    }

}
