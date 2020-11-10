package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingRequest {

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
private OverallRating overallRating;

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

public OverallRating getOverallRating() {
return overallRating;
}

public void setOverallRating(OverallRating overallRating) {
this.overallRating = overallRating;
}

}