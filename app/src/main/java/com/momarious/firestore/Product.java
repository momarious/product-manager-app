package com.momarious.firestore;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Product implements Serializable {

    @Exclude private String id;

    private String name, brand, description;
    private double price;
    private int quantity;

    public Product() {
        // empty
    }

    public Product(String name, String brand, String description, double price, int quantity) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

}
