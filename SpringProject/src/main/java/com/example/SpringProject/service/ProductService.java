package com.example.SpringProject.service;

import com.example.SpringProject.model.ApiResponse;
import com.example.SpringProject.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<ApiResponse<Object>> createProduct(Product product);
    ResponseEntity<ApiResponse<Object>> updateProduct(int product_id,Product product);
    ResponseEntity<ApiResponse<Object>> deleteProduct(int product_id);
    ResponseEntity<ApiResponse<Object>> getProduct(int product_id);
    ResponseEntity<ApiResponse<Object>> getAllProducts();
    ResponseEntity<ApiResponse<Object>> getAllProductsByCategoryId(int category_id);
}
