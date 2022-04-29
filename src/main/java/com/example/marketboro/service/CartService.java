package com.example.marketboro.service;

import com.example.marketboro.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartProductRepository cartProductRepository;

}
