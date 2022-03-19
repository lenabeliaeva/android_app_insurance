package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.insurance.pojo.User;

import java.util.Calendar;

public class FillingUserActivity extends AppCompatActivity {

    private User user;
    private EditText name;
    private EditText secondName;
    private EditText surname;
    private Button birthDate;
    private Button passportDate;
    private EditText passport;
    private EditText city;
    private EditText email;
    private Button btn;

    Calendar dateAndTime=Calendar.getInstance();
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_user);
        setTitle("Ввод личных данных");
        user = (User) getIntent().getExtras().get(EXTRA_USER);

        initControls();

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
                if (ValidUtil.isValidPassport(s)) {
                    user.setPassport(s.toString());
                    city.setEnabled(true);
                } else {
                    makeRed(passport);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ValidUtil.isValidPassport(s)) {
                    user.setPassport(s.toString());
                    city.setEnabled(true);
                } else {
                    makeRed(passport);
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
                    email.setEnabled(true);
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ValidUtil.isValidEmail(s)){
                    user.setEmail(s.toString());
                    btn.setEnabled(true);
                } else {
                    makeRed(email);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setEmail(email.getText().toString());
                if (user.isConnected()){
                    Intent intent = new Intent(FillingUserActivity.this, CalculationActivity.class);
                    intent.putExtra(CalculationActivity.EXTRA_USER, user);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(FillingUserActivity.this, PasswordActivity.class);
                    intent.putExtra(PasswordActivity.EXTRA_USER, user);
                    startActivity(intent);
                }
            }
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
        birthDate = findViewById(R.id.birth_date);
        passportDate = findViewById(R.id.passportDate);
        passport = findViewById(R.id.passport);
        passport.setEnabled(false);
        city = findViewById(R.id.city);
        city.setEnabled(false);
        email = findViewById(R.id.email);
        if (user.getEmail() != null) {
            email.setText(user.getEmail(), TextView.BufferType.EDITABLE);
        } else {
            email.setEnabled(false);
        }
        btn = findViewById(R.id.proceed_btn);
        btn.setEnabled(false);
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    };

    public void setDate(View v) {
        new DatePickerDialog(FillingUserActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}