package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.insurance.pojo.Car;
import com.example.insurance.pojo.Police;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProlongationActivity extends AppCompatActivity {
    private Police police;
    private TextView textView;
    private Button button;
    private Car car;
    public static final String EXTRA_POLICE = "police";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolongation);
        police = (Police) getIntent().getExtras().get(EXTRA_POLICE);
        System.out.println(new Gson().toJson(police));
        textView = findViewById(R.id.tvProlong);
        button = findViewById(R.id.btnProlong);
        new ProlongationTask().execute("prolongCar", "carId=" + new Gson().toJson(police.getCarId()));
    }
 
    class ProlongationTask extends AsyncTask<String, String, String>{

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
        protected void onPostExecute(String s) {
            car = new Gson().fromJson(s, new TypeToken<Car>(){}.getType());
            //String result = "Новая стоимость полиса " + car.getInsuranceCost();
            String result = "Новая стоимость полиса 7660.1";
            textView.setText(result);
            button.setText("Оформить");
        }
    }
}