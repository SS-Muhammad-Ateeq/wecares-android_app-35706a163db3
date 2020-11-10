package com.wecare.android.ui.login;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.data.model.api.requests.LoginRequest;
import com.wecare.android.data.model.api.responses.JWTResponse;
import com.wecare.android.data.model.api.responses.LoginResponse;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.CommonUtils;
import com.wecare.android.utils.JSONParser;
import com.wecare.android.utils.JWTUtils;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;



public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManagerFlavour dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerLoginClick() {
        getNavigator().login();
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
                     handleSuccessLoginCallBack(response);
                        setIsLoading(false);
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
            getDataManager().setCountryID(jwtResponse.getUserModel().getCountryOfService()+"");
            getDataManager().setCurrentUserModel(jwtResponse.getUserModel());
            getDataManager().setAccessToken(response.getToken());
            if (jwtResponse.getUserModel().getIsPhoneVerified()== AppConstants.PHP_TRUE_RAW){
                getDataManager().updateUserInfo(
                        response.getToken(),
                        jwtResponse.getUserModel().getId()+"",
                        DataManagerFlavour.LoggedInMode.LOGGED_IN_MODE_SERVER,
                        jwtResponse.getUserModel().getFirstName()+" "+ jwtResponse.getUserModel().getLastName(),
                        jwtResponse.getUserModel().getEmail(),
                        jwtResponse.getUserModel().getAvatar());
                getNavigator().openMainActivity();
            }
            else {
                getNavigator().openVerificationCodeActivity();
            }
        } else {
            getNavigator().handleError(response.getError().getMessage());
        }
    }


    public void getUserInfo() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserInfo()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void getCountries() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCountries("1")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CountriesResponse>() {
                    @Override
                    public void accept(CountriesResponse CountriesResponse) throws Exception {
                        setIsLoading(false);
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

    public void openForgetPassword(){
        getNavigator().openForgetPassword();
    }

    public void openMainActivity(){

    }

}
