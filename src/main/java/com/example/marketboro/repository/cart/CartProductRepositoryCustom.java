package com.example.marketboro.repository.cart;

import com.example.marketboro.dto.response.CartProductResponseDto;

import java.util.List;

public interface CartProductRepositoryCustom {
    List<CartProductResponseDto> findCartProductByCartId(Long cartId);
}
