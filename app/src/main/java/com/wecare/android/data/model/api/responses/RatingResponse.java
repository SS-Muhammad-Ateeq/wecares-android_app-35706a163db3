package com.wecare.android.data.model.api.responses;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingResponse extends BaseResponse {

@SerializedName("order_id")
@Expose
private String orderId;
@SerializedName("description")
@Expose
private String description;
@SerializedName("base_rating")
@Expose
private String baseRating;
@SerializedName("overall_rating")
@Expose
private Integer overallRating;
@SerializedName("caregiver_id")
@Expose
private Integer caregiverId;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("user_ip")
@Expose
private String userIp;
@SerializedName("id")
@Expose
private Integer id;

public String getOrderId() {
return orderId;
}

public void setOrderId(String orderId) {
this.orderId = orderId;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getBaseRating() {
return baseRating;
}

public void setBaseRating(String baseRating) {
this.baseRating = baseRating;
}

public Integer getOverallRating() {
return overallRating;
}

public void setOverallRating(Integer overallRating) {
this.overallRating = overallRating;
}

public Integer getCaregiverId() {
return caregiverId;
}

public void setCaregiverId(Integer caregiverId) {
this.caregiverId = caregiverId;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getUserIp() {
return userIp;
}

public void setUserIp(String userIp) {
this.userIp = userIp;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.orderId);
        dest.writeString(this.description);
        dest.writeString(this.baseRating);
        dest.writeValue(this.overallRating);
        dest.writeValue(this.caregiverId);
        dest.writeValue(this.userId);
        dest.writeString(this.userIp);
        dest.writeValue(this.id);
    }

    public RatingResponse() {
    }

    protected RatingResponse(Parcel in) {
        super(in);
        this.orderId = in.readString();
        this.description = in.readString();
        this.baseRating = in.readString();
        this.overallRating = (Integer) in.readValue(Integer.class.getClassLoader());
        this.caregiverId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userIp = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<RatingResponse> CREATOR = new Creator<RatingResponse>() {
        @Override
        public RatingResponse createFromParcel(Parcel source) {
            return new RatingResponse(source);
        }

        @Override
        public RatingResponse[] newArray(int size) {
            return new RatingResponse[size];
        }
    };
}