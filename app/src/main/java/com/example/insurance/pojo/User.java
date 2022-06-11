package com.example.insurance.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class User implements Serializable {
    private int id;
    private String name, secondName, surname, birthDate, passport, passportDate, email, password, city;
    private int age;
    private Gender gender;
    private EducationLevel educationLevel;
    private ActivitySphere activitySphere;
    private IncomeLevel incomeLevel;
    private boolean isConnected = false;
    private List<Police> policies = new ArrayList<>();
    private Police police;
    private List<Car> cars = new LinkedList<>();

    public void setId(int id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public ActivitySphere getActivitySphere() {
        return activitySphere;
    }

    public void setActivitySphere(ActivitySphere activitySphere) {
        this.activitySphere = activitySphere;
    }

    public IncomeLevel getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(IncomeLevel incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addPolicies(List<Police> policeList){
        policies = policeList;
    }

    public void addPolice(Police police){
        this.police = police;
    }

    public List<Police> getPolicies() {
        return policies;
    }

    public Police getLastPolice(){
        return police;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate){ this.birthDate = birthDate;}

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setPassportDate(String passportDate) {
        this.passportDate = passportDate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void makeConnected(){
        isConnected = true;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean carsIsEmpty() {
        return cars.isEmpty();
    }

    public Car getCar(){
        return cars.remove(cars.size() - 1);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " " + secondName + " " + surname + " ";
    }
}
