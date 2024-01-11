package com.example.SpringProject.controller;


import com.example.SpringProject.model.Product;
import com.example.SpringProject.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{product_id}")
    public Product getProductDetails(@PathVariable("product_id") int product_id){
        return productService.getProduct(product_id);
    }

    @GetMapping()
    public List<Product> getAllProductDetails(){
        return productService.getAllProducts();
    }

    @PostMapping()
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        if(productService.createProduct(product)){
            return ResponseEntity.status(HttpStatus.OK).body("Product created successfully!");
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("CategoryId is not found or some field is missing!");
    }

    @PutMapping("{product_id}")
    public ResponseEntity<String>updateProductDetails(@PathVariable("product_id") int product_id, @RequestBody Product product){
        if(productService.updateProduct(product_id, product)){
            return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ProductId or the CategoryId does not exist or some field is missing!");
    }

    @DeleteMapping("{product_id}")
    public ResponseEntity<String> deleteProductDetails(@PathVariable("product_id") int product_id){
        if(productService.deleteProduct(product_id)){
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ProductId does not exist!");
    }

    @GetMapping("byCategory/{category_id}")
    public List<Product> getAllProductsByCategoryId(@PathVariable int category_id) {
        return productService.getAllProductsByCategoryId(category_id);
    }

}