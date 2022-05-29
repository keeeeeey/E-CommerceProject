package com.example.marketboro.controller;

import com.example.marketboro.dto.request.CartRequestDto;
import com.example.marketboro.dto.request.CommonDto;
import com.example.marketboro.dto.request.CommonDto.IdDto;
import com.example.marketboro.dto.request.CommonDto.OrderDto;
import com.example.marketboro.dto.request.OrderRequestDto;
import com.example.marketboro.dto.request.OrderRequestDto.CancelOrderDto;
import com.example.marketboro.dto.request.OrderRequestDto.CreateOrderDto;
import com.example.marketboro.dto.response.CartProductResponseDto;
import com.example.marketboro.dto.response.OrderProductResponseDto;
import com.example.marketboro.entity.OrderStatus;
import com.example.marketboro.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    MockMvc mockMvc;

    MockHttpServletRequest request = new MockHttpServletRequest();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    @DisplayName("상품 주문 접수")
    public void createOrder() throws Exception {
        // given
        List<OrderDto> orderList = new ArrayList<>();
        OrderDto requestDto = new OrderDto(100L, 100L, 10);
        orderList.add(requestDto);
        CreateOrderDto requestDtoList = new CreateOrderDto(orderList);

        List<OrderProductResponseDto> responseDtoList = new ArrayList<>();
        OrderProductResponseDto responseDto = OrderProductResponseDto.builder()
                .orderProductId(1L)
                .productId(100L)
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .productCount(5)
                .orderStatus(OrderStatus.주문접수)
                .build();
        responseDtoList.add(responseDto);

        when(orderService.createOrder(any(Long.class), any(CreateOrderDto.class), any(MockHttpServletRequest.class))).thenReturn(responseDtoList);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtoList))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("상품 주문 접수"))
                .andDo(print());
    }

    @Test
    @DisplayName("주문취소")
    public void cancelOrderTest() throws Exception {
        // given
        List<IdDto> orderList = new ArrayList<>();
        IdDto requestDto = new IdDto(1L);
        orderList.add(requestDto);
        CancelOrderDto requestDtoList = new CancelOrderDto(orderList);

        List<OrderProductResponseDto> responseDtoList = new ArrayList<>();
        OrderProductResponseDto responseDto = OrderProductResponseDto.builder()
                .orderProductId(1L)
                .productId(100L)
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .productCount(5)
                .orderStatus(OrderStatus.주문취소)
                .build();
        responseDtoList.add(responseDto);

        when(orderService.cancelOrder(any(CancelOrderDto.class))).thenReturn(responseDtoList);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtoList))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("상품 주문 취소"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 배송 완료")
    public void finishDelivery() throws Exception {
        // given
        Long orderProductId = 1L;

        OrderProductResponseDto responseDto = OrderProductResponseDto.builder()
                .orderProductId(1L)
                .productId(100L)
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .productCount(5)
                .orderStatus(OrderStatus.배송완료)
                .build();

        when(orderService.finishDelivery(any(Long.class))).thenReturn(responseDto);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/order/" + orderProductId)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("상품 배송 완료"))
                .andDo(print());
    }
}
