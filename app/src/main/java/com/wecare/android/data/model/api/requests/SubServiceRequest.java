package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubServiceRequest {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("caregiver_service_id")
@Expose
private Integer caregiverServiceId;
@SerializedName("sub_service_type")
@Expose
private Integer subServiceType;
@SerializedName("quantity")
@Expose
private Integer quantity;
@SerializedName("hours")
@Expose
private Integer hours;
@SerializedName("deleted")
@Expose
private Boolean deleted;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getCaregiverServiceId() {
return caregiverServiceId;
}

public void setCaregiverServiceId(Integer caregiverServiceId) {
this.caregiverServiceId = caregiverServiceId;
}

public Integer getSubServiceType() {
return subServiceType;
}

public void setSubServiceType(Integer subServiceType) {
this.subServiceType = subServiceType;
}

public Integer getQuantity() {
return quantity;
}

public void setQuantity(Integer quantity) {
this.quantity = quantity;
}

public Integer getHours() {
return hours;
}

public void setHours(Integer hours) {
this.hours = hours;
}

public Boolean getDeleted() {
return deleted;
}

public void setDeleted(Boolean deleted) {
this.deleted = deleted;
}

}