package com.wecare.android.ui.login;

import com.wecare.android.BuildConfig;
import com.wecare.android.WeCareApplication;
import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.requests.LoginRequest;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.data.model.api.responses.JWTResponse;
import com.wecare.android.data.model.api.responses.LoginResponse;
import com.wecare.android.data.model.api.responses.SocialLoginResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.*;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

import java.util.List;


public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManagerFlavour dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void openMainActivity() {
        getNavigator().openMainActivity();
    }

    public void onFacebookClicked() {
        getNavigator().onFacebookClicked();
    }

    public void onTwitterClicked() {
        getNavigator().onTwitterClicked();
    }

    public void onGoogleClicked() {
        getNavigator().onGoogleClicked();
    }

    public void doTwitterLoginClick(String accessToken, String secretKey) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doTwitterLoginApiCall(accessToken, secretKey)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SocialLoginResponse>() {
                    @Override
                    public void accept(SocialLoginResponse response) throws Exception {
                        if (!WeCareUtils.isNullOrEmpty(response.getTwitter_id())) {
                            getNavigator().onSocialAccountRegistrationNeeded(response.getFacebook_id(), AppConstants.ARGS_KEY_SOCIAL_FACEBOOK_HASH);
                            setIsLoading(false);
                        } else {
                            handleSuccessLoginCallBack(response);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void onGoogleLoginClick(String accessToken) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGoogleLoginApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SocialLoginResponse>() {
                    @Override
                    public void accept(SocialLoginResponse response) throws Exception {
                        if (!WeCareUtils.isNullOrEmpty(response.getGoogle_id())) {
                            getNavigator().onSocialAccountRegistrationNeeded(response.getFacebook_id(), AppConstants.ARGS_KEY_SOCIAL_FACEBOOK_HASH);
                            setIsLoading(false);
                        } else {
                            handleSuccessLoginCallBack(response);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void onFbLoginClick(String accessToken) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doFacebookLoginApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SocialLoginResponse>() {
                    @Override
                    public void accept(SocialLoginResponse response) throws Exception {
                        if (!WeCareUtils.isNullOrEmpty(response.getFacebook_id())) {
                            getNavigator().onSocialAccountRegistrationNeeded(response.getFacebook_id(), AppConstants.ARGS_KEY_SOCIAL_FACEBOOK_HASH);
                            setIsLoading(false);
                        } else {
                            handleSuccessLoginCallBack(response);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();

                    }
                }));
    }

    public void login(String email, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        setIsLoading(false);
                        handleSuccessLoginCallBack(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();

                    }
                }));
    }

    private void handleSuccessLoginCallBack(LoginResponse response) throws Exception {
        if (response.isSuccess()) {
            JWTResponse jwtResponse = JSONParser.parseJWTResponse(JWTUtils.decoded(response.getToken()));

            getDataManager().setCurrentUserBio(jwtResponse.getUserModel().getBio());
            if (BuildConfig.isSeekerFlavor) {//no country of service for seeker return here.
                getDataManager().setCountryID(jwtResponse.getUserModel().getCountryID());
            } else {
                getDataManager().setCountryID(jwtResponse.getUserModel().getCountryOfService());
            }
            getDataManager().setCurrentUserModel(jwtResponse.getUserModel());
            getDataManager().setAccessToken(response.getToken());
            if (jwtResponse.getUserModel().getIsPhoneVerified() == AppConstants.PHP_TRUE_RAW) {
                getDataManager().updateUserInfo(
                        response.getToken(),
                        jwtResponse.getUserModel().getId() + "",
                        DataManagerFlavour.LoggedInMode.LOGGED_IN_MODE_SERVER,
                        jwtResponse.getUserModel().getFirstName() + " " + jwtResponse.getUserModel().getLastName(),
                        jwtResponse.getUserModel().getEmail(),
                        jwtResponse.getUserModel().getAvatar());
                getNavigator().openMainActivity();
            } else {
                getNavigator().openVerificationCodeActivity();
            }
        } else {
            getNavigator().handleError(response.getError().getMessage());
        }
        setIsLoading(false);
    }


    public void getCountries() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCountries("1")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CountriesResponse>() {
                    @Override
                    public void accept(CountriesResponse countriesResponse) throws Exception {
                        setIsLoading(false);
                        if (!countriesResponse.isSuccess()) {
                            getNavigator().handleError(countriesResponse.getError().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void getGenders() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getGenderIndex()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<LookUpModel>>() {
                    @Override
                    public void accept(List<LookUpModel> CountriesResponse) throws Exception {
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public boolean isEmailAndPasswordValid(String email, String password) {
        //validate email and password
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }
        return true;
    }

    public void openForgetPassword() {
        getNavigator().openForgetPassword();
    }

}
