package com.example.SpringProject.service;

import com.example.SpringProject.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

public interface ProductService {
    public Boolean createProduct(Product product);
    public Boolean updateProduct(int product_id,Product product);
    public Boolean deleteProduct(int product_id);
    public Product getProduct(int product_id);
    public List<Product> getAllProducts();
    public List<Product> getAllProductsByCategoryId(int category_id);
}
