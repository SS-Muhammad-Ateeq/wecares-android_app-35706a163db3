package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private LookUpModel gender;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("relationship")
    @Expose
    private LookUpModel relationship;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("blood_type")
    @Expose
    private String bloodType;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("blood_pressure")
    @Expose
    private String bloodPressure;
    @SerializedName("chronic_diseases")
    @Expose
    private String chronicDiseases;
    @SerializedName("health_insurance")
    @Expose
    private String healthInsurance;
    @SerializedName("insurance_company")
    @Expose
    private InsuranceCompanyModel insuranceCompany;
    @SerializedName("insurance_number")
    @Expose
    private String insuranceNumber;
    @SerializedName("type_of_insurance")
    @Expose
    private LookUpModel typeOfInsurance;
    @SerializedName("insurance_expiration_date")
    @Expose
    private String insuranceExpirationDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LookUpModel getGender() {
        return gender;
    }

    public void setGender(LookUpModel gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LookUpModel getRelationship() {
        return relationship;
    }

    public void setRelationship(LookUpModel relationship) {
        this.relationship = relationship;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public InsuranceCompanyModel getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(InsuranceCompanyModel insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public LookUpModel getTypeOfInsurance() {
        return typeOfInsurance;
    }

    public void setTypeOfInsurance(LookUpModel typeOfInsurance) {
        this.typeOfInsurance = typeOfInsurance;
    }

    public String getInsuranceExpirationDate() {
        return insuranceExpirationDate;
    }

    public void setInsuranceExpirationDate(String insuranceExpirationDate) {
        this.insuranceExpirationDate = insuranceExpirationDate;
    }

    public UserProfile() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userId);
        dest.writeString(this.name);
        dest.writeParcelable(this.gender, flags);
        dest.writeValue(this.age);
        dest.writeParcelable(this.relationship, flags);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.countryCode);
        dest.writeString(this.bloodType);
        dest.writeString(this.weight);
        dest.writeString(this.length);
        dest.writeString(this.bloodPressure);
        dest.writeString(this.chronicDiseases);
        dest.writeString(this.healthInsurance);
        dest.writeParcelable(this.insuranceCompany, flags);
        dest.writeString(this.insuranceNumber);
        dest.writeParcelable(this.typeOfInsurance, flags);
        dest.writeString(this.insuranceExpirationDate);
    }

    protected UserProfile(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.gender = in.readParcelable(LookUpModel.class.getClassLoader());
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.relationship = in.readParcelable(LookUpModel.class.getClassLoader());
        this.mobileNumber = in.readString();
        this.countryCode = in.readString();
        this.bloodType = in.readString();
        this.weight = in.readString();
        this.length = in.readString();
        this.bloodPressure = in.readString();
        this.chronicDiseases = in.readString();
        this.healthInsurance = in.readString();
        this.insuranceCompany = in.readParcelable(InsuranceCompanyModel.class.getClassLoader());
        this.insuranceNumber = in.readString();
        this.typeOfInsurance = in.readParcelable(LookUpModel.class.getClassLoader());
        this.insuranceExpirationDate = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel source) {
            return new UserProfile(source);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
}