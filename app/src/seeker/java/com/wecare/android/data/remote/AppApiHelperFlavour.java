package com.wecare.android.data.remote;

import android.util.Log;

import com.androidnetworking.interfaces.UploadProgressListener;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.models.*;
import com.wecare.android.data.model.api.responses.*;
import com.wecare.android.utils.ServerKeys;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class AppApiHelperFlavour extends AppApiHelper implements ApiHelperFlavour {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelperFlavour(ApiHeader apiHeader) {
        super(apiHeader);
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<SocialLoginResponse> doGoogleLoginApiCall(String accessToken) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addBodyParameter(ServerKeys.PARAM_ACCESS_TOKEN, accessToken)
                .build()
                .getObjectSingle(SocialLoginResponse.class);
    }

    @Override
    public Single<SocialLoginResponse> doFacebookLoginApiCall(String accessToken) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addBodyParameter(ServerKeys.PARAM_ACCESS_TOKEN, accessToken)
                .build()
                .getObjectSingle(SocialLoginResponse.class);
    }

    @Override
    public Single<SocialLoginResponse> doTwitterLoginApiCall(String accessToken, String secretKey) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TWITTER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addBodyParameter(ServerKeys.PARAM_ACCESS_TOKEN, accessToken)
                .addBodyParameter(ServerKeys.PARAM_SECRET_KEY, secretKey)
                .build()
                .getObjectSingle(SocialLoginResponse.class);
    }

    @Override
    public Single<List<RelativeProfileResponse>> getRelativeProfilesApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RELATIVE_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getInfoApiHeader())
                .build()
                .getObjectListSingle(RelativeProfileResponse.class);
    }

    @Override
    public Single<UserModel> getMYSeekerProfilesApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_MY_SEEKER_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(UserModel.class);

    }

    @Override
    public Single<BaseResponse> createRelativeProfilesApiCall(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_RELATIVE_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<RelativeProfileResponse> getRelativeProfilesByIDApiCall(String profileID) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_SPECIFIC_RELATIVE_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_ID, profileID)
                .build()
                .getObjectSingle(RelativeProfileResponse.class);
    }

    @Override
    public Single<BaseResponse> updateRelativeProfilesApiCall(JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UPDATE_RELATIVE_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> deleteRelativeProfilesApiCall(String profileID) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_RELATIVE_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_ID, profileID)
//                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<UserModel> updateMyProfilesApiCall(JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_SET_MY_SEEKER_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(UserModel.class);
    }

    @Override
    public Single<List<UserLocationResponse>> getUserLocationApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_LOCATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(UserLocationResponse.class);
    }

    @Override
    public Single<List<SearchGiverResponse>> getRecommendedCaregivers(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_RECOMMENDED_CAREGIVERS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectListSingle(SearchGiverResponse.class);
    }

    @Override
    public Single<List<SearchGiverResponse>> getFavouriteCaregivers(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_FAVOURITE_CAREGIVERS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectListSingle(SearchGiverResponse.class);
    }

    @Override
    public Single<List<SearchGiverResponse>> getSearchForCaregivers(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_SEARCH_FOR_CAREGIVERS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectListSingle(SearchGiverResponse.class);
    }

    @Override
    public Single<BaseResponse> createUserLocationApiCall(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_LOCATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> updateUserLocationApiCall(JSONObject object, String locationId) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UPDATE_LOCATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_ID, locationId)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> deleteUserLocationApiCall(String locationId) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_LOCATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_ID, locationId)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> createOrderApiCall(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_ORDER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<InformationAttachmentObj> uploadOrderImageApiCall(String name, File file) {

        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_UPLOAD_ORDER_IMAGES)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_IMAGE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addMultipartParameter(ServerKeys.PARAM_TITLE, name)
                .addMultipartFile(ServerKeys.PARAM_FILE, file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.d("Uploading", bytesUploaded + "");
                    }
                })
                .getObjectSingle(InformationAttachmentObj.class);
    }

    @Override
    public Single<UserModel> updateUserAvatarApiCall(File file) {
        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_UPLOAD_USER_AVATARS)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_IMAGE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addMultipartFile(ServerKeys.PARAM_AVATAR_IMAGE, file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
//                        Log.d("Uploading", bytesUploaded + "");
                    }
                })
                .getObjectSingle(UserModel.class);
    }

    @Override
    public Single<List<InsuranceCompanyModel>> getInsuranceCompany() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_INSURANCE_COMPANY)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(InsuranceCompanyModel.class);
    }

    @Override
    public Single<List<OrderModel>> getOrders(String status, int offset, int limit) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ORDERS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_STATUS, status)
                .addQueryParameter(ServerKeys.PARAM_OFFSET, offset + "")
                .addQueryParameter(ServerKeys.PARAM_LIMIT, limit + "")
                .build()
                .getObjectListSingle(OrderModel.class);
    }

    @Override
    public Single<BaseResponse> cancelOrder(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CANCEL_ORDER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<RatingResponse> doRating(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RATE_ORDER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(RatingResponse.class);
    }

    @Override
    public Single<CaregiverUser> getCaregiverProfile(String caregiverID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_CAREGIVER_PROFILE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.CreateOrderParams.PARAM_CAREGIVER_ID, caregiverID)
                .addQueryParameter(ServerKeys.PARAM_EXPAND, "educations,languages,attachments,experiences,certificates,services")
                .build()
                .getObjectSingle(CaregiverUser.class);
    }


    @Override
    public Single<List<SearchGiverResponse>> getAllBlockedCaregiverProfile() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ALL_USER_BLOCKED_CAREGIVERS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(SearchGiverResponse.class);
    }

    @Override
    public Single<List<SearchGiverResponse>> getAllFavouriteCaregiverProfile() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ALL_USER_FAVOURITE_CAREGIVERS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(SearchGiverResponse.class);
    }

    @Override
    public Single<CaregiverUser> getAllSearchCaregiverProfile(String caregiverID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ALL_USER_SEARCH_FOR_CAREGIVERS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.RecommendedCaregiversFilters.PARAM_WECARE_ID, caregiverID)
                .build()
                .getObjectSingle(CaregiverUser.class);
    }

}
