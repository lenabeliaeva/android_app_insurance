package com.example.insurance.retrofit;

import com.example.insurance.pojo.Police;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PoliceApi {

    @GET("/getPoliciesList")
    Call<List<Police>> getPoliciesByUserId(@Query("userId") long userId);
}
