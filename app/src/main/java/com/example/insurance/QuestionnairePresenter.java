package com.example.insurance;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.ActivitySphere;
import com.example.insurance.pojo.EducationLevel;
import com.example.insurance.pojo.Gender;
import com.example.insurance.pojo.IncomeLevel;
import com.example.insurance.pojo.User;
import com.example.insurance.retrofit.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnairePresenter {
    private final QuestionnaireActivity activity;

    public QuestionnairePresenter(QuestionnaireActivity a) {
        activity = a;
    }

    public void tryToGetAllGenders() {
        NetworkService.getInstance()
                .getJSONUserApi()
                .getAllGenders()
                .enqueue(new Callback<List<Gender>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Gender>> call, @NonNull Response<List<Gender>> response) {
                        activity.setGenders(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Gender>> call, @NonNull Throwable t) {

                    }
                });
    }

    public void tryToGetAllEducations() {
        NetworkService.getInstance()
                .getJSONUserApi()
                .getAllEducations()
                .enqueue(new Callback<List<EducationLevel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<EducationLevel>> call, @NonNull Response<List<EducationLevel>> response) {
                        activity.setEducationLevels(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<EducationLevel>> call, @NonNull Throwable t) {

                    }
                });
    }

    public void tryToGetAllActivitySpheres() {
        NetworkService.getInstance()
                .getJSONUserApi()
                .getAllActivitySpheres()
                .enqueue(new Callback<List<ActivitySphere>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ActivitySphere>> call, @NonNull Response<List<ActivitySphere>> response) {
                        activity.setActivitySpheres(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ActivitySphere>> call, @NonNull Throwable t) {

                    }
                });
    }

    public void tryToGetAllIncomes() {
        NetworkService.getInstance()
                .getJSONUserApi()
                .getAllIncomeLevels()
                .enqueue(new Callback<List<IncomeLevel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<IncomeLevel>> call, @NonNull Response<List<IncomeLevel>> response) {
                        activity.setIncomeLevels(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<IncomeLevel>> call, @NonNull Throwable t) {

                    }
                });
    }

    public void tryToSaveAnswers(User user) {
        NetworkService.getInstance()
                .getJSONUserApi()
                .update(user)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        activity.showSuccess();
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                    }
                });
    }
}
