package com.wecare.android.data.model.api.models;

import android.widget.LinearLayout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleDayModel {

    @SerializedName("week_day")
    @Expose
    int CurrentDay;

    @SerializedName("start_time")
    @Expose
     String startTime;

    @SerializedName("end_time")
    @Expose
     String endTime;

    public ScheduleDayModel() {
    }
//those added to validate start hour selected

    transient Integer  startHour;
    transient Integer startMinute;

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ScheduleDayModel(int currentDay) {
        CurrentDay = currentDay;
    }

    public int getCurrentDay() {
        return CurrentDay;
    }

    public void setCurrentDay(int currentDay) {
        CurrentDay = currentDay;
    }

}