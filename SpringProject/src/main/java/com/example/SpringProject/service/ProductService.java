package com.example.SpringProject.service;

import com.example.SpringProject.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Object> createProduct(Product product);
    ResponseEntity<Object> updateProduct(int product_id,Product product);
    ResponseEntity<Object> deleteProduct(int product_id);
    ResponseEntity<Object> getProduct(int product_id);
    ResponseEntity<Object> getAllProducts();
    ResponseEntity<Object> getProductsByCategoryId(int category_id);
}
