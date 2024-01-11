package com.example.SpringProject.controller;

import com.example.SpringProject.model.Category;
import com.example.SpringProject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{category_id}")
    public Category getCategoryDetails(@PathVariable("category_id") int category_id){
        return categoryService.getCategory(category_id);
    }

    @GetMapping()
    public List<Category> getAllCategoryDetails(){
        return categoryService.getAllCategories();
    }

    @PostMapping()
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        if(categoryService.createCategory(category)){
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully!");
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Category already exist or some field is missing!");
    }

    @PutMapping("edit/{category_id}")
    public ResponseEntity<String> updateCategoryDetails(@PathVariable("category_id") int category_id, @RequestBody Category category){
        if(categoryService.updateCategory(category_id, category)){
            return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CategoryId does not exist or CategoryName already exist!");
    }

    @DeleteMapping("{category_id}")
    public ResponseEntity<String> deleteCategoryDetails(@PathVariable("category_id") int category_id){
        if(categoryService.deleteCategory(category_id)){
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CategoryId does not exist!");
    }
}
