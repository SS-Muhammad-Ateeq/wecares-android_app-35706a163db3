package com.wecare.android.data.model.api.responses;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.StatisticModel;

public class StatisticResponse extends BaseResponse {

    @SerializedName("caregivers")
    @Expose
    private StatisticModel caregivers;
    @SerializedName("careseekers")
    @Expose
    private StatisticModel careseekers;
    @SerializedName("services")
    @Expose
    private StatisticModel services;

    public StatisticModel getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(StatisticModel caregivers) {
        this.caregivers = caregivers;
    }

    public StatisticModel getCareseekers() {
        return careseekers;
    }

    public void setCareseekers(StatisticModel careseekers) {
        this.careseekers = careseekers;
    }

    public StatisticModel getServices() {
        return services;
    }

    public void setServices(StatisticModel services) {
        this.services = services;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.caregivers, flags);
        dest.writeParcelable(this.careseekers, flags);
        dest.writeParcelable(this.services, flags);
    }

    public StatisticResponse() {
    }

    protected StatisticResponse(Parcel in) {
        super(in);
        this.caregivers = in.readParcelable(StatisticModel.class.getClassLoader());
        this.careseekers = in.readParcelable(StatisticModel.class.getClassLoader());
        this.services = in.readParcelable(StatisticModel.class.getClassLoader());
    }

    public static final Creator<StatisticResponse> CREATOR = new Creator<StatisticResponse>() {
        @Override
        public StatisticResponse createFromParcel(Parcel source) {
            return new StatisticResponse(source);
        }

        @Override
        public StatisticResponse[] newArray(int size) {
            return new StatisticResponse[size];
        }
    };
}