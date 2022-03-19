package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.insurance.pojo.Car;
import com.example.insurance.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PasswordActivity extends AppCompatActivity {
    private static final String KEY_RESPONSE_TEXT = "key";
    private EditText password;
    private Button btn;
    private User user;
    private Car car;
    private Handler uiAdapter;
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        //user = (User) getIntent().getExtras().get(EXTRA_USER);
        if (getIntent().getExtras().get(EXTRA_USER) != null) {
            user = (User) getIntent().getExtras().get(EXTRA_USER);
            car = user != null ? user.getCar() : null;
        }
        init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setPassword(password.getText().toString());
                sendHttpRequest();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void init(){
        password = findViewById(R.id.mustPassword);
        btn = findViewById(R.id.mustBtn);
        if (uiAdapter == null){
            uiAdapter = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            user = (User) bundle.getSerializable(KEY_RESPONSE_TEXT);
                            Intent intent = new Intent(PasswordActivity.this, CalculationActivity.class);
                            intent.putExtra(CalculationActivity.EXTRA_USER, user);
                            startActivity(intent);
                        }
                    }
                }
            };
        }
    }

    private void sendHttpRequest(){
        Thread sendHttpRequest = new Thread(){
            public void run(){
                URL url;
                HttpURLConnection connection;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/registerInstantly");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("user=" + new Gson().toJson(user));
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    user = new Gson().fromJson(stringBuilder.toString(), new TypeToken<User>(){}.getType());
                    user.addCar(car);
                    user.makeConnected();
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_RESPONSE_TEXT, user);
                    message.setData(bundle);
                    uiAdapter.sendMessage(message);
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        sendHttpRequest.start();
    }
}