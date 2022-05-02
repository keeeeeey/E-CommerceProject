package com.example.marketboro.entity;

import com.example.marketboro.dto.request.CartRequestDto.UpdateCartDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CartProduct extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int productcount;

    @Builder
    public CartProduct(final Cart cart, final Product product, final int productcount) {
        this.cart = cart;
        this.product = product;
        this.productcount = productcount;
    }

    public void updateCartProduct(UpdateCartDto requestDto) {
        this.productcount = requestDto.getProductcount();
    };

}
