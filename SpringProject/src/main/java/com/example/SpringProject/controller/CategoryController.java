package com.example.SpringProject.controller;

import com.example.SpringProject.model.Category;
import com.example.SpringProject.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
    
    @GetMapping("/{category_id}")
    public ResponseEntity<Object> getCategoryDetails(@PathVariable("category_id") int categoryId){
        return categoryService.selectCategory(categoryId);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllCategories(){
       return categoryService.getAllCategories();
    }


    @PutMapping("update/{category_id}")
    public ResponseEntity<Object> updateCategoryDetails(@PathVariable("category_id") int categoryId, @RequestBody Category category) {
       return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping("/delete/{category_id}")
    public ResponseEntity<Object> deleteCategoryDetails(@PathVariable("category_id") int categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
