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
import com.example.SpringProject.model.Category;

@Repository
public class CategoryDAO{

    private final DataSource dataSource;

    public CategoryDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private static String sql_insert_category = "INSERT INTO category (category_name) VALUES (:category_name);";
    private static String sql_select_category = "SELECT category_name from category";
    private static String sql_update_category = "UPDATE category SET category_name = :category_name WHERE category_id = :category_id;";
    private static String sql_delete_category = "DELETE from category WHERE category_id = :category_id;";

    //Insert category into database
    public void insertCategory(Category category) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("category_name", category.getCategory_name());
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        List<String> categoryNames = namedParameterJdbcTemplate.queryForList(sql_select_category, namedParameters, String.class);
        if(category.getCategory_name().isEmpty()){
            throw new IllegalArgumentException("Category name cannot be null");
        }
        if(categoryNames.contains(category.getCategory_name())){
            throw new IllegalArgumentException("Category name already exist!");
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql_insert_category, namedParameters, keyHolder);
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to insert category with ID: " + e);
        }
    }

    //Update Category
    public void updateCategory(int category_id, Category updatedCategory) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        if (updatedCategory.getCategory_name() == null || updatedCategory.getCategory_name().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
    
        MapSqlParameterSource checkNamedParameters = new MapSqlParameterSource();
        checkNamedParameters.addValue("category_name", updatedCategory.getCategory_name());
        List<String> categoryNames = namedParameterJdbcTemplate.queryForList(sql_select_category, checkNamedParameters, String.class);
        
        if (categoryNames.contains(updatedCategory.getCategory_name())) {
            throw new IllegalArgumentException("Category name already exists!");
        }
        try {
            MapSqlParameterSource updateNamedParameters = new MapSqlParameterSource();
            updateNamedParameters.addValue("category_name", updatedCategory.getCategory_name());
            updateNamedParameters.addValue("category_id", category_id); // Add category_id parameter
    
            namedParameterJdbcTemplate.update(sql_update_category, updateNamedParameters);
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to update category with ID: " + category_id, e);
        }
    }
    
    
    //Select a category by the ID
    public Category selectCategory(int category_id) {
        String sqlSelectById = sql_select_category + " WHERE category_id = :category_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        namedParameters.addValue("category_id", category_id);
        try {
            List<Category> result = namedParameterJdbcTemplate.query(sqlSelectById, namedParameters, new BeanPropertyRowMapper<>(Category.class));
            if (result.isEmpty()) {
                throw new IllegalArgumentException("There is no such category with the given ID: " + category_id);
            }
            return result.get(0);
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to retrieve category with ID: " + category_id, e);
        }
    }
    
     // Select all categories
    public List<Category> selectAllCategories() {
        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            List<Category> categories = namedParameterJdbcTemplate.query(sql_select_category, new BeanPropertyRowMapper<>(Category.class));
            return categories;
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to retrieve all categories.", e);
        }
    }

    //Delete category by ID
    public void deleteCategory(int category_id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        try {
            namedParameters.addValue("category_id", category_id);
            int rowsAffected = namedParameterJdbcTemplate.update(sql_delete_category, namedParameters);
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("No category found with ID: " + category_id);
            }
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to delete category with ID: " + category_id, e);
        }
    }
    
}