package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.insurance.pojo.Car;
import com.example.insurance.pojo.Police;
import com.example.insurance.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PoliceActivity extends AppCompatActivity {
    private Police police;
    private Car insuredCar;
    private User user;
    private Handler uiAdapter;
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
        getCar();
        getUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoliceActivity.this, ProlongationActivity.class);
                intent.putExtra(ProlongationActivity.EXTRA_POLICE, police);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void init(){
        cost = findViewById(R.id.policeCost);
        city = findViewById(R.id.policeCity);
        car = findViewById(R.id.car);
        driver = findViewById(R.id.driver);
        srok = findViewById(R.id.srok);
        btn = findViewById(R.id.prolong);
        if (uiAdapter == null){
            uiAdapter = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        city.setText(user.getCity());
                        cost.setText(((Double)police.getCost()).toString());
                        car.setText(insuredCar.toString());
                        driver.setText(user.toString());
                    }
                }
            };
        }
    }

    private void getCar(){
        final Thread sendHttpRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/getCar");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("id=" + new Gson().toJson(police.getCarId()));
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line_from_service;
                    while ((line_from_service = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line_from_service);
                    }
                    insuredCar = new Gson().fromJson(stringBuilder.toString(), new TypeToken<Car>(){}.getType());
                    Message message = new Message();
                    message.what = 1;
                    uiAdapter.sendMessage(message);
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }

                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        sendHttpRequest.start();
    }

    private void getUser(){
        final Thread sendHttpRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/getUser");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("userId=" + new Gson().toJson(police.getUserId()));
                    outputStream.flush();
                    outputStream.close();
                    String line_from_service;
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line_from_service = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line_from_service);
                    }
                    user = new Gson().fromJson(stringBuilder.toString(), new TypeToken<User>(){}.getType());
                    Message message = new Message();
                    message.what = 1;
                    uiAdapter.sendMessage(message);
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }

                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        sendHttpRequest.start();
    }
}