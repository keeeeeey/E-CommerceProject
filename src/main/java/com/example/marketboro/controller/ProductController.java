package com.example.marketboro.controller;

import com.example.marketboro.dto.Success;
import com.example.marketboro.dto.request.ProductRequestDto.CreateProduct;
import com.example.marketboro.dto.request.ProductRequestDto.UpdateProduct;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.security.UserDetailsImpl;
import com.example.marketboro.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Secured(UserRoleEnum.Authority.ADMIN)
    @PostMapping("/api/product")
    public ResponseEntity<Success> createProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @Valid @RequestBody CreateProduct requestDto) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("상품 등록",
                    productService.createProduct(requestDto)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @PatchMapping("/api/product/{productId}")
    public ResponseEntity<Success> updateProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @Valid @RequestBody UpdateProduct requestDto,
                                                 @PathVariable Long productId) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("상품 정보 수정",
                    productService.updateProduct(requestDto, productId)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/api/product/{productId}")
    public ResponseEntity<Success> deleteProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long productId) {
        if (userDetails != null) {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(new Success("상품 삭제", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @GetMapping("/api/product")
    public ResponseEntity<Success> getAllProduct(@RequestParam("start") int start) {
        return new ResponseEntity<>(new Success("전체 상품 조회",
                productService.getAllProduct(start)), HttpStatus.OK);
    }

    @GetMapping("/api/product/{productId}")
    public ResponseEntity<Success> getProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(new Success("단일 상품 조회",
                productService.getProduct(productId)), HttpStatus.OK);
    }
}
