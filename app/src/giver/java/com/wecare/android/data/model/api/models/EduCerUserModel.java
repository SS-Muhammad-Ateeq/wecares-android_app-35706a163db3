package com.wecare.android.data.model.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EduCerUserModel {

@SerializedName("bio")
@Expose
private String bio;
@SerializedName("issuer_of_certificate")
@Expose
private Integer issuerOfCertificate;

@SerializedName("professional_license_number")
@Expose
private String professionalLicenseNumber;

@SerializedName("issuer_name")
@Expose
private String issuername;

@SerializedName("years_of_experience")
@Expose
private String yearsOfExperience;

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getBio() {
return bio;
}

public void setBio(String bio) {
this.bio = bio;
}

public Integer getIssuerOfCertificate() {
return issuerOfCertificate;
}

public void setIssuerOfCertificate(Integer issuerOfCertificate) {
this.issuerOfCertificate = issuerOfCertificate;
}

public String getProfessionalLicenseNumber() {
return professionalLicenseNumber;
}

public void setProfessionalLicenseNumber(String professionalLicenseNumber) {
this.professionalLicenseNumber = professionalLicenseNumber;
}
public void setIssuername(String issuername){
        this.issuername=issuername;
}
}