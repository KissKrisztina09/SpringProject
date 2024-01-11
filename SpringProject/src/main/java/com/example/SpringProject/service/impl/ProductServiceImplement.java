package com.example.SpringProject.service.impl;

import com.example.SpringProject.model.ApiResponse;
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
    public ResponseEntity<ApiResponse<Object>> createProduct(Product product) {
        if (product != null && !product.getProduct_name().isEmpty() && !product.getDescription().isEmpty() && product.getPrice() >= 0 && product.getQuantity() >= 0 && product.getCategoryId() >=0) {
            List<Integer> categoryId = categoryRepository.findAll().stream().map(Category::getCategory_id).toList();
            List<String> ProductNames = productRepository.findByCategoryId(product.getCategoryId()).stream().map(Product::getProduct_name).toList();
            if(ProductNames.contains(product.getProduct_name())){
                ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Product name already exist!",HttpStatus.BAD_REQUEST.value(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (categoryId.contains(product.getCategoryId())) {
                productRepository.save(product);
                ApiResponse<Object> successResponse = new ApiResponse<>(true, "Product created successfully!", HttpStatus.OK.value(), product);
                return ResponseEntity.status(HttpStatus.OK).body(successResponse);
            }
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Category ID does not exist!",HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }else{
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Some field is empty!",HttpStatus.BAD_REQUEST.value(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Object>> updateProduct(int productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct == null) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Product ID does not exist!", HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }else {
            if (product != null && !product.getProduct_name().isEmpty() && !product.getDescription().isEmpty() && product.getPrice() >= 0 && product.getQuantity() >= 0 && product.getCategoryId() > 0) {
                List<Integer> categoryId = categoryRepository.findAll().stream().map(Category::getCategory_id).toList();
                List<String> ProductNames = productRepository.findByCategoryId(product.getProduct_id()).stream().map(Product::getProduct_name).toList();
                if (ProductNames.contains(product.getProduct_name())) {
                    ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Product name already exist!", HttpStatus.BAD_REQUEST.value(), null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
                if (categoryId.contains(product.getCategoryId())) {
                    existingProduct.setProduct_name(product.getProduct_name());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setCategoryId(product.getCategoryId());
                    existingProduct.setQuantity(product.getQuantity());
                    existingProduct.setPrice(product.getPrice());

                    productRepository.save(existingProduct);
                    ApiResponse<Object> successResponse = new ApiResponse<>(true, "Product updated successfully!", HttpStatus.OK.value(), product);
                    return ResponseEntity.status(HttpStatus.OK).body(successResponse);
                }
            }
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Some field is empty!", HttpStatus.BAD_REQUEST.value(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> deleteProduct(int product_id) {
        Product product = productRepository.findById(product_id).orElse(null);
        if(product == null){
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "Product Id does not exist!", HttpStatus.BAD_REQUEST.value(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        categoryRepository.deleteById(product_id);
        ApiResponse<Object> successResponse = new ApiResponse<>(true, "Product deleted successfully!", HttpStatus.OK.value(), null);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getProduct(int product_id) {
        Product existingProduct = productRepository.findById(product_id).orElse(null);

        if(existingProduct==null){
            ApiResponse<Object> errorResponse = new ApiResponse<>(false, "There is no such product with the given ID!",HttpStatus.NOT_FOUND.value(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ApiResponse<Object> successResponse = new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), existingProduct);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);

    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        ApiResponse<Object> successResponse = new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), productList);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getAllProductsByCategoryId(int category_id) {
        List<Product> productList = productRepository.findByCategoryId(category_id);
        ApiResponse<Object> successResponse = new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), productList);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}