package com.wecare.android.data.remote;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.DummyVerificationCode;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.requests.LoginRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.data.model.api.responses.LoginResponse;
import com.wecare.android.data.model.api.responses.LookupResponse;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.NotificationCountResponse;
import com.wecare.android.data.model.api.responses.PageContentResponse;
import com.wecare.android.data.model.api.responses.ResetPassResponse;
import com.wecare.android.data.model.api.responses.StatisticResponse;
import com.wecare.android.data.model.api.responses.WalletResponse;
import com.wecare.android.utils.ServerKeys;
import com.wecare.android.utils.WeCareUtils;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;


//@Singleton
class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    //    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LookupResponse> getLookups() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_LOOKUPS)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .build()
                .getObjectSingle(LookupResponse.class);
    }

    @Override
    public Single<UserModel> getUserInfo() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_USER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(UserModel.class);
    }

    @Override
    public Single<List<LookUpModel>> getGenderIndex() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENDERS)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(LookUpModel.class);
    }

    @Override
    public Single<CountriesResponse> getCountries(String active) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_COUNTRIES)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_ACTIVE, active)
                .build()
                .getObjectSingle(CountriesResponse.class);
    }

    @Override
    public Single<List<CountryModel>> getNationality() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_NATIONALITY)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(CountryModel.class);    }

    @Override
    public Single<List<PageContentResponse>> getPagesRemote() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PAGES)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(PageContentResponse.class);    }

    @Override
    public Single<List<CityModel>> getCites(String countryID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CITIES)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_COUNTRY_ID, countryID)
                .build()
                .getObjectListSingle(CityModel.class);
    }

    @Override
    public Single<BaseResponse> doLogoutApiCall() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<List<MainServiceModel>> getServicesApiCall(String countryId) {
        Rx2ANRequest.GetRequestBuilder getRequestBuilder = Rx2AndroidNetworking.get(CommonApiEndPoint.ENDPOINT_MAIN_SERVICES)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON);
        if (!WeCareUtils.isNullOrEmpty(countryId)) {
            getRequestBuilder.addQueryParameter(ServerKeys.PARAM_COUNTRY_ID, countryId);
        }
        return getRequestBuilder.build().getObjectListSingle(MainServiceModel.class);
    }

    @Override
    public Single<StatisticResponse> getStatistics(String countryID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_STATISTICS)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_COUNTRY_ID, countryID)
                .build()
                .getObjectSingle(StatisticResponse.class);
    }

    @Override
    public Single<ResetPassResponse> resetPassByEmail(JSONObject jsonObject) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RESET_PASS_EMAIL)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectSingle(ResetPassResponse.class);
    }

    @Override
    public Single<UserModel> updatePassByEmail(JSONObject jsonObject) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_RESET_PASS_EMAIL)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectSingle(UserModel.class);
    }

    @Override
    public Single<ResetPassResponse> resetPassByPhone(JSONObject jsonObject) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RESET_PASS_PHONE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectSingle(ResetPassResponse.class);
    }

    @Override
    public Single<UserModel> updatePassByPhone(JSONObject jsonObject) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_RESET_PASS_PHONE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectSingle(UserModel.class);
    }

    @Override
    public Single<LoginResponse> registerCaregiver(JSONObject registrationModel) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_REGISTER_CAREGIVER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(registrationModel)
                .build()
                .getObjectSingle(LoginResponse.class);
    }


    @Override
    public Single<DummyVerificationCode> resendVerificationCode(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RESEND_VERIFICATION_CODE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(DummyVerificationCode.class);
    }

    @Override
    public Single<UserModel> sendVerificationCode(String verification_code, JSONObject jsonObject) {

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SUBMIT_VERIFICATION_CODE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_VERIFICATION_CODE, verification_code)
                .addJSONObjectBody(jsonObject)
                .build()
                .getObjectSingle(UserModel.class);
    }


    @Override
    public Single<List<NotificationModel>> getNotifications() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_NOTIFICATION)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .build()
                .getObjectListSingle(NotificationModel.class);
    }

    @Override
    public Single<BaseResponse> deleteNotification(String id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_DELETE_NOTIFICATION)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.PARAM_ID, id)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<UserModel> changePassword(JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_SETTINGS_CHANGE_PASSWORD)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(UserModel.class);
    }

    @Override
    public Single<CaregiverUser> favoriteCaregiver(JSONObject object) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SET_CAREGIVER_FAVORITE)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(CaregiverUser.class);
    }

    @Override
    public Single<BaseResponse> unFavoriteCaregiver(String caregiverID) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_UNFAVORITE_CAREGIVER_)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addQueryParameter(ServerKeys.CreateOrderParams.PARAM_CAREGIVER_ID, caregiverID)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> blockGiver(JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_BLOCK_GIVER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> unBlockGiver(JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UNBLOCK_GIVER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> blockSeeker(JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_BLOCK_SEEKER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<BaseResponse> unBlockSeeker(JSONObject object) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_UNBLOCK_SEEKER)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addJSONObjectBody(object)
                .build()
                .getObjectSingle(BaseResponse.class);
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
    public Single<WalletResponse> getWallet() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_WALLET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(WalletResponse.class);
    }

    @Override
    public Single<List<TransactionsModel>> getTransactions(String countryID, String offset) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TRANSACTIONS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_COUNTRY_ID, countryID)
                .addQueryParameter(ServerKeys.PARAM_LIMIT, "10")
                .addQueryParameter(ServerKeys.PARAM_OFFSET, offset)
                .build()
                .getObjectListSingle(TransactionsModel.class);
    }

    @Override
    public Single<List<WalletResponse>> getSeekerWallets() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_WALLET)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectListSingle(WalletResponse.class);
    }

    @Override
    public Single<NotificationCountResponse> getNotificationsCount() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_NOTIFICATION_COUNT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(NotificationCountResponse.class);
    }

    @Override
    public Single<NotificationCountResponse> markNotificationsRead() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MARK_NOTIFICATION_READ)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .build()
                .getObjectSingle(NotificationCountResponse.class);
    }

    @Override
    public Single<BaseResponse> enableNotification(String active) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SETTINGS_NOTIFICATIONS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_ACTIVE, active)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<OrderModel> getOrderDetails(String orderID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_ORDER_DETAILS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_ORDER_ID, orderID)
                .build()
                .getObjectSingle(OrderModel.class);
    }

    @Override
    public Single<List<MembershipType>> getMemberShipTypes(String countryID) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MEMBERSHIP_TYPE)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addHeaders(mApiHeader.getInfoApiHeader())
                .addHeaders(ServerKeys.PARAM_ACCEPT, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addHeaders(ServerKeys.PARM_CONTENT_TYPE, ServerKeys.PARM_CONTENT_TYPE_JSON)
                .addQueryParameter(ServerKeys.PARAM_COUNTRY_ID, countryID)
                .build()
                .getObjectListSingle(MembershipType.class);
    }
}

