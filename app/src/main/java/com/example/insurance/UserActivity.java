package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.insurance.pojo.User;

public class UserActivity extends AppCompatActivity {
    private User user;
    private EditText name;
    private EditText secondName;
    private EditText surname;
    private EditText passport;
    private EditText city;
    private Button btn;
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("Ввод личных данных");
        user = (User) getIntent().getExtras().get(EXTRA_USER);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    makeRed(name);
                } else {
                    user.setName(s.toString());
                    secondName.setEnabled(true);
                }
            }
        });

        secondName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    makeRed(secondName);
                } else {
                    user.setSecondName(s.toString());
                    surname.setEnabled(true);
                }
            }
        });

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().contains(" ") && s.length() > 0) {
                    user.setSurname(s.toString());
                    passport.setEnabled(true);
                } else {
                    makeRed(surname);
                }
            }
        });

        passport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    makeRed(passport);
                } else {
                    user.setPassport(s.toString());
                    city.setEnabled(true);
                }
            }
        });

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    makeRed(city);
                } else {
                    user.setCity(s.toString());
                    btn.setEnabled(true);
                }
            }
        });

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, SignUpActivity.class);
            intent.putExtra(SignInActivity.EXTRA_USER, user);
            startActivity(intent);
        });
    }

    private void makeRed(EditText editText) {
        editText.setHintTextColor(Color.RED);
        editText.setHint("Это поле не должно быть пустым");
        btn.setEnabled(false);
    }

    private void initControls() {
        name = findViewById(R.id.name);
        secondName = findViewById(R.id.otchestvo);
        secondName.setEnabled(false);
        surname = findViewById(R.id.familiya);
        surname.setEnabled(false);
//        birthDate = findViewById(R.id.birth_date);
//        birthDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                user.setBirthDate(dayOfMonth + "." + monthOfYear + 1 + "." + year);
//            }
//        });
//        birthDate.setEnabled(false);
        passport = findViewById(R.id.passport);
        passport.setEnabled(false);
//        passportDate = findViewById(R.id.passport_date);
//        passportDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                user.setPassportDate(dayOfMonth + "." + monthOfYear + 1 + "." + year);
//            }
//        });
//        passportDate.setEnabled(false);
        city = findViewById(R.id.city);
        city.setEnabled(false);
        btn = findViewById(R.id.proceed_btn);
        btn.setEnabled(false);
    }
}