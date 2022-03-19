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

import com.example.insurance.TopLevelScreen.TopLevelActivity;
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

public class CalculationActivity extends AppCompatActivity {
    private User user;
    private Car car;
    private Police police;
    private Handler uiAdapter;
    private TextView calc;
    private Button btn;
    private TextView goToMain;
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        user = (User) getIntent().getExtras().get(EXTRA_USER);
        car = user != null ? user.getCar() : null;
        police = user != null ? user.getLastPolice() : null;

        initControls();
        getCalculation();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.addCar(car);
                user.addPolice(police);
                System.out.println("USER " + new Gson().toJson(user));
                Intent intent = new Intent(CalculationActivity.this, OrderActivity.class);
                intent.putExtra(OrderActivity.EXTRA_USER, user);
                startActivity(intent);
            }
        });
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculationActivity.this, TopLevelActivity.class);
                intent.putExtra(TopLevelActivity.EXTRA_USER, user);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void initControls() {
        calc = findViewById(R.id.calculated);
        btn = findViewById(R.id.order_btn);
        goToMain = findViewById(R.id.go_to_main);
        if (uiAdapter == null) {
            uiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        String result = car.getInsuranceCost() + "";
                        calc.setText(result);
                    }
                }
            };
        }
    }

    private void getCalculation() {
        Thread sendHttpRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/calculate");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("car=" + new Gson().toJson(car));
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line_from_service;
                    while ((line_from_service = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line_from_service);
                    }
                    System.out.println(stringBuilder.toString());
                    car = new Gson().fromJson(stringBuilder.toString(), new TypeToken<Car>() {
                    }.getType());
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