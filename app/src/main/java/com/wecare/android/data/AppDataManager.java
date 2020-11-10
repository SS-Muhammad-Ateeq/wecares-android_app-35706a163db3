package com.wecare.android.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.wecare.android.data.local.db.DbHelper;
import com.wecare.android.data.local.prefs.AppPreferencesHelper;
import com.wecare.android.data.local.prefs.PreferencesHelper;
import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.DummyVerificationCode;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.RegistrationModel;
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
import com.wecare.android.data.model.db.Option;
import com.wecare.android.data.model.db.Question;
import com.wecare.android.data.model.db.User;
import com.wecare.android.data.remote.ApiHeader;
import com.wecare.android.data.remote.ApiHelperFlavour;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.CommonUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;


//@Singleton
abstract class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;

    private List<PageContentResponse> pageContentResponseList;


    private LookupResponse lookupsModel;
    private RegistrationModel registrationModel;

    //this is for development purposes, delete after go live
    private String verificationCode;
    private UserModel caregiverUserModel;

    private final ApiHelperFlavour mApiHelper;

    //    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelperFlavour apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public Single<List<LookUpModel>> getGenderIndex() {
        return mApiHelper.getGenderIndex();
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void updateApiInfoHeader(String deviceType, String osVersion, String appVersion) {
        mApiHelper.getApiHeader().getInfoApiHeader().setDeviceType(deviceType);
        mApiHelper.getApiHeader().getInfoApiHeader().setOsVersion(osVersion);
        mApiHelper.getApiHeader().getInfoApiHeader().setAppVersion(appVersion);
        if (getPushToken() != AppPreferencesHelper.KEY_NO_VALUE)
            mApiHelper.getApiHeader().getInfoApiHeader().setFirebaseToken(getPushToken());
        if (getAppLocale() != AppPreferencesHelper.KEY_NO_VALUE)
            mApiHelper.getApiHeader().getInfoApiHeader().setLang(getAppLocale());
    }

    @Override
    public void updateApiInfoHeader(String pushToken) {
        mApiHelper.getApiHeader().getInfoApiHeader().setFirebaseToken(pushToken);

    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Boolean isIntroViewed() {
        return mPreferencesHelper.isIntroViewed();
    }

    @Override
    public void setIntroViewed(Boolean isViewed) {
        mPreferencesHelper.setIntroViewed(isViewed);
    }

    @Override
    public String getUserBio() {
        return mPreferencesHelper.getUserBio();
    }

    @Override
    public void setCurrentUserBio(String userBio) {
        mPreferencesHelper.setCurrentUserBio(userBio);
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Single<List<CityModel>> getCites(String countryID) {
        return mApiHelper.getCites(countryID);
    }

    @Override
    public Single<CountriesResponse> getCountries(String active) {
        return mApiHelper.getCountries(active);
    }

    @Override
    public Single<List<CountryModel>> getNationality() {
        return mApiHelper.getNationality();
    }

    @Override
    public Single<List<PageContentResponse>> getPagesRemote() {
        return mApiHelper.getPagesRemote();
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Single<LookupResponse> getLookups() {
        return mApiHelper.getLookups();
    }

    @Override
    public Single<UserModel> getUserInfo() {
        return mApiHelper.getUserInfo();
    }

    @Override
    public Single<BaseResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateApiHeader(String accessToken) {
        setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Single<DummyVerificationCode> resendVerificationCode(JSONObject object) {
        return mApiHelper.resendVerificationCode(object);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);

        updateApiHeader(accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }

    @Override
    public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<List<Option>> getOptionsForQuestionId(Long questionId) {
        return mDbHelper.getOptionsForQuestionId(questionId);
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isQuestionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = $Gson$Types
                                    .newParameterizedTypeWithOwner(null, List.class,
                                            Question.class);
                            List<Question> questionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_QUESTIONS),
                                    type);

                            return saveQuestionList(questionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isOptionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = new TypeToken<List<Option>>() {
                            }
                                    .getType();
                            List<Option> optionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_OPTIONS),
                                    type);

                            return saveOptionList(optionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Single<List<MainServiceModel>> getServicesApiCall(String countryId) {
        return mApiHelper.getServicesApiCall(countryId);
    }

    @Override
    public Single<StatisticResponse> getStatistics(String countryID) {
        return mApiHelper.getStatistics(countryID);
    }

    @Override
    public Single<ResetPassResponse> resetPassByEmail(JSONObject jsonObject) {
        return mApiHelper.resetPassByEmail(jsonObject);
    }

    @Override
    public Single<UserModel> updatePassByEmail(JSONObject jsonObject) {
        return mApiHelper.updatePassByEmail(jsonObject);
    }

    @Override
    public Single<ResetPassResponse> resetPassByPhone(JSONObject jsonObject) {
        return mApiHelper.resetPassByPhone(jsonObject);
    }

    @Override
    public Single<UserModel> updatePassByPhone(JSONObject jsonObject) {
        return mApiHelper.updatePassByPhone(jsonObject);
    }

    @Override
    public Single<LoginResponse> registerCaregiver(JSONObject registrationModel) {
        return mApiHelper.registerCaregiver(registrationModel);
    }


    @Override
    public Single<List<NotificationModel>> getNotifications() {
        return mApiHelper.getNotifications();
    }

    @Override
    public Single<BaseResponse> deleteNotification(String id) {
        return mApiHelper.deleteNotification(id);
    }

    @Override
    public Single<UserModel> changePassword(JSONObject object) {
        return mApiHelper.changePassword(object);
    }

    ///////

    @Override
    public void setLookupsModel(LookupResponse lookupsModel) {
        this.lookupsModel = lookupsModel;
    }

    @Override
    public LookupResponse getLookupsModel() {
        return lookupsModel;
    }

    @Override
    public void setPages(List<PageContentResponse> pages) {
        this.pageContentResponseList = pages;
    }

    @Override
    public List<PageContentResponse> getPages() {
        return pageContentResponseList;
    }

    @Override
    public void setVerificationCode(String code) {
        verificationCode = code;
    }

    @Override
    public String getVerificationCode() {
        return verificationCode;
    }

    @Override
    public void setRegistrationModel(RegistrationModel registrationModel) {
        this.registrationModel = registrationModel;
    }

    @Override
    public RegistrationModel getRegistrationModel() {
        return registrationModel;
    }

    @Override
    public void setCurrentUserModel(UserModel userModel) {
        caregiverUserModel = userModel;
    }

    @Override
    public UserModel getCurrentUserModel() {
        return caregiverUserModel;
    }

    @Override
    public Single<UserModel> sendVerificationCode(String code, JSONObject jsonObject) {
        return mApiHelper.sendVerificationCode(code, jsonObject);
    }


    @Override
    public String getCountryID() {
        return mPreferencesHelper.getCountryID();
    }

    @Override
    public void setCountryID(String countryID) {
        mPreferencesHelper.setCountryID(countryID);
    }

    @Override
    public void setAppLocale(String locale) {
        mApiHelper.getApiHeader().getInfoApiHeader().setLang(locale);
        mPreferencesHelper.setAppLocale(locale);
    }

    @Override
    public String getAppLocale() {
        return mPreferencesHelper.getAppLocale();
    }


    @Override
    public Single<CaregiverUser> favoriteCaregiver(JSONObject object) {
        return mApiHelper.favoriteCaregiver(object);
    }

    @Override
    public Single<BaseResponse> unFavoriteCaregiver(String caregiverID) {
        return mApiHelper.unFavoriteCaregiver(caregiverID);
    }

    @Override
    public Single<BaseResponse> blockGiver(JSONObject object) {
        return mApiHelper.blockGiver(object);
    }

    @Override
    public Single<BaseResponse> unBlockGiver(JSONObject object) {
        return mApiHelper.unBlockGiver(object);
    }

    @Override
    public Single<BaseResponse> blockSeeker(JSONObject object) {
        return mApiHelper.blockSeeker(object);
    }

    @Override
    public Single<BaseResponse> unBlockSeeker(JSONObject object) {
        return mApiHelper.unBlockSeeker(object);
    }

    @Override
    public Single<CaregiverUser> getCaregiverProfile(String caregiverID) {
        return mApiHelper.getCaregiverProfile(caregiverID);
    }

    @Override
    public Single<WalletResponse> getWallet() {
        return mApiHelper.getWallet();
    }

    @Override
    public Single<List<TransactionsModel>> getTransactions(String countryID, String offset) {
        return mApiHelper.getTransactions(countryID, offset);
    }

    @Override
    public Single<List<WalletResponse>> getSeekerWallets() {
        return mApiHelper.getSeekerWallets();
    }

    @Override
    public Single<NotificationCountResponse> getNotificationsCount() {
        return mApiHelper.getNotificationsCount();
    }

    @Override
    public Single<NotificationCountResponse> markNotificationsRead() {
        return mApiHelper.markNotificationsRead();
    }

    @Override
    public void setPushToken(String pushToken) {
        mPreferencesHelper.setPushToken(pushToken);
    }

    @Override
    public String getPushToken() {
        return mPreferencesHelper.getPushToken();
    }

    @Override
    public Single<BaseResponse> enableNotification(String active) {
        return mApiHelper.enableNotification(active);
    }

    @Override
    public Single<OrderModel> getOrderDetails(String orderID) {
        return mApiHelper.getOrderDetails(orderID);
    }

    @Override
    public Single<List<MembershipType>> getMemberShipTypes(String countryID) {
        return mApiHelper.getMemberShipTypes(countryID);
    }
}
