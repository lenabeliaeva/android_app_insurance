package com.example.insurance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insurance.TopLevelScreen.TopLevelActivity;
import com.example.insurance.pojo.User;

public class SignUpActivity extends AppCompatActivity {
    private User user;
    private EditText email;
    private EditText password;
    private Button registerButton;
    private TextView loginLink;
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
        registerButton.setOnClickListener(v -> {
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            SignUpPresenter presenter = new SignUpPresenter(this);
            presenter.tryToSignUp(user);
        });
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            intent.putExtra(SignInActivity.EXTRA_USER, user);
            startActivity(intent);
        });
    }

    public void doAfterSuccess(User signedUpUser) {
        signedUpUser.makeConnected();
        Toast toast = Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_LONG);
        toast.setText("Регистрация прошла успешно");
        toast.show();
        Intent intent = new Intent(SignUpActivity.this, TopLevelActivity.class);
        intent.putExtra(TopLevelActivity.EXTRA_USER, signedUpUser);
        startActivity(intent);
    }

    public void doAfterFailure() {
        Toast toast = Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_LONG);
        toast.setText(BAD_RESPONSE);
        toast.show();
    }

    private void initControls() {
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.pass_reg);
        registerButton = findViewById(R.id.register_btn);
        registerButton.setEnabled(false);
        loginLink = findViewById(R.id.loginLink);
    }

    private void makeRed(EditText editText) {
        editText.setHintTextColor(Color.RED);
        editText.setHint("Это поле не должно быть пустым");
        registerButton.setEnabled(false);
    }
}
