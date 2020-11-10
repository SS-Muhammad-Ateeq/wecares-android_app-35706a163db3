package com.wecare.android.utils;

import com.google.gson.Gson;
import com.wecare.android.data.model.api.models.EduCerUserModel;
import com.wecare.android.data.model.api.models.EducationModel;
import com.wecare.android.data.model.api.models.ExperienceModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.requests.BankInfoRequest;
import com.wecare.android.data.model.api.requests.LanguageRequest;

import org.json.JSONObject;

public class JSONBuilderFlavour extends JSONBuilder {

    public static JSONObject getAddLanguageParams(LanguageRequest model){
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
    public static JSONObject getUpdateEduCerUserParams(EduCerUserModel model){
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
    public static JSONObject getAddExperienceParams(ExperienceModel model){
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
    public static JSONObject getAddEducationParams(EducationModel model){
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
    public static JSONObject getUpdateUserParams(UserModel model){
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
    public static JSONObject getBankInfoRequestParams(BankInfoRequest model){
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
    public static JSONObject getCommonRequestParams(Object model){
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
