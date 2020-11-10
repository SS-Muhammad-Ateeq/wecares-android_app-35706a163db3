package com.wecare.android.data.model.api.responses;

import android.os.Parcel;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.CaregiverUser;

import java.util.ArrayList;
import java.util.List;

public class SearchGiverResponse extends BaseResponse {


    /**
     * caregiver : {"id":"3","wecare_id":"0011000003","first_name":"farah2","middle_name":"hussam","last_name":"alrabee","email":"farah@caregiver.com","country_of_service":"1","years_of_experience":"3","organization_code":"","organization_id":"","professional_license_number":"12244","issuer_of_certificate":{"id":"1","label":"Syndicate"},"phone_number":"798516364","country_code":"00962","city":{"id":41521,"title":"'Abbin","country_id":"1","region_id":"","created_by":1,"updated_by":"","active":"1","created_at":"","updated_at":""},"gender":{"id":2,"englishName":"Male","arabicName":"ذكر","created_at":"2019-04-13 16:35:03","created_by":"1","updated_at":"2019-04-13 16:35:03","updated_by":"","type":"0"},"birth_date":"2019-05-26","nationality":{"id":"2","iso2":"SA","iso3":"KSA","phone_code":"966","title":"Saudi Arabia","time_zone":"","nationality":"Saudi","currency":"Saudi Riyal","currency_code":"SAR","created_by":"1","created_at":1550578494,"updated_by":"","updated_at":"","active":"1"},"national_id":"123456789","membership_type":{"id":4,"amount":"180JD","country_id":"1","type":"Quarterly ","category":"Membership fees","created_at":"","created_by":"","updated_at":"","updated_by":""},"reset_expires_on":"2019-04-15","is_phone_verified":"1","verification_code":"6192","avatar":"http://stg-storage.wecare-app.org:88/source/caregiver-user/3/avatar/EkTX3--A.jpg","bio":"fagag  gdssrfvابس بس بس","status":"1","status_note":"","confirm_at":"","blocked_at":"","created_at":"2019-03-17 18:33:00","created_by":"","updated_at":"2019-05-27 19:15:52","updated_by":"","working_24_hours":"1","status_label":"Inactive","rating":"0","languages":[{"id":"15","user_id":"3","language_id":"1","englishName":"English","arabicName":"الإنجليزية","level_id":"1","level":"Fluent"}],"settings":{"enable_providing_services":"1","enable_notifications":"1","interface_language":"en"},"age":"0","availability":"1","isFavorite":"0","isBlocked":"0"}
     * estimated_total_price : 0
     */

    private CaregiverUser caregiver;
    private String estimated_total_price;
    @SerializedName("services")
    private ArrayList<MainServiceModel> mainServiceModelArrayList;

    public CaregiverUser getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(CaregiverUser caregiver) {
        this.caregiver = caregiver;
    }

    public String getEstimated_total_price() {
        if (estimated_total_price == null) {
            estimated_total_price = "";
        }
        return estimated_total_price;
    }

    public ArrayList<MainServiceModel> getMainServiceModelArrayList() {
        if (mainServiceModelArrayList == null) {
            mainServiceModelArrayList = new ArrayList<>();
        }
        return mainServiceModelArrayList;
    }

    public SearchGiverResponse setMainServiceModelArrayList(ArrayList<MainServiceModel> mainServiceModelArrayList) {
        this.mainServiceModelArrayList = mainServiceModelArrayList;
        return this;
    }

    public void setEstimated_total_price(String estimated_total_price) {
        this.estimated_total_price = estimated_total_price;
    }

    public SearchGiverResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.caregiver, flags);
        dest.writeString(this.estimated_total_price);
        dest.writeTypedList(this.mainServiceModelArrayList);
    }

    protected SearchGiverResponse(Parcel in) {
        super(in);
        this.caregiver = in.readParcelable(CaregiverUser.class.getClassLoader());
        this.estimated_total_price = in.readString();
        this.mainServiceModelArrayList = in.createTypedArrayList(MainServiceModel.CREATOR);
    }

    public static final Creator<SearchGiverResponse> CREATOR = new Creator<SearchGiverResponse>() {
        @Override
        public SearchGiverResponse createFromParcel(Parcel source) {
            return new SearchGiverResponse(source);
        }

        @Override
        public SearchGiverResponse[] newArray(int size) {
            return new SearchGiverResponse[size];
        }
    };
}
