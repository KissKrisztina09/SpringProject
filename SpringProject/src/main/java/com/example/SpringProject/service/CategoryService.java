package com.example.SpringProject.service;

import com.example.SpringProject.model.ApiResponse;
import com.example.SpringProject.model.Category;
import org.springframework.http.ResponseEntity;


public interface CategoryService {
    ResponseEntity<ApiResponse<Object>> createCategory(Category category);
    ResponseEntity<ApiResponse<Object>> updateCategory(int category_id,Category category);
    ResponseEntity<ApiResponse<Object>> deleteCategory(int category_id);
    ResponseEntity<ApiResponse<Object>> getCategory(int category_id);
    ResponseEntity<ApiResponse<Object>> getAllCategories();
}
