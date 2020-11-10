package com.wecare.android.ui.auth.registration.info;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.data.model.api.models.RegistrationModel;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.data.model.api.responses.JWTResponse;
import com.wecare.android.data.model.api.responses.LoginResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.JSONParser;
import com.wecare.android.utils.JWTUtils;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class RegistrationInfoViewModel extends BaseViewModel<RegistrationInfoFragmentNavigator> {

    //to send social login.
    @AppConstants.SocialLoginType
    private int selectedSocialType;

//    public ObservableBoolean getIsTermsAccebted() {
//        return isTermsAccebted;
//    }
//
//    public void setIsTermsAccebted(boolean isLoading) {
//        isTermsAccebted.set(isLoading);
//    }

//    private final ObservableBoolean isTermsAccebted = new ObservableBoolean(false);

    public RegistrationInfoViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public int getSelectedSocialType() {
        return selectedSocialType;
    }

    public RegistrationInfoViewModel setSelectedSocialType(int selectedSocialType) {
        this.selectedSocialType = selectedSocialType;
        return this;
    }

    public void registerClicked() {
        getNavigator().nextClicked();
    }

    public void countriesClicked() {
        getNavigator().countriesClicked();
    }

    //Cities Navigator
    public void citiesClicked() {
        getNavigator().citiesClicked();
    }

    public void genderClicked() {
        getNavigator().genderClicked();
    }

    public void showAccountTypes() {
        getNavigator().showAccountTypes();
    }

    public void birthDateClicked() {
        getNavigator().birthDateClicked();
    }

    public void membershipTypeClicked() {
        getNavigator().membershipTypeClicked();
    }

    public void nationalityClicked() {
        getNavigator().nationalityClicked();
    }

    public void termsConditionsClicked() {
        getNavigator().termsConditionsClicked();
    }

    public void getCountries(String status) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCountries(status)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CountriesResponse>() {
                    @Override
                    public void accept(CountriesResponse lookupResponse) throws Exception {
                        setIsLoading(false);
                        if (lookupResponse.isSuccess()) {
                            getNavigator().countriesFinishedSuccessfully(status, lookupResponse.getCountries());
                        } else
                            getNavigator().handleError(lookupResponse.getError().getMessage());


                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));

    }
//Get Cities
    public void getCities(String countryID) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCites(countryID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CityModel>>() {
                    @Override
                    public void accept(List<CityModel> cityModels) throws Exception {
                        ArrayList<CityModel> models = new ArrayList<CityModel>();
                        models.addAll(cityModels);
                        getNavigator().citiesFinishedSuccessfully(models);
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

    public void initRegModel() {
        getDataManager().setRegistrationModel(new RegistrationModel());
    }

//    public void onCheckedChanged(boolean checked) {
//        isTermsAccebted.set(checked);
//        getNavigator().termsConditionsChecked(checked);
//    }

    public void registerCaregiver() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .registerCaregiver(JSONBuilderFlavour.getRegisterJsonObject(getDataManager().getRegistrationModel()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        if (response.isSuccess()) {
                            JWTResponse jwtResponse = JSONParser.parseJWTResponse(JWTUtils.decoded(response.getToken()));

                            //this is for development purposes, delete after go live
                            getDataManager().setVerificationCode(jwtResponse.getUserModel().getVerificationCode());

                            getDataManager().setCurrentUserBio(jwtResponse.getUserModel().getBio());
                            getDataManager().setCountryID(jwtResponse.getUserModel().getCountryOfService()+"");
                            getDataManager().setCurrentUserModel(jwtResponse.getUserModel());
                            getDataManager().updateUserInfo(
                                    response.getToken(),
                                    jwtResponse.getUserModel().getId()+"",
                                    DataManagerFlavour.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                                    jwtResponse.getUserModel().getFirstName()+" "+ jwtResponse.getUserModel().getLastName(),
                                    jwtResponse.getUserModel().getEmail(),
                                    jwtResponse.getUserModel().getAvatar());
                            getDataManager().updateApiHeader(response.getToken());
                            getNavigator().userRegisteredSuccessfully(jwtResponse.getUserModel());
                        } else {
                            getNavigator().handleError(response.getError().getMessage());
                        }
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

    public void getMembershipTypes(String countryID){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getMemberShipTypes(countryID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<MembershipType>>() {
                    @Override
                    public void accept(List<MembershipType> membershipTypeList) throws Exception {
                        getNavigator().memberShipTypesFetchedSuccessfully(new ArrayList<MembershipType>(membershipTypeList));

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

}
