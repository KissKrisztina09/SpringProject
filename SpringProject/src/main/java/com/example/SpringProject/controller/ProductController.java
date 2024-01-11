package com.example.SpringProject.controller;


import com.example.SpringProject.model.ApiResponse;
import com.example.SpringProject.model.Product;
import com.example.SpringProject.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{product_id}")
    public ResponseEntity<ApiResponse<Object>> getProductDetails(@PathVariable("product_id") int product_id){
        return productService.getProduct(product_id);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getAllProductDetails(){
        return productService.getAllProducts();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Object>>createProduct(@RequestBody Product product){
        return productService.createProduct(product);

    }

    @PutMapping("{product_id}")
    public ResponseEntity<ApiResponse<Object>> updateProductDetails(@PathVariable("product_id") int product_id, @RequestBody Product product){
        return productService.updateProduct(product_id, product);

    }

    @DeleteMapping("{product_id}")
    public ResponseEntity<ApiResponse<Object>> deleteProductDetails(@PathVariable("product_id") int product_id){
        return productService.deleteProduct(product_id);
    }

    @GetMapping("byCategory/{category_id}")
    public ResponseEntity<ApiResponse<Object>> getAllProductsByCategoryId(@PathVariable int category_id) {
        return productService.getAllProductsByCategoryId(category_id);
    }

}