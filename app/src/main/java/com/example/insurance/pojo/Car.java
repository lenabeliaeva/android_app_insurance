package com.example.insurance.pojo;

import java.io.Serializable;

public class Car implements Serializable {
    private int id, tsType, marka, model, year, power;
    private double insuranceCost;
    private String regNumber;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMarka(int marka){
        this.marka = marka;
    }

    public void setModel(int model){
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPower(int power){
        this.power = power;
    }

    public int getMarka() {
        return marka;
    }

    public int getModel() {
        return model;
    }

    public int getPower() {
        return power;
    }

    public int getYear() {
        return year;
    }

    public void setTsType(int tsType) {
        this.tsType = tsType;
    }

    public int getTsType() {
        return tsType;
    }

    public void setInsuranceCost(double insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public double getInsuranceCost() {
        return insuranceCost;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    @Override
    public String toString() {
        return  marka + " " + model + ", " + year + ", " + power;
    }
}
