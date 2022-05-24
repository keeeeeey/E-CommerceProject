package com.example.marketboro.repository.order;

import com.example.marketboro.dto.response.OrderProductResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.marketboro.entity.QOrderProduct.orderProduct;
import static com.example.marketboro.entity.QProduct.product;

@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderProductResponseDto> findOrderProductByOrderId(Long orderId) {
        return queryFactory
                .select(Projections.constructor(OrderProductResponseDto.class,
                    orderProduct.id,
                    product.id,
                    product.productName,
                    product.productInfo,
                    product.productPrice,
                    orderProduct.productCount,
                    orderProduct.orderStatus
                ))
                .from(orderProduct)
                .join(orderProduct.product, product)
                .where(orderProduct.order.id.eq(orderId))
                .groupBy(orderProduct.id)
                .orderBy(orderProduct.order.createdAt.desc())
                .fetch();
    }
}
