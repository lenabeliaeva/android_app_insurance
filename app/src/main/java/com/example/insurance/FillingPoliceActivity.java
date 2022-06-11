package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.insurance.pojo.Car;
import com.example.insurance.pojo.DictItem;
import com.example.insurance.pojo.Police;
import com.example.insurance.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class FillingPoliceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Spinner spinnerTypes;
    Spinner spinnerMarks;
    Spinner spinnerModels;
    Spinner spinnerYears;
    Spinner spinnerPower;
    Button startDate;
    Button endDate;
    Spinner currentSpinner;
    EditText carRegNumber;
    Button button;
    long categoryId;
    private Car car;
    private Police police;
    private User user;
    private ArrayList<DictItem> list = new ArrayList<>();
    String[] names;
    Calendar c;
    public static final String EXTRA_CATEGORY_ID = "categoryId";
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_police);
        car = new Car();
        categoryId = (long) getIntent().getExtras().get(EXTRA_CATEGORY_ID);
        police = new Police();
        police.setTypeOfObject((int)categoryId);
        user = (User) getIntent().getExtras().get(EXTRA_USER);
        switch ((int)categoryId) {
            case 21: {
                setTitle(R.string.osago);
                break;
            }
            case 22: {
                setTitle(R.string.casco);
                break;
            }
        }

        initSpinners();

        carRegNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ValidUtil.isValidCarNumber(s)){
                    car.setRegNumber(s.toString());
                    button.setEnabled(true);
                } else {
                    Toast toast = Toast.makeText(FillingPoliceActivity.this, "", Toast.LENGTH_SHORT);
                    toast.setText("Введите номер транспорта без пробелов большими буквами");
                    toast.show();
                }
            }
        });
        startDate.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            if (c != null) {
                police.setStart(c.getTime());
            }
        });
        endDate.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            if (c != null){
                police.setEnd(c.getTime());
            }
        });
        button.setOnClickListener(v -> {
            System.out.println("POLICE "+ new Gson().toJson(police));
            Intent intent = new Intent(FillingPoliceActivity.this, FillingUserActivity.class);
            user.addCar(car);
            user.addPolice(police);
            intent.putExtra(UserActivity.EXTRA_USER, user);
            startActivity(intent);
        });
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.equals(spinnerTypes)){
                    car.setTsType(list.get(position).getId());
                    spinnerMarks.setEnabled(true);
                    if (car.getTsType() != 0){
                        currentSpinner = spinnerMarks;
                        new HttpRequest().execute("getMarks", "id=" + car.getTsType());
                    }
                } else if (parent.equals(spinnerMarks)){
                    car.setMarka(list.get(position).getId());
                    spinnerModels.setEnabled(true);
                    if (car.getMarka() != 0){
                        currentSpinner = spinnerModels;
                        new HttpRequest().execute("getModels", "id=" + car.getMarka());
                    }
                } else if (parent.equals(spinnerModels)){
                    car.setModel(list.get(position).getId());
                    spinnerYears.setEnabled(true);
                    if (car.getModel() != 0){
                        currentSpinner = spinnerYears;
                        new HttpRequest().execute("getYears", "id=" + car.getModel());
                    }
                } else if (parent.equals(spinnerYears)){
                    car.setYear(list.get(position).getId());
                    spinnerPower.setEnabled(true);
                    if (car.getYear() != 0){
                        currentSpinner = spinnerPower;
                        new HttpRequest().execute("getPower", "id=" + car.getYear());
                    }
                } else if (parent.equals(spinnerPower)){
                    car.setPower(list.get(position).getId());
                    carRegNumber.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerTypes.setOnItemSelectedListener(itemSelectedListener);
        spinnerMarks.setOnItemSelectedListener(itemSelectedListener);
        spinnerModels.setOnItemSelectedListener(itemSelectedListener);
        spinnerYears.setOnItemSelectedListener(itemSelectedListener);
        spinnerPower.setOnItemSelectedListener(itemSelectedListener);
        currentSpinner = spinnerTypes;
        new HttpRequest().execute("getTsType", "");
    }

    @SuppressLint("HandlerLeak")
    private void initSpinners(){
        spinnerTypes = findViewById(R.id.transport_spinner);
        spinnerMarks = findViewById(R.id.marks);
        spinnerMarks.setEnabled(false);
        spinnerModels = findViewById(R.id.model);
        spinnerModels.setEnabled(false);
        spinnerYears = findViewById(R.id.spinner_year);
        spinnerYears.setEnabled(false);
        spinnerPower = findViewById(R.id.power);
        spinnerPower.setEnabled(false);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        carRegNumber = findViewById(R.id.car_reg_number);
        carRegNumber.setEnabled(false);
        button = findViewById(R.id.car_btn);
        button.setEnabled(false);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpRequest extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String inputLine;
            String response = "";
            String path = strings[0];
            String params = strings[1];
            HttpURLConnection connection;
            BufferedReader bufferedReader;
            StringBuilder stringBuilder = new StringBuilder();
            URL url;
            try {
                url = new URL("http://10.0.2.2:8080/" + path);
                connection = (HttpURLConnection) url.openConnection();
                if (!"".equals(params)) {
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes(params);
                    outputStream.flush();
                    outputStream.close();
                }
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                response = stringBuilder.toString();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            list = new Gson().fromJson(result, new TypeToken<ArrayList<DictItem>>(){}.getType());
            names = new String[list.size()];
            for (int i = 0; i < list.size(); ++i){
                names[i] = list.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(FillingPoliceActivity.this, android.R.layout.simple_spinner_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            currentSpinner.setAdapter(adapter);
        }
    }
}