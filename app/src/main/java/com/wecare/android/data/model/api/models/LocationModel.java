package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.BaseResponse;

public class LocationModel extends BaseResponse implements Parcelable  {

    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("building_number")
    @Expose
    private Integer buildingNumber;
    @SerializedName("floor_number")
    @Expose
    private Integer floorNumber;
    @SerializedName("caregiver_id")
    @Expose
    private Integer caregiverId;

    @SerializedName("city")
    @Expose
    private CityModel city;

    @SerializedName("country")
    @Expose
    private CountryModel country;

    @SerializedName("street_name")
    @Expose
    private String streetName;

    @SerializedName("area")
    @Expose
    private String area;

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public LocationModel() {
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Integer caregiverId) {
        this.caregiverId = caregiverId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.longitude);
        dest.writeValue(this.latitude);
        dest.writeValue(this.type);
        dest.writeValue(this.id);
        dest.writeValue(this.buildingNumber);
        dest.writeValue(this.floorNumber);
        dest.writeValue(this.caregiverId);
        dest.writeParcelable(this.city, flags);
        dest.writeParcelable(this.country, flags);
        dest.writeString(this.streetName);
        dest.writeString(this.area);
        dest.writeString(this.name);
    }

    protected LocationModel(Parcel in) {
        super(in);
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.buildingNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.floorNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.caregiverId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.city = in.readParcelable(CityModel.class.getClassLoader());
        this.country = in.readParcelable(CountryModel.class.getClassLoader());
        this.streetName = in.readString();
        this.area = in.readString();
        this.name = in.readString();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel source) {
            return new LocationModel(source);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };
}