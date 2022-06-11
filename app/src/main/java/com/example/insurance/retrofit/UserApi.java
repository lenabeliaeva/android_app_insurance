package com.example.insurance.retrofit;

import com.example.insurance.pojo.ActivitySphere;
import com.example.insurance.pojo.EducationLevel;
import com.example.insurance.pojo.Gender;
import com.example.insurance.pojo.IncomeLevel;
import com.example.insurance.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/register")
    Call<User> signUp(@Body User user);

    @POST("/login")
    Call<User> signIn(@Query("login")String login, @Query("password") String password);

    @GET("/genders")
    Call<List<Gender>> getAllGenders();

    @GET("/educations")
    Call<List<EducationLevel>> getAllEducations();

    @GET("/activities")
    Call<List<ActivitySphere>> getAllActivitySpheres();

    @GET("/incomes")
    Call<List<IncomeLevel>> getAllIncomeLevels();

    @PATCH("/update")
    Call<User> update(@Body User user);
}
