package com.wecare.android.data.remote;


import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.models.*;
import com.wecare.android.data.model.api.requests.LoginRequest;
import com.wecare.android.data.model.api.responses.*;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import io.reactivex.Single;

public interface ApiHelperFlavour extends ApiHelper {

    // seeker API
    Single<SocialLoginResponse> doGoogleLoginApiCall(String accessToken);

    Single<SocialLoginResponse> doFacebookLoginApiCall(String accessToken);

    Single<SocialLoginResponse> doTwitterLoginApiCall(String accessToken, String secretKey);

    Single<List<RelativeProfileResponse>> getRelativeProfilesApiCall();

    Single<UserModel> getMYSeekerProfilesApiCall();

    Single<RelativeProfileResponse> getRelativeProfilesByIDApiCall(String profileID);

    Single<BaseResponse> updateRelativeProfilesApiCall(JSONObject object);

    Single<BaseResponse> deleteRelativeProfilesApiCall(String profileID);

    Single<UserModel> updateMyProfilesApiCall(JSONObject object);

    Single<BaseResponse> createRelativeProfilesApiCall(JSONObject object);

    Single<List<UserLocationResponse>> getUserLocationApiCall();

    Single<List<SearchGiverResponse>> getRecommendedCaregivers(JSONObject object);

    Single<List<SearchGiverResponse>> getFavouriteCaregivers(JSONObject object);

    Single<List<SearchGiverResponse>> getSearchForCaregivers(JSONObject object);

    Single<BaseResponse> createUserLocationApiCall(JSONObject object);

    Single<BaseResponse> updateUserLocationApiCall(JSONObject object, String locationId);

    Single<BaseResponse> deleteUserLocationApiCall(String locationId);

    Single<BaseResponse> createOrderApiCall(JSONObject object);

    Single<InformationAttachmentObj> uploadOrderImageApiCall(String name, File file);

    Single<UserModel> updateUserAvatarApiCall(File file);

    Single<List<InsuranceCompanyModel>> getInsuranceCompany();

    Single<List<SearchGiverResponse>> getAllBlockedCaregiverProfile();

    Single<List<SearchGiverResponse>> getAllFavouriteCaregiverProfile();

    Single<CaregiverUser> getAllSearchCaregiverProfile(String caregiverID);

    //orders
    public Single<List<OrderModel>> getOrders(String status, int offset, int limit);

    Single<BaseResponse> cancelOrder(JSONObject object);

    Single<RatingResponse> doRating(JSONObject object);

    Single<CaregiverUser> getCaregiverProfile(String caregiverID);


}
