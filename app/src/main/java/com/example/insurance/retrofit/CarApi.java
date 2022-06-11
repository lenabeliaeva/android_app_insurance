package com.example.insurance.retrofit;

import com.example.insurance.pojo.Car;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CarApi {

    @GET("/getCar")
    Call<Car> getCarById(@Query("carId") long carId);
}
