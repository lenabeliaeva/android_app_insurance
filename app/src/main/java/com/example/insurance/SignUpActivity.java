package com.example.insurance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SignUpActivity extends AppCompatActivity {
    private User user;
    private EditText email;
    private EditText password;
    private Button registerButton;
    private TextView loginLink;
    private Handler uiAdapter;
    private static final String KEY = "KEY";
    private static final String BAD_RESPONSE = "false";
    public static final String EXTRA_USER = "user";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.get(EXTRA_USER);
        initControls();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ValidUtil.isValidPassword(password.getText()) && ValidUtil.isValidEmail(s)){
                    registerButton.setEnabled(true);
                } else if (!ValidUtil.isValidPassword(password.getText())) {
                    makeRed(password);
                } else if (!ValidUtil.isValidEmail(s)){
                    makeRed(email);
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ValidUtil.isValidPassword(s) && ValidUtil.isValidEmail(email.getText())){
                    registerButton.setEnabled(true);
                } else if (!ValidUtil.isValidPassword(s)) {
                    makeRed(password);
                } else if (!ValidUtil.isValidEmail(email.getText())){
                    makeRed(email);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                startSendHttpRequest();
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.putExtra(SignInActivity.EXTRA_USER, user);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void initControls() {
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.pass_reg);
        registerButton = findViewById(R.id.register_btn);
        registerButton.setEnabled(false);
        loginLink = findViewById(R.id.loginLink);
        if (uiAdapter == null) {
            uiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            String responseText = bundle.getString(KEY);
                            Toast toast = Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_LONG);
                            if (BAD_RESPONSE.equals(responseText)) {
                                toast.setText(responseText);
                                toast.show();
                            } else {
                                user = (User) new Gson().fromJson(responseText, new TypeToken<User>() {
                                }.getRawType());
                                user.makeConnected();
                                toast.setText("Регистрация прошла успешно");
                                toast.show();
                                Intent intent = new Intent(SignUpActivity.this, TopLevelActivity.class);
                                intent.putExtra(TopLevelActivity.EXTRA_USER, user);
                                startActivity(intent);

                            }
                        }
                    }
                }
            };
        }
    }

    private void makeRed(EditText editText) {
        editText.setHintTextColor(Color.RED);
        editText.setHint("Это поле не должно быть пустым");
        registerButton.setEnabled(false);
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
                    url = new URL("http://10.0.2.2:8080/register");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("user=" + new Gson().toJson(user));
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((response = bufferedReader.readLine()) != null) {
                        stringBuffer.append(response);
                    }
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY, stringBuffer.toString());
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
