package com.wecare.android.data.model.api.responses;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.TaxModel;

public class SubServiceResponse extends BaseResponse implements Parcelable {

    /**
     * id : 1
     * main_service_id : 3
     * service_name : Wound Dressings - Care
     * service_description : Changing an old wound dressing can be a little tricky, you'll need to know when to change it, how to remove it, how to clean it, how to replace it, and when to call the doctor. Why don't you let the nursing team take care of that for you?
     * duration_minutes : 60
     * need_certificate : 0
     * max_cost : 50.00
     * min_cost : 10.00
     * order_no : 3
     * service_type : quantity = 1, hours = 2
     */

    @Expose
    private int id;
    @Expose
    @SerializedName("order_no")
    private int orderNo;
    @Expose
    @SerializedName("main_service_id")
    private int mainServiceId;
    @Expose
    @SerializedName("duration_minutes")
    private int durationMinutes;
    @Expose
    @SerializedName("need_certificate")
    private int needCertificate;

    @Expose
    @SerializedName("order_selected")
    private int orderSelected;
    @Expose
    @SerializedName("service_name")
    private String serviceName;
    @Expose
    @SerializedName("service_description")
    private String serviceDescription;
    @Expose
    @SerializedName("max_cost")
    private String maxCost;
    @Expose
    @SerializedName("min_cost")
    private String minCost;
    @Expose
    @SerializedName("selected")
    private String selected;

    private String service_type;

    @SerializedName("sub_service_type")
    private int sub_service_type;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("hours")
    private int hours;

    @Expose
    @SerializedName("caregiver_service_id")
    private int caregiverServiceID;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("gender")
    private String gender;
    @SerializedName("deleted")
    private boolean deleted = false;

    @Expose
    @SerializedName("tax_class")
    private TaxModel taxModel;

    private String country_id;
    /**
     * total_taxes_percentage : 0.16
     * total_taxes_amount : 1.6
     * total_amount : 10
     * grand_total_amount : 11.6
     */

    private String total_taxes_percentage;
    private String total_taxes_amount;
    private String total_amount;
    private String grand_total_amount;
    private String price_per_hour;

    //local
    private transient String color;
    private transient int hourlyDuration = 1;
    private transient int position;
    private transient double calculatedPrice;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private transient String currency;


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getService_type() {
        return service_type;
    }

    public SubServiceResponse setService_type(String service_type) {
        this.service_type = service_type;
        return this;
    }

    public int getSub_service_type() {
        if (sub_service_type == 0)
            return sub_service_type;
        else
            return Integer.parseInt(service_type);
    }

    public void setSub_service_type(int sub_service_type) {
        this.sub_service_type = sub_service_type;
    }

