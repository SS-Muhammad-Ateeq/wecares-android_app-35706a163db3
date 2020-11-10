package com.wecare.android.data.model.api.responses;

import android.os.Parcel;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;

public class UserLocationResponse extends BaseResponse{

    /**
     * id : 1
     * user_id : 37
     * name : address 1
     * country_id :
     * city : null
     * country :
     * area : 1
     * street_name : 2332444
     * building_number : 322
     * floor_number : 1
     * latitude :
     * longitude :
     */

    private String id;
    private String user_id;
    private String floor_number;
    private String building_number;
    private String name;
    private String area;
    private String street_name;
    private String latitude;
    private String longitude;
    private String country_id;
    private CityModel city;
    private CountryModel country;

    public String getId() {
        return id;
    }

    public UserLocationResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getUser_id() {
        return user_id;
    }

    public UserLocationResponse setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getFloor_number() {
        return floor_number;
    }

    public UserLocationResponse setFloor_number(String floor_number) {
        this.floor_number = floor_number;
        return this;
    }

    public String getBuilding_number() {
        return building_number;
    }

    public UserLocationResponse setBuilding_number(String building_number) {
        this.building_number = building_number;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserLocationResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getArea() {
        return area;
    }

    public UserLocationResponse setArea(String area) {
        this.area = area;
        return this;
    }

    public String getStreet_name() {
        return street_name;
    }

    public UserLocationResponse setStreet_name(String street_name) {
        this.street_name = street_name;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public UserLocationResponse setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public UserLocationResponse setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getCountry_id() {
        return country_id;
    }

    public UserLocationResponse setCountry_id(String country_id) {
        this.country_id = country_id;
        return this;
    }

    public CityModel getCity() {
        return city;
    }

    public UserLocationResponse setCity(CityModel city) {
        this.city = city;
        return this;
    }

    public CountryModel getCountry() {
        return country;
    }

    public UserLocationResponse setCountry(CountryModel country) {
        this.country = country;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.user_id);
        dest.writeString(this.floor_number);
        dest.writeString(this.building_number);
        dest.writeString(this.name);
        dest.writeString(this.area);
        dest.writeString(this.street_name);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.country_id);
        dest.writeParcelable(this.city, flags);
        dest.writeParcelable(this.country, flags);
    }

    public UserLocationResponse() {
    }

    protected UserLocationResponse(Parcel in) {
        super(in);
        this.id = in.readString();
        this.user_id = in.readString();
        this.floor_number = in.readString();
        this.building_number = in.readString();
        this.name = in.readString();
        this.area = in.readString();
        this.street_name = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.country_id = in.readString();
        this.city = in.readParcelable(CityModel.class.getClassLoader());
        this.country = in.readParcelable(CountryModel.class.getClassLoader());
    }

    public static final Creator<UserLocationResponse> CREATOR = new Creator<UserLocationResponse>() {
        @Override
        public UserLocationResponse createFromParcel(Parcel source) {
            return new UserLocationResponse(source);
        }

        @Override
        public UserLocationResponse[] newArray(int size) {
            return new UserLocationResponse[size];
        }
    };
}
