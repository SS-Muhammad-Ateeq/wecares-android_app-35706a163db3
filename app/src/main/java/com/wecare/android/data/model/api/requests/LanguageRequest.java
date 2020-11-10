package com.wecare.android.data.model.api.requests;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.utils.AppConstants;

public class LanguageRequest implements Parcelable {
    public LanguageRequest(String languageId, String level) {
        this.languageId = languageId;
        this.level = level;
    }

    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    @SerializedName("user_id")
    @Expose
    private Integer userID;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("englishName")
    @Expose
    private String englishName;
    @SerializedName("arabicName")
    @Expose
    private String arabicName;

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @NonNull
    @Override
    public String toString() {
        return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? getEnglishName() : getArabicName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.languageId);
        dest.writeValue(this.id);
        dest.writeInt(this.userID);
        dest.writeString(this.level);
        dest.writeString(this.englishName);
        dest.writeString(this.arabicName);
    }

    protected LanguageRequest(Parcel in) {
        this.languageId = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userID = in.readInt();
        this.level = in.readString();
        this.englishName = in.readString();
        this.arabicName = in.readString();
    }

    public static final Creator<LanguageRequest> CREATOR = new Creator<LanguageRequest>() {
        @Override
        public LanguageRequest createFromParcel(Parcel source) {
            return new LanguageRequest(source);
        }

        @Override
        public LanguageRequest[] newArray(int size) {
            return new LanguageRequest[size];
        }
    };
}