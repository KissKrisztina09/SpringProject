package com.example.SpringProject.service.impl;

import com.example.SpringProject.model.Category;
import com.example.SpringProject.repository.CategoryRepository;
import com.example.SpringProject.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplement implements CategoryService {

    CategoryRepository categoryRepository;

    public CategoryServiceImplement(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean createCategory(Category category) {
        if(!category.getCategory_name().isEmpty()){
            List<String> categoryNames = categoryRepository.findAll().stream().map(Category::getCategory_name).toList();
            if(!categoryNames.contains(category.getCategory_name())){
                categoryRepository.save(category);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public Boolean updateCategory(int categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);

        if(!category.getCategory_name().isEmpty() && existingCategory!=null) {
            existingCategory.setCategory_name(category.getCategory_name());
            categoryRepository.save(existingCategory);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteCategory(int category_id) {
        if(categoryRepository.findById(category_id).orElse(null) == null){
            return false;
        }
        categoryRepository.deleteById(category_id);
        return true;
    }

    @Override
    public Category getCategory(int category_id) {
        return categoryRepository.findById(category_id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + category_id));

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
