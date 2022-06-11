package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.insurance.pojo.ActivitySphere;
import com.example.insurance.pojo.EducationLevel;
import com.example.insurance.pojo.Gender;
import com.example.insurance.pojo.IncomeLevel;
import com.example.insurance.pojo.User;
import com.example.insurance.spinnerAdapters.ActSpinnerAdapter;
import com.example.insurance.spinnerAdapters.EduSpinnerAdapter;
import com.example.insurance.spinnerAdapters.GenderSpinnerAdapter;
import com.example.insurance.spinnerAdapters.IncomeSpinnerAdapter;

import java.util.List;

public class QuestionnaireActivity extends AppCompatActivity {
    private User user;
    private EditText age;
    private Spinner gender;
    private Spinner education;
    private Spinner activitySphere;
    private Spinner income;
    private GenderSpinnerAdapter genderSpinnerAdapter;
    private EduSpinnerAdapter eduSpinnerAdapter;
    private ActSpinnerAdapter actSpinnerAdapter;
    private IncomeSpinnerAdapter incomeSpinnerAdapter;
    private Button btn;
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        setTitle("Анкета");
        user = (User) getIntent().getExtras().get(EXTRA_USER);

        QuestionnairePresenter presenter = new QuestionnairePresenter(this);
        presenter.tryToGetAllGenders();
        presenter.tryToGetAllEducations();
        presenter.tryToGetAllActivitySpheres();
        presenter.tryToGetAllIncomes();
        initControls();

        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    makeRed(age);
                } else {
                    user.setAge(Integer.parseInt(editable.toString()));
                }
            }
        });

        btn.setOnClickListener(l -> presenter.tryToSaveAnswers(user));
    }

    public void setGenders(List<Gender> genders) {
        genderSpinnerAdapter = new GenderSpinnerAdapter(QuestionnaireActivity.this,
                android.R.layout.simple_spinner_item, genders);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderSpinnerAdapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setGender((Gender) genderSpinnerAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void setEducationLevels(List<EducationLevel> educationLevels) {
        eduSpinnerAdapter = new EduSpinnerAdapter(QuestionnaireActivity.this,
                android.R.layout.simple_spinner_item, educationLevels);
        eduSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(eduSpinnerAdapter);
        education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setEducationLevel((EducationLevel) eduSpinnerAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void setActivitySpheres(List<ActivitySphere> activitySpheres) {
        actSpinnerAdapter = new ActSpinnerAdapter(QuestionnaireActivity.this,
                android.R.layout.simple_spinner_item, activitySpheres);
        actSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySphere.setAdapter(actSpinnerAdapter);
        activitySphere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setActivitySphere((ActivitySphere) actSpinnerAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void setIncomeLevels(List<IncomeLevel> incomeLevels) {
        incomeSpinnerAdapter = new IncomeSpinnerAdapter(QuestionnaireActivity.this,
                android.R.layout.simple_spinner_item, incomeLevels);
        incomeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        income.setAdapter(incomeSpinnerAdapter);
        income.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setIncomeLevel((IncomeLevel) incomeSpinnerAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void showSuccess() {
        Toast toast = Toast.makeText(QuestionnaireActivity.this, "", Toast.LENGTH_LONG);
        toast.setText("Ответы сохранены");
        toast.show();
    }

    private void initControls() {
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        education = findViewById(R.id.edu);
        activitySphere = findViewById(R.id.act_sphere);
        income = findViewById(R.id.income);
        btn = findViewById(R.id.proceed_btn);
//        btn.setEnabled(false);
    }

    private void makeRed(EditText editText) {
        editText.setHintTextColor(Color.RED);
        editText.setHint("Это поле не должно быть пустым");
        btn.setEnabled(false);
    }
}