    public double getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }


    public int getOrderSelected() {
        return orderSelected;
    }

    public void setOrderSelected(int orderSelected) {
        this.orderSelected = orderSelected;
    }

    public int getCaregiverServiceID() {
        return caregiverServiceID;
    }

    public void setCaregiverServiceID(int caregiverServiceID) {
        this.caregiverServiceID = caregiverServiceID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public SubServiceResponse setId(int id) {
        this.id = id;
        return this;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public SubServiceResponse setOrderNo(int orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public int getMainServiceId() {
        return mainServiceId;
    }

    public SubServiceResponse setMainServiceId(int mainServiceId) {
        this.mainServiceId = mainServiceId;
        return this;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public SubServiceResponse setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public int getNeedCertificate() {
        return needCertificate;
    }

    public SubServiceResponse setNeedCertificate(int needCertificate) {
        this.needCertificate = needCertificate;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public SubServiceResponse setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public SubServiceResponse setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
        return this;
    }

    public String getMaxCost() {
        return maxCost;
    }

    public SubServiceResponse setMaxCost(String maxCost) {
        this.maxCost = maxCost;
        return this;
    }

    public String getMinCost() {
        return minCost;
    }

    public SubServiceResponse setMinCost(String minCost) {
        this.minCost = minCost;
        return this;
    }

    public String getColor() {
        return color;
    }

    public SubServiceResponse setColor(String color) {
        this.color = color;
        return this;
    }

    public int getHourlyDuration() {
        return hourlyDuration;
    }

    public SubServiceResponse setHourlyDuration(int hourlyDuration) {
        this.hourlyDuration = hourlyDuration;
        return this;
    }

    public String getCountry_id() {
        return country_id;
    }

    public SubServiceResponse setCountry_id(String country_id) {
        this.country_id = country_id;
        return this;
    }

    public TaxModel getTaxModel() {
        return taxModel;
    }

    public void setTaxModel(TaxModel taxModel) {
        this.taxModel = taxModel;
    }

    public SubServiceResponse() {
    }

    public String getTotal_taxes_percentage() {
        return total_taxes_percentage;
    }

    public void setTotal_taxes_percentage(String total_taxes_percentage) {
        this.total_taxes_percentage = total_taxes_percentage;
    }

    public String getTotal_taxes_amount() {
        return total_taxes_amount;
    }

    public void setTotal_taxes_amount(String total_taxes_amount) {
        this.total_taxes_amount = total_taxes_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getGrand_total_amount() {
        return grand_total_amount;
    }

    public void setGrand_total_amount(String grand_total_amount) {
        this.grand_total_amount = grand_total_amount;
    }

    public String getPrice_per_hour() {
        return price_per_hour;
    }

    public SubServiceResponse setPrice_per_hour(String price_per_hour) {
        this.price_per_hour = price_per_hour;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeInt(this.orderNo);
        dest.writeInt(this.mainServiceId);
        dest.writeInt(this.durationMinutes);
        dest.writeInt(this.needCertificate);
        dest.writeInt(this.orderSelected);
        dest.writeString(this.serviceName);
        dest.writeString(this.serviceDescription);
        dest.writeString(this.maxCost);
        dest.writeString(this.minCost);
        dest.writeString(this.selected);
        dest.writeString(this.service_type);
        dest.writeInt(this.sub_service_type);
        dest.writeInt(this.quantity);
        dest.writeInt(this.hours);
        dest.writeInt(this.caregiverServiceID);
        dest.writeString(this.status);
        dest.writeString(this.price);
        dest.writeString(this.gender);
        dest.writeByte(this.deleted ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.taxModel, flags);
        dest.writeString(this.country_id);
        dest.writeString(this.total_taxes_percentage);
        dest.writeString(this.total_taxes_amount);
        dest.writeString(this.total_amount);
        dest.writeString(this.grand_total_amount);
        dest.writeString(this.price_per_hour);
    }

    protected SubServiceResponse(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.orderNo = in.readInt();
        this.mainServiceId = in.readInt();
        this.durationMinutes = in.readInt();
        this.needCertificate = in.readInt();
        this.orderSelected = in.readInt();
        this.serviceName = in.readString();
        this.serviceDescription = in.readString();
        this.maxCost = in.readString();
        this.minCost = in.readString();
        this.selected = in.readString();
        this.service_type = in.readString();
        this.sub_service_type = in.readInt();
        this.quantity = in.readInt();
        this.hours = in.readInt();
        this.caregiverServiceID = in.readInt();
        this.status = in.readString();
        this.price = in.readString();
        this.gender = in.readString();
        this.deleted = in.readByte() != 0;
        this.taxModel = in.readParcelable(TaxModel.class.getClassLoader());
        this.country_id = in.readString();
        this.total_taxes_percentage = in.readString();
        this.total_taxes_amount = in.readString();
        this.total_amount = in.readString();
        this.grand_total_amount = in.readString();
        this.price_per_hour = in.readString();
    }

    public static final Creator<SubServiceResponse> CREATOR = new Creator<SubServiceResponse>() {
        @Override
        public SubServiceResponse createFromParcel(Parcel source) {
            return new SubServiceResponse(source);
        }

        @Override
        public SubServiceResponse[] newArray(int size) {
            return new SubServiceResponse[size];
        }
    };
}