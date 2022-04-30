package com.example.marketboro.service;

import com.example.marketboro.dto.request.CartRequestDto;
import com.example.marketboro.dto.request.CartRequestDto.AddOrUpdateCartDto;
import com.example.marketboro.dto.request.CartRequestDto.DeleteCartDto;
import com.example.marketboro.entity.Cart;
import com.example.marketboro.entity.CartProduct;
import com.example.marketboro.entity.Product;
import com.example.marketboro.entity.ProductEnum;
import com.example.marketboro.repository.CartProductRepository;
import com.example.marketboro.repository.CartRepository;
import com.example.marketboro.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

    @Transactional
    public Long addCart(Long userId, AddOrUpdateCartDto requestDto) {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        if (product.getProductEnum().equals(ProductEnum.SOLDOUT)) {
            throw new RuntimeException("상품의 재고가 없습니다.");
        }

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .productcount(requestDto.getProductcount())
                .build();
        CartProduct saveCartProduct = cartProductRepository.save(cartProduct);
        return saveCartProduct.getId();
    }

    @Transactional
    public Long updateCart(Long userId, AddOrUpdateCartDto requestDto) {
        Cart cart = cartRepository.findByUserId(userId);
        CartProduct cartProduct = cartProductRepository
                .findByCartIdAndProductId(cart.getId(), requestDto.getProductId());

        cartProduct.updateCartProduct(requestDto);
        return cartProduct.getId();
    }

    @Transactional
    public void deleteCart(Long userId, DeleteCartDto requestDto) {
        Cart cart = cartRepository.findByUserId(userId);
        cartProductRepository.deleteByCartIdAndProductId(cart.getId(), requestDto.getProductId());
    }

}
