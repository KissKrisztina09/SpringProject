package com.example.SpringProject.service.impl;

import com.example.SpringProject.model.Category;
import com.example.SpringProject.model.Product;
import com.example.SpringProject.repository.CategoryRepository;
import com.example.SpringProject.repository.ProductRepository;
import com.example.SpringProject.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImplement implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductServiceImplement(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }



    @Override
    public ResponseEntity<Object> createProduct(Product product) {
        if (product != null && !product.getProduct_name().isEmpty() && !product.getDescription().isEmpty() && product.getPrice() >= 0 && product.getQuantity() >= 0 && product.getCategoryId() >=0) {
            List<Integer> categoryId = categoryRepository.findAll().stream().map(Category::getCategory_id).toList();
            List<String> ProductNames = productRepository.findByCategoryId(product.getCategoryId()).stream().map(Product::getProduct_name).toList();
            if(ProductNames.contains(product.getProduct_name())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product name already exist!");
            }
            if (categoryId.contains(product.getCategoryId())) {
                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.OK).body("Product created successfully!");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID does not exist!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some field is empty!");
        }

    }

    @Override
    public ResponseEntity<Object> updateProduct(int productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product ID does not exist!");
        }else {
            if (product != null && !product.getProduct_name().isEmpty() && !product.getDescription().isEmpty() && product.getPrice() >= 0 && product.getQuantity() >= 0 && product.getCategoryId() > 0) {
                List<Integer> categoryId = categoryRepository.findAll().stream().map(Category::getCategory_id).toList();
                List<String> ProductNames = productRepository.findByCategoryId(product.getProduct_id()).stream().map(Product::getProduct_name).toList();
                if (ProductNames.contains(product.getProduct_name())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product name already exist!");
                }
                if (categoryId.contains(product.getCategoryId())) {
                    existingProduct.setProduct_name(product.getProduct_name());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setCategoryId(product.getCategoryId());
                    existingProduct.setQuantity(product.getQuantity());
                    existingProduct.setPrice(product.getPrice());

                    productRepository.save(existingProduct);
                    return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully!");
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some field is empty!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(int product_id) {
        Product product = productRepository.findById(product_id).orElse(null);
        if(product == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Id does not exist!");
        }
        categoryRepository.deleteById(product_id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
    }

    @Override
    public ResponseEntity<Object> getProduct(int product_id) {
        Product existingProduct = productRepository.findById(product_id).orElse(null);

        if(existingProduct==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such product with the given ID!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(existingProduct);
    }

    @Override
    public ResponseEntity<Object> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body("Success!");
    }

    @Override
    public ResponseEntity<Object> getAllProductsByCategoryId(int category_id) {
        List<Product> productList = productRepository.findByCategoryId(category_id);
        return ResponseEntity.status(HttpStatus.OK).body("Success!");
    }
}