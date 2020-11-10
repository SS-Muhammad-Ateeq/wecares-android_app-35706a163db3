package com.wecare.android.data.remote;

import android.util.Log;

import com.androidnetworking.interfaces.UploadProgressListener;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.wecare.android.data.model.api.models.AttachmentModel;
import com.wecare.android.data.model.api.models.BankModel;
import com.wecare.android.data.model.api.models.EducationModel;
import com.wecare.android.data.model.api.models.ExperienceModel;
import com.wecare.android.data.model.api.models.InsuranceCompanyLookup;
import com.wecare.android.data.model.api.models.LocationModel;
import com.wecare.android.data.model.api.models.MalInsuranceModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.requests.LanguageRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.GiverWalletResponse;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.RatingResponse;
import com.wecare.android.data.model.api.responses.UserMainServicesResponse;
import com.wecare.android.data.model.api.responses.UserScheduleResponse;
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
    public Single<BaseResponse> createSchedule(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_SCHEDULE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<UserScheduleResponse> getUserSchedule() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_USER_SCHEDULE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .build()
                .getObjectSingle(UserScheduleResponse.class);
    }

    @Override
    public Single<UserMainServicesResponse> getUserMainServices() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ALL_USER_SERVICES)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(UserMainServicesResponse.class);
    }

    @Override
    public Single<BaseResponse> addUserService(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ADD_SERVICE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> editUserService(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_EDIT_SERVICE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> addLanguage(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ADD_LANGUAGE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<List<LanguageRequest>> getLanguage() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_LANGUAGES)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(LanguageRequest.class);
    }

    @Override
    public Single<BaseResponse> deleteUserService(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELETE_SERVICE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> addEducation(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ADD_EDUCATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<EducationModel> updateEducation(String id, JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UPDATE_EDUCATION)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(EducationModel.class);
    }

    @Override
    public Single<MalInsuranceModel> updateMalInsurance(String id, JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UPDATE_MAL_INSURANCE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(MalInsuranceModel.class);    }

    @Override
    public Single<BaseResponse> addExperience(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ADD_EXPERIENCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> addMalInsurance(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ADD_MAL_INSURANCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);    }

    @Override
    public Single<ExperienceModel> updateExperience(String id, JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UPDATE_EXPERIENCE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(ExperienceModel.class);
    }

    @Override
    public Single<UserModel> updateUser(JSONObject jsonObject) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UPDATE_USER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectSingle(UserModel.class);
    }

    @Override
    public Single<BaseResponse> deleteExperience(String id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_EXPERIENCE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> deleteEducation(String id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_EDUCATION)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> deleteLanguage(String id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_LANGUAGE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> deleteAttachment(String id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_ATTACHMENT)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> deleteCertificate(String id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_CERTIFICATE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> deleteMalInsurance(String id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_MAL_INSURANCE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .build()
                .getObjectSingle(BaseResponse.class);    }

    @Override
    public Single<List<EducationModel>> getUserEducation() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_EDUCATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(EducationModel.class);
    }

    @Override
    public Single<List<MalInsuranceModel>> getUserMalInsurance() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_MAL_INSURANCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(MalInsuranceModel.class);    }

    @Override
    public Single<List<ExperienceModel>> getUserExperience() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_EXPERIENCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(ExperienceModel.class);
    }

    @Override
    public Single<AttachmentModel> uploadCaregiverCertificate(String name, File file) {
        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_UPLOAD_CERTIFICATE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_IMAGE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addMultipartParameter(ServerKeys.PARAM_TITLE, name)
                .addMultipartFile(ServerKeys.PARAM_CERTIFICATE, file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.d("Uplaoding", bytesUploaded + "");
                    }
                })
                .getObjectSingle(AttachmentModel.class);
    }

    @Override
    public Single<AttachmentModel> uploadCaregiverAttachment(String name, String categoryID, File file) {
        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_UPLOAD_ATTACHMENT)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_IMAGE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders("")
                .addMultipartParameter(ServerKeys.PARAM_CATEGORY_ID, categoryID)
                .addMultipartFile(ServerKeys.PARAM_ATTACHMENT, file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.d("Uplaoding", bytesUploaded + "");
                    }
                })
                .getObjectSingle(AttachmentModel.class);
    }

    @Override
    public Single<List<AttachmentModel>> getUserAttachments() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ATTACHMENTS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(AttachmentModel.class);
    }

    @Override
    public Single<List<AttachmentModel>> getUserCertificates() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_CERTIFICATES)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(AttachmentModel.class);
    }

    @Override
    public Single<UserModel> uploadUserProfilePicture(File file) {
        String token=mApiHeader.getProtectedApiHeader().getAccessToken();
        Log.e("token",token);
        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_USER_PROFILE_PICTURE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, "multipart/form-data")
                .addHeaders("Authorization",token)
                .addMultipartFile(ServerKeys.PARAM_AVATAR_IMAGE, file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.d("Uplaoding", bytesUploaded + "");
                    }
                })
                .getObjectSingle(UserModel.class);
    }



    @Override
    public Single<BankModel> getBankInfo() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_BANK_INFO)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(BankModel.class);
    }

    @Override
    public Single<BankModel> setBankInfo(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SET_BANK_INFO)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BankModel.class);
    }

    @Override
    public Single<LocationModel> getServiceAreaLocation() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_LOCATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(LocationModel.class);
    }

    @Override
    public Single<LocationModel> setServiceAreaLocation(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SET_LOCATION)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(LocationModel.class);
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
    public Single<OrderModel> changeOrderStatus(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CHANGE_ORDER_STATUS)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(OrderModel.class);
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
    public Single<BaseResponse> rejectOrder(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_REJECT_ORDER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<List<MainServiceModel>> getOrderServices(String orderID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ORDER_SERVICES_FOR_CAREGIVER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ORDER_ID, orderID)
                .build()
                .getObjectListSingle(MainServiceModel.class);
    }

    @Override
    public Single<OrderModel> finishOrder(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FINISH_ORDER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(OrderModel.class);
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
    public Single<BaseResponse> requestToActivate() {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_REQUEST_TO_ACTIVATE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> holdServices(String status) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SETTINGS_HOLD_SERVICE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ACTIVE, status)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<GiverWalletResponse> getGiverWallet() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_WALLET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(GiverWalletResponse.class);    }

    @Override
    public Single<List<InsuranceCompanyLookup>> getInsuranceCompanies(String countryID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_INSURANCE_COMPANIES)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_COUNTRY_ID, countryID)
                .build()
                .getObjectListSingle(InsuranceCompanyLookup.class);     }
}
