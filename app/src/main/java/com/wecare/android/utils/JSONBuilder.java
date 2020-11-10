package com.wecare.android.utils;

import com.google.gson.Gson;
import com.wecare.android.data.model.api.models.RegistrationModel;
import com.wecare.android.data.model.api.requests.CreateScheduleRequest;
import com.wecare.android.data.model.api.requests.UserServiceRequestModel;

import org.json.JSONException;
import org.json.JSONObject;

class JSONBuilder {

    public static JSONObject getUpdatePasswordByEmailParams(String code, String pass) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.PARAM_PASSWORD_CODE, code);
            jsonObject.put(ServerKeys.PARAM_PASSWORD_NEW, pass);
            jsonObject.put(ServerKeys.PARAM_PASSWORD_REPEAT, pass);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getResetPasswordByEmailParams(String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.PARAM_EMAIL, email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getResetPasswordByPhoneParams(String phoneNumber, String countryCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.PARAM_PHONE_NUMBER, phoneNumber);
            jsonObject.put(ServerKeys.PARAM_COUNTRY_CODE, countryCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getUpdatePasswordByPhoneParams(String code, String pass) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.PARAM_CODE, code);
            jsonObject.put(ServerKeys.PARAM_PASSWORD, pass);
            jsonObject.put(ServerKeys.PARAM_PASSWORD_NEW, pass);
            jsonObject.put(ServerKeys.PARAM_PASSWORD_REPEAT, pass);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getRegisterJsonObject(RegistrationModel model) {
        Gson gson = new Gson();
        JSONObject jsonObject;
        String jsonInString = gson.toJson(model);
        try {
            jsonObject = new JSONObject(jsonInString);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getResendVerificationCodeParams(String countryCode, String phoneNumber) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.PARAM_COUNTRY_CODE, countryCode);
            jsonObject.put(ServerKeys.PARAM_PHONE_NUMBER, phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getSendVerificationCodeParams(String code) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ServerKeys.PARAM_VERIFICATION_CODE, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getCreateScheduleParams(CreateScheduleRequest model) {
        Gson gson = new Gson();
        JSONObject jsonObject;
        String jsonInString = gson.toJson(model);
        try {
            jsonObject = new JSONObject(jsonInString);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getAddEditDeleteServiceParams(UserServiceRequestModel model) {
        Gson gson = new Gson();
        JSONObject jsonObject;
        String jsonInString = gson.toJson(model);
        try {
            jsonObject = new JSONObject(jsonInString);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
