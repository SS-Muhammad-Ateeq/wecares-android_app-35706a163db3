package com.wecare.android.data.model.api.responses;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainServiceModel extends BaseResponse implements Parcelable {


    /**
     * id : 3
     * service_name : Nursing Care
     * service_description : Nursing Care Description
     * icon : http://stg-storage.wecare-app.org:88/services/icons/3.png
     * image : http://stg-storage.wecare-app.org:88/services/images/Pic3.png
     * color : #f29b4f
     * order_no : 1
     */

    @Expose
    private int id;
    @Expose
    @SerializedName("order_no")
    private int orderNo;
    @Expose
    private String icon;
    @Expose
    private String image;
    @Expose
    private String color;
    @Expose
    @SerializedName("service_name")
    private String serviceName;
    @Expose
    @SerializedName("service_description")
    private String serviceDescription;
    @Expose
    @SerializedName("sub_services")
    private ArrayList<SubServiceResponse> subServiceResponseList;

    @Expose
    @SerializedName("no_of_selected_services")
    private int selectedServicesNumber;

    //used in get recommended caregiver request
    @Expose
    @SerializedName("caregiver_id")
    private String caregiver_id;

    /**
     * total_taxes_percentage : 0.16
     * total_taxes_amount : 1.6
     * total_amount : 10
     */

    private String grand_total_amount;
    private String total_taxes_percentage;
    private String total_taxes_amount;
    private String total_amount;
    /**
     * used only in searchGiverResponse as alaa return object :???//////
     */
    @SerializedName("subService")
    private SubServiceResponse subServiceResponse;

    //this one is used in get orders request.
    private String price_per_hour;

    public int getId() {
        return id;
    }

    public MainServiceModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public MainServiceModel setOrderNo(int orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public MainServiceModel setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getImage() {
        return image;
    }

    public MainServiceModel setImage(String image) {
        this.image = image;
        return this;
    }

    public String getColor() {
        return color;
    }

    public MainServiceModel setColor(String color) {
        this.color = color;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public MainServiceModel setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public MainServiceModel setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
        return this;
    }

    public ArrayList<SubServiceResponse> getSubServiceResponseList() {
        if (subServiceResponseList == null) {
            subServiceResponseList = new ArrayList<>();
        }
        return subServiceResponseList;
    }

    public MainServiceModel setSubServiceResponseList(ArrayList<SubServiceResponse> subServiceResponseList) {
        this.subServiceResponseList = subServiceResponseList;
        return this;
    }

    public String getCaregiver_id() {
        return caregiver_id;
    }

    public MainServiceModel setCaregiver_id(String caregiver_id) {
        this.caregiver_id = caregiver_id;
        return this;
    }

    public int getSelectedServicesNumber() {
        return selectedServicesNumber;
    }

    public void setSelectedServicesNumber(int selectedServicesNumber) {
        this.selectedServicesNumber = selectedServicesNumber;
    }

    public String getGrand_total_amount() {
        return grand_total_amount;
    }

    public MainServiceModel setGrand_total_amount(String grand_total_amount) {
        this.grand_total_amount = grand_total_amount;
        return this;
    }

    public String getTotal_taxes_percentage() {
        return total_taxes_percentage;
    }

    public MainServiceModel setTotal_taxes_percentage(String total_taxes_percentage) {
        this.total_taxes_percentage = total_taxes_percentage;
        return this;
    }

    public String getTotal_taxes_amount() {
        return total_taxes_amount;
    }

    public MainServiceModel setTotal_taxes_amount(String total_taxes_amount) {
        this.total_taxes_amount = total_taxes_amount;
        return this;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getPrice_per_hour() {
        return price_per_hour;
    }

    public MainServiceModel setPrice_per_hour(String price_per_hour) {
        this.price_per_hour = price_per_hour;
        return this;
    }

    public MainServiceModel setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
        return this;
    }

    public SubServiceResponse getSubServiceResponse() {
        return subServiceResponse;
    }

    public MainServiceModel setSubServiceResponse(SubServiceResponse subServiceResponse) {
        this.subServiceResponse = subServiceResponse;
        return this;
    }

    public MainServiceModel() {
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
        dest.writeString(this.icon);
        dest.writeString(this.image);
        dest.writeString(this.color);
        dest.writeString(this.serviceName);
        dest.writeString(this.serviceDescription);
        dest.writeTypedList(this.subServiceResponseList);
        dest.writeInt(this.selectedServicesNumber);
        dest.writeString(this.caregiver_id);
        dest.writeString(this.grand_total_amount);
        dest.writeString(this.total_taxes_percentage);
        dest.writeString(this.total_taxes_amount);
        dest.writeString(this.total_amount);
        dest.writeParcelable(this.subServiceResponse, flags);
        dest.writeString(this.price_per_hour);
    }

    protected MainServiceModel(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.orderNo = in.readInt();
        this.icon = in.readString();
        this.image = in.readString();
        this.color = in.readString();
        this.serviceName = in.readString();
        this.serviceDescription = in.readString();
        this.subServiceResponseList = in.createTypedArrayList(SubServiceResponse.CREATOR);
        this.selectedServicesNumber = in.readInt();
        this.caregiver_id = in.readString();
        this.grand_total_amount = in.readString();
        this.total_taxes_percentage = in.readString();
        this.total_taxes_amount = in.readString();
        this.total_amount = in.readString();
        this.subServiceResponse = in.readParcelable(SubServiceResponse.class.getClassLoader());
        this.price_per_hour = in.readString();
    }

    public static final Creator<MainServiceModel> CREATOR = new Creator<MainServiceModel>() {
        @Override
        public MainServiceModel createFromParcel(Parcel source) {
            return new MainServiceModel(source);
        }

        @Override
        public MainServiceModel[] newArray(int size) {
            return new MainServiceModel[size];
        }
    };
}