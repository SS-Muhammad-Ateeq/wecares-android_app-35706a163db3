package com.wecare.android.data.model.api.responses;

import android.os.Parcel;
import android.os.Parcelable;
import com.wecare.android.data.model.api.models.InsuranceCompanyModel;
import com.wecare.android.data.model.api.models.LookUpModel;


public class RelativeProfileResponse extends BaseResponse implements Parcelable {


    /**
     * id : 16
     * user_id : 1
     * name : alaa
     * gender : {"id":1,"englishName":"Female",
     * "arabicName":"&#1571;&#1606;&#1579;&#1609;","created_at":"2019-04-13 16:35:03",
     * "created_by":null,"updated_at":"2019-04-13 16:35:03","updated_by":null}
     * <p>
     * age : 30
     * relationship : Father
     * mobile_number : 0099828282
     * country_code : 00962
     * blood_type : 2
     * weight : 78
     * length : 189
     * blood_pressure : 80/120
     * chronic_diseases : no
     * health_insurance : 0
     * insurance_company :
     * insurance_number :
     * type_of_insurance :
     * insurance_expiration_date :
     * created_at : 2019-04-29 21:16:23
     * created_by :
     * updated_at : 2019-04-29 21:16:23
     * updated_by :
     */

    private String id;
    private String user_id;
    private String age;
    private String weight;
    private String length;
    private String blood_type;
    private String health_insurance;
    private String name;
    private String mobile_number;
    private String country_code;
    private String blood_pressure;
    private String chronic_diseases;
    private String insurance_number;
    private String insurance_expiration_date;
    private String created_at;
    private String created_by;
    private String updated_at;
    private String updated_by;
    private InsuranceCompanyModel insurance_company;
    private LookUpModel type_of_insurance;
    private LookUpModel gender;
    private LookUpModel relationship;

    //local field
    private String selectedInsuranceCompany;//use in edit add profile.
    private String selectedTypeOfInsurance;//use in edit add profile.
    private String selectedNewRelationShip;//use in edit add profile.
    private String needSomeMaterial;

    public String getNeededMaterial() {
        return neededMaterial;
    }

    public void setNeededMaterial(String neededMaterial) {
        this.neededMaterial = neededMaterial;
    }

    private String neededMaterial;
    private String moreDescription;

    //edit my profiled redundant object
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthDate;
    private String nationalityID;
    private String nationalityNumberID;
    private String countryOfServiceID;


    public RelativeProfileResponse() {
    }

    public String getId() {
        return id;
    }

    public RelativeProfileResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getUser_id() {
        return user_id;
    }

    public RelativeProfileResponse setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getAge() {
        return age;
    }

    public RelativeProfileResponse setAge(String age) {
        this.age = age;
        return this;
    }

    public String getWeight() {
        return weight;
    }

    public RelativeProfileResponse setWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public String getLength() {
        return length;
    }

    public RelativeProfileResponse setLength(String length) {
        this.length = length;
        return this;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public RelativeProfileResponse setBlood_type(String blood_type) {
        this.blood_type = blood_type;
        return this;
    }

    public String getHealth_insurance() {
        return health_insurance;
    }

    public RelativeProfileResponse setHealth_insurance(String health_insurance) {
        this.health_insurance = health_insurance;
        return this;
    }

    public String getName() {
        return name;
    }

    public RelativeProfileResponse setName(String name) {
        this.name = name;
        return this;
    }

    public LookUpModel getRelationship() {
        return relationship;
    }

    public RelativeProfileResponse setRelationship(LookUpModel relationship) {
        this.relationship = relationship;
        return this;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public RelativeProfileResponse setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
        return this;
    }

    public String getCountry_code() {
        return country_code;
    }

    public RelativeProfileResponse setCountry_code(String country_code) {
        this.country_code = country_code;
        return this;
    }

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public RelativeProfileResponse setBlood_pressure(String blood_pressure) {
        this.blood_pressure = blood_pressure;
        return this;
    }

    public String getChronic_diseases() {
        return chronic_diseases;
    }

    public RelativeProfileResponse setChronic_diseases(String chronic_diseases) {
        this.chronic_diseases = chronic_diseases;
        return this;
    }

    public InsuranceCompanyModel getInsurance_company() {
        return insurance_company;
    }

    public RelativeProfileResponse setInsurance_company(InsuranceCompanyModel insurance_company) {
        this.insurance_company = insurance_company;
        return this;
    }

    public String getInsurance_number() {
        return insurance_number;
    }

    public RelativeProfileResponse setInsurance_number(String insurance_number) {
        this.insurance_number = insurance_number;
        return this;
    }

    public LookUpModel getType_of_insurance() {
        return type_of_insurance;
    }

    public RelativeProfileResponse setType_of_insurance(LookUpModel type_of_insurance) {
        this.type_of_insurance = type_of_insurance;
        return this;
    }

    public String getInsurance_expiration_date() {
        return insurance_expiration_date;
    }

    public RelativeProfileResponse setInsurance_expiration_date(String insurance_expiration_date) {
        this.insurance_expiration_date = insurance_expiration_date;
        return this;
    }

    public String getCreated_at() {
        return created_at;
    }

    public RelativeProfileResponse setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getCreated_by() {
        return created_by;
    }

    public RelativeProfileResponse setCreated_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public RelativeProfileResponse setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public RelativeProfileResponse setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
        return this;
    }

    public LookUpModel getGender() {
        return gender;
    }

