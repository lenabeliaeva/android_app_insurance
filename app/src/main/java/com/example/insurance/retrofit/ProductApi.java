package com.example.insurance.retrofit;

import com.example.insurance.pojo.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductApi {

    @GET("/bayesAvgProducts")
    Call<List<Product>> getBayesAvgProducts();

    @GET("/products")
    Call<List<Product>> getAllProducts();

    @GET("/products/category")
    Call<List<Product>> getProductsByCategory(@Query("id") long categoryId);
}
