package com.example.insurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insurance.pojo.Police;
import com.example.insurance.pojo.Rating;
import com.example.insurance.retrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssessmentActivity extends AppCompatActivity {
    private Police police;
    private TextView tvPrice;
    private RatingBar rbPrice;
    private TextView tvConv;
    private RatingBar rbConv;
    private TextView tvImpr;
    private RatingBar rbImpr;
    private Button button;
    public static final String EXTRA_POLICE = "police";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        Rating userRating = new Rating();
        police = (Police) getIntent().getExtras().get(EXTRA_POLICE);
        tvPrice = findViewById(R.id.tvPrice);
        rbPrice = findViewById(R.id.rbPrice);
        tvConv = findViewById(R.id.tvConv);
        rbConv = findViewById(R.id.rbConv);
        tvImpr = findViewById(R.id.tvImpr);
        rbImpr = findViewById(R.id.rbImpr);
        button = findViewById(R.id.btnProlong);

        tvPrice.setText("Цена");
        tvConv.setText("Удобство оформления");
        tvImpr.setText("Впечатление от обслуживания");

        rbPrice.setOnRatingBarChangeListener((ratingBar, v, b) -> userRating.setPrice((int) ratingBar.getRating()));
        rbConv.setOnRatingBarChangeListener((ratingBar, v, b) -> userRating.setConv((int) ratingBar.getRating()));
        rbImpr.setOnRatingBarChangeListener((ratingBar, v, b) -> userRating.setImpression((int) ratingBar.getRating()));

        button.setOnClickListener(l -> {
            NetworkService.getInstance()
                    .getJSONRatingApi()
                    .save(userRating)
                    .enqueue(new Callback<Rating>() {
                        @Override
                        public void onResponse(@NonNull Call<Rating> call, @NonNull Response<Rating> response) {
                            Toast toast = Toast.makeText(AssessmentActivity.this, "", Toast.LENGTH_LONG);
                            toast.setText("Оценки сохранены");
                            toast.show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Rating> call, @NonNull Throwable t) {

                        }
                    });
        });
    }
}