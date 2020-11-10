//package com.wecare.android.data.model.api.responses;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainServiceModel extends BaseResponse implements Parcelable {
//
//    /**
//     * id : 3
//     * service_name : Nursing Care
//     * service_description : Nursing Care Description
//     * icon : http://stg-storage.wecare-app.org:88/services/icons/3.png
//     * image : http://stg-storage.wecare-app.org:88/services/images/Pic3.png
//     * color : #f29b4f
//     * order_no : 1
//     */
//
//    @Expose
//    private int id;
//    @Expose
//    @SerializedName("order_no")
//    private int orderNo;
//    @Expose
//    private String icon;
//    @Expose
//    private String image;
//    @Expose
//    private String color;
//    @Expose
//    @SerializedName("service_name")
//    private String serviceName;
//    @Expose
//    @SerializedName("service_description")
//    private String serviceDescription;
//    @Expose
//    @SerializedName("sub_services")
//    private List<SubServiceResponse> subServiceResponseList;
//
//    @Expose
//    @SerializedName("no_of_selected_services")
//    private int selectedServicesNumber;
//
//
//    public int getId() {
//        return id;
//    }
//
//    public MainServiceModel setId(int id) {
//        this.id = id;
//        return this;
//    }
//
//    public int getOrderNo() {
//        return orderNo;
//    }
//
//    public MainServiceModel setOrderNo(int orderNo) {
//        this.orderNo = orderNo;
//        return this;
//    }
//
//    public String getIcon() {
//        return icon;
//    }
//
//    public MainServiceModel setIcon(String icon) {
//        this.icon = icon;
//        return this;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public MainServiceModel setImage(String image) {
//        this.image = image;
//        return this;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public MainServiceModel setColor(String color) {
//        this.color = color;
//        return this;
//    }
//
//    public String getServiceName() {
//        return serviceName;
//    }
//
//    public MainServiceModel setServiceName(String serviceName) {
//        this.serviceName = serviceName;
//        return this;
//    }
//
//    public String getServiceDescription() {
//        return serviceDescription;
//    }
//
//    public MainServiceModel setServiceDescription(String serviceDescription) {
//        this.serviceDescription = serviceDescription;
//        return this;
//    }
//
//    public List<SubServiceResponse> getSubServiceResponseList() {
//        return subServiceResponseList;
//    }
//
//    public MainServiceModel setSubServiceResponseList(List<SubServiceResponse> subServiceResponseList) {
//        this.subServiceResponseList = subServiceResponseList;
//        return this;
//    }
//
//    public int getSelectedServicesNumber() {
//        return selectedServicesNumber;
//    }
//
//    public MainServiceModel setSelectedServicesNumber(int selectedServicesNumber) {
//        this.selectedServicesNumber = selectedServicesNumber;
//        return this;
//    }
//
//    protected MainServiceModel(Parcel in) {
//        super(in);
//        id = in.readInt();
//        icon = in.readString();
//        orderNo = in.readInt();
//        image = in.readString();
//        color = in.readString();
//        serviceName = in.readString();
//        selectedServicesNumber = in.readInt();
//
//        serviceDescription = in.readString();
//        if (in.readByte() == 0x01) {
//            subServiceResponseList = new ArrayList<SubServiceResponse>();
//            in.readList(subServiceResponseList, SubServiceResponse.class.getClassLoader());
//        } else {
//            subServiceResponseList = null;
//        }
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        super.writeToParcel(dest, flags);
//        dest.writeInt(id);
//        dest.writeString(icon);
//        dest.writeInt(orderNo);
//        dest.writeString(image);
//        dest.writeString(color);
//        dest.writeString(serviceName);
//        dest.writeInt(selectedServicesNumber);
//        dest.writeString(serviceDescription);
//        if (subServiceResponseList == null) {
//            dest.writeByte((byte) (0x00));
//        } else {
//            dest.writeByte((byte) (0x01));
//            dest.writeList(subServiceResponseList);
//        }
//    }
//
//    @SuppressWarnings("unused")
//    public static final Parcelable.Creator<MainServiceModel> CREATOR = new Parcelable.Creator<MainServiceModel>() {
//        @Override
//        public MainServiceModel createFromParcel(Parcel in) {
//            return new MainServiceModel(in);
//        }
//
//        @Override
//        public MainServiceModel[] newArray(int size) {
//            return new MainServiceModel[size];
//        }
//    };
//
//}