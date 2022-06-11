package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.insurance.pojo.Police;
import com.example.insurance.pojo.User;

public class PoliceActivity extends AppCompatActivity {
    private Police police;
    private User user;
    TextView cost;
    TextView city;
    TextView car;
    TextView driver;
    TextView srok;
    Button btn;
    public static final String EXTRA_POLICE = "police";
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        police = (Police) getIntent().getExtras().get(EXTRA_POLICE);
        user = (User) getIntent().getExtras().get(EXTRA_USER);
        init();
        setValues();
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(PoliceActivity.this, AssessmentActivity.class);
            intent.putExtra(AssessmentActivity.EXTRA_POLICE, police);
            startActivity(intent);
        });
    }

    private void init(){
        cost = findViewById(R.id.policeCost);
        city = findViewById(R.id.policeCity);
        car = findViewById(R.id.car);
        driver = findViewById(R.id.driver);
        srok = findViewById(R.id.srok);
        btn = findViewById(R.id.prolong);
    }

    private void setValues() {
        cost.setText((int) police.getCost());
        city.setText(user.getCity());
        car.setText(police.getCar().toString());
        driver.setText(user.toString());
        srok.setText((CharSequence) police.getStart());
    }
}