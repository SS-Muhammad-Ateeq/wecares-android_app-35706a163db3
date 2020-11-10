package com.wecare.android.data.model.api.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.utils.AppConstants;

import ir.mirrajabi.searchdialog.core.Searchable;

 public class InsuranceCompanyLookup extends LookUpModel implements Searchable {

    @Expose
    @SerializedName("english_name")
    private String engName;

    @Expose
    @SerializedName("arabic_name")
    private String arabName;

    @Override
    public String getTitle() {
        return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? getEngName() : getArabName();
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getArabName() {
        return arabName;
    }

    public void setArabName(String arabName) {
        this.arabName = arabName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.engName);
        dest.writeString(this.arabName);
    }

    protected InsuranceCompanyLookup(Parcel in) {
        super(in);
        this.engName = in.readString();
        this.arabName = in.readString();
    }

    public static final Creator<InsuranceCompanyLookup> CREATOR = new Creator<InsuranceCompanyLookup>() {
        @Override
        public InsuranceCompanyLookup createFromParcel(Parcel source) {
            return new InsuranceCompanyLookup(source);
        }

        @Override
        public InsuranceCompanyLookup[] newArray(int size) {
            return new InsuranceCompanyLookup[size];
        }
    };
}

