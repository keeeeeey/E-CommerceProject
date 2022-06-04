package com.example.marketboro.controller;

import com.example.marketboro.dto.Success;
import com.example.marketboro.dto.request.OrderRequestDto.CancelOrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CreateOrderDto;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.security.UserDetailsImpl;
import com.example.marketboro.service.OrderService;
import com.example.marketboro.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<Success> createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody CreateOrderDto requestDto,
                                               HttpServletRequest request) {
        Validator.authenticationValidator(userDetails);
        return new ResponseEntity<>(new Success("상품 주문 접수",
                orderService.createOrder(userDetails.getUser().getId(), requestDto, request)), HttpStatus.OK);
    }

    @PatchMapping("/api/order")
    public ResponseEntity<Success> cancelOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody CancelOrderDto requestDto) {
        Validator.authenticationValidator(userDetails);
        return new ResponseEntity<>(new Success("상품 주문 취소",
                orderService.cancelOrder(requestDto)), HttpStatus.OK);
    }

    @PatchMapping("/api/order/{orderProductId}")
    public ResponseEntity<Success> finishDelivery(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long orderProductId) {
        Validator.authenticationValidator(userDetails);
        return new ResponseEntity<>(new Success("상품 배송 완료",
                orderService.finishDelivery(orderProductId)), HttpStatus.OK);
    }

    @GetMapping("/api/order")
    public ResponseEntity<Success> getAllOrder(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Validator.authenticationValidator(userDetails);
        return new ResponseEntity<>(new Success("전체 주문 조회",
                orderService.getAllOrder(userDetails.getUser().getId())), HttpStatus.OK);
    }
}
