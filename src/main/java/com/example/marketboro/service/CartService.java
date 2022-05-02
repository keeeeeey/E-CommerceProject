package com.example.marketboro.service;

import com.example.marketboro.dto.request.CartRequestDto.AddCartDto;
import com.example.marketboro.dto.request.CartRequestDto.DeleteCartDto;
import com.example.marketboro.dto.request.CartRequestDto.UpdateCartDto;
import com.example.marketboro.dto.request.CommonDto.IdDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

    @Transactional
    public Long addCart(Long userId, AddCartDto requestDto) {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        productCountValidation(product, requestDto.getProductcount());

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .productcount(requestDto.getProductcount())
                .build();
        CartProduct saveCartProduct = cartProductRepository.save(cartProduct);
        return saveCartProduct.getId();
    }

    @Transactional
    public Long updateCart(UpdateCartDto requestDto) {
        CartProduct cartProduct = cartProductRepository.findById(requestDto.getCartProductId())
                .orElseThrow(() -> new RuntimeException("해당 상품이 장바구니에 존재하지 않습니다."));

        productCountValidation(cartProduct.getProduct(), requestDto.getProductcount());

        cartProduct.updateCartProduct(requestDto);
        return cartProduct.getId();
    }

    @Transactional
    public void deleteCart(DeleteCartDto requestDto) {
        List<IdDto> cartProductIdDtoList = requestDto.getCartProductIdDtoList();
        cartProductIdDtoList.forEach((cartProductIdDto) -> {
            cartProductRepository.deleteById(cartProductIdDto.getId());
        });

    }

    private void productCountValidation(Product product, int productcount) {
        if (product.getProductEnum().equals(ProductEnum.SOLDOUT)) {
            throw new RuntimeException("상품이 품절되었습니다.");
        }

        if (productcount > product.getLeftproduct()) {
            throw new RuntimeException("재고가 부족합니다.");
        }
    }

}
