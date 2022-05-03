package com.example.insurance.retrofit;

import com.example.insurance.pojo.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {

    @GET("/bayesAvgProducts")
    Call<List<Product>> getBayesAvgProducts();
}
