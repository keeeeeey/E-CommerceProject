package com.example.marketboro.controller;

import com.example.marketboro.dto.Success;
import com.example.marketboro.dto.request.OrderRequestDto.CancelOrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CreateOrderDto;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.security.UserDetailsImpl;
import com.example.marketboro.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<Success> createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody CreateOrderDto requestDto) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("상품 주문 접수",
                    orderService.createOrder(userDetails.getUser().getId(), requestDto)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/api/order")
    public ResponseEntity<Success> cancelOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody CancelOrderDto requestDto) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("상품 주문 취소",
                    orderService.cancelOrder(requestDto)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/api/order/{orderProductId}")
    public ResponseEntity<Success> finishDelivery(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long orderProductId) {
        if (userDetails != null) {
            return new ResponseEntity<>(new Success("상품 배송 완료",
                    orderService.finishDelivery(orderProductId)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
}
