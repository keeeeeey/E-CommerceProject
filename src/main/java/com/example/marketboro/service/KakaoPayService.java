package com.example.marketboro.service;

import com.example.marketboro.dto.KakaoOrderDto;
import com.example.marketboro.dto.KakaoPayReadyVo;
import com.example.marketboro.entity.OrderProduct;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.order.OrderProductRepository;
import com.example.marketboro.repository.order.OrderRepository;
import com.example.marketboro.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log
public class KakaoPayService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVo kakaoPayReadyVo;

    public String kakaoPayReady(UserDetailsImpl userDetails, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long orderId = (Long) session.getAttribute("orderId");

        int totalPrice = 0;
        int totalQty = 0;

        List<OrderProduct> orderProductList = orderProductRepository.findAllByOrderId(orderId);

        for (OrderProduct orderProduct : orderProductList) {
            totalPrice = totalPrice + (orderProduct.getProduct().getProductprice() * orderProduct.getProductcount());
            totalQty = totalQty + orderProduct.getProductcount();
        }

        KakaoOrderDto orderDto = KakaoOrderDto.builder()
                .orderId(orderId)
                .userId(userDetails.getUser().getId())
                .totalPrice(totalPrice)
                .totalQty(totalQty)
                .build();

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "admin key를 넣어주세요~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", orderDto.getOrderId().toString());
        params.add("partner_user_id", orderDto.getUserId().toString());
        params.add("item_name", orderDto.getOrderId().toString());
        params.add("quantity", Integer.toString(orderDto.getTotalQty()));
        params.add("total_amount", Integer.toString(orderDto.getTotalPrice()));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8080/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8080/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVo = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVo.class);

            log.info("" + kakaoPayReadyVo);

            return kakaoPayReadyVo.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/pay";

    }
}
