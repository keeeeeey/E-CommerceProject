package com.example.marketboro.repository;

import com.example.marketboro.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
