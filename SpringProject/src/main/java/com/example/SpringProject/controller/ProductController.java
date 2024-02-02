package com.example.SpringProject.controller;

import com.example.SpringProject.model.Product;
import com.example.SpringProject.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("update/{product_id}")
    public ResponseEntity<Object> updateProductDetails(@PathVariable("product_id") int productId, @RequestBody Product product) {
       return productService.updateProduct(productId, product);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllProducts(){
        return productService.getAllProducts();
    }    

    @GetMapping("/{product_id}")
    public ResponseEntity<Object> getProduct(@PathVariable("product_id")int productId) {
        return productService.getProduct(productId);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("product_id") int product_id){
        return productService.deleteProduct(product_id);
    }

    @GetMapping("/all/{category_id}")
    public ResponseEntity<Object> getProductsByCategoryId(@PathVariable("category_id") int category_id){
        return productService.getProductsByCategoryId(category_id);
    }
    
}