package com.example.insurance.retrofit;

import com.example.insurance.pojo.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesApi {

    @GET("/categories")
    Call<List<Category>> getPoliciesCategoriesList();
}
