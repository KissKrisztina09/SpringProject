package com.example.SpringProject.service;

import com.example.SpringProject.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    public Boolean createCategory(Category category);
    public Boolean updateCategory(int category_id,Category category);
    public Boolean deleteCategory(int category_id);
    public Category getCategory(int category_id);
    public List<Category> getAllCategories();
}
