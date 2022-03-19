package com.example.insurance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insurance.TopLevelScreen.TopLevelActivity;
import com.example.insurance.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignInActivity extends AppCompatActivity {
    private User user;
    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView registrationLink;
    private Handler uiAdapter;
    private final String KEY_RESPONSE_TEXT = "KEY";
    private static final String BAD_PASSWORD_RESPONSE = "falsePassword";
    public static final String BAD_LOGIN_RESPONSE = "falseLogin";
    public static final String EXTRA_USER = "user";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.get(EXTRA_USER);
        initControls();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSendHttpRequest();
            }
        });
        registrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, UserActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void initControls() {
        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_pass);
        loginBtn = findViewById(R.id.sign_in_btn);
        registrationLink = findViewById(R.id.reg_link);
        if (uiAdapter == null) {
            uiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            String responseText = bundle.getString(KEY_RESPONSE_TEXT);
                            if (BAD_LOGIN_RESPONSE.equals(responseText)){
                                Toast toast = Toast.makeText(SignInActivity.this, "", Toast.LENGTH_LONG);
                                toast.setText("Такой пользователь не зарегистрирован");
                                toast.show();
                            } else if (BAD_PASSWORD_RESPONSE.equals(responseText)) {
                                Toast toast = Toast.makeText(SignInActivity.this, "", Toast.LENGTH_LONG);
                                toast.setText("Неверный пароль");
                                toast.show();
                            } else {
                                user = new Gson().fromJson(responseText, new TypeToken<User>(){}.getType());
                                user.makeConnected();
                                Intent intent = new Intent(SignInActivity.this, TopLevelActivity.class);
                                intent.putExtra(TopLevelActivity.EXTRA_USER, user);
                                startActivity(intent);
                            }
                        }
                    }
                }
            };
        }
    }

    private void startSendHttpRequest() {
        Thread sendRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuffer = new StringBuilder();
                String response;
                try {
                    url = new URL("http://10.0.2.2:8080/login");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("login=" + email.getText().toString() + "&password=" + password.getText().toString());
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((response = bufferedReader.readLine()) != null) {
                        stringBuffer.append(response);
                    }
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_RESPONSE_TEXT, stringBuffer.toString());
                    message.setData(bundle);
                    uiAdapter.sendMessage(message);
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
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
        sendRequest.start();
    }
}
