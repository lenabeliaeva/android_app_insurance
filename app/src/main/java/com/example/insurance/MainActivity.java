package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editTextLogin;
    EditText editTextPassword;
    TextView textView;
    Button button;

    private void initControls(){
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSendHttpRequestMessage(editTextLogin.getText().toString(), editTextPassword.getText().toString());
            }
        });
    }

    public void startSendHttpRequestMessage(final String login, final String password){
        Thread sendHttpRequest = new Thread()
        {
            public void run(){
                try {
                    URL url = new URL("http://localhost/login");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    OutputStream outputStream = connection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write("login="+login+"&password="+password);
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = bufferedReader.readLine();
                    textView.setText(line);
                    bufferedWriter.close();
                    connection.disconnect();
                }
                catch (MalformedURLException e){
                    Log.e("MalformedURL", e.getMessage(), e);
                }
                catch (IOException e){
                    Log.e("IO", e.getMessage(), e);
                }
            }
        };
        sendHttpRequest.start();
    }
}