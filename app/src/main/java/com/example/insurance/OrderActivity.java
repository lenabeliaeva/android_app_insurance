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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrderActivity extends AppCompatActivity {
    private static final String KEY_RESPONSE_TEXT = "key";
    private static final String GOOD_RESPONSE = "true";
    Handler uiAdapter;
    private User user;
    private Car car;
    private Police police;
    private TextView textView;
    private Button btn;
    public final static String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initControls();
        user = (User) getIntent().getExtras().get(EXTRA_USER);
        car = user.getCar();
        police = user.getLastPolice();
        police.setUserId(user.getId());
        police.setCarId(car.getId());
        police.setCost(car.getInsuranceCost());
        savePolice();
    }

    @SuppressLint("HandlerLeak")
    private void initControls() {
        textView = findViewById(R.id.order_made);
        btn = findViewById(R.id.goToCabinet);
        if (uiAdapter == null) {
            uiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            String responseText = bundle.getString(KEY_RESPONSE_TEXT);
                            if (GOOD_RESPONSE.equals(responseText)) {
                                textView.setText("Полис оформлен!");
                            } else {
                                textView.setText("Не удалось оформить полис");
                            }
                        }
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(OrderActivity.this, TopLevelActivity.class);
                                intent.putExtra(TopLevelActivity.EXTRA_USER, user);
                                startActivity(intent);
                            }
                        });
                    }
                }
            };
        }
    }

    private void savePolice() {
        Thread sendHttpRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/addPolice");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("police=" + new Gson().toJson(police));
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line_from_service;
                    while ((line_from_service = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line_from_service);
                    }
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_RESPONSE_TEXT, stringBuilder.toString());
                    message.setData(bundle);
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