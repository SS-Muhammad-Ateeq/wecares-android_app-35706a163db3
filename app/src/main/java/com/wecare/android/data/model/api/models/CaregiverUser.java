package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.requests.LanguageRequest;

import java.util.ArrayList;

public class CaregiverUser extends UserModel {

    @Expose
    @SerializedName("educations")
    private ArrayList<EducationModel> educationModels;

    @Expose
    @SerializedName("attachments")
    private ArrayList<AttachmentModel> attachmentModels;

    @Expose
    @SerializedName("certificates")
    private ArrayList<AttachmentModel> certificateModels;

    @Expose
    @SerializedName("experiences")
    private ArrayList<ExperienceModel> experienceModels;

    @Expose
    @SerializedName("languages")
    private ArrayList<LanguageRequest> languageModels;

    public ArrayList<EducationModel> getEducationModels() {
        return educationModels;
    }

    public void setEducationModels(ArrayList<EducationModel> educationModels) {
        this.educationModels = educationModels;
    }

    public ArrayList<AttachmentModel> getAttachmentModels() {
        return attachmentModels;
    }

    public void setAttachmentModels(ArrayList<AttachmentModel> attachmentModels) {
        this.attachmentModels = attachmentModels;
    }

    public ArrayList<AttachmentModel> getCertificateModels() {
        return certificateModels;
    }

    public void setCertificateModels(ArrayList<AttachmentModel> certificateModels) {
        this.certificateModels = certificateModels;
    }

    public ArrayList<ExperienceModel> getExperienceModels() {
        return experienceModels;
    }

    public void setExperienceModels(ArrayList<ExperienceModel> experienceModels) {
        this.experienceModels = experienceModels;
    }

    public ArrayList<LanguageRequest> getLanguageModels() {
        return languageModels;
    }

    public void setLanguageModels(ArrayList<LanguageRequest> languageModels) {
        this.languageModels = languageModels;
    }

    public CaregiverUser() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.educationModels);
        dest.writeTypedList(this.attachmentModels);
        dest.writeTypedList(this.certificateModels);
        dest.writeTypedList(this.experienceModels);
        dest.writeTypedList(this.languageModels);
    }

    protected CaregiverUser(Parcel in) {
        super(in);
        this.educationModels = in.createTypedArrayList(EducationModel.CREATOR);
        this.attachmentModels = in.createTypedArrayList(AttachmentModel.CREATOR);
        this.certificateModels = in.createTypedArrayList(AttachmentModel.CREATOR);
        this.experienceModels = in.createTypedArrayList(ExperienceModel.CREATOR);
        this.languageModels = in.createTypedArrayList(LanguageRequest.CREATOR);
    }

    public static final Creator<CaregiverUser> CREATOR = new Creator<CaregiverUser>() {
        @Override
        public CaregiverUser createFromParcel(Parcel source) {
            return new CaregiverUser(source);
        }

        @Override
        public CaregiverUser[] newArray(int size) {
            return new CaregiverUser[size];
        }
    };
}
