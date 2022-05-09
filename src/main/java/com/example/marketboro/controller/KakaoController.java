package com.example.marketboro.controller;

import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.security.UserDetailsImpl;
import com.example.marketboro.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
@Log
public class KakaoController {

    private final KakaoPayService kakaoPayService;

    @GetMapping("/kakaoPay")
    public void kakaoPayGet() {

    }

    @PostMapping("/kakaoPay")
    public String kakaoPay(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           HttpServletRequest request) {
        if (userDetails != null) {
            log.info("kakaoPay post............................................");

            return "redirect:" + kakaoPayService.kakaoPayReady(userDetails, request);
        }

        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);

    }

//    @GetMapping("/kakaoPaySuccess")
//    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
//        log.info("kakaoPaySuccess get............................................");
//        log.info("kakaoPaySuccess pg_token : " + pg_token);
//
//        model.addAttribute("info", kakaoPayService.kakaoPayInfo(pg_token));
//
//    }
}
