package com.example.insurance;

public class Product {
    private long id;
    private String name;
    private String description;
    private String CATEGORY_ID;

    public Product(long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    long getId() {
        return id;
    }

    String getName(){
        return name;
    }

    String getDescription(){
        return description;
    }

}
