package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.BaseResponse;

import ir.mirrajabi.searchdialog.core.Searchable;

public class MembershipType extends BaseResponse implements Searchable, Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("amount_label")
    @Expose
    private String amount;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    @Override
    public String getTitle() {
        return amount;
    }

    public MembershipType() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.amount);
        dest.writeString(this.countryId);
        dest.writeString(this.type);
        dest.writeString(this.category);
        dest.writeString(this.createdAt);
    }

    protected MembershipType(Parcel in) {
        super(in);
        this.id = in.readString();
        this.amount = in.readString();
        this.countryId = in.readString();
        this.type = in.readString();
        this.category = in.readString();
        this.createdAt = in.readString();
    }

    public static final Creator<MembershipType> CREATOR = new Creator<MembershipType>() {
        @Override
        public MembershipType createFromParcel(Parcel source) {
            return new MembershipType(source);
        }

        @Override
        public MembershipType[] newArray(int size) {
            return new MembershipType[size];
        }
    };
}