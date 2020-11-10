package com.wecare.android.data.model.api.responses;

import android.os.Parcel;

public class SocialLoginResponse extends LoginResponse {


    /**
     * first_name : Mohd
     * last_name : Maher
     * facebook_id : 10220854985670338
     */

    private String first_name;
    private String last_name;
    private String facebook_id;
    private String twitter_id;
    private String google_id;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public SocialLoginResponse setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
        return this;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public SocialLoginResponse setGoogle_id(String google_id) {
        this.google_id = google_id;
        return this;
    }

    public SocialLoginResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.facebook_id);
        dest.writeString(this.twitter_id);
        dest.writeString(this.google_id);
    }

    protected SocialLoginResponse(Parcel in) {
        super(in);
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.facebook_id = in.readString();
        this.twitter_id = in.readString();
        this.google_id = in.readString();
    }

    public static final Creator<SocialLoginResponse> CREATOR = new Creator<SocialLoginResponse>() {
        @Override
        public SocialLoginResponse createFromParcel(Parcel source) {
            return new SocialLoginResponse(source);
        }

        @Override
        public SocialLoginResponse[] newArray(int size) {
            return new SocialLoginResponse[size];
        }
    };
}