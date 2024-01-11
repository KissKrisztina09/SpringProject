package com.example.SpringProject.service.impl;

import com.example.SpringProject.model.ApiResponse;
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
    public ResponseEntity<ApiResponse<Object>> createCategory(Category category) {
        if(!category.getCategory_name().isEmpty()){
            List<String> categoryNames = categoryRepository.findAll().stream().map(Category::getCategory_name).toList();
            if(!categoryNames.contains(category.getCategory_name())){
                categoryRepository.save(category);
                ApiResponse<Object> successResponse = new ApiResponse<>(true, "Category created successfully.", HttpStatus.OK.value(), category);
                return ResponseEntity.status(HttpStatus.OK).body(successResponse);
            }else{
                ApiResponse<Object> errorResponse = new ApiResponse<>(false, "The name already exist!",HttpStatus.BAD_REQUEST.value(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        }else{
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "The field is empty!",HttpStatus.BAD_REQUEST.value(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> updateCategory(int categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        List<String> categoryNames = categoryRepository.findAll().stream().map(Category::getCategory_name).toList();
        if(existingCategory==null ) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "There is no such category with the given ID!",HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        if(!category.getCategory_name().isEmpty()) {
                if(!categoryNames.contains(category.getCategory_name())){
                    existingCategory.setCategory_name(category.getCategory_name());
                    categoryRepository.save(existingCategory);
                    ApiResponse<Object> successResponse = new ApiResponse<>(true, "Category updated successfully.", HttpStatus.OK.value(), existingCategory);
                    return ResponseEntity.status(HttpStatus.OK).body(successResponse);
                }else{
                    ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Category name already exists!", HttpStatus.BAD_REQUEST.value(), null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
        }
        ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Field is empty!",HttpStatus.BAD_REQUEST.value(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> deleteCategory(int category_id) {
        if (categoryRepository.findById(category_id).isEmpty()) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Category_id does not exist!", HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        categoryRepository.deleteById(category_id);
        ApiResponse<Object> successResponse = new ApiResponse<>(true, "Category deleted successfully.", HttpStatus.OK.value(), null);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }


    @Override
    public ResponseEntity<ApiResponse<Object>> getCategory(int category_id) {
        Category existingCategory = categoryRepository.findById(category_id).orElse(null);

        if(existingCategory==null ) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "There is no such category with the given ID!",HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ApiResponse<Object> successResponse = new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), existingCategory);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);

    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();

        ApiResponse<Object> successResponse = new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), categoryList);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

}
