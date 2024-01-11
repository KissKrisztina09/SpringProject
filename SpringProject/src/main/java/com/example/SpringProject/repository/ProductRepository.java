package com.example.SpringProject.repository;

import com.example.SpringProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findByCategoryId(int category_id);
}
