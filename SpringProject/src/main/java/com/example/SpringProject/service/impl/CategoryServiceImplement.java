package com.example.SpringProject.service.impl;

import com.example.SpringProject.DAO.CategoryDAO;
import com.example.SpringProject.model.Category;
import com.example.SpringProject.service.CategoryService;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CategoryServiceImplement implements CategoryService {

    CategoryDAO categoryDAO;

    public CategoryServiceImplement(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }


    //@PostMapping("/insert")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        try {
            categoryDAO.insertCategory(category);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Failed to create category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

  // @GetMapping("/{categoryId}")
    public ResponseEntity<Object> selectCategory(@PathVariable int categoryId) {
        try {
            Category category = categoryDAO.selectCategory(categoryId);
            if (category != null) {
                return new ResponseEntity<>(category, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Category not found with ID: " + categoryId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to select category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PutMapping("/update/{categoryId}")
    public ResponseEntity<Object> updateCategory(int categoryId, Category category) {
        try {
            categoryDAO.updateCategory(categoryId, category);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Failed to update category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   // @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Object> deleteCategory(int categoryId) {
        try{
            categoryDAO.deleteCategory(categoryId);
            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
        }catch (DataAccessException e) {
            return new ResponseEntity<>("Failed to delete category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //@GetMapping()
    public ResponseEntity<Object> getAllCategories() {
        try {
            List<Category> categories = categoryDAO.selectAllCategories();
            if (!categories.isEmpty()) {
                return new ResponseEntity<>(categories, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No categories found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve categories: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
