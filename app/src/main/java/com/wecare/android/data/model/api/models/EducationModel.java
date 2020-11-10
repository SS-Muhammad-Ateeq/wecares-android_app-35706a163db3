package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationModel implements Parcelable {

    @SerializedName("education_degree")
    @Expose
    private String educationDegree;

    @SerializedName("education_degree_englishName")
    @Expose
    private String educationDegreeEnglish;

    public String getEducationDegreeEnglish() {
        return educationDegreeEnglish;
    }

    public void setEducationDegreeEnglish(String educationDegreeEnglish) {
        this.educationDegreeEnglish = educationDegreeEnglish;
    }

    public String getEducationDegreeArabic() {
        return educationDegreeArabic;
    }

    public void setEducationDegreeArabic(String educationDegreeArabic) {
        this.educationDegreeArabic = educationDegreeArabic;
    }

    @SerializedName("education_degree_arabicName")
    @Expose
    private String educationDegreeArabic;
    @SerializedName("university_or_college_name")
    @Expose
    private String universityOrCollegeName;
    @SerializedName("specialization")
    @Expose
    private String specialization;
    @SerializedName("graduation_year")
    @Expose
    private String graduationYear = "0";

    @SerializedName("id")
    @Expose
    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @SerializedName("user_id")
    @Expose
    private Integer userID;

    boolean isNew = false;

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getUniversityOrCollegeName() {
        return universityOrCollegeName;
    }

    public void setUniversityOrCollegeName(String universityOrCollegeName) {
        this.universityOrCollegeName = universityOrCollegeName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public EducationModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.educationDegree);
        dest.writeString(this.educationDegreeEnglish);
        dest.writeString(this.educationDegreeArabic);
        dest.writeString(this.universityOrCollegeName);
        dest.writeString(this.specialization);
        dest.writeString(this.graduationYear);
        dest.writeValue(this.Id);
        dest.writeValue(this.userID);
        dest.writeByte(this.isNew ? (byte) 1 : (byte) 0);
    }

    protected EducationModel(Parcel in) {
        this.educationDegree = in.readString();
        this.educationDegreeEnglish = in.readString();
        this.educationDegreeArabic = in.readString();
        this.universityOrCollegeName = in.readString();
        this.specialization = in.readString();
        this.graduationYear = in.readString();
        this.Id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isNew = in.readByte() != 0;
    }

    public static final Creator<EducationModel> CREATOR = new Creator<EducationModel>() {
        @Override
        public EducationModel createFromParcel(Parcel source) {
            return new EducationModel(source);
        }

        @Override
        public EducationModel[] newArray(int size) {
            return new EducationModel[size];
        }
    };
}