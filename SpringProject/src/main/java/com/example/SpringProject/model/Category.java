package com.example.SpringProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Category {

    @JsonIgnore
    private int category_id;
    private String category_name;

    public Category() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int categoryId) {
        this.category_id = categoryId;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String categoryName) {
        this.category_name = categoryName;
    }
}
