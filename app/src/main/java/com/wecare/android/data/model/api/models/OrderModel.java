package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.data.model.api.responses.UserLocationResponse;

import java.util.List;

public class OrderModel extends BaseResponse implements Parcelable {

    /**
     * ref_profile_id : 43
     * ref_location_id : 23
     * ref_order_id :
     */

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("careseeker_id")
    @Expose
    private Integer careseekerId;
    @SerializedName("caregiver_id")
    @Expose
    private Integer caregiverId;
    @SerializedName("caregiver_age")
    @Expose
    private String caregiverAge;
    @SerializedName("caregiver_gender_id")
    @Expose
    private String caregiverGenderId;
    @SerializedName("caregiver_language_id")
    @Expose
    private String caregiverLanguageId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("estimated_time")
    @Expose
    private Integer estimatedTime;
    @SerializedName("estimated_total_amount")
    @Expose
    private String estimatedTotalAmount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("need_materials")
    @Expose
    private Integer needMaterials;
    @SerializedName("order_status")
    @Expose
    private OrderStatusModel orderStatusModel;
    @SerializedName("payment_status")
    @Expose
    private LookUpModel paymentStatus;
    @SerializedName("payment_method")
    @Expose
    private Integer paymentMethod;
    @SerializedName("payment_referenc44e")
    @Expose
    private String paymentReference;
    @SerializedName("checkin")
    @Expose
    private String checkin;
    @SerializedName("checkout")
    @Expose
    private String checkout;
    @SerializedName("caring_duration_minutes")
    @Expose
    private String caringDurationMinutes;
    @SerializedName("caring_duration_hours")
    @Expose
    private String caringDurationHours;
    @SerializedName("actual_total_amount")
    @Expose
    private String actualTotalAmount;
    @SerializedName("details_of_care_given")
    @Expose
    private String detailsOfCareGiven;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("cancel_reason_id")
    @Expose
    private String cancelReasonId;
    @SerializedName("cancel_other_reason")
    @Expose
    private String cancelOtherReason;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;

    private String ref_profile_id;
    private String ref_location_id;
    private String ref_order_id;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("rejected_reason_id")
    @Expose
    private Integer rejectedReasonId;
    @SerializedName("rejected_other_reason")
    @Expose
    private String rejectedOtherReason;
    @SerializedName("careseeker")
    @Expose
    private UserModel careseeker;
    @SerializedName("caregiver")
    @Expose
    private UserModel caregiver;
    @SerializedName("services")
    @Expose
    private List<RequiredServiceModel> services = null;
    @SerializedName("images")
    @Expose
    private List<InformationAttachmentObj> images = null;
    @SerializedName("profile")
    @Expose
    private RelativeProfileResponse profile;
    @SerializedName("location")
    @Expose
    private UserLocationResponse location;
    @SerializedName("canCancel")
    @Expose
    private Integer canCancel;

    @SerializedName("country")
    @Expose
    private CountryModel country;

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    //local
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCareseekerId() {
        return careseekerId;
    }

    public void setCareseekerId(Integer careseekerId) {
        this.careseekerId = careseekerId;
    }

    public Integer getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Integer caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getCaregiverAge() {
        return caregiverAge;
    }

    public OrderModel setCaregiverAge(String caregiverAge) {
        this.caregiverAge = caregiverAge;
        return this;
    }

    public String getCaregiverGenderId() {
        return caregiverGenderId;
    }

    public OrderModel setCaregiverGenderId(String caregiverGenderId) {
        this.caregiverGenderId = caregiverGenderId;
        return this;
    }

    public String getCaregiverLanguageId() {
        return caregiverLanguageId;
    }

