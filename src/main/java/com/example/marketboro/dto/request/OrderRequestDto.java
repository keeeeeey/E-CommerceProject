package com.example.marketboro.dto.request;

import com.example.marketboro.dto.request.CommonDto.IdDto;
import com.example.marketboro.dto.request.CommonDto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderDto {
        private List<OrderDto> orderList;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CancelOrderDto {
        private List<IdDto> cancelOrderList;
    }
}
