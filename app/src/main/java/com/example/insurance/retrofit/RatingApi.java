package com.example.insurance.retrofit;

import com.example.insurance.pojo.Rating;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface RatingApi {

    @GET("/saveRating")
    Call<Rating> save(@Body Rating rating);
}