    public OrderModel setCaregiverLanguageId(String caregiverLanguageId) {
        this.caregiverLanguageId = caregiverLanguageId;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getEstimatedTotalAmount() {
        return estimatedTotalAmount;
    }

    public void setEstimatedTotalAmount(String estimatedTotalAmount) {
        this.estimatedTotalAmount = estimatedTotalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNeedMaterials() {
        return needMaterials;
    }

    public void setNeedMaterials(Integer needMaterials) {
        this.needMaterials = needMaterials;
    }

    public OrderStatusModel getOrderStatusModel() {
        return orderStatusModel;
    }

    public void setOrderStatusModel(OrderStatusModel orderStatusModel) {
        this.orderStatusModel = orderStatusModel;
    }

    public LookUpModel getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(LookUpModel paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getCaringDurationMinutes() {
        return caringDurationMinutes;
    }

    public void setCaringDurationMinutes(String caringDurationMinutes) {
        this.caringDurationMinutes = caringDurationMinutes;
    }

    public String getCaringDurationHours() {
        return caringDurationHours;
    }

    public void setCaringDurationHours(String caringDurationHours) {
        this.caringDurationHours = caringDurationHours;
    }

    public String getActualTotalAmount() {
        return actualTotalAmount;
    }

    public void setActualTotalAmount(String actualTotalAmount) {
        this.actualTotalAmount = actualTotalAmount;
    }

    public String getDetailsOfCareGiven() {
        return detailsOfCareGiven;
    }

    public void setDetailsOfCareGiven(String detailsOfCareGiven) {
        this.detailsOfCareGiven = detailsOfCareGiven;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(String cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getCancelOtherReason() {
        return cancelOtherReason;
    }

    public void setCancelOtherReason(String cancelOtherReason) {
        this.cancelOtherReason = cancelOtherReason;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getRejectedReasonId() {
        return rejectedReasonId;
    }

    public void setRejectedReasonId(Integer rejectedReasonId) {
        this.rejectedReasonId = rejectedReasonId;
    }

    public String getRejectedOtherReason() {
        return rejectedOtherReason;
    }

    public void setRejectedOtherReason(String rejectedOtherReason) {
        this.rejectedOtherReason = rejectedOtherReason;
    }

    public UserModel getCareseeker() {
        return careseeker;
    }

    public void setCareseeker(UserModel careseeker) {
        this.careseeker = careseeker;
    }

    public UserModel getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(UserModel caregiver) {
        this.caregiver = caregiver;
    }

    public List<RequiredServiceModel> getServices() {
        return services;
    }

    public void setServices(List<RequiredServiceModel> services) {
        this.services = services;
    }

    public List<InformationAttachmentObj> getImages() {
        return images;
    }

    public void setImages(List<InformationAttachmentObj> images) {
        this.images = images;
    }

    public RelativeProfileResponse getProfile() {
        return profile;
    }

    public void setProfile(RelativeProfileResponse profile) {
        this.profile = profile;
    }

    public UserLocationResponse getLocation() {
        return location;
    }

    public void setLocation(UserLocationResponse location) {
        this.location = location;
    }

    public Integer getCanCancel() {
        return canCancel;
    }

    public void setCanCancel(Integer canCancel) {
        this.canCancel = canCancel;
    }

    public OrderModel() {
    }

    public String getRef_profile_id() {
        return ref_profile_id;
    }

    public void setRef_profile_id(String ref_profile_id) {
        this.ref_profile_id = ref_profile_id;
    }

    public String getRef_location_id() {
        return ref_location_id;
    }

    public void setRef_location_id(String ref_location_id) {
        this.ref_location_id = ref_location_id;
    }

    public String getRef_order_id() {
        return ref_order_id;
    }

    public void setRef_order_id(String ref_order_id) {
        this.ref_order_id = ref_order_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.id);
        dest.writeValue(this.careseekerId);
        dest.writeValue(this.caregiverId);
        dest.writeString(this.caregiverAge);
        dest.writeString(this.caregiverGenderId);
        dest.writeString(this.caregiverLanguageId);
        dest.writeString(this.date);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeValue(this.estimatedTime);
        dest.writeString(this.estimatedTotalAmount);
        dest.writeString(this.description);
        dest.writeValue(this.needMaterials);
        dest.writeParcelable(this.orderStatusModel, flags);
        dest.writeParcelable(this.paymentStatus, flags);
        dest.writeValue(this.paymentMethod);
        dest.writeString(this.paymentReference);
        dest.writeString(this.checkin);
        dest.writeString(this.checkout);
        dest.writeString(this.caringDurationMinutes);
        dest.writeString(this.caringDurationHours);
        dest.writeString(this.actualTotalAmount);
        dest.writeString(this.detailsOfCareGiven);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.countryCode);
        dest.writeString(this.cancelReasonId);
        dest.writeString(this.cancelOtherReason);
        dest.writeString(this.paidAmount);
        dest.writeString(this.ref_profile_id);
        dest.writeString(this.ref_location_id);
        dest.writeString(this.ref_order_id);
        dest.writeString(this.createdAt);
        dest.writeValue(this.rejectedReasonId);
        dest.writeString(this.rejectedOtherReason);
        dest.writeParcelable(this.careseeker, flags);
        dest.writeParcelable(this.caregiver, flags);
        dest.writeTypedList(this.services);
        dest.writeTypedList(this.images);
        dest.writeParcelable(this.profile, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeValue(this.canCancel);
        dest.writeParcelable(this.country, flags);
        dest.writeInt(this.position);
    }

    protected OrderModel(Parcel in) {
        super(in);
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.careseekerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.caregiverId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.caregiverAge = in.readString();
        this.caregiverGenderId = in.readString();
        this.caregiverLanguageId = in.readString();
        this.date = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.estimatedTime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.estimatedTotalAmount = in.readString();
        this.description = in.readString();
        this.needMaterials = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderStatusModel = in.readParcelable(OrderStatusModel.class.getClassLoader());
        this.paymentStatus = in.readParcelable(LookUpModel.class.getClassLoader());
        this.paymentMethod = (Integer) in.readValue(Integer.class.getClassLoader());
        this.paymentReference = in.readString();
        this.checkin = in.readString();
        this.checkout = in.readString();
        this.caringDurationMinutes = in.readString();
        this.caringDurationHours = in.readString();
        this.actualTotalAmount = in.readString();
        this.detailsOfCareGiven = in.readString();
        this.phoneNumber = in.readString();
        this.countryCode = in.readString();
        this.cancelReasonId = in.readString();
        this.cancelOtherReason = in.readString();
        this.paidAmount = in.readString();
        this.ref_profile_id = in.readString();
        this.ref_location_id = in.readString();
        this.ref_order_id = in.readString();
        this.createdAt = in.readString();
        this.rejectedReasonId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rejectedOtherReason = in.readString();
        this.careseeker = in.readParcelable(UserModel.class.getClassLoader());
        this.caregiver = in.readParcelable(UserModel.class.getClassLoader());
        this.services = in.createTypedArrayList(RequiredServiceModel.CREATOR);
        this.images = in.createTypedArrayList(InformationAttachmentObj.CREATOR);
        this.profile = in.readParcelable(RelativeProfileResponse.class.getClassLoader());
        this.location = in.readParcelable(UserLocationResponse.class.getClassLoader());
        this.canCancel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.country = in.readParcelable(CountryModel.class.getClassLoader());
        this.position = in.readInt();
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel source) {
            return new OrderModel(source);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };
}