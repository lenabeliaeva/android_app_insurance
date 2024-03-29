package com.example.insurance.pojo;

import java.io.Serializable;
import java.util.Date;

public class Police implements Serializable {
    private int id, number, typeOfObject;
    private double cost;
    private Date start, end;
    private Car car;
    private User user;
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getTypeOfObject() {
        return typeOfObject;
    }

    public void setTypeOfObject(int typeOfObject) {
        this.typeOfObject = typeOfObject;
    }

    public Date getEnd() {
        return end;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public String toString() {
        switch (typeOfObject){
            case 21:
                return "Полис ОСАГО";
            case 22:
                return "Полис КАСКО";
            case 31:
                return "Полис застрахованной квартиры";
            case 32:
                return "Полис застрахованного дома";
        }
        return "Полис страхования";
    }
}
