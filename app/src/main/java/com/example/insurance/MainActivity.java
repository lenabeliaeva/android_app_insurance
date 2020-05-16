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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_RESPONSE_TEXT = "KEY_RESPONSE_TEXT";
    private static final String GOOD_RESPONSE = "Пароль верный";
    EditText editTextLogin;
    EditText editTextPassword;
    TextView textView;
    Button button;
    Handler UiAdapter = null;

    @SuppressLint("HandlerLeak")
    private void initControls() {
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text_view);
        if (UiAdapter == null) {
            UiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            String responseText = bundle.getString(KEY_RESPONSE_TEXT);
                            if (GOOD_RESPONSE.equals(responseText)) {
                                Intent intent = new Intent(MainActivity.this, TopLevelActivity.class);
                                startActivity(intent);
                            } else textView.setText(responseText);
                        }
                    }
                }
            };
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSendHttpRequestMessage(editTextLogin.getText().toString(), editTextPassword.getText().toString());
            }
        });
    }

    public void startSendHttpRequestMessage(final String login, final String password) {
        Thread sendHttpRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuffer = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/login");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("login=" + login + "&password=" + password);
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line_from_service;
                    while ((line_from_service = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line_from_service);
                    }
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_RESPONSE_TEXT, stringBuffer.toString());
                    message.setData(bundle);
                    UiAdapter.sendMessage(message);
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