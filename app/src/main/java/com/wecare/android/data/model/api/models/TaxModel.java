package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TaxModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private Integer updatedBy;
    @SerializedName("tax_rates")
    @Expose
    private List<TaxRateModel> taxRates = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<TaxRateModel> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<TaxRateModel> taxRates) {
        this.taxRates = taxRates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.createdAt);
        dest.writeValue(this.createdBy);
        dest.writeValue(this.updatedBy);
        dest.writeList(this.taxRates);
    }

    public TaxModel() {
    }

    protected TaxModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.createdAt = in.readString();
        this.createdBy = (Integer) in.readValue(Integer.class.getClassLoader());
        this.updatedBy = (Integer) in.readValue(Integer.class.getClassLoader());
        this.taxRates = new ArrayList<TaxRateModel>();
        in.readList(this.taxRates, TaxRateModel.class.getClassLoader());
    }

    public static final Creator<TaxModel> CREATOR = new Creator<TaxModel>() {
        @Override
        public TaxModel createFromParcel(Parcel source) {
            return new TaxModel(source);
        }

        @Override
        public TaxModel[] newArray(int size) {
            return new TaxModel[size];
        }
    };
}
