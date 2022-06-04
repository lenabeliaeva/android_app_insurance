package com.example.insurance.TopLevelScreen;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.Police;
import com.example.insurance.retrofit.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPoliciesPresenter {
    private final MyPoliciesFragment fragment;

    public MyPoliciesPresenter(MyPoliciesFragment fragment) {
        this.fragment = fragment;
    }

    public void tryGetUserPoliciesList(long userId) {
        NetworkService.getInstance()
                .getJSONPoliceApi()
                .getPoliciesByUserId(userId)
                .enqueue(new Callback<List<Police>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Police>> call, @NonNull Response<List<Police>> response) {
                        fragment.setPoliciesList(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Police>> call, @NonNull Throwable t) {

                    }
                });
    }
}
