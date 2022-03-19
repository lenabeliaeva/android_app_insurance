package com.example.insurance;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class ProductListActivity extends ListActivity {
    private static final String KEY_RESPONSE_TEXT = "KEY_RESPONSE_TEXT";
    Handler UiAdapter = null;
    ListView productList;
    private ArrayList<Product> products;
    private User user;
    private int id;
    public static final String EXTRA_POLICE_ID = "policeId";
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (int) getIntent().getExtras().get(EXTRA_POLICE_ID);
        //user = (User) getIntent().getExtras().get(EXTRA_USER);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductListActivity.this, FillingPoliceActivity.class);
                intent.putExtra(FillingPoliceActivity.EXTRA_CATEGORY_ID, products.get(position).getId());
                intent.putExtra(FillingPoliceActivity.EXTRA_USER, user);
                startActivity(intent);
            }
        };
        getListView().setOnItemClickListener(itemClickListener);
        initControls();
        startHttpConnection(id + "");
    }

    @SuppressLint("HandlerLeak")
    private void initControls(){
        productList = getListView();
        if (UiAdapter == null) {
            UiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            products = (ArrayList<Product>) bundle.getSerializable(KEY_RESPONSE_TEXT);
                            setListAdapter(new ProductListAdapter(ProductListActivity.this, products));
                        }
                    }
                }
            };
        }
    }

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
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    products = new Gson().fromJson(stringBuilder.toString(), new TypeToken<ArrayList<Product>>(){}.getType());
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_RESPONSE_TEXT, products);
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
}
