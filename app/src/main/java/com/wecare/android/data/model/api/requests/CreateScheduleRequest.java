package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.ScheduleDayModel;
import com.wecare.android.utils.AppConstants;

import java.util.ArrayList;

public class CreateScheduleRequest {

//    public CreateScheduleRequest(ArrayList<ScheduleDayModel> dayModels, boolean is24Available) {
//        this.dayModels = dayModels;
//        this.is24Available = is24Available ? 1 : 0;
//    }

    @SerializedName("schedule")
     ArrayList<ScheduleDayModel> dayModels;

    public CreateScheduleRequest() {
    }

    @SerializedName("working_24_hours")
     int is24Available;

    public ArrayList<ScheduleDayModel> getDayModels() {
        return dayModels;
    }

    public void setDayModels(ArrayList<ScheduleDayModel> dayModels) {
        this.dayModels = dayModels;
    }

    public int getIsAvailable24() {
        return is24Available;
    }

    public void setIsAvailable24(int isAvailable24) {
        this.is24Available = isAvailable24;
    }


}
