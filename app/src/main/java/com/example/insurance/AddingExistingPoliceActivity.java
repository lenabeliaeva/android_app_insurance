package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.insurance.pojo.User;

public class AddingExistingPoliceActivity extends AppCompatActivity {
    private User user;
    private TextView tv;
    private EditText editText;
    private Button btn;
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_existing_police);

        user = (User)getIntent().getExtras().get(EXTRA_USER);
        tv = findViewById(R.id.textView2);
        editText = findViewById(R.id.policeNumber);
        btn = findViewById(R.id.addBtn);
        addPolice();
    }

    private void addPolice(){

    }
}