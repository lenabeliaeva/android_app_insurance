package com.example.insurance.pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CarDict {
    private static String response = null;

    public static String[] array(){
        String[] result = null;
        try {
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.getJSONArray("name");
            result = new String[jsonArray.length() - 1];
            for (int i = 0; i < result.length; ++i){
                result[i] = (String) jsonArray.get(i);
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int[] id(){
        int[] result = null;
        try {
            while (response == null);
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.getJSONArray("id");
            result = new int[jsonArray.length() - 1];
            for (int i = 0; i < result.length; ++i){
                result[i] = (int) jsonArray.get(i);
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
