package com.example.insurance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity {
    public static final String EXTRA_POLICE_ID = "policeId";
    private static final String KEY_RESPONSE_TEXT = "KEY_RESPONSE_TEXT";
    Handler UiAdapter = null;
    ListView productList;
    private Map<String, String> products;
    private String[] productsList;

    private void startHttpConnection(final String id){
        Thread sendHttpRequest = new Thread(){
            public void run(){
                URL url;
                HttpURLConnection connection;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/products");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("id=" + id);
                    System.out.println(id);
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    products = new Gson().fromJson(stringBuilder.toString(), new TypeToken<Map<String, String>>(){}.getType());
                    productsList = products.values().toArray(new String[0]);
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putStringArray(KEY_RESPONSE_TEXT,  productsList);
                    message.setData(bundle);
                    UiAdapter.sendMessage(message);
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
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
        sendHttpRequest.start();
    }

    @SuppressLint("HandlerLeak")
    private void initControls(){
        productList = findViewById(R.id.prod_list);
        if (UiAdapter == null) {
            UiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            productsList = bundle.getStringArray(KEY_RESPONSE_TEXT);
                            ArrayAdapter<String> policiesAdapter = new ArrayAdapter<>(
                                    ProductListActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    productsList
                            );
                            productList.setAdapter(policiesAdapter);
                        }
                    }
                }
            };
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        String id = (String) getIntent().getExtras().get(EXTRA_POLICE_ID);
        initControls();
        startHttpConnection(id);
//        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> policiesCategory, View view, int position, long id) {
//            }
//        };
//        productList.setOnItemClickListener(itemClickListener);
    }
}
