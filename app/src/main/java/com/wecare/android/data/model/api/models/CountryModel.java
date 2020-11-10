
package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.BaseResponse;

import ir.mirrajabi.searchdialog.core.Searchable;

@SuppressWarnings("unused")
public class CountryModel extends BaseResponse implements Searchable, Parcelable {
        @Expose
    @SerializedName("active")
    private int mActive;
    @Expose
    @SerializedName("currency")
    private String mCurrency;
    @Expose
    @SerializedName("currency_code")
    private String mCurrencyCode;
    @Expose
    @SerializedName("id")
    private String mId;
    @Expose
    @SerializedName("iso2")
    private String mIso2;
    @Expose
    @SerializedName("iso3")
    private String mIso3;
    @Expose
    @SerializedName("nationality")
    private String mNationality;
    @Expose
    @SerializedName("phone_code")
    private String mPhoneCode;
    @Expose
    @SerializedName("time_zone")
    private String mTimeZone;
    @Expose
    @SerializedName("title")
    private String mTitle;

    public String getCustomerSupportNumber() {
        return customerSupportNumber;
    }

    public void setCustomerSupportNumber(String customerSupportNumber) {
        this.customerSupportNumber = customerSupportNumber;
    }

    @Expose
    @SerializedName("customer_support_number")
    private String customerSupportNumber;
    @Expose
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public int getActive() {
        return mActive;
    }

    public void setActive(int active) {
        mActive = active;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        mCurrencyCode = currencyCode;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIso2() {
        return mIso2;
    }

    public void setIso2(String iso2) {
        mIso2 = iso2;
    }

    public String getIso3() {
        return mIso3;
    }

    public void setIso3(String iso3) {
        mIso3 = iso3;
    }

    public String getNationality() {
        return mNationality;
    }

    public void setNationality(String nationality) {
        mNationality = nationality;
    }

    public String getPhoneCode() {
        return mPhoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        mPhoneCode = phoneCode;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }




    public CountryModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mActive);
        dest.writeString(this.mCurrency);
        dest.writeString(this.mCurrencyCode);
        dest.writeString(this.mId);
        dest.writeString(this.mIso2);
        dest.writeString(this.mIso3);
        dest.writeString(this.mNationality);
        dest.writeString(this.mPhoneCode);
        dest.writeString(this.mTimeZone);
        dest.writeString(this.mTitle);
        dest.writeString(this.customerSupportNumber);
        dest.writeString(this.mUpdatedAt);
    }

    protected CountryModel(Parcel in) {
        super(in);
        this.mActive = in.readInt();
        this.mCurrency = in.readString();
        this.mCurrencyCode = in.readString();
        this.mId = in.readString();
        this.mIso2 = in.readString();
        this.mIso3 = in.readString();
        this.mNationality = in.readString();
        this.mPhoneCode = in.readString();
        this.mTimeZone = in.readString();
        this.mTitle = in.readString();
        this.customerSupportNumber = in.readString();
        this.mUpdatedAt = in.readString();
    }

    public static final Creator<CountryModel> CREATOR = new Creator<CountryModel>() {
        @Override
        public CountryModel createFromParcel(Parcel source) {
            return new CountryModel(source);
        }

        @Override
        public CountryModel[] newArray(int size) {
            return new CountryModel[size];
        }
    };
}
