package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverallRating {

@SerializedName("1")
@Expose
private Integer appointmentCommitment;
@SerializedName("2")
@Expose
private Integer locationReadiness;
@SerializedName("3")
@Expose
private Integer providingCustomizedTools;
@SerializedName("4")
@Expose
private Integer hospitalityLiterature;
@SerializedName("5")
@Expose
private Integer addressAccuracy;
    @SerializedName("6")
    @Expose
    private Integer appearance;
    @SerializedName("7")
    @Expose
    private Integer cleanliness;
    @SerializedName("8")
    @Expose
    private Integer performance;
    @SerializedName("9")
    @Expose
    private Integer timeAttendance;
    @SerializedName("10")
    @Expose
    private Integer qualityOfService;

    public Integer getAppearance() {
        return appearance;
    }

    public void setAppearance(Integer appearance) {
        this.appearance = appearance;
    }

    public Integer getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(Integer cleanliness) {
        this.cleanliness = cleanliness;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Integer getTimeAttendance() {
        return timeAttendance;
    }

    public void setTimeAttendance(Integer timeAttendance) {
        this.timeAttendance = timeAttendance;
    }

    public Integer getQualityOfService() {
        return qualityOfService;
    }

    public void setQualityOfService(Integer qualityOfService) {
        this.qualityOfService = qualityOfService;
    }

    public Integer getAppointmentCommitment() {
        return appointmentCommitment;
    }

    public void setAppointmentCommitment(Integer appointmentCommitment) {
        this.appointmentCommitment = appointmentCommitment;
    }

    public Integer getLocationReadiness() {
        return locationReadiness;
    }

    public void setLocationReadiness(Integer locationReadiness) {
        this.locationReadiness = locationReadiness;
    }

    public Integer getProvidingCustomizedTools() {
        return providingCustomizedTools;
    }

    public void setProvidingCustomizedTools(Integer providingCustomizedTools) {
        this.providingCustomizedTools = providingCustomizedTools;
    }

    public Integer getHospitalityLiterature() {
        return hospitalityLiterature;
    }

    public void setHospitalityLiterature(Integer hospitalityLiterature) {
        this.hospitalityLiterature = hospitalityLiterature;
    }

    public Integer getAddressAccuracy() {
        return addressAccuracy;
    }

    public void setAddressAccuracy(Integer addressAccuracy) {
        this.addressAccuracy = addressAccuracy;
    }
}