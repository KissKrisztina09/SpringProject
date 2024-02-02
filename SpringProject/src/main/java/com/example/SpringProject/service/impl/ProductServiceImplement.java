package com.example.SpringProject.service.impl;

import com.example.SpringProject.DAO.ProductDAO;
import com.example.SpringProject.model.JoinTables;
import com.example.SpringProject.model.Product;
import com.example.SpringProject.service.ProductService;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImplement implements ProductService {
    ProductDAO productDAO;

    public ProductServiceImplement(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    public ResponseEntity<Object> getProductsByCategoryId(int category_id) {
        try {
            List<JoinTables> productsById = productDAO.getProductsByCategoryId(category_id);
            if (!productsById.isEmpty()) {
                return new ResponseEntity<>(productsById, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No products found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve products: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @Override
    public ResponseEntity<Object> createProduct(Product product) {
       try {
            productDAO.insertProduct(product);
            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Failed to create product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateProduct(int productId, Product product) {
        try {
            productDAO.updateProduct(productId, product);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Failed to update product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(int product_id) {
        try{
            productDAO.deleteProduct(product_id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Failed to delete product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getProduct(int product_id) {
        try {
            Product product = productDAO.selectProduct(product_id);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found with ID: " + product_id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to select product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAllProducts() {
         try {
            List<Product> products = productDAO.selectAllProducts();
            if (!products.isEmpty()) {
                return new ResponseEntity<>(products, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No products found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve products: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}