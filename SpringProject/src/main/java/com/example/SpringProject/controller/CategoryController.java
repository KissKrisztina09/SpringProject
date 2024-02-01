package com.example.SpringProject.controller;

import com.example.SpringProject.model.Category;
import com.example.SpringProject.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{category_id}")
    public ResponseEntity<Object> getCategoryDetails(@PathVariable("category_id") int category_id){
        return categoryService.getCategory(category_id);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllCategoryDetails(){
        return categoryService.getAllCategories();
    }

    @PostMapping()
    public ResponseEntity<Object> createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);

    }

    @PutMapping("edit/{category_id}")
    public ResponseEntity<Object> updateCategoryDetails(@PathVariable("category_id") int category_id, @RequestBody Category category) {
        return categoryService.updateCategory(category_id, category);
    }

    @DeleteMapping("{category_id}")
    public ResponseEntity<Object> deleteCategoryDetails(@PathVariable("category_id") int category_id) {
        return categoryService.deleteCategory(category_id);
    }
}
