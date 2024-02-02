package com.example.SpringProject.service;

import org.springframework.http.ResponseEntity;

import com.example.SpringProject.model.Category;


public interface CategoryService {
    ResponseEntity<Object> createCategory(Category category);
    ResponseEntity<Object> updateCategory(int categoryId,Category category);
    ResponseEntity<Object> deleteCategory(int categoryId);
    ResponseEntity<Object> selectCategory(int categoryId);
    ResponseEntity<Object> getAllCategories();
}
