package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalInfoRequestModel {

@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("middle_name")
@Expose
private String middleName;
@SerializedName("last_name")
@Expose
private String lastName;
@SerializedName("email")
@Expose
private String email;
@SerializedName("city")
@Expose
private Integer city;
@SerializedName("country_of_service")
@Expose
private String countryOfService;
@SerializedName("gender")
@Expose
private Integer gender;
@SerializedName("nationality")
@Expose
private Integer nationality;
@SerializedName("national_id")
@Expose
private String nationalId;
@SerializedName("professional_license_number")
@Expose
private String professionalLicenseNumber;
@SerializedName("issuer_of_certificate")
@Expose
private String issuerOfCertificate;
@SerializedName("years_of_experience")
@Expose
private String yearsOfExperience;
@SerializedName("birth_date")
@Expose
private String birthDate;
@SerializedName("membership_type")
@Expose
private String membershipType;
    //Street Address
    @SerializedName("street_address")
    @Expose
    private String streetAddress;
    //Province
    @SerializedName("province_state")
    @Expose
    private String provinceState;
    //postalCode
    @SerializedName("postal_code")
    @Expose
    private String postalCode;

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getMiddleName() {
return middleName;
}

public void setMiddleName(String middleName) {
this.middleName = middleName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public Integer getCity() {
return city;
}

public void setCity(Integer city) {
this.city = city;
}

public String getCountryOfService() {
return countryOfService;
}

public void setCountryOfService(String countryOfService) {
this.countryOfService = countryOfService;
}

public Integer getGender() {
return gender;
}

public void setGender(Integer gender) {
this.gender = gender;
}

public Integer getNationality() {
return nationality;
}

public void setNationality(Integer nationality) {
this.nationality = nationality;
}

public String getNationalId() {
return nationalId;
}

public void setNationalId(String nationalId) {
this.nationalId = nationalId;
}

public String getProfessionalLicenseNumber() {
return professionalLicenseNumber;
}

public void setProfessionalLicenseNumber(String professionalLicenseNumber) {
this.professionalLicenseNumber = professionalLicenseNumber;
}

public String getIssuerOfCertificate() {
return issuerOfCertificate;
}

public void setIssuerOfCertificate(String issuerOfCertificate) {
this.issuerOfCertificate = issuerOfCertificate;
}

public String getYearsOfExperience() {
return yearsOfExperience;
}

public void setYearsOfExperience(String yearsOfExperience) {
this.yearsOfExperience = yearsOfExperience;
}

public String getBirthDate() {
return birthDate;
}

public void setBirthDate(String birthDate) {
this.birthDate = birthDate;
}

public String getMembershipType() {
return membershipType;
}

public void setMembershipType(String membershipType) {
this.membershipType = membershipType;
}
    //Street Address getter setter
    public String getStreetAddress(){
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress){
        this.streetAddress=streetAddress;
    }
    //Province
    public String getProvinceState(){
        return provinceState;
    }
    public void setProvinceState(String provinceState){
        this.provinceState=provinceState;
    }
    //Postal Code
    public String getPostalCode(){
        return postalCode;
    }
    public void setPostalCode(String postalCode){
        this.postalCode=postalCode;
    }
}