package com.wecare.android.data.model.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.UserModel;

public class JWTResponse {

    @SerializedName("iss")
    @Expose
    private String iss;
    @SerializedName("aud")
    @Expose
    private String aud;
    @SerializedName("iat")
    @Expose
    private Integer iat;
    @SerializedName("nbf")
    @Expose
    private Integer nbf;
    @SerializedName("exp")
    @Expose
    private Integer exp;

    @SerializedName("data")
    @Expose
    private UserModel userModel;
    @SerializedName("jti")
    @Expose
    private Integer jti;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public Integer getNbf() {
        return nbf;
    }

    public void setNbf(Integer nbf) {
        this.nbf = nbf;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }


    public Integer getJti() {
        return jti;
    }

    public void setJti(Integer jti) {
        this.jti = jti;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

}