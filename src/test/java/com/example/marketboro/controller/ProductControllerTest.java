package com.example.marketboro.controller;

import com.example.marketboro.dto.request.ProductRequestDto;
import com.example.marketboro.dto.request.ProductRequestDto.CreateProduct;
import com.example.marketboro.dto.request.ProductRequestDto.UpdateProduct;
import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.entity.Product;
import com.example.marketboro.entity.ProductEnum;
import com.example.marketboro.service.ProductService;
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
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @DisplayName("상품 등록")
    public void createProductTest() throws Exception {
        // given
        CreateProduct requestDto = new CreateProduct(
                "당근",
                "신선한 당근",
                5000,
                100
        );

        Product product = Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(100)
                .productEnum(ProductEnum.SELLING)
                .build();

        when(productService.createProduct(any(CreateProduct.class))).thenReturn(product);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("상품 등록"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 정보 수정")
    public void updateProductTest() throws Exception {
        // given
        Long productId = 100L;

        UpdateProduct requestDto = new UpdateProduct(
                "당근",
                "신선한 당근",
                5000,
                0,
                "SOLDOUT"
        );

        Product product = Product.builder()
                .productName("당근")
                .productInfo("신선한 당근")
                .productPrice(5000)
                .leftProduct(0)
                .productEnum(ProductEnum.SOLDOUT)
                .build();

        when(productService.updateProduct(any(UpdateProduct.class), any(Long.class))).thenReturn(product);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("상품 정보 수정"))
                .andDo(print());
    }
}
