package com.frontend.Ketthuchocphan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frontend.Ketthuchocphan.entity.Product;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByActiveTrue();
}
