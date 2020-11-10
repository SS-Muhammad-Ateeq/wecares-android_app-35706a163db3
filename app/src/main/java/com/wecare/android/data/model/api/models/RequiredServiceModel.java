package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;

public class RequiredServiceModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("main_service_id")
    @Expose
    private Integer mainServiceId;
    @SerializedName("sub_service_id")
    @Expose
    private Integer subServiceId;
    @SerializedName("caregiver_service_id")
    @Expose
    private Integer caregiverServiceId;
    @SerializedName("sub_service_type")
    @Expose
    private Integer subServiceType;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("price_per_hour")
    @Expose
    private String pricePerHour;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("sub_service")
    @Expose
    private SubServiceResponse subService;
    @SerializedName("main_service")
    @Expose
    private MainServiceModel mainService;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMainServiceId() {
        return mainServiceId;
    }

    public void setMainServiceId(Integer mainServiceId) {
        this.mainServiceId = mainServiceId;
    }

    public Integer getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(Integer subServiceId) {
        this.subServiceId = subServiceId;
    }

    public Integer getCaregiverServiceId() {
        return caregiverServiceId;
    }

    public void setCaregiverServiceId(Integer caregiverServiceId) {
        this.caregiverServiceId = caregiverServiceId;
    }

    public Integer getSubServiceType() {
        return subServiceType;
    }

    public void setSubServiceType(Integer subServiceType) {
        this.subServiceType = subServiceType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SubServiceResponse getSubService() {
        return subService;
    }

    public void setSubService(SubServiceResponse subService) {
        this.subService = subService;
    }

    public MainServiceModel getMainService() {
        return mainService;
    }

    public void setMainService(MainServiceModel mainService) {
        this.mainService = mainService;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.orderId);
        dest.writeValue(this.mainServiceId);
        dest.writeValue(this.subServiceId);
        dest.writeValue(this.caregiverServiceId);
        dest.writeValue(this.subServiceType);
        dest.writeValue(this.quantity);
        dest.writeValue(this.hours);
        dest.writeString(this.pricePerHour);
        dest.writeString(this.totalAmount);
        dest.writeParcelable(this.subService, flags);
        dest.writeParcelable(this.mainService, flags);
    }

    public RequiredServiceModel() {
    }

    protected RequiredServiceModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mainServiceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subServiceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.caregiverServiceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subServiceType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.hours = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pricePerHour = in.readString();
        this.totalAmount = in.readString();
        this.subService = in.readParcelable(SubServiceResponse.class.getClassLoader());
        this.mainService = in.readParcelable(MainServiceModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<RequiredServiceModel> CREATOR = new Parcelable.Creator<RequiredServiceModel>() {
        @Override
        public RequiredServiceModel createFromParcel(Parcel source) {
            return new RequiredServiceModel(source);
        }

        @Override
        public RequiredServiceModel[] newArray(int size) {
            return new RequiredServiceModel[size];
        }
    };
}