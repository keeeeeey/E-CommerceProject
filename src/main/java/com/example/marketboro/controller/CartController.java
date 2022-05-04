package com.example.marketboro.controller;

import com.example.marketboro.dto.Success;
import com.example.marketboro.dto.request.CartRequestDto.AddCartDto;
import com.example.marketboro.dto.request.CartRequestDto.DeleteCartDto;
import com.example.marketboro.dto.request.CartRequestDto.UpdateCartDto;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.security.UserDetailsImpl;
import com.example.marketboro.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    public ResponseEntity<Success> addCart(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody AddCartDto requestDto) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("장바구니 담기",
                    cartService.addCart(userDetails.getUser().getId(), requestDto)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/api/cart")
    public ResponseEntity<Success> updateCart(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestBody UpdateCartDto requestDto) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("장바구니 수정",
                    cartService.updateCart(requestDto)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/api/cart")
    public ResponseEntity<Success> deleteCart(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestBody DeleteCartDto requestDto) {
        if (userDetails != null) {
            cartService.deleteCart(requestDto);
            return new ResponseEntity<>(new Success("장바구니 삭제", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @GetMapping("/api/cart")
    public ResponseEntity<Success> getAllCartProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestParam("start") int start) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("장바구니 조회",
                    cartService.getAllCartProduct(userDetails.getUser().getId(), start)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
}
