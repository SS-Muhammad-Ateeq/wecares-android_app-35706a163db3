package com.wecare.android.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class BaseApiError implements Parcelable {


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Expose
    @SerializedName("code")
    public int errorCode;


    public String getMessage() {
        if (message == null) {
            message = "Error";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;

//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//
//        BaseApiError apiError = (BaseApiError) object;
//
//        if (errorCode != apiError.errorCode) return false;
//        if (statusCode != null ? !statusCode.equals(apiError.statusCode)
//                : apiError.statusCode != null)
//            return false;
//        return message != null ? message.equals(apiError.message) : apiError.message == null;
//
//    }


    public BaseApiError() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.errorCode);
        dest.writeString(this.message);
    }

    protected BaseApiError(Parcel in) {
        this.errorCode = in.readInt();
        this.message = in.readString();
    }

    public static final Creator<BaseApiError> CREATOR = new Creator<BaseApiError>() {
        @Override
        public BaseApiError createFromParcel(Parcel source) {
            return new BaseApiError(source);
        }

        @Override
        public BaseApiError[] newArray(int size) {
            return new BaseApiError[size];
        }
    };
}