    public RelativeProfileResponse setGender(LookUpModel gender) {
        this.gender = gender;
        return this;
    }

    public String getNeedSomeMaterial() {
        return needSomeMaterial;
    }

    public RelativeProfileResponse setNeedSomeMaterial(String needSomeMaterial) {
        this.needSomeMaterial = needSomeMaterial;
        return this;
    }

    public String getMoreDescription() {
        return moreDescription;
    }

    public RelativeProfileResponse setMoreDescription(String moreDescription) {
        this.moreDescription = moreDescription;
        return this;
    }

    public String getSelectedNewRelationShip() {
        return selectedNewRelationShip;
    }

    public RelativeProfileResponse setSelectedNewRelationShip(String selectedNewRelationShip) {
        this.selectedNewRelationShip = selectedNewRelationShip;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RelativeProfileResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public RelativeProfileResponse setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RelativeProfileResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public RelativeProfileResponse setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getNationalityID() {
        return nationalityID;
    }

    public RelativeProfileResponse setNationalityID(String nationalityID) {
        this.nationalityID = nationalityID;
        return this;
    }

    public String getNationalityNumberID() {
        return nationalityNumberID;
    }

    public RelativeProfileResponse setNationalityNumberID(String nationalityNumberID) {
        this.nationalityNumberID = nationalityNumberID;
        return this;
    }

    public String getCountryOfServiceID() {
        return countryOfServiceID;
    }

    public RelativeProfileResponse setCountryOfServiceID(String countryOfService) {
        this.countryOfServiceID = countryOfService;
        return this;
    }

    public String getSelectedTypeOfInsurance() {
        return selectedTypeOfInsurance;
    }

    public RelativeProfileResponse setSelectedTypeOfInsurance(String selectedTypeOfInsurance) {
        this.selectedTypeOfInsurance = selectedTypeOfInsurance;
        return this;
    }

    public String getSelectedInsuranceCompany() {
        return selectedInsuranceCompany;
    }

    public RelativeProfileResponse setSelectedInsuranceCompany(String selectedInsuranceCompany) {
        this.selectedInsuranceCompany = selectedInsuranceCompany;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.user_id);
        dest.writeString(this.age);
        dest.writeString(this.weight);
        dest.writeString(this.length);
        dest.writeString(this.blood_type);
        dest.writeString(this.health_insurance);
        dest.writeString(this.name);
        dest.writeString(this.mobile_number);
        dest.writeString(this.country_code);
        dest.writeString(this.blood_pressure);
        dest.writeString(this.chronic_diseases);
        dest.writeString(this.insurance_number);
        dest.writeString(this.insurance_expiration_date);
        dest.writeString(this.created_at);
        dest.writeString(this.created_by);
        dest.writeString(this.updated_at);
        dest.writeString(this.updated_by);
        dest.writeParcelable(this.insurance_company, flags);
        dest.writeParcelable(this.type_of_insurance, flags);
        dest.writeParcelable(this.gender, flags);
        dest.writeParcelable(this.relationship, flags);
        dest.writeString(this.selectedInsuranceCompany);
        dest.writeString(this.selectedTypeOfInsurance);
        dest.writeString(this.selectedNewRelationShip);
        dest.writeString(this.needSomeMaterial);
        dest.writeString(this.neededMaterial);
        dest.writeString(this.moreDescription);
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.birthDate);
        dest.writeString(this.nationalityID);
        dest.writeString(this.nationalityNumberID);
        dest.writeString(this.countryOfServiceID);
    }

    protected RelativeProfileResponse(Parcel in) {
        super(in);
        this.id = in.readString();
        this.user_id = in.readString();
        this.age = in.readString();
        this.weight = in.readString();
        this.length = in.readString();
        this.blood_type = in.readString();
        this.health_insurance = in.readString();
        this.name = in.readString();
        this.mobile_number = in.readString();
        this.country_code = in.readString();
        this.blood_pressure = in.readString();
        this.chronic_diseases = in.readString();
        this.insurance_number = in.readString();
        this.insurance_expiration_date = in.readString();
        this.created_at = in.readString();
        this.created_by = in.readString();
        this.updated_at = in.readString();
        this.updated_by = in.readString();
        this.insurance_company = in.readParcelable(InsuranceCompanyModel.class.getClassLoader());
        this.type_of_insurance = in.readParcelable(LookUpModel.class.getClassLoader());
        this.gender = in.readParcelable(LookUpModel.class.getClassLoader());
        this.relationship = in.readParcelable(LookUpModel.class.getClassLoader());
        this.selectedInsuranceCompany = in.readString();
        this.selectedTypeOfInsurance = in.readString();
        this.selectedNewRelationShip = in.readString();
        this.needSomeMaterial = in.readString();
        this.neededMaterial = in.readString();
        this.moreDescription = in.readString();
        this.firstName = in.readString();
        this.middleName = in.readString();
        this.lastName = in.readString();
        this.birthDate = in.readString();
        this.nationalityID = in.readString();
        this.nationalityNumberID = in.readString();
        this.countryOfServiceID = in.readString();
    }

    public static final Creator<RelativeProfileResponse> CREATOR = new Creator<RelativeProfileResponse>() {
        @Override
        public RelativeProfileResponse createFromParcel(Parcel source) {
            return new RelativeProfileResponse(source);
        }

        @Override
        public RelativeProfileResponse[] newArray(int size) {
            return new RelativeProfileResponse[size];
        }
    };
}