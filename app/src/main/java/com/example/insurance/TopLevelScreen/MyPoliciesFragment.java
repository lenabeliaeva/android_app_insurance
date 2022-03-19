package com.example.insurance.TopLevelScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.insurance.PoliceActivity;
import com.example.insurance.R;
import com.example.insurance.SignInActivity;
import com.example.insurance.UserActivity;
import com.example.insurance.pojo.Police;
import com.example.insurance.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyPoliciesFragment extends ListFragment {
    private static final String KEY_RESPONSE_TEXT = "true";
    private static final String BAD_RESPONSE = "false";
    User user;
    Button signInButton;
    Button signUpButton;
    TextView textView;
    Strategy strategy;
    Handler uiAdapter;
    List<Police> policies;
    String[] policiesArray;

    public MyPoliciesFragment() {
        // Required empty public constructor
    }

    public MyPoliciesFragment(User u) {
        user = u;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (user.isConnected()) {
            View layout = inflater.inflate(R.layout.fragment_my_policies, container, false);
            strategy = new ShowPolicesStrategy();
            init();
            makeHttpRequest();
            return layout;
        } else {
            View layout = inflater.inflate(R.layout.fragment_my_policies_not, container, false);
            strategy = new SignInStrategy();
            return layout;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strategy.execute(view);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PoliceActivity.class);
                intent.putExtra(PoliceActivity.EXTRA_POLICE, policies.get(position));
                intent.putExtra(PoliceActivity.EXTRA_USER, user);
                startActivity(intent);
            }
        };
        getListView().setOnItemClickListener(itemClickListener);
    }

    class ShowPolicesStrategy implements Strategy {
        @Override
        public void execute(View view) {
            textView = view.findViewById(R.id.my_p_l);
        }
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        if (uiAdapter == null) {
            uiAdapter = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            if (!BAD_RESPONSE.equals(bundle.getSerializable(KEY_RESPONSE_TEXT))) {
                                policies = user.getPolicies();
                                policiesArray = new String[policies.size()];
                                Police police;
                                for (int i = 0; i < policies.size(); ++i) {
                                    police = policies.get(i);
                                    policiesArray[i] = police.toString();
                                }
                                ArrayAdapter<String> policiesAdapter = new ArrayAdapter<>(
                                        requireActivity(),
                                        R.layout.category_list_item,
                                        R.id.cat_list_item,
                                        policiesArray
                                );
                                setListAdapter(policiesAdapter);
                            } else {
                                textView.setText("Пока ни одного приобретённого полиса");
                            }
                        }
                    }
                }
            };
        }
    }

    private void makeHttpRequest() {
        Thread sendHttpRequest = new Thread() {
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    url = new URL("http://10.0.2.2:8080/getPoliciesList");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("userId=" + new Gson().toJson(user.getId()));
                    outputStream.flush();
                    outputStream.close();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line_from_service;
                    while ((line_from_service = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line_from_service);
                    }
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    if (stringBuilder.toString().equals("false")) {
                        bundle.putSerializable(KEY_RESPONSE_TEXT, "false");
                    } else {
                        System.out.println(stringBuilder);
                        user.addPolicies((List<Police>) new Gson().fromJson(stringBuilder.toString(), new TypeToken<ArrayList<Police>>() {
                        }.getType()));
                        bundle.putSerializable(KEY_RESPONSE_TEXT, (Serializable) user.getPolicies());
                    }
                    message.setData(bundle);
                    uiAdapter.sendMessage(message);
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }

                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        sendHttpRequest.start();
    }

    class SignInStrategy implements Strategy {
        @Override
        public void execute(View layout) {
            signInButton = layout.findViewById(R.id.button1);
            signUpButton = layout.findViewById(R.id.button2);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    intent.putExtra(SignInActivity.EXTRA_USER, user);
                    startActivity(intent);
                }
            });
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    intent.putExtra(UserActivity.EXTRA_USER, user);
                    startActivity(intent);
                }
            });
        }
    }
}
