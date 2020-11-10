package com.wecare.android.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wecare.android.data.model.api.responses.JWTResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JSONParser {
    public static String getNonFieldError(String json) {
        String error = "System Error";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("non_field_errors");

            error = array.get(0).toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;

    }

    public static JWTResponse parseJWTResponse(String json) {

        Type type = new TypeToken<JWTResponse>() {
        }.getType();
        JWTResponse jwtResponse =
                new GsonBuilder().create().fromJson(json, type);

        return jwtResponse;
    }
}
