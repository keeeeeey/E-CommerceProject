package com.example.marketboro.dto.request;

import com.example.marketboro.dto.request.CommonDto.IdDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartRequestDto {

    @Getter
    @NoArgsConstructor
    public static class AddCartDto {
        private Long productId;
        private int productCount;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateCartDto {
        private Long cartProductId;
        private int productCount;
    }

    @Getter
    @NoArgsConstructor
    public static class DeleteCartDto {
        private List<IdDto> cartProductIdDtoList;
    }

}
