package com.example.marketboro.controller;

import com.example.marketboro.dto.request.CartRequestDto.AddCartDto;
import com.example.marketboro.dto.request.CartRequestDto.UpdateCartDto;
import com.example.marketboro.dto.response.CartProductResponseDto;
import com.example.marketboro.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @InjectMocks
    CartController cartController;

    @Mock
    CartService cartService;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    @DisplayName("장바구니 담기")
    public void addCart() throws Exception {
        // given
        AddCartDto requestDto = new AddCartDto(100L, 5);

        CartProductResponseDto responseDto = CartProductResponseDto.builder()
                .cartProductId(1L)
                .productId(100L)
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .productCount(5)
                .build();

        when(cartService.addCart(any(Long.class), any(AddCartDto.class))).thenReturn(responseDto);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("장바구니 담기"))
                .andDo(print());
    }

    @Test
    @DisplayName("장바구니 수정")
    public void updateCart() throws Exception {
        // given
        UpdateCartDto requestDto = new UpdateCartDto(100L, 10);

        CartProductResponseDto responseDto = CartProductResponseDto.builder()
                .cartProductId(1L)
                .productId(100L)
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .productCount(10)
                .build();

        when(cartService.updateCart(any(UpdateCartDto.class))).thenReturn(responseDto);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("장바구니 수정"))
                .andDo(print());
    }
}
