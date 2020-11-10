package com.wecare.android.data.model.api.responses;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.CountryModel;

import java.util.ArrayList;

public class CountriesResponse extends BaseResponse {

    public ArrayList<CountryModel> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<CountryModel> countries) {
        this.countries = countries;
    }

    @Expose
    @SerializedName("items")
    private ArrayList<CountryModel> countries;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.countries);
    }

    public CountriesResponse() {
    }

    protected CountriesResponse(Parcel in) {
        super(in);
        this.countries = in.createTypedArrayList(CountryModel.CREATOR);
    }

    public static final Creator<CountriesResponse> CREATOR = new Creator<CountriesResponse>() {
        @Override
        public CountriesResponse createFromParcel(Parcel source) {
            return new CountriesResponse(source);
        }

        @Override
        public CountriesResponse[] newArray(int size) {
            return new CountriesResponse[size];
        }
    };
}
