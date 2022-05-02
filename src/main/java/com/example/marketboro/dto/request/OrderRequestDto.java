package com.example.marketboro.dto.request;

import com.example.marketboro.dto.request.CommonDto.IdDto;
import com.example.marketboro.dto.request.CommonDto.OrderDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderRequestDto {

    @Getter
    @NoArgsConstructor
    public static class CreateOrderDto {
        private List<OrderDto> orderList;
    }

    @Getter
    @NoArgsConstructor
    public static class CancelOrderDto {
        private List<IdDto> cancelOrderList;
    }
}
