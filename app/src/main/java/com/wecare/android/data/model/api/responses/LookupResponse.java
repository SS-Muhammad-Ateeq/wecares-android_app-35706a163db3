package com.wecare.android.data.model.api.responses;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.LookUpModel;

import java.util.ArrayList;
import java.util.List;

public class LookupResponse extends BaseResponse implements Parcelable {

    @SerializedName("gender")
    @Expose
    private ArrayList<LookUpModel> genderArrayList = null;


    @SerializedName("provider_service_genders")
    @Expose
    private ArrayList<LookUpModel> providerServiceGendersArrayList = null;

    @SerializedName("languages")
    @Expose
    private ArrayList<LookUpModel> languageArrayList = null;

    @SerializedName("caregiver_rating_types")
    @Expose
    private ArrayList<LookUpModel> caregiverRatingTypeArrayList = null;

    @SerializedName("careseeker_rating_types")
    @Expose
    private ArrayList<LookUpModel> careSeekerRatingTypeArrayList = null;

    @SerializedName("payment_methods")
    @Expose
    private ArrayList<LookUpModel> paymentMethodTypeArrayList = null;

    @SerializedName("contact_us_types")
    @Expose
    private ArrayList<LookUpModel> contactUsTypesTypeArrayList = null;

    @SerializedName("relationships")
    @Expose
    private ArrayList<LookUpModel> relationshipsArrayList = null;

    @SerializedName("blood_types")
    @Expose
    private ArrayList<LookUpModel> bloodTypesArrayList = null;

    @SerializedName("education_degree")
    @Expose
    private ArrayList<LookUpModel> educationDegreeArrayList = null;

    @SerializedName("caregiver_age")
    @Expose
    private ArrayList<LookUpModel> caregiverAgeArrayList = null;

    @SerializedName("insurance_types")
    @Expose
    private ArrayList<LookUpModel> insuranceTypesArrayList = null;


    @SerializedName("attachment_categories")
    @Expose
    private ArrayList<LookUpModel> attachmentCategories = null;

    @SerializedName("caregiver_issuers_of_certificates")
    @Expose
    private ArrayList<LookUpModel> caregiverIssuersOfCertificates = null;

    private List<LookUpModel> caregiver_order_status;
    private List<LookUpModel> careseeker_order_status;
    private List<LookUpModel> admin_order_status;
    private List<LookUpModel> caregiver_user_status;
    private List<LookUpModel> caregiver_order_cancel_reasons;
    private List<LookUpModel> careseeker_order_cancel_reasons;
    private List<LookUpModel> caregiver_order_rejected_reasons;


    public ArrayList<LookUpModel> getGenderArrayList() {
        return genderArrayList;
    }

