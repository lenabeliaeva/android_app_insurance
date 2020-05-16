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
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PoliciesCategoryActivity extends AppCompatActivity {
    private static final String KEY_RESPONSE_TEXT = "KEY_RESPONSE_TEXT";
    ListView policiesCategory;
    Handler UiAdapter = null;
    Map<String, String> policiesCategories = new HashMap<>();
    Map<String, String> auxiliaryMap = new HashMap<>();
    String[] categories;

    private void startSendHttpRequestMessage() {
        Thread sendHttpRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/policies");
                    connection = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                    policiesCategories = new Gson().fromJson(stringBuilder.toString(), type);
                    swapHashMap();
                    categories = policiesCategories.values().toArray(new String[0]);
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putStringArray(KEY_RESPONSE_TEXT, categories);
                    message.setData(bundle);
                    UiAdapter.sendMessage(message);
                    connection.disconnect();
                }
                catch (IOException e) {
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
    private void initControls() {
        policiesCategory = findViewById(R.id.policies_category);
        if (UiAdapter == null) {
            UiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            categories = bundle.getStringArray(KEY_RESPONSE_TEXT);
                            ArrayAdapter<String> policiesAdapter = new ArrayAdapter<>(
                                    PoliciesCategoryActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    categories
                            );
                            policiesCategory.setAdapter(policiesAdapter);
                        }
                    }
                }
            };
        }
    }

    private void swapHashMap(){
        for (String key: policiesCategories.keySet()){
            auxiliaryMap.put(policiesCategories.get(key), key);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies_category);
        initControls();
        startSendHttpRequestMessage();
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> policiesCategory, View view, int position, long id) {
                Intent intent = new Intent(PoliciesCategoryActivity.this, ProductListActivity.class);
                intent.putExtra(ProductListActivity.EXTRA_POLICE_ID, auxiliaryMap.get(categories[position]));
                startActivity(intent);
            }
        };
        policiesCategory.setOnItemClickListener(itemClickListener);
    }
}
