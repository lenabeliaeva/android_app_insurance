package com.example.insurance.retrofit;

import com.example.insurance.pojo.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductApi {

    @GET("/bayesAvgProducts")
    Call<List<Product>> getBayesAvgProducts();

    @GET("/contentBased")
    Call<List<Product>> getContentBased(@Query("id") long userId);

    @GET("/userKnn")
    Call<List<Product>> getUserKnn(@Query("id") long userId);

    @GET("/itemKnn")
    Call<List<Product>> getItemKnn(@Query("id") long userId);

    @GET("/slopeOne")
    Call<List<Product>> getSlopeOne(@Query("id") long userId);

    @GET("/hybrid")
    Call<List<Product>> getHybrid(@Query("id") long userId);

    @GET("/products/category")
    Call<List<Product>> getProductsByCategory(@Query("id") long categoryId);
}
