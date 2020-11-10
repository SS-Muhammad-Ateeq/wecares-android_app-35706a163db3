package com.wecare.android.data.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.SubServiceResponse;

import java.util.ArrayList;

public class FilterObject extends BaseResponse implements Parcelable {
    /**
     * caregiver_age : 1
     * caregiver_gender_id : 1
     * caregiver_language_id : 1
     * date : 2019-04-27
     * start_time : 11:30:00
     * description : test
     * payment_method : 1
     * profile_id : 1
     * location_id : 1
     * services : [{"main_service_id":"3","sub_service_id":"3","sub_service_type":"1","quantity":"1"}]
     * //sub_service_type : 1 if 0 then use hour
     * //quantity : 1 --- always one
     * //hour : 1
     */

    // used in search
    private String wecare_id;
    private String caregiver_age = "0";
    private String caregiver_gender_id = "0";
    private String caregiver_language_id = "0";
    private String date;
    private String start_time;
    private String payment_method;
    private String profile_id;
    private String location_id;
    private ArrayList<SubServiceResponse> services;

    //local used for search care giver
    private String sortYearsOfExperience;
    private String sortRating;
    private String sortPrice;
    private String sortAge;
    private String sortGender;

    //local for special request values. /// don't use this values in request
    private String caregiverAgeValue;
    private String caregiverGenderValue;
    private String caregiverLanguageValue;


    public String getWecare_id() {
        return wecare_id;
    }

    public FilterObject setWecare_id(String wecare_id) {
        this.wecare_id = wecare_id;
        return this;
    }

    public String getCaregiver_age() {
        return caregiver_age;
    }

    public void setCaregiver_age(String caregiver_age) {
        this.caregiver_age = caregiver_age;
    }

    public String getCaregiver_gender_id() {
        return caregiver_gender_id;
    }

    public void setCaregiver_gender_id(String caregiver_gender_id) {
        this.caregiver_gender_id = caregiver_gender_id;
    }

    public String getCaregiver_language_id() {
        return caregiver_language_id;
    }

    public void setCaregiver_language_id(String caregiver_language_id) {
        this.caregiver_language_id = caregiver_language_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public ArrayList<SubServiceResponse> getServices() {
        return services;
    }

    public FilterObject setServices(ArrayList<SubServiceResponse> services) {
        this.services = services;
        return this;
    }

    public String getSortYearsOfExperience() {
        return sortYearsOfExperience;
    }

    public FilterObject setSortYearsOfExperience(String sortYearsOfExperience) {
        this.sortYearsOfExperience = sortYearsOfExperience;
        return this;
    }

    public String getSortRating() {
        return sortRating;
    }

    public FilterObject setSortRating(String sortRating) {
        this.sortRating = sortRating;
        return this;
    }

    public String getSortPrice() {
        return sortPrice;
    }

    public FilterObject setSortPrice(String sortPrice) {
        this.sortPrice = sortPrice;
        return this;
    }

    public String getSortAge() {
        return sortAge;
    }

    public FilterObject setSortAge(String sortAge) {
        this.sortAge = sortAge;
        return this;
    }

    public String getSortGender() {
        return sortGender;
    }

    public FilterObject setSortGender(String sortGender) {
        this.sortGender = sortGender;
        return this;
    }

    public String getCaregiverAgeValue() {
        return caregiverAgeValue;
    }

    public FilterObject setCaregiverAgeValue(String caregiverAgeValue) {
        this.caregiverAgeValue = caregiverAgeValue;
        return this;
    }

    public String getCaregiverGenderValue() {
        return caregiverGenderValue;
    }

    public FilterObject setCaregiverGenderValue(String caregiverGenderValue) {
        this.caregiverGenderValue = caregiverGenderValue;
        return this;
    }

    public String getCaregiverLanguageValue() {
        return caregiverLanguageValue;
    }

    public FilterObject setCaregiverLanguageValue(String caregiverLanguageValue) {
        this.caregiverLanguageValue = caregiverLanguageValue;
        return this;
    }

    public FilterObject() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.wecare_id);
        dest.writeString(this.caregiver_age);
        dest.writeString(this.caregiver_gender_id);
        dest.writeString(this.caregiver_language_id);
        dest.writeString(this.date);
        dest.writeString(this.start_time);
        dest.writeString(this.payment_method);
        dest.writeString(this.profile_id);
        dest.writeString(this.location_id);
        dest.writeTypedList(this.services);
        dest.writeString(this.sortYearsOfExperience);
        dest.writeString(this.sortRating);
        dest.writeString(this.sortPrice);
        dest.writeString(this.sortAge);
        dest.writeString(this.sortGender);
        dest.writeString(this.caregiverAgeValue);
        dest.writeString(this.caregiverGenderValue);
        dest.writeString(this.caregiverLanguageValue);
    }

    protected FilterObject(Parcel in) {
        super(in);
        this.wecare_id = in.readString();
        this.caregiver_age = in.readString();
        this.caregiver_gender_id = in.readString();
        this.caregiver_language_id = in.readString();
        this.date = in.readString();
        this.start_time = in.readString();
        this.payment_method = in.readString();
        this.profile_id = in.readString();
        this.location_id = in.readString();
        this.services = in.createTypedArrayList(SubServiceResponse.CREATOR);
        this.sortYearsOfExperience = in.readString();
        this.sortRating = in.readString();
        this.sortPrice = in.readString();
        this.sortAge = in.readString();
        this.sortGender = in.readString();
        this.caregiverAgeValue = in.readString();
        this.caregiverGenderValue = in.readString();
        this.caregiverLanguageValue = in.readString();
    }

    public static final Creator<FilterObject> CREATOR = new Creator<FilterObject>() {
        @Override
        public FilterObject createFromParcel(Parcel source) {
            return new FilterObject(source);
        }

        @Override
        public FilterObject[] newArray(int size) {
            return new FilterObject[size];
        }
    };
}
