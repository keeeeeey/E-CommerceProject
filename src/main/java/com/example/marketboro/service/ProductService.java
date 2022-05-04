package com.example.marketboro.service;

import com.example.marketboro.dto.request.ProductRequestDto.CreateProduct;
import com.example.marketboro.dto.request.ProductRequestDto.UpdateProduct;
import com.example.marketboro.entity.Product;
import com.example.marketboro.entity.ProductEnum;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.cart.CartProductRepository;
import com.example.marketboro.repository.order.OrderProductRepository;
import com.example.marketboro.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

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
        cartProductRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_EXISTENCE_ERROR));
        return product;
    }
}
