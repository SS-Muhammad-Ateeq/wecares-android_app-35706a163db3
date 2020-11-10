package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExperienceModel implements Parcelable {

    @SerializedName("is_current")
    @Expose
    private int isCurret;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("company_or_hospital_name")
    @Expose
    private String companyOrHospitalName;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("job_description")
    @Expose
    private String jobDescription;
    @SerializedName("id")
    @Expose

    private Integer Id;
    boolean isNew = false;

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

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

    public int getIsCurretnt(){return isCurret;}
    public void setIsCurretnt(int isCurretnt){ this.isCurret=isCurret;}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCompanyOrHospitalName() {
        return companyOrHospitalName;
    }

    public void setCompanyOrHospitalName(String companyOrHospitalName) {
        this.companyOrHospitalName = companyOrHospitalName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.companyOrHospitalName);
        dest.writeString(this.jobTitle);
        dest.writeString(this.jobDescription);
        dest.writeValue(this.Id);
        dest.writeByte(this.isNew ? (byte) 1 : (byte) 0);
        dest.writeValue(this.userID);
    }

    public ExperienceModel() {
    }

    protected ExperienceModel(Parcel in) {
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.companyOrHospitalName = in.readString();
        this.jobTitle = in.readString();
        this.jobDescription = in.readString();
        this.Id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isNew = in.readByte() != 0;
        this.userID = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExperienceModel> CREATOR = new Parcelable.Creator<ExperienceModel>() {
        @Override
        public ExperienceModel createFromParcel(Parcel source) {
            return new ExperienceModel(source);
        }

        @Override
        public ExperienceModel[] newArray(int size) {
            return new ExperienceModel[size];
        }
    };
}