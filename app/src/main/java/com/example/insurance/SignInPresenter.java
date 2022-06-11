package com.example.insurance;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.User;
import com.example.insurance.retrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInPresenter {
    private final SignInActivity activity;

    SignInPresenter(SignInActivity a) {
        activity = a;
    }

    public void tryToSignIn(String email, String password) {
        NetworkService.getInstance()
                .getJSONUserApi()
                .signIn(email, password)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        activity.doAfterSuccess(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        activity.doAfterFailure();
                    }
                });
    }
}
