package com.example.marketboro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_USERNAME_ERROR(400, "V001","이미 사용중인 아이디입니다."),
    ALREADY_NICKNAME_ERROR(400, "V002","이미 사용중인 닉네임입니다."),
    NO_MATCH_PASSWORD_ERROR(400, "V003","비밀번호가 일치하지 않습니다."),
    NO_AUTHENTICATION_ERROR(401, "401-1", "로그인 후 사용가능합니다."),
    TOKEN_EXPIRATION_ERROR(401,"401-2","로그인 정보가 만료되었습니다."),
    EXPIRED_JWT_ERROR(444,"A007", "기존 토큰이 만료되었습니다."),
    REFRESH_EXPIRATION_ERROR(401,"401-3","리프레시 토큰이 만료되었습니다."),
    NO_AUTHORIZATION_ERROR(403,"403-1","접근 권한이 없습니다."),
    NO_MATCH_USER_ERROR(403,"403-2","작성자만 수정 및 삭제가 가능합니다."),
    NO_USER_ERROR(404, "404-1","해당 유저를 찾을 수 없습니다."),
    NO_EXISTENCE_ERROR(400,"P001","존재하지 않는 상품입니다"),
    SOLD_OUT_ERROR(400, "P002", "상품이 품절되었습니다."),
    SHORTAGE_PRODUCT_ERROR(400, "P002", "재고가 부족합니다."),
    NO_EXISTENCECART_ERROR(400, "C001", "해당 상품이 장바구니에 존재하지 않습니다."),
    ALREADY_CANCELORDER_ERROR(400, "O001", "이미 취소된 주문입니다."),
    ALREADY_DELIVERED_ERROR(400, "O002", "이미 배송이 완료된 상품입니다. 주문을 취소할 수 없습니다."),
    NO_ORDERED_ERROR(400, "O003", "주문하신 상품이 아닙니다."),
    TEST(000, "T000", "빌드 자동화 테스트");

    private int statusCode;
    private final String errorCode;
    private final String message;

}