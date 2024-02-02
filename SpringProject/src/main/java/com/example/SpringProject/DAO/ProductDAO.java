package com.example.SpringProject.DAO;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.SpringProject.model.JoinTables;
import com.example.SpringProject.model.Product;

@Repository
public class ProductDAO{

    private final DataSource dataSource;

    public ProductDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private static String sql_insert_product = "INSERT INTO product (product_name, description, price, quantity) VALUES ( :product_name, :description, :price, :quantity);";
    private static String sql_select_product = "SELECT product_id, product_name, description, price, quantity FROM product";
    private static String sql_update_product = "UPDATE product SET product_name = :product_name, description = :description, " +
                                                "price = :price, " +
                                                "quantity = :quantity " +
                                                "WHERE product_id = :product_id;";
    private static String sql_delete_product = "DELETE FROM product WHERE product_id = :product_id;";
    private static String sql_product_names = "SELECT product_name FROM product;";
    private static String sql_join = "SELECT p.product_name, p.description, p.price, p.quantity, p.category_id " +
    "FROM " +
    "product p " +
    "INNER JOIN " +
    "category c ON p.category_id = c.category_id " +
    "WHERE c.category_id = :category_id";

    
    public List<JoinTables> getProductsByCategoryId(int category_id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("category_id", category_id);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return namedParameterJdbcTemplate.query(sql_join, parameters, new BeanPropertyRowMapper<>(JoinTables.class));
    }

    //Insert product into database
    public void insertProduct(Product product) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        List<String> productNames = namedParameterJdbcTemplate.queryForList(sql_product_names, namedParameters, String.class);
        if(product.getProduct_name().isEmpty()){
            throw new IllegalArgumentException("Product name cannot be null!");
        }
        if(productNames.contains(product.getProduct_name())){
            throw new IllegalArgumentException("Product name already exist!");
        }
        namedParameters.addValue("product_name", product.getProduct_name());
        namedParameters.addValue("description", product.getDescription());
        namedParameters.addValue("price", product.getPrice());
        namedParameters.addValue("quantity", product.getQuantity());

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();  
            namedParameterJdbcTemplate.update(sql_insert_product, namedParameters, keyHolder);
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to insert product with ID: " + e);
        }
    }
  

    //Select a product by ID
    public Product selectProduct(int productId) {
        String sqlSelectById = sql_select_product + " WHERE product_id = :productId";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("productId", productId);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        try {
            List<Product> result = namedParameterJdbcTemplate.query(sqlSelectById, namedParameters, new BeanPropertyRowMapper<>(Product.class));
            if (result.isEmpty()) {
                throw new IllegalArgumentException("There is no such product with the given ID: " + productId);
            }
            return result.get(0);
        }catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to retrieve product with ID: " + productId, e);
        }
    }
    
    // Select all products
    public List<Product> selectAllProducts() {
        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            List<Product> products = namedParameterJdbcTemplate.query(sql_select_product, new BeanPropertyRowMapper<>(Product.class));
            return products;
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to retrieve all products.", e);
        }
    }

    // Update product
    public void updateProduct(int product_id, Product updatedProduct) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        if (updatedProduct.getProduct_name() == null || updatedProduct.getProduct_name().isEmpty() || 
            updatedProduct.getDescription() == null || updatedProduct.getDescription().isEmpty() ||
            updatedProduct.getPrice() == 0 || updatedProduct.getQuantity() == 0) {
            throw new IllegalArgumentException("Some field is null or is empty!");
        }
        MapSqlParameterSource checkNamedParameters = new MapSqlParameterSource();
        checkNamedParameters.addValue("product_name", updatedProduct.getProduct_name());
        checkNamedParameters.addValue("description", updatedProduct.getDescription());
        checkNamedParameters.addValue("price", updatedProduct.getPrice());
        checkNamedParameters.addValue("quantity", updatedProduct.getQuantity());
        List<String> productNames = namedParameterJdbcTemplate.queryForList(sql_product_names, checkNamedParameters, String.class);
        if (productNames.contains(updatedProduct.getProduct_name())) {
            throw new IllegalArgumentException("Product name already exists!");
        }
        try {
                MapSqlParameterSource updateNamedParameters = new MapSqlParameterSource();
                updateNamedParameters.addValue("product_name", updatedProduct.getProduct_name());
                updateNamedParameters.addValue("description", updatedProduct.getDescription());
                updateNamedParameters.addValue("price", updatedProduct.getPrice());
                updateNamedParameters.addValue("quantity", updatedProduct.getQuantity());
                updateNamedParameters.addValue("product_id", product_id);
    
                namedParameterJdbcTemplate.update(sql_update_product, updateNamedParameters);
        }catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to update product with ID: " , e);
        }
    }

    public void deleteProduct(int product_id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        try {
            namedParameters.addValue("product_id", product_id);
            int rowsAffected = namedParameterJdbcTemplate.update(sql_delete_product, namedParameters);
            if(rowsAffected == 0){
                throw new IllegalArgumentException("No product found with ID: "+ product_id);
            }
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to delete product with ID: " + product_id, e);
        }
    }

}