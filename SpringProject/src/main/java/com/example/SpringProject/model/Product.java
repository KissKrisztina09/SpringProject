package com.example.SpringProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Product {

    @JsonIgnore
    private int product_id;
    private String product_name;
    private String description;
    private float price;
    private int quantity;

    public Product() {
    }

    public int getProduct_id(){
        return product_id;
    }

    public void setProduct_id(int product_id){
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
