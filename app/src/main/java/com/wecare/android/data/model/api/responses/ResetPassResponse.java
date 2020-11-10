package com.wecare.android.data.model.api.responses;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPassResponse extends BaseResponse {

    @SerializedName("reset_password_code")
    @Expose
    private Integer resetPasswordCode;

    public Integer getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(Integer resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.resetPasswordCode);
    }

    public ResetPassResponse() {
    }

    protected ResetPassResponse(Parcel in) {
        super(in);
        this.resetPasswordCode = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ResetPassResponse> CREATOR = new Creator<ResetPassResponse>() {
        @Override
        public ResetPassResponse createFromParcel(Parcel source) {
            return new ResetPassResponse(source);
        }

        @Override
        public ResetPassResponse[] newArray(int size) {
            return new ResetPassResponse[size];
        }
    };
}