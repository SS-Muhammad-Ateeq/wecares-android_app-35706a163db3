package com.wecare.android.data.model.api.models;

import android.os.Parcel;
import ir.mirrajabi.searchdialog.core.Searchable;

public class InsuranceCompanyModel extends LookUpModel implements Searchable {

    /**
     * id : 1
     * company : GIG
     */

    private String company;
    private String mTitle;

    public InsuranceCompanyModel setTitle(String title) {
        mTitle = title;
        return this;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String getTitle() {
        return getCompany();
    }

    public InsuranceCompanyModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.company);
        dest.writeString(this.mTitle);
    }

    protected InsuranceCompanyModel(Parcel in) {
        super(in);
        this.company = in.readString();
        this.mTitle = in.readString();
    }

    public static final Creator<InsuranceCompanyModel> CREATOR = new Creator<InsuranceCompanyModel>() {
        @Override
        public InsuranceCompanyModel createFromParcel(Parcel source) {
            return new InsuranceCompanyModel(source);
        }

        @Override
        public InsuranceCompanyModel[] newArray(int size) {
            return new InsuranceCompanyModel[size];
        }
    };
}
