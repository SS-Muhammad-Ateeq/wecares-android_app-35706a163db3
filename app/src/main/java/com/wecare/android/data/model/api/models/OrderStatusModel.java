package com.wecare.android.data.model.api.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.utils.AppConstants;

public class OrderStatusModel extends BaseResponse {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("englishName")
@Expose
private String englishName;
@SerializedName("arabicName")
@Expose
private String arabicName;
@SerializedName("description")
@Expose
private String description;
@SerializedName("user_type")
@Expose
private Integer userType;


public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getEnglishName() {
    return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? englishName : arabicName;
}

public void setEnglishName(String englishName) {
this.englishName = englishName;
}

public String getArabicName() {
return arabicName;
}

public void setArabicName(String arabicName) {
this.arabicName = arabicName;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Integer getUserType() {
return userType;
}

public void setUserType(Integer userType) {
this.userType = userType;
}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.id);
        dest.writeString(this.englishName);
        dest.writeString(this.arabicName);
        dest.writeString(this.description);
        dest.writeValue(this.userType);
    }

    public OrderStatusModel() {
    }

    protected OrderStatusModel(Parcel in) {
        super(in);
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.englishName = in.readString();
        this.arabicName = in.readString();
        this.description = in.readString();
        this.userType = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<OrderStatusModel> CREATOR = new Creator<OrderStatusModel>() {
        @Override
        public OrderStatusModel createFromParcel(Parcel source) {
            return new OrderStatusModel(source);
        }

        @Override
        public OrderStatusModel[] newArray(int size) {
            return new OrderStatusModel[size];
        }
    };
}