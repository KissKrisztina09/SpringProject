package com.example.SpringProject.service.impl;

import com.example.SpringProject.model.Category;
import com.example.SpringProject.model.Product;
import com.example.SpringProject.repository.CategoryRepository;
import com.example.SpringProject.repository.ProductRepository;
import com.example.SpringProject.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
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
    public Boolean createProduct(Product product) {
        if (product != null && !product.getProduct_name().isEmpty() && !product.getDescription().isEmpty() && product.getPrice() >= 0 && product.getQuantity() >= 0 && product.getCategoryId() >=0) {
            List<Integer> categoryId = categoryRepository.findAll().stream().map(Category::getCategory_id).toList();
            List<String> ProductNames = productRepository.findAll().stream().map(Product::getProduct_name).toList();
            if (categoryId.contains(product.getCategoryId()) && !ProductNames.contains(product.getProduct_name())) {
                productRepository.save(product);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Boolean updateProduct(int productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        if(existingProduct == null){
            return false;
        }
        if (product != null && !product.getProduct_name().isEmpty() && !product.getDescription().isEmpty() && product.getPrice() >= 0 && product.getQuantity() >= 0 && product.getCategoryId() > 0) {
            List<Integer> categoryId = categoryRepository.findAll().stream().map(Category::getCategory_id).toList();
            List<String> ProductNames = productRepository.findAll().stream().map(Product::getProduct_name).toList();
            if (categoryId.contains(product.getCategoryId()) && !ProductNames.contains(product.getProduct_name())) {
                existingProduct.setProduct_name(product.getProduct_name());
                existingProduct.setDescription(product.getDescription());
                existingProduct.setCategoryId(product.getCategoryId());
                existingProduct.setQuantity(product.getQuantity());
                existingProduct.setPrice(product.getPrice());

                productRepository.save(existingProduct);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Boolean deleteProduct(int product_id) {
        Product product = productRepository.findById(product_id).orElse(null);
        if(product == null){
            return false;
        }
        categoryRepository.deleteById(product_id);
        return true;
    }

    @Override
    public Product getProduct(int product_id) {
        return productRepository.findById(product_id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + product_id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int category_id) {
        return productRepository.findByCategoryId(category_id);

    }
}