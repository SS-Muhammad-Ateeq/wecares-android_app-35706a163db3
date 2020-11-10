package com.wecare.android.data;

import android.content.Context;

import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.local.db.DbHelper;
import com.wecare.android.data.local.prefs.PreferencesHelper;
import com.wecare.android.data.model.api.models.*;
import com.wecare.android.data.model.api.requests.LoginRequest;
import com.wecare.android.data.model.api.responses.*;
import com.wecare.android.data.remote.ApiHelperFlavour;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class AppDataManagerFlavour extends AppDataManager implements DataManagerFlavour {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;

    private final ApiHelperFlavour mApiHelper;

    @Inject
    public AppDataManagerFlavour(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelperFlavour apiHelper) {
        super(context, dbHelper, preferencesHelper, apiHelper);

        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public Single<SocialLoginResponse> doGoogleLoginApiCall(String accessToken) {
        return mApiHelper.doGoogleLoginApiCall(accessToken);
    }

    @Override
    public Single<SocialLoginResponse> doFacebookLoginApiCall(String accessToken) {
        return mApiHelper.doFacebookLoginApiCall(accessToken);
    }

    @Override
    public Single<SocialLoginResponse> doTwitterLoginApiCall(String accessToken, String secretKey) {
        return mApiHelper.doTwitterLoginApiCall(accessToken, secretKey);
    }

    @Override
    public Single<List<RelativeProfileResponse>> getRelativeProfilesApiCall() {
        return mApiHelper.getRelativeProfilesApiCall();
    }

    @Override
    public Single<UserModel> getMYSeekerProfilesApiCall() {
        return mApiHelper.getMYSeekerProfilesApiCall();
    }

    @Override
    public Single<RelativeProfileResponse> getRelativeProfilesByIDApiCall(String profileID) {
        return mApiHelper.getRelativeProfilesByIDApiCall(profileID);
    }

    @Override
    public Single<BaseResponse> updateRelativeProfilesApiCall(JSONObject object) {
        return mApiHelper.updateRelativeProfilesApiCall(object);
    }

    @Override
    public Single<BaseResponse> deleteRelativeProfilesApiCall(String profileID) {
        return mApiHelper.deleteRelativeProfilesApiCall(profileID);
    }

    @Override
    public Single<UserModel> updateMyProfilesApiCall(JSONObject object) {
        return mApiHelper.updateMyProfilesApiCall(object);
    }

    @Override
    public Single<BaseResponse> createRelativeProfilesApiCall(JSONObject object) {
        return mApiHelper.createRelativeProfilesApiCall(object);
    }

    @Override
    public Single<List<UserLocationResponse>> getUserLocationApiCall() {
        return mApiHelper.getUserLocationApiCall();
    }

    @Override
    public Single<List<SearchGiverResponse>> getRecommendedCaregivers(JSONObject object) {
        return mApiHelper.getRecommendedCaregivers(object);
    }

    @Override
    public Single<List<SearchGiverResponse>> getFavouriteCaregivers(JSONObject object) {
        return mApiHelper.getFavouriteCaregivers(object);
    }

    @Override
    public Single<List<SearchGiverResponse>> getSearchForCaregivers(JSONObject object) {
        return mApiHelper.getSearchForCaregivers(object);
    }

    @Override
    public Single<BaseResponse> createUserLocationApiCall(JSONObject object) {
        return mApiHelper.createUserLocationApiCall(object);
    }

    @Override
    public Single<BaseResponse> updateUserLocationApiCall(JSONObject object, String locationId) {
        return mApiHelper.updateUserLocationApiCall(object, locationId);
    }

    @Override
    public Single<BaseResponse> deleteUserLocationApiCall(String locationId) {
        return mApiHelper.deleteUserLocationApiCall(locationId);
    }

    @Override
    public Single<BaseResponse> createOrderApiCall(JSONObject object) {
        return mApiHelper.createOrderApiCall(object);
    }

    @Override
    public Single<InformationAttachmentObj> uploadOrderImageApiCall(String name, File file) {
        return mApiHelper.uploadOrderImageApiCall(name, file);
    }

    @Override
    public Single<UserModel> updateUserAvatarApiCall(File file) {
        return mApiHelper.updateUserAvatarApiCall(file);
    }

    @Override
    public Single<List<InsuranceCompanyModel>> getInsuranceCompany() {
        return mApiHelper.getInsuranceCompany();
    }

    @Override
    public Single<List<OrderModel>> getOrders(String status, int offset, int limit) {
        return mApiHelper.getOrders(status, offset, limit);
    }

    @Override
    public Single<BaseResponse> cancelOrder(JSONObject object) {
        return mApiHelper.cancelOrder(object);
    }

    @Override
    public Single<RatingResponse> doRating(JSONObject object) {
        return mApiHelper.doRating(object);
    }

    @Override
    public Single<List<SearchGiverResponse>> getAllBlockedCaregiverProfile() {
        return mApiHelper.getAllBlockedCaregiverProfile();
    }

    @Override
    public Single<List<SearchGiverResponse>> getAllFavouriteCaregiverProfile() {
        return mApiHelper.getAllFavouriteCaregiverProfile();
    }

    @Override
    public Single<CaregiverUser> getAllSearchCaregiverProfile(String caregiverID) {
        return mApiHelper.getAllSearchCaregiverProfile(caregiverID);
    }

}
