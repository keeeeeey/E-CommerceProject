package com.example.marketboro.repository.cart;

import com.example.marketboro.dto.response.CartProductResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.marketboro.entity.QCartProduct.cartProduct;
import static com.example.marketboro.entity.QProduct.product;

@RequiredArgsConstructor
public class CartProductRepositoryImpl implements CartProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CartProductResponseDto> findCartProductByCartId(Long cartId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(CartProductResponseDto.class,
                    cartProduct.id,
                    product.id,
                    product.productName,
                    product.productInfo,
                    product.productPrice,
                    cartProduct.productCount
                ))
                .from(cartProduct)
                .join(cartProduct.product, product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(cartProduct.cart.id.eq(cartId))
                .groupBy(cartProduct.id)
                .orderBy(cartProduct.modifiedAt.desc())
                .fetch();
    }
}
