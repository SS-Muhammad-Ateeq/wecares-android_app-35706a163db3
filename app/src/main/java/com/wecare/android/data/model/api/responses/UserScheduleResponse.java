package com.wecare.android.data.model.api.responses;

import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.ScheduleDayModel;

import java.util.ArrayList;

public class UserScheduleResponse extends BaseResponse {
    @SerializedName("schedule")
    ArrayList<ScheduleDayModel> dayModels;

    @SerializedName("isSameTime")
    Boolean isSameTime;

    public int getIsAvailable24() {
        return isAvailable24;
    }

    public void setIsAvailable24(int isAvailable24) {
        this.isAvailable24 = isAvailable24;
    }

    @SerializedName("working_24_hours")
    int isAvailable24;


    public Boolean getSameTime() {
        return isSameTime;
    }

    public void setSameTime(Boolean sameTime) {
        isSameTime = sameTime;
    }

    public ArrayList<ScheduleDayModel> getDayModels() {
        return dayModels;
    }

    public void setDayModels(ArrayList<ScheduleDayModel> dayModels) {
        this.dayModels = dayModels;
    }


}
