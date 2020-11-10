package com.wecare.android.data.model.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesTotalModel {

@SerializedName("inactive")
@Expose
private String inactive;
@SerializedName("active")
@Expose
private String active;
@SerializedName("underReview")
@Expose
private String underReview;

public String getInactive() {
return inactive;
}

public void setInactive(String inactive) {
this.inactive = inactive;
}

public String getActive() {
return active;
}

public void setActive(String active) {
this.active = active;
}

public String getUnderReview() {
return underReview;
}

public void setUnderReview(String underReview) {
this.underReview = underReview;
}

}