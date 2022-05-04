package com.example.marketboro.service;

import com.example.marketboro.dto.request.CartRequestDto.AddCartDto;
import com.example.marketboro.dto.request.CartRequestDto.DeleteCartDto;
import com.example.marketboro.dto.request.CartRequestDto.UpdateCartDto;
import com.example.marketboro.dto.request.CommonDto.IdDto;
import com.example.marketboro.dto.response.CartProductResponseDto;
import com.example.marketboro.entity.Cart;
import com.example.marketboro.entity.CartProduct;
import com.example.marketboro.entity.Product;
import com.example.marketboro.entity.ProductEnum;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.cart.CartProductRepository;
import com.example.marketboro.repository.cart.CartRepository;
import com.example.marketboro.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR));
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_EXISTENCE_ERROR));

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
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_EXISTENCECART_ERROR));

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

    @Transactional(readOnly = true)
    public List<CartProductResponseDto> getAllCartProduct(Long userId, int start) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR));
        Pageable pageable = PageRequest.of(start, 10);
        List<CartProductResponseDto> responseDto = cartProductRepository.findCartProductByCartId(cart.getId(), pageable);
        return responseDto;
    }

    private void productCountValidation(Product product, int productcount) {
        if (product.getProductEnum().equals(ProductEnum.SOLDOUT)) {
            throw new ErrorCustomException(ErrorCode.SOLD_OUT_ERROR);
        }

        if (productcount > product.getLeftproduct()) {
            throw new ErrorCustomException(ErrorCode.SHORTAGE_PRODUCT_ERROR);
        }
    }

}
