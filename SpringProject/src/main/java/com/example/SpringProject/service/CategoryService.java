package com.example.SpringProject.service;

import com.example.SpringProject.model.Category;
import org.springframework.http.ResponseEntity;


public interface CategoryService {
    ResponseEntity<Object> createCategory(Category category);
    ResponseEntity<Object> updateCategory(int category_id,Category category);
    ResponseEntity<Object> deleteCategory(int category_id);
    ResponseEntity<Object> getCategory(int category_id);
    ResponseEntity<Object> getAllCategories();
}
