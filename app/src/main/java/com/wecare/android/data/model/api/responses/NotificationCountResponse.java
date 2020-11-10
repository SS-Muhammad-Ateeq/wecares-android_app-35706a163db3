package com.wecare.android.data.model.api.responses;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.ui.base.BaseActivity;

public class NotificationCountResponse extends BaseResponse {

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @SerializedName("count")
    private int count;

    public NotificationCountResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.count);
    }

    protected NotificationCountResponse(Parcel in) {
        super(in);
        this.count = in.readInt();
    }

    public static final Creator<NotificationCountResponse> CREATOR = new Creator<NotificationCountResponse>() {
        @Override
        public NotificationCountResponse createFromParcel(Parcel source) {
            return new NotificationCountResponse(source);
        }

        @Override
        public NotificationCountResponse[] newArray(int size) {
            return new NotificationCountResponse[size];
        }
    };
}
