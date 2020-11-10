package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.SerializedName;

public class UserServiceRequestModel {
    public UserServiceRequestModel() {
    }

    //delete service constructor
    public UserServiceRequestModel(String caregiverServiceID,String subServiceID) {
        this.caregiverServiceID = caregiverServiceID;
        this.subServiceID = subServiceID;
    }

    @SerializedName("sub_service_id")
    private String subServiceID;

    @SerializedName("gender_id")
    private String genderID;

    @SerializedName("price")
    private String price;

    public String getSubServiceID() {
        return subServiceID;
    }

    public void setSubServiceID(String subServiceID) {
        this.subServiceID = subServiceID;
    }

    public String getGenderID() {
        return genderID;
    }

    public void setGenderID(String genderID) {
        this.genderID = genderID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCaregiverServiceID() {
        return caregiverServiceID;
    }

    public void setCaregiverServiceID(String caregiverServiceID) {
        this.caregiverServiceID = caregiverServiceID;
    }

    @SerializedName("caregiver_service_id")
    private String caregiverServiceID;


}
