package com.wecare.android.data.remote;

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

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;


//apis list
public interface ApiHelper {

    ApiHeader getApiHeader();

    // common API
    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Single<LookupResponse> getLookups();

    Single<CountriesResponse> getCountries(String active);

    Single<List<CountryModel>> getNationality();

    Single<List<PageContentResponse>> getPagesRemote();

    Single<List<CityModel>> getCites(String countryID);

    // giver API
    Single<UserModel> getUserInfo();

    Single<List<LookUpModel>> getGenderIndex();


    Single<BaseResponse> doLogoutApiCall();

    //home services api
    Single<List<MainServiceModel>> getServicesApiCall(String countryId);

    Single<StatisticResponse> getStatistics(String countryID);

    Single<ResetPassResponse> resetPassByEmail(JSONObject jsonObject);

    Single<UserModel> updatePassByEmail(JSONObject jsonObject);

    Single<ResetPassResponse> resetPassByPhone(JSONObject jsonObject);

    Single<UserModel> updatePassByPhone(JSONObject jsonObject);

    Single<LoginResponse> registerCaregiver(JSONObject registrationModel);


    Single<UserModel> sendVerificationCode(String verification_code,JSONObject jsonObject);

    Single<DummyVerificationCode> resendVerificationCode(JSONObject object);

    Single<List<NotificationModel>> getNotifications();

    Single<BaseResponse> deleteNotification(String id);

    Single <UserModel> changePassword(JSONObject object);

    Single <CaregiverUser> favoriteCaregiver(JSONObject object);

    Single <BaseResponse> unFavoriteCaregiver(String caregiverID);

    public Single<BaseResponse> blockGiver(JSONObject object);
    public Single<BaseResponse> unBlockGiver(JSONObject object);

    Single<BaseResponse> blockSeeker(JSONObject object);
    Single<BaseResponse> unBlockSeeker(JSONObject object);

    Single <CaregiverUser> getCaregiverProfile(String caregiverID);

    Single <WalletResponse> getWallet();
    Single <List<TransactionsModel>> getTransactions(String countryID,String offset);
    Single <List<WalletResponse>> getSeekerWallets();

    Single <NotificationCountResponse> getNotificationsCount();
    Single <NotificationCountResponse> markNotificationsRead();

    Single<BaseResponse> enableNotification(String active);


    Single<OrderModel> getOrderDetails(String orderID);

    Single<List<MembershipType>> getMemberShipTypes(String countryID);




}
