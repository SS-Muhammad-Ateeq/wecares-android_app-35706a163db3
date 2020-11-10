
package com.wecare.android.data.model.api.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;

@SuppressWarnings("unused")
public class UserModel extends BaseResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("wecare_id")
    @Expose
    private String wecareID;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country_of_service")
    @Expose
    private String countryOfService;
    @SerializedName("years_of_experience")
    @Expose
    private String yearsOfExperience;
    @SerializedName("organization_code")
    @Expose
    private String organizationCode;
    @SerializedName("organization_id")
    @Expose
    private String organizationId;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("city")
    @Expose
    private CityModel city;
    @SerializedName("country")
    @Expose
    private CountryModel country;
    @SerializedName("gender")
    @Expose
    private LookUpModel gender;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("nationality")
    @Expose
    private CountryModel nationality;

    @SerializedName("membership_type")
    @Expose
    private MembershipType membershipType;

    @SerializedName("country_id")
    @Expose
    private String countryID;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;
    @SerializedName("password_hash")
    @Expose
    private String passwordHash;
    @SerializedName("reset_expires_on")
    @Expose
    private String resetExpiresOn;
    @SerializedName("reset_password_code")
    @Expose
    private String resetPasswordCode;
    @SerializedName("password_salt")
    @Expose
    private String passwordSalt;
    @SerializedName("auth_key")
    @Expose
    private String authKey;
    @SerializedName("is_phone_verified")
    @Expose
    private Integer isPhoneVerified;
    @SerializedName("isBlocked")
    @Expose
    private Integer isBlocked;
    @SerializedName("isFavorite")
    @Expose
    private Integer isFavorite;
    @SerializedName("age")
    @Expose
    private Integer age;

    public LookUpModel getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(LookUpModel paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @SerializedName("payment_method")
    @Expose
    private LookUpModel paymentMethod;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("availability")
    @Expose
    private Integer availability;

    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("created_at")
    @Expose
    private String createdAt;


    @SerializedName("professional_license_number")
    @Expose
    private String professionalLicenseNumber;

    @SerializedName("issuer_name")
    @Expose
    private String issuername;

    @SerializedName("settings")
    @Expose
    private UserSettings settings;

    @SerializedName("issuer_of_certificate")
    @Expose
    private LookUpModel issuerOfCertificate;

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @SerializedName("national_id")
    @Expose
    private String nationalId;


    @SerializedName("status_label")
    private String statusLabel;



    @SerializedName("user_profile")
    private RelativeProfileResponse user_profile;

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

    public double getRating() {
        return rating;
    }

    public UserModel setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public String getWecareID() {
        return wecareID;
    }

    public void setWecareID(String wecareID) {
        this.wecareID = wecareID;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public Integer getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Integer isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getId() {
        return id;
    }

    public UserModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountryOfService() {
        return countryOfService;
    }

    public UserModel setCountryOfService(String countryOfService) {
        this.countryOfService = countryOfService;
        return this;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public UserModel setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
        return this;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public UserModel setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
        return this;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public UserModel setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public UserModel setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public CityModel getCity() {
        return city;
    }

    public UserModel setCity(CityModel city) {
        this.city = city;
        return this;
    }

    public CountryModel getCountry() {
        return country;
    }

    public UserModel setCountry(CountryModel country) {
        this.country = country;
        return this;
    }

    public LookUpModel getGender() {
        return gender;
    }

    public UserModel setGender(LookUpModel gender) {
        this.gender = gender;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public UserModel setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public CountryModel getNationality() {
        return nationality;
    }

    public UserModel setNationality(CountryModel nationality) {
        this.nationality = nationality;
        return this;
    }

    public String getCountryID() {
        return countryID;
    }

    public UserModel setCountryID(String countryID) {
        this.countryID = countryID;
        return this;
    }

    public String getGoogleId() {
        return googleId;
    }

    public UserModel setGoogleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public UserModel setFacebookId(String facebookId) {
        this.facebookId = facebookId;
        return this;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public UserModel setTwitterId(String twitterId) {
        this.twitterId = twitterId;
        return this;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserModel setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public String getResetExpiresOn() {
        return resetExpiresOn;
    }

    public UserModel setResetExpiresOn(String resetExpiresOn) {
        this.resetExpiresOn = resetExpiresOn;
        return this;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public UserModel setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
        return this;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public UserModel setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
        return this;
    }

    public String getAuthKey() {
        return authKey;
    }

    public UserModel setAuthKey(String authKey) {
        this.authKey = authKey;
        return this;
    }

    public Integer getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public UserModel setIsPhoneVerified(Integer isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UserModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getAvailability() {
        return availability;
    }

    public UserModel setAvailability(Integer availability) {
        this.availability = availability;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public UserModel setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserModel setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getBio() {
        return bio;
    }

    public UserModel setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public UserModel setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }


    public String getProfessionalLicenseNumber() {
        return professionalLicenseNumber;
    }

    public UserModel setProfessionalLicenseNumber(String professionalLicenseNumber) {
        this.professionalLicenseNumber = professionalLicenseNumber;
        return this;
    }
//issuername
   public String getIssuername(){
        return issuername;
   }
   public UserModel setIssuername(String issuername){
        this.issuername=issuername;
        return this;
   }

    public UserSettings getSettings() {
        return settings;
    }

    public UserModel setSettings(UserSettings settings) {
        this.settings = settings;
        return this;
    }

    public LookUpModel getIssuerOfCertificate() {
        return issuerOfCertificate;
    }

    public UserModel setIssuerOfCertificate(LookUpModel issuerOfCertificate) {
        this.issuerOfCertificate = issuerOfCertificate;
        return this;
    }


    public String getStatusLabel() {
        return statusLabel;
    }

    public UserModel setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
        return this;
    }

    public RelativeProfileResponse getUser_profile() {
        return user_profile;
    }

    public UserModel setUser_profile(RelativeProfileResponse user_profile) {
        this.user_profile = user_profile;
        return this;
    }

    public UserModel() {
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
    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeDouble(this.rating);
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.wecareID);
        dest.writeString(this.email);
        dest.writeString(this.streetAddress);
        dest.writeString(this.postalCode);
        dest.writeString(this.provinceState);
        dest.writeString(this.countryOfService);
        dest.writeString(this.yearsOfExperience);
        dest.writeString(this.organizationCode);
        dest.writeString(this.organizationId);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.countryCode);
        dest.writeParcelable(this.city, flags);
        dest.writeParcelable(this.country, flags);
        dest.writeParcelable(this.gender, flags);
        dest.writeString(this.birthDate);
        dest.writeParcelable(this.nationality, flags);
        dest.writeParcelable(this.membershipType, flags);
        dest.writeString(this.countryID);
        dest.writeString(this.googleId);
        dest.writeString(this.facebookId);
        dest.writeString(this.twitterId);
        dest.writeString(this.passwordHash);
        dest.writeString(this.resetExpiresOn);
        dest.writeString(this.resetPasswordCode);
        dest.writeString(this.passwordSalt);
        dest.writeString(this.authKey);
        dest.writeValue(this.isPhoneVerified);
        dest.writeValue(this.isBlocked);
        dest.writeValue(this.isFavorite);
        dest.writeValue(this.age);
        dest.writeParcelable(this.paymentMethod, flags);
        dest.writeString(this.status);
        dest.writeValue(this.availability);
        dest.writeString(this.verificationCode);
        dest.writeString(this.avatar);
        dest.writeString(this.bio);
        dest.writeString(this.createdAt);
        dest.writeString(this.professionalLicenseNumber);
        dest.writeParcelable(this.settings, flags);
        dest.writeParcelable(this.issuerOfCertificate, flags);
        dest.writeString(this.nationalId);
        dest.writeString(this.statusLabel);
        dest.writeParcelable(this.user_profile, flags);
    }

    protected UserModel(Parcel in) {
        super(in);
        this.id = in.readString();
        this.rating = in.readDouble();
        this.firstName = in.readString();
        this.middleName = in.readString();
        this.lastName = in.readString();
        this.streetAddress=in.readString();
        this.postalCode=in.readString();
        this.provinceState=in.readString();
        this.wecareID = in.readString();
        this.email = in.readString();
        this.countryOfService = in.readString();
        this.yearsOfExperience = in.readString();
        this.organizationCode = in.readString();
        this.organizationId = in.readString();
        this.phoneNumber = in.readString();
        this.countryCode = in.readString();
        this.city = in.readParcelable(CityModel.class.getClassLoader());
        this.country = in.readParcelable(CountryModel.class.getClassLoader());
        this.gender = in.readParcelable(LookUpModel.class.getClassLoader());
        this.birthDate = in.readString();
        this.nationality = in.readParcelable(CountryModel.class.getClassLoader());
        this.membershipType = in.readParcelable(MembershipType.class.getClassLoader());
        this.countryID= in.readString();
        this.googleId = in.readString();
        this.facebookId = in.readString();
        this.twitterId = in.readString();
        this.passwordHash = in.readString();
        this.resetExpiresOn = in.readString();
        this.resetPasswordCode = in.readString();
        this.passwordSalt = in.readString();
        this.authKey = in.readString();
        this.isPhoneVerified = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isBlocked = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isFavorite = (Integer) in.readValue(Integer.class.getClassLoader());
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.paymentMethod = in.readParcelable(LookUpModel.class.getClassLoader());
        this.status = in.readString();
        this.availability = (Integer) in.readValue(Integer.class.getClassLoader());
        this.verificationCode = in.readString();
        this.avatar = in.readString();
        this.bio = in.readString();
        this.createdAt = in.readString();
        this.professionalLicenseNumber = in.readString();
        this.settings = in.readParcelable(UserSettings.class.getClassLoader());
        this.issuerOfCertificate = in.readParcelable(LookUpModel.class.getClassLoader());
        this.nationalId = in.readString();
        this.statusLabel = in.readString();
        this.user_profile = in.readParcelable(RelativeProfileResponse.class.getClassLoader());
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
