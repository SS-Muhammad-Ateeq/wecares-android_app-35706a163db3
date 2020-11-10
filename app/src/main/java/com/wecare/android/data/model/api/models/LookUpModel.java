package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.utils.AppConstants;

import ir.mirrajabi.searchdialog.core.Searchable;

public class LookUpModel extends BaseResponse implements Searchable,Parcelable {

    @Expose
    @SerializedName("englishName")
    private String englishName;
    @Expose
    @SerializedName("arabicName")
    private String arabicName;
    @Expose
    @SerializedName("id")
    private String mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("label")
    private String label;

    public LookUpModel() {
    }

    public String getEnglishName() {
        return englishName;
    }

    public LookUpModel setEnglishName(String englishname) {
        this.englishName = englishname;
        return this;
    }

    public String getArabicName() {
        return arabicName;
    }

    public LookUpModel setArabicName(String arabicname) {
        this.arabicName = arabicname;
        return this;
    }

    public String getId() {
        return mId;
    }

    public LookUpModel setId(String mId) {
        this.mId = mId;
        return this;
    }

    public String getmName() {
        return mName;
    }

    public LookUpModel setmName(String mName) {
        this.mName = mName;
        return this;
    }


    public String getLabel() {
        return label;
    }

    public LookUpModel setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? getEnglishName() : getArabicName();
    }

    @Override
    public String getTitle() {
        return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? getEnglishName() : getArabicName();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.englishName);
        dest.writeString(this.arabicName);
        dest.writeString(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.label);
    }

    protected LookUpModel(Parcel in) {
        super(in);
        this.englishName = in.readString();
        this.arabicName = in.readString();
        this.mId = in.readString();
        this.mName = in.readString();
        this.label = in.readString();
    }

    public static final Creator<LookUpModel> CREATOR = new Creator<LookUpModel>() {
        @Override
        public LookUpModel createFromParcel(Parcel source) {
            return new LookUpModel(source);
        }

        @Override
        public LookUpModel[] newArray(int size) {
            return new LookUpModel[size];
        }
    };
}