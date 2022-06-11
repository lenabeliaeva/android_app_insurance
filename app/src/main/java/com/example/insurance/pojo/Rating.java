package com.example.insurance.pojo;

public class Rating {
    long id;
    int price;
    int conv;
    int impression;
    Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getConv() {
        return conv;
    }

    public void setConv(int conv) {
        this.conv = conv;
    }

    public int getImpression() {
        return impression;
    }

    public void setImpression(int impression) {
        this.impression = impression;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
