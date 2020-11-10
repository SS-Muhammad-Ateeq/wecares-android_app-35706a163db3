package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSettings implements Parcelable {

@SerializedName("enable_providing_services")
@Expose
private Integer enableProvidingServices;
@SerializedName("enable_notifications")
@Expose
private Integer enableNotifications;
@SerializedName("interface_language")
@Expose
private String interfaceLanguage;

public Integer getEnableProvidingServices() {
return enableProvidingServices;
}

public void setEnableProvidingServices(Integer enableProvidingServices) {
this.enableProvidingServices = enableProvidingServices;
}

public Integer getEnableNotifications() {
return enableNotifications;
}

public void setEnableNotifications(Integer enableNotifications) {
this.enableNotifications = enableNotifications;
}

public String getInterfaceLanguage() {
return interfaceLanguage;
}

public void setInterfaceLanguage(String interfaceLanguage) {
this.interfaceLanguage = interfaceLanguage;
}

    public UserSettings() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.enableProvidingServices);
        dest.writeValue(this.enableNotifications);
        dest.writeString(this.interfaceLanguage);
    }

    protected UserSettings(Parcel in) {
        this.enableProvidingServices = (Integer) in.readValue(Integer.class.getClassLoader());
        this.enableNotifications = (Integer) in.readValue(Integer.class.getClassLoader());
        this.interfaceLanguage = in.readString();
    }

    public static final Creator<UserSettings> CREATOR = new Creator<UserSettings>() {
        @Override
        public UserSettings createFromParcel(Parcel source) {
            return new UserSettings(source);
        }

        @Override
        public UserSettings[] newArray(int size) {
            return new UserSettings[size];
        }
    };
}