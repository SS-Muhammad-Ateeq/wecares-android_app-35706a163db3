package com.wecare.android.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.di.ApiInfo;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;
    private PublicApiHeader mPublicApiHeader;


    private InfoApiHeader infoApiHeader;

    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader,InfoApiHeader infoApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
        this.infoApiHeader = infoApiHeader;

    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }
    public InfoApiHeader getInfoApiHeader() {
        return infoApiHeader;
    }

    public static final class PublicApiHeader {

        @Expose
        @SerializedName("Authorization")
        private String mApiKey;

        @Inject
        public PublicApiHeader(@ApiInfo String apiKey) {
            mApiKey = apiKey;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }
    }

    public static final class ProtectedApiHeader {


        @Expose
        @SerializedName("Authorization")
        private String mAccessToken;

        public ProtectedApiHeader(String mAccessToken) {
            this.mAccessToken = mAccessToken;
        }


        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = "Bearer " + accessToken;
        }
    }

    public static final class InfoApiHeader {

        @SerializedName("firebase-token")
        private String firebaseToken="";


        @SerializedName("device-type")
        private String deviceType;


        @SerializedName("os")
        private String os = "Android";

        @SerializedName("os-version")
        private String osVersion;

        @SerializedName("app-version")
        private String appVersion;



        @SerializedName("lang")
        private String lang;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getFirebaseToken() {
            return firebaseToken;
        }

        public void setFirebaseToken(String firebaseToken) {
            this.firebaseToken = firebaseToken;
        }


        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public InfoApiHeader() {

        }

    }
}
