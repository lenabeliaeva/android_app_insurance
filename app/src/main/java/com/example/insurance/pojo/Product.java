package com.example.insurance.pojo;

public class Product {
    private long id;
    private String name;
    private String description;
    private Category category;
    private float bayesAverageRating;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public float getBayesAverageRating() {
        return bayesAverageRating;
    }
}