    public LookupResponse setGenderArrayList(ArrayList<LookUpModel> genderArrayList) {
        this.genderArrayList = genderArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getLanguageArrayList() {
        return languageArrayList;
    }

    public LookupResponse setLanguageArrayList(ArrayList<LookUpModel> languageArrayList) {
        this.languageArrayList = languageArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getCaregiverRatingTypeArrayList() {
        return caregiverRatingTypeArrayList;
    }

    public LookupResponse setCaregiverRatingTypeArrayList(ArrayList<LookUpModel> caregiverRatingTypeArrayList) {
        this.caregiverRatingTypeArrayList = caregiverRatingTypeArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getCareSeekerRatingTypeArrayList() {
        return careSeekerRatingTypeArrayList;
    }

    public LookupResponse setCareSeekerRatingTypeArrayList(ArrayList<LookUpModel> careSeekerRatingTypeArrayList) {
        this.careSeekerRatingTypeArrayList = careSeekerRatingTypeArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getPaymentMethodTypeArrayList() {
        return paymentMethodTypeArrayList;
    }

    public LookupResponse setPaymentMethodTypeArrayList(ArrayList<LookUpModel> paymentMethodTypeArrayList) {
        this.paymentMethodTypeArrayList = paymentMethodTypeArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getContactUsTypesTypeArrayList() {
        return contactUsTypesTypeArrayList;
    }

    public LookupResponse setContactUsTypesTypeArrayList(ArrayList<LookUpModel> contactUsTypesTypeArrayList) {
        this.contactUsTypesTypeArrayList = contactUsTypesTypeArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getRelationshipsArrayList() {
        return relationshipsArrayList;
    }

    public LookupResponse setRelationshipsArrayList(ArrayList<LookUpModel> relationshipsArrayList) {
        this.relationshipsArrayList = relationshipsArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getBloodTypesArrayList() {
        return bloodTypesArrayList;
    }

    public LookupResponse setBloodTypesArrayList(ArrayList<LookUpModel> bloodTypesArrayList) {
        this.bloodTypesArrayList = bloodTypesArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getEducationDegreeArrayList() {
        return educationDegreeArrayList;
    }

    public LookupResponse setEducationDegreeArrayList(ArrayList<LookUpModel> educationDegreeArrayList) {
        this.educationDegreeArrayList = educationDegreeArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getCaregiverAgeArrayList() {
        return caregiverAgeArrayList;
    }

    public LookupResponse setCaregiverAgeArrayList(ArrayList<LookUpModel> caregiverAgeArrayList) {
        this.caregiverAgeArrayList = caregiverAgeArrayList;
        return this;
    }

    public ArrayList<LookUpModel> getAttachmentCategories() {
        return attachmentCategories;
    }

    public LookupResponse setAttachmentCategories(ArrayList<LookUpModel> attachmentCategories) {
        this.attachmentCategories = attachmentCategories;
        return this;
    }

    public ArrayList<LookUpModel> getCaregiverIssuersOfCertificates() {
        return caregiverIssuersOfCertificates;
    }

    public LookupResponse setCaregiverIssuersOfCertificates(ArrayList<LookUpModel> caregiverIssuersOfCertificates) {
        this.caregiverIssuersOfCertificates = caregiverIssuersOfCertificates;
        return this;
    }

    public List<LookUpModel> getCaregiver_order_status() {
        return caregiver_order_status;
    }

    public LookupResponse setCaregiver_order_status(List<LookUpModel> caregiver_order_status) {
        this.caregiver_order_status = caregiver_order_status;
        return this;
    }

    public List<LookUpModel> getCareseeker_order_status() {
        return careseeker_order_status;
    }

    public LookupResponse setCareseeker_order_status(List<LookUpModel> careseeker_order_status) {
        this.careseeker_order_status = careseeker_order_status;
        return this;
    }

    public List<LookUpModel> getAdmin_order_status() {
        return admin_order_status;
    }

    public LookupResponse setAdmin_order_status(List<LookUpModel> admin_order_status) {
        this.admin_order_status = admin_order_status;
        return this;
    }

    public List<LookUpModel> getCaregiver_user_status() {
        return caregiver_user_status;
    }

    public LookupResponse setCaregiver_user_status(List<LookUpModel> caregiver_user_status) {
        this.caregiver_user_status = caregiver_user_status;
        return this;
    }

    public List<LookUpModel> getCaregiver_order_cancel_reasons() {
        return caregiver_order_cancel_reasons;
    }

    public LookupResponse setCaregiver_order_cancel_reasons(List<LookUpModel> caregiver_order_cancel_reasons) {
        this.caregiver_order_cancel_reasons = caregiver_order_cancel_reasons;
        return this;
    }

    public List<LookUpModel> getCareseeker_order_cancel_reasons() {
        return careseeker_order_cancel_reasons;
    }

    public LookupResponse setCareseeker_order_cancel_reasons(List<LookUpModel> careseeker_order_cancel_reasons) {
        this.careseeker_order_cancel_reasons = careseeker_order_cancel_reasons;
        return this;
    }

    public List<LookUpModel> getCaregiver_order_rejected_reasons() {
        return caregiver_order_rejected_reasons;
    }

    public LookupResponse setCaregiver_order_rejected_reasons(List<LookUpModel> caregiver_order_rejected_reasons) {
        this.caregiver_order_rejected_reasons = caregiver_order_rejected_reasons;
        return this;
    }

    public ArrayList<LookUpModel> getProviderServiceGendersArrayList() {
        return providerServiceGendersArrayList;
    }

    public void setProviderServiceGendersArrayList(ArrayList<LookUpModel> providerServiceGendersArrayList) {
        this.providerServiceGendersArrayList = providerServiceGendersArrayList;
    }

    public ArrayList<LookUpModel> getInsuranceTypesArrayList() {
        return insuranceTypesArrayList;
    }

    public LookupResponse setInsuranceTypesArrayList(ArrayList<LookUpModel> insuranceTypesArrayList) {
        this.insuranceTypesArrayList = insuranceTypesArrayList;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.genderArrayList);
        dest.writeTypedList(this.providerServiceGendersArrayList);
        dest.writeTypedList(this.languageArrayList);
        dest.writeTypedList(this.caregiverRatingTypeArrayList);
        dest.writeTypedList(this.careSeekerRatingTypeArrayList);
        dest.writeTypedList(this.paymentMethodTypeArrayList);
        dest.writeTypedList(this.contactUsTypesTypeArrayList);
        dest.writeTypedList(this.relationshipsArrayList);
        dest.writeTypedList(this.bloodTypesArrayList);
        dest.writeTypedList(this.educationDegreeArrayList);
        dest.writeTypedList(this.caregiverAgeArrayList);
        dest.writeTypedList(this.insuranceTypesArrayList);
        dest.writeTypedList(this.attachmentCategories);
        dest.writeTypedList(this.caregiverIssuersOfCertificates);
        dest.writeTypedList(this.caregiver_order_status);
        dest.writeTypedList(this.careseeker_order_status);
        dest.writeTypedList(this.admin_order_status);
        dest.writeTypedList(this.caregiver_user_status);
        dest.writeTypedList(this.caregiver_order_cancel_reasons);
        dest.writeTypedList(this.careseeker_order_cancel_reasons);
        dest.writeTypedList(this.caregiver_order_rejected_reasons);
    }

    public LookupResponse() {
    }

    protected LookupResponse(Parcel in) {
        super(in);
        this.genderArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.providerServiceGendersArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.languageArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.caregiverRatingTypeArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.careSeekerRatingTypeArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.paymentMethodTypeArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.contactUsTypesTypeArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.relationshipsArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.bloodTypesArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.educationDegreeArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.caregiverAgeArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.insuranceTypesArrayList = in.createTypedArrayList(LookUpModel.CREATOR);
        this.attachmentCategories = in.createTypedArrayList(LookUpModel.CREATOR);
        this.caregiverIssuersOfCertificates = in.createTypedArrayList(LookUpModel.CREATOR);
        this.caregiver_order_status = in.createTypedArrayList(LookUpModel.CREATOR);
        this.careseeker_order_status = in.createTypedArrayList(LookUpModel.CREATOR);
        this.admin_order_status = in.createTypedArrayList(LookUpModel.CREATOR);
        this.caregiver_user_status = in.createTypedArrayList(LookUpModel.CREATOR);
        this.caregiver_order_cancel_reasons = in.createTypedArrayList(LookUpModel.CREATOR);
        this.careseeker_order_cancel_reasons = in.createTypedArrayList(LookUpModel.CREATOR);
        this.caregiver_order_rejected_reasons = in.createTypedArrayList(LookUpModel.CREATOR);
    }

    public static final Creator<LookupResponse> CREATOR = new Creator<LookupResponse>() {
        @Override
        public LookupResponse createFromParcel(Parcel source) {
            return new LookupResponse(source);
        }

        @Override
        public LookupResponse[] newArray(int size) {
            return new LookupResponse[size];
        }
    };
}
