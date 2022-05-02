package com.example.marketboro.service;

import com.example.marketboro.dto.request.ProductRequestDto.CreateProduct;
import com.example.marketboro.dto.request.ProductRequestDto.UpdateProduct;
import com.example.marketboro.entity.Product;
import com.example.marketboro.entity.ProductEnum;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.CartProductRepository;
import com.example.marketboro.repository.OrderProductRepository;
import com.example.marketboro.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Long createProduct(CreateProduct requestDto) {
        Product product = Product.builder()
                .productname(requestDto.getProductname())
                .productinfo(requestDto.getProductinfo())
                .productprice(requestDto.getProductprice())
                .leftproduct(requestDto.getLeftproduct())
                .productEnum(ProductEnum.SELLING)
                .build();
        Product saveproduct = productRepository.save(product);
        return saveproduct.getId();
    }

    @Transactional
    public Long updateProduct(UpdateProduct requestDto, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_EXISTENCE_ERROR));
        product.updateProduct(requestDto);
        return product.getId();
    }

    @Transactional
    public void deleteProduct(Long productId) {
//        cartProductRepository.deleteByProductId(productId);
//        orderProductRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }
}
