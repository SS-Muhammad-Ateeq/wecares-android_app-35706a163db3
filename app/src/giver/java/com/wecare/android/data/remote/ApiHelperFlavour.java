package com.wecare.android.data.remote;


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

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import io.reactivex.Single;

public interface ApiHelperFlavour extends ApiHelper {

    Single<BaseResponse> createSchedule(JSONObject object);

    Single<UserScheduleResponse> getUserSchedule();

    Single<UserMainServicesResponse> getUserMainServices();

    Single<BaseResponse> addUserService(JSONObject object);
    Single<BaseResponse> editUserService(JSONObject object);
    Single<BaseResponse> deleteUserService(JSONObject object);
    Single<BaseResponse> addLanguage(JSONObject object);
    Single<List<LanguageRequest>> getLanguage();
    Single<UserModel> updateUser(JSONObject object);
    Single<BaseResponse> addEducation(JSONObject object);
    Single<EducationModel> updateEducation(String id,JSONObject object);
    Single<MalInsuranceModel> updateMalInsurance(String id, JSONObject object);
    Single<List<EducationModel>> getUserEducation();
    Single<BaseResponse> addExperience(JSONObject object);
    Single<BaseResponse> addMalInsurance(JSONObject object);
    Single<ExperienceModel> updateExperience(String id,JSONObject object);
    Single<List<ExperienceModel>> getUserExperience();
    Single<List<MalInsuranceModel>> getUserMalInsurance();
    Single<BaseResponse> deleteExperience(String id);
    Single<BaseResponse> deleteEducation(String id);
    Single<BaseResponse> deleteLanguage(String id);
    Single<BaseResponse> deleteAttachment(String id);
    Single<BaseResponse> deleteCertificate(String id);
    Single<BaseResponse> deleteMalInsurance(String id);


    Single<AttachmentModel> uploadCaregiverCertificate(String name, File file);
    Single<AttachmentModel> uploadCaregiverAttachment(String name, String categoryID, File file);

    Single<List<AttachmentModel>> getUserAttachments();
    Single<List<AttachmentModel>> getUserCertificates();

    Single<UserModel> uploadUserProfilePicture(File file);


    Single<BankModel> getBankInfo();
    Single<BankModel> setBankInfo(JSONObject object);

    Single<LocationModel> getServiceAreaLocation();
    Single<LocationModel> setServiceAreaLocation(JSONObject object);

    Single<List<OrderModel>> getOrders(String status,int offset,int limit);

    Single<OrderModel> changeOrderStatus(JSONObject object);
    Single<BaseResponse> cancelOrder(JSONObject object);
    Single<BaseResponse> rejectOrder(JSONObject object);

    Single<List<MainServiceModel>> getOrderServices(String orderID);

    Single<OrderModel> finishOrder(JSONObject object);

    Single <RatingResponse> doRating(JSONObject object);

    Single <BaseResponse> requestToActivate();

    //settings
    Single <BaseResponse> holdServices(String status);

    Single <GiverWalletResponse> getGiverWallet();

    Single <List<InsuranceCompanyLookup>> getInsuranceCompanies(String countryID);


}
