package com.example.marketboro.repository.cart;

import com.example.marketboro.dto.response.CartProductResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartProductRepositoryCustom {
    List<CartProductResponseDto> findCartProductByCartId(Long cartId, Pageable pageable);
}
