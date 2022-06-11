package com.example.insurance;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insurance.TopLevelScreen.TopLevelActivity;
import com.example.insurance.pojo.User;

public class SignInActivity extends AppCompatActivity {
    private User user;
    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView registrationLink;
    public static final String EXTRA_USER = "user";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.get(EXTRA_USER);
        initControls();
        loginBtn.setOnClickListener(v -> {
            SignInPresenter presenter = new SignInPresenter(this);
            presenter.tryToSignIn(email.getText().toString(), password.getText().toString());
        });
        registrationLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, UserActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }

    public void doAfterSuccess(User signedInUser) {
        signedInUser.makeConnected();
        Intent intent = new Intent(SignInActivity.this, TopLevelActivity.class);
        intent.putExtra(TopLevelActivity.EXTRA_USER, signedInUser);
        startActivity(intent);
    }

    public void doAfterFailure() {
        Toast toast = Toast.makeText(SignInActivity.this, "", Toast.LENGTH_LONG);
        toast.setText("Такой пользователь не зарегистрирован или неверный пароль");
        toast.show();
    }

    private void initControls() {
        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_pass);
        loginBtn = findViewById(R.id.sign_in_btn);
        registrationLink = findViewById(R.id.reg_link);
    }
}
