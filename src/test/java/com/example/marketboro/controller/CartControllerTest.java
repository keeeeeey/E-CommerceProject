package com.example.marketboro.controller;

import com.example.marketboro.dto.request.CartRequestDto.AddCartDto;
import com.example.marketboro.dto.request.CartRequestDto.UpdateCartDto;
import com.example.marketboro.dto.response.CartProductResponseDto;
import com.example.marketboro.entity.*;
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

//    @Test
//    @DisplayName("장바구니 담기")
//    public void addCart() throws Exception {
//        // given
//        AddCartDto requestDto = new AddCartDto(100L, 5);
//
//        CartProductResponseDto responseDto = CartProductResponseDto.builder()
//                .cartProductId(1L)
//                .productId(100L)
//                .productName("당근")
//                .productInfo("신선한 당근")
//                .productPrice(5000)
//                .productCount(5)
//                .build();
//
//        when(cartService.addCart(any(Long.class), any(AddCartDto.class))).thenReturn(responseDto);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/cart")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto))
//        );
//
//        // then
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("result").value("success"))
//                .andExpect(jsonPath("msg").value("장바구니 담기"))
//                .andDo(print());
//    }

    @Test
    @DisplayName("장바구니 수정")
    public void updateCart() throws Exception {
        // given
        UpdateCartDto requestDto = new UpdateCartDto(100L, 10);

        User user = user();

        Product product = product();

        Cart cart = Cart.builder()
                .user(user)
                .build();

        CartProduct cartProduct = cartProduct(cart, product);

        CartProductResponseDto responseDto = CartProductResponseDto.builder()
                .cartProduct(cartProduct)
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

    private User user() {
        return User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();
    }

    private Product product() {
        return Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();
    }

    private CartProduct cartProduct(Cart cart, Product product) {
        return CartProduct.builder()
                .cart(cart)
                .product(product)
                .productCount(10)
                .build();
    }
}
