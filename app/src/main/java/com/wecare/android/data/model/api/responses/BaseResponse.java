package com.wecare.android.data.model.api.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.BaseApiError;

public class BaseResponse implements Parcelable {

    @Expose
    @SerializedName("success")
    private boolean success = true;

    @Expose
    @SerializedName("errors")
    private BaseApiError error;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BaseApiError getError() {
        return error;
    }

    public void setError(BaseApiError error) {
        this.error = error;
    }

    public BaseResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.error, flags);
    }

    protected BaseResponse(Parcel in) {
        this.success = in.readByte() != 0;
        this.error = in.readParcelable(BaseApiError.class.getClassLoader());
    }

}