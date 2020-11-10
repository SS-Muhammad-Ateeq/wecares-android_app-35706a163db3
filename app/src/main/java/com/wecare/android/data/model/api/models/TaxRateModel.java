package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxRateModel implements Parcelable {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("rate")
@Expose
private String rate;
@SerializedName("type")
@Expose
private Integer type;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("created_by")
@Expose
private Integer createdBy;
@SerializedName("updated_by")
@Expose
private Integer updatedBy;
@SerializedName("name_ar")
@Expose
private String nameAr;
@SerializedName("type_label")
@Expose
private String typeLabel;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getRate() {
return rate;
}

public void setRate(String rate) {
this.rate = rate;
}

public Integer getType() {
return type;
}

public void setType(Integer type) {
this.type = type;
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

public String getNameAr() {
return nameAr;
}

public void setNameAr(String nameAr) {
this.nameAr = nameAr;
}

public String getTypeLabel() {
return typeLabel;
}

public void setTypeLabel(String typeLabel) {
this.typeLabel = typeLabel;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.rate);
        dest.writeValue(this.type);
        dest.writeString(this.createdAt);
        dest.writeValue(this.createdBy);
        dest.writeValue(this.updatedBy);
        dest.writeString(this.nameAr);
        dest.writeString(this.typeLabel);
    }

    public TaxRateModel() {
    }

    protected TaxRateModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.rate = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdAt = in.readString();
        this.createdBy = (Integer) in.readValue(Integer.class.getClassLoader());
        this.updatedBy = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nameAr = in.readString();
        this.typeLabel = in.readString();
    }

    public static final Creator<TaxRateModel> CREATOR = new Creator<TaxRateModel>() {
        @Override
        public TaxRateModel createFromParcel(Parcel source) {
            return new TaxRateModel(source);
        }

        @Override
        public TaxRateModel[] newArray(int size) {
            return new TaxRateModel[size];
        }
    };
}