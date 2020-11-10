package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {
    public ChangePasswordRequest(String password, String passwordNew) {
        this.password = password;
        this.passwordNew = passwordNew;
        this.passwordRepeat = passwordNew;
    }

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password_new")
    @Expose
    private String passwordNew;
    @SerializedName("password_repeat")
    @Expose
    private String passwordRepeat;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

}
