package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.utils.AppConstants;

public class StatisticModel implements Parcelable {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("englishName")
    @Expose
    private String englishName;
    @SerializedName("arabicName")
    @Expose
    private String arabicName;



    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getEnglishName() {
        return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? englishName : arabicName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.number);
        dest.writeString(this.englishName);
        dest.writeString(this.arabicName);
    }

    public StatisticModel() {
    }

    protected StatisticModel(Parcel in) {
        this.number = (Integer) in.readValue(Integer.class.getClassLoader());
        this.englishName = in.readString();
        this.arabicName = in.readString();
    }

    public static final Parcelable.Creator<StatisticModel> CREATOR = new Parcelable.Creator<StatisticModel>() {
        @Override
        public StatisticModel createFromParcel(Parcel source) {
            return new StatisticModel(source);
        }

        @Override
        public StatisticModel[] newArray(int size) {
            return new StatisticModel[size];
        }
    };
}