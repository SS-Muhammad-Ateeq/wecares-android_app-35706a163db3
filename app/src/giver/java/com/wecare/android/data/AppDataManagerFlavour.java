package com.wecare.android.data;

import android.content.Context;

import com.wecare.android.data.local.db.DbHelper;
import com.wecare.android.data.local.prefs.PreferencesHelper;
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
    public Single<BaseResponse> createSchedule(JSONObject object) {
        return mApiHelper.createSchedule(object);
    }

    @Override
    public Single<UserScheduleResponse> getUserSchedule() {
        return mApiHelper.getUserSchedule();
    }

    @Override
    public Single<UserMainServicesResponse> getUserMainServices() {
        return mApiHelper.getUserMainServices();
    }

    @Override
    public Single<BaseResponse> addUserService(JSONObject object) {
        return mApiHelper.addUserService(object);
    }

    @Override
    public Single<BaseResponse> editUserService(JSONObject object) {
        return mApiHelper.editUserService(object);
    }

    @Override
    public Single<BaseResponse> deleteUserService(JSONObject object) {
        return mApiHelper.deleteUserService(object);
    }

    @Override
    public Single<BaseResponse> addLanguage(JSONObject object) {
        return mApiHelper.addLanguage(object);
    }

    @Override
    public Single<List<LanguageRequest>> getLanguage() {
        return mApiHelper.getLanguage();
    }

    @Override
    public Single<UserModel> updateUser(JSONObject object) {
        return mApiHelper.updateUser(object);
    }

    @Override
    public Single<BaseResponse> addEducation(JSONObject object) {
        return mApiHelper.addEducation(object);
    }

    @Override
    public Single<EducationModel> updateEducation(String id, JSONObject object) {
        return mApiHelper.updateEducation(id, object);
    }

    @Override
    public Single<MalInsuranceModel> updateMalInsurance(String id, JSONObject object) {
        return mApiHelper.updateMalInsurance(id,object);
    }

    @Override
    public Single<List<EducationModel>> getUserEducation() {
        return mApiHelper.getUserEducation();
    }

    @Override
    public Single<BaseResponse> addExperience(JSONObject object) {
        return mApiHelper.addExperience(object);
    }

    @Override
    public Single<BaseResponse> addMalInsurance(JSONObject object) {
        return mApiHelper.addMalInsurance(object);
    }

    @Override
    public Single<ExperienceModel> updateExperience(String id, JSONObject object) {
        return mApiHelper.updateExperience(id, object);

    }

    @Override
    public Single<List<ExperienceModel>> getUserExperience() {
        return mApiHelper.getUserExperience();
    }

    @Override
    public Single<List<MalInsuranceModel>> getUserMalInsurance() {
        return mApiHelper.getUserMalInsurance();
    }

    @Override
    public Single<BaseResponse> deleteExperience(String id) {
        return mApiHelper.deleteExperience(id);
    }

    @Override
    public Single<BaseResponse> deleteEducation(String id) {
        return mApiHelper.deleteEducation(id);
    }

    @Override
    public Single<AttachmentModel> uploadCaregiverCertificate(String name, File file) {
        return mApiHelper.uploadCaregiverCertificate(name, file);
    }

    @Override
    public Single<AttachmentModel> uploadCaregiverAttachment(String name, String categoryID, File file) {
        return mApiHelper.uploadCaregiverAttachment(name, categoryID, file);
    }

    @Override
    public Single<BaseResponse> deleteLanguage(String id) {
        return mApiHelper.deleteLanguage(id);
    }

    @Override
    public Single<BaseResponse> deleteAttachment(String id) {
        return mApiHelper.deleteAttachment(id);
    }

    @Override
    public Single<BaseResponse> deleteCertificate(String id) {
        return mApiHelper.deleteCertificate(id);
    }

    @Override
    public Single<BaseResponse> deleteMalInsurance(String id) {
        return mApiHelper.deleteMalInsurance(id);
    }

    @Override
    public Single<List<AttachmentModel>> getUserAttachments() {
        return mApiHelper.getUserAttachments();
    }

    @Override
    public Single<List<AttachmentModel>> getUserCertificates() {
        return mApiHelper.getUserCertificates();
    }

    @Override
    public Single<UserModel> uploadUserProfilePicture(File file) {
        return mApiHelper.uploadUserProfilePicture(file);
    }


    @Override
    public Single<BankModel> getBankInfo() {
        return mApiHelper.getBankInfo();
    }

    @Override
    public Single<BankModel> setBankInfo(JSONObject object) {
        return mApiHelper.setBankInfo(object);
    }

    @Override
    public Single<LocationModel> getServiceAreaLocation() {
        return mApiHelper.getServiceAreaLocation();
    }

    @Override
    public Single<LocationModel> setServiceAreaLocation(JSONObject object) {
        return mApiHelper.setServiceAreaLocation(object);
    }

    @Override
    public Single<List<OrderModel>> getOrders(String status, int offset, int limit) {
        return mApiHelper.getOrders(status,offset,limit);
    }

    @Override
    public Single<OrderModel> changeOrderStatus(JSONObject object) {
        return mApiHelper.changeOrderStatus(object);
    }

    @Override
    public Single<BaseResponse> cancelOrder(JSONObject object) {
        return mApiHelper.cancelOrder(object);
    }

    @Override
    public Single<BaseResponse> rejectOrder(JSONObject object) {
        return mApiHelper.rejectOrder(object);
    }

    @Override
    public Single<List<MainServiceModel>> getOrderServices(String orderID) {
        return mApiHelper.getOrderServices(orderID);
    }

    @Override
    public Single<OrderModel> finishOrder(JSONObject object) {
        return mApiHelper.finishOrder(object);
    }

    @Override
    public Single<RatingResponse> doRating(JSONObject object) {
        return mApiHelper.doRating(object);
    }

    @Override
    public Single<BaseResponse> requestToActivate() {
        return mApiHelper.requestToActivate();
    }

    @Override
    public Single<BaseResponse> holdServices(String status) {
        return mApiHelper.holdServices(status);
    }

    @Override
    public Single<GiverWalletResponse> getGiverWallet() {
        return mApiHelper.getGiverWallet();
    }

    @Override
    public Single<List<InsuranceCompanyLookup>> getInsuranceCompanies(String countryID) {
        return mApiHelper.getInsuranceCompanies(countryID);
    }
}
