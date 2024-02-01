package com.example.SpringProject.service.impl;

import com.example.SpringProject.model.Category;
import com.example.SpringProject.repository.CategoryRepository;
import com.example.SpringProject.service.CategoryService;
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
    public ResponseEntity<Object> createCategory(Category category) {
        if(!category.getCategory_name().isEmpty()){
            List<String> categoryNames = categoryRepository.findAll().stream().map(Category::getCategory_name).toList();
            if(!categoryNames.contains(category.getCategory_name())){
                categoryRepository.save(category);
                return ResponseEntity.status(HttpStatus.OK).body("Category created successfully.");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The name already exist!");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The field is empty!");
        }
    }

    @Override
    public ResponseEntity<Object> updateCategory(int categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        List<String> categoryNames = categoryRepository.findAll().stream().map(Category::getCategory_name).toList();
        if(existingCategory==null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such category with the given ID!");
        }
        if(!category.getCategory_name().isEmpty()) {
                if(!categoryNames.contains(category.getCategory_name())){
                    existingCategory.setCategory_name(category.getCategory_name());
                    categoryRepository.save(existingCategory);
                    return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully.");
                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category name already exists!");
                }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field is empty!");
    }

    @Override
    public ResponseEntity<Object> deleteCategory(int category_id) {
        if (categoryRepository.findById(category_id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category_id does not exist!");
        }

        categoryRepository.deleteById(category_id);
        return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully.");
    }


    @Override
    public ResponseEntity<Object> getCategory(int category_id) {
        Category existingCategory = categoryRepository.findById(category_id).orElse(null);

        if(existingCategory==null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such category with the given ID!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success!");

    }

    @Override
    public ResponseEntity<Object> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body("Success!");
    }

}
