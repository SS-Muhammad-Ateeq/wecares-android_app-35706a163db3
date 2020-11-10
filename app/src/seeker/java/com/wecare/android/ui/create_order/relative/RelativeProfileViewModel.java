package com.wecare.android.ui.create_order.relative;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.*;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RelativeProfileViewModel extends BaseViewModel<RelativeProfileNavigator> {

    private final ObservableArrayList<RelativeProfileResponse> relativeProfileObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<RelativeProfileResponse>> relativeProfileListLiveData;

    public final ObservableField<Boolean> enableFieldsEditMode = new ObservableField<>();

    public final ObservableField<String> picURL = new ObservableField<>();

    //endicater edit or add mode
    public boolean isEditMode = false;
    public boolean isMyProfileEditMode = false;
    private String profileID = "";

    //add new relative.
    ArrayList<CityModel> cities;
    ArrayList<CountryModel> allCountries;
    ArrayList<CountryModel> activeCountries;
    ArrayList<CountryModel> nonActiveCountries;
    ArrayList<InsuranceCompanyModel> insuranceCompanyList;
    private RelativeProfileResponse toBeFilledRelativeProfileResponse;

    //userModel used in edit profile
    private UserModel userModel;

    public RelativeProfileViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        if (getDataManager().getCurrentUserModel() != null) {
            picURL.set(getDataManager().getCurrentUserModel().getAvatar());
        }
        userModel = new UserModel();
        allCountries = new ArrayList<>();
        insuranceCompanyList = new ArrayList<>();
        relativeProfileListLiveData = new MutableLiveData<>();
    }

    public void fetchMyRelativeProfilesByID() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRelativeProfilesByIDApiCall(profileID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RelativeProfileResponse>() {
                    @Override
                    public void accept(@NonNull RelativeProfileResponse relativeProfileResponses) throws Exception {
                        toBeFilledRelativeProfileResponse = relativeProfileResponses;
                        getNavigator().fillUserInfoForEdit();
//                        profileID = String.valueOf(toBeFilledRelativeProfileResponse.getId());
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    void fetchRelativeProfiles() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRelativeProfilesApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<RelativeProfileResponse>>() {
                    @Override
                    public void accept(@NonNull List<RelativeProfileResponse> relativeProfileResponses) throws Exception {
                        relativeProfileListLiveData.setValue(relativeProfileResponses);
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void deleteRelativeProfiles(RelativeProfileResponse profileResponse) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteRelativeProfilesApiCall(profileResponse.getId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse success) throws Exception {
                        setIsLoading(false);
                        if (success.isSuccess()) {
                            getNavigator().onDeletedSuccessfully(profileResponse);
                        } else {
                            getNavigator().handleError(success.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void updateRelativeProfiles() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updateRelativeProfilesApiCall(JSONBuilderFlavour.getCreateRelativeProfileParams(toBeFilledRelativeProfileResponse, profileID))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse success) throws Exception {
                        setIsLoading(false);
                        if (success.isSuccess()) {
                            getNavigator().goBack();
                        } else {
                            getNavigator().handleError(success.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void fetchUserProfileByID() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getMYSeekerProfilesApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(@NonNull UserModel userModel) throws Exception {
                        if (userModel.isSuccess()) {
                            setUserModel(userModel);
                            getDataManager().setCurrentUserModel(userModel);
                            toBeFilledRelativeProfileResponse = userModel.getUser_profile();
                            getNavigator().fillUserInfoForEdit();
//                        profileID = String.valueOf(toBeFilledRelativeProfileResponse.getId());
                        } else {
                            getNavigator().handleError(userModel.getError().getMessage());
                        }
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void updateMyProfiles() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updateMyProfilesApiCall(JSONBuilderFlavour.getUpdateUserProfileParams(toBeFilledRelativeProfileResponse, profileID))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(@NonNull UserModel userModel) throws Exception {
                        setIsLoading(false);
                        if (userModel.isSuccess()) {
                            getDataManager().setCurrentUserModel(userModel);
                            getNavigator().goBack();
                        } else {
                            getNavigator().handleError(userModel.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void createRelativeProfiles() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .createRelativeProfilesApiCall(JSONBuilderFlavour.getCreateRelativeProfileParams(toBeFilledRelativeProfileResponse, profileID))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse success) throws Exception {
                        setIsLoading(false);
                        if (success.isSuccess()) {
                            getNavigator().goBack();
                        } else {
                            getNavigator().handleError(success.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
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
                        if (lookupResponse.isSuccess()) {
                            if (status.equals(AppConstants.ACTIVE_COUNTRIES)) {
                                setActiveCountries(lookupResponse.getCountries());
                                allCountries.addAll(getActiveCountries());
                            } else {
                                setNonActiveCountries(lookupResponse.getCountries());
                                allCountries.addAll(getNonActiveCountries());
                            }
                        } else {
                            getNavigator().handleError(lookupResponse.getError().getMessage());
                        }
                        setIsLoading(false);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));

    }

    public void getInsuranceCompany() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getInsuranceCompany()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<InsuranceCompanyModel>>() {
                    @Override
                    public void accept(List<InsuranceCompanyModel> companyList) throws Exception {
                        insuranceCompanyList.addAll(companyList);
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

    public void uploadProfilePicture(File file) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().updateUserAvatarApiCall(file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess()) {
                            getDataManager().setCurrentUserModel(model);
                            picURL.set(model.getAvatar());
                        } else {
                            getNavigator().handleError(model.getError().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // inCase anything went wrong
                        getNavigator().showCommonError();
                    }
                }));
    }

    public MutableLiveData<List<RelativeProfileResponse>> getRelativeProfileListLiveData() {
        return relativeProfileListLiveData;
    }

    public void addRelativeProfileItemsToList(List<RelativeProfileResponse> blogs) {
        relativeProfileObservableArrayList.clear();
        relativeProfileObservableArrayList.addAll(blogs);
    }

    public ObservableArrayList<RelativeProfileResponse> getRelativeProfileObservableArrayList() {
        return relativeProfileObservableArrayList;
    }

    public RelativeProfileResponse getToBeFilledRelativeProfileResponse() {
        if (toBeFilledRelativeProfileResponse == null) {
            toBeFilledRelativeProfileResponse = new RelativeProfileResponse();
        }
        return toBeFilledRelativeProfileResponse;
    }

    public RelativeProfileViewModel setToBeFilledRelativeProfileResponse(RelativeProfileResponse toBeFilledRelativeProfileResponse) {
        this.toBeFilledRelativeProfileResponse = toBeFilledRelativeProfileResponse;
        return this;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public RelativeProfileViewModel setUserModel(UserModel userModel) {
        this.userModel = userModel;
        return this;
    }

    public ArrayList<CountryModel> getActiveCountries() {
        return activeCountries;
    }

    public RelativeProfileViewModel setActiveCountries(ArrayList<CountryModel> activeCountries) {
        this.activeCountries = activeCountries;
        return this;
    }

    public ArrayList<CountryModel> getNonActiveCountries() {
        return nonActiveCountries;
    }

    public ArrayList<CountryModel> getAllCountries() {
        return allCountries;
    }

    public RelativeProfileViewModel setAllCountries(ArrayList<CountryModel> allCountries) {
        this.allCountries = allCountries;
        return this;
    }

    public RelativeProfileViewModel setNonActiveCountries(ArrayList<CountryModel> nonActiveCountries) {
        this.nonActiveCountries = nonActiveCountries;
        return this;
    }

    public ArrayList<CityModel> getCities() {
        return cities;
    }

    public RelativeProfileViewModel setCities(ArrayList<CityModel> cities) {
        this.cities = cities;
        return this;
    }

    public ArrayList<InsuranceCompanyModel> getInsuranceCompanyList() {
        return insuranceCompanyList;
    }

    public RelativeProfileViewModel setInsuranceCompanyList(ArrayList<InsuranceCompanyModel> insuranceCompanyList) {
        this.insuranceCompanyList = insuranceCompanyList;
        return this;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public RelativeProfileViewModel setEditMode(boolean editMode) {
        isEditMode = editMode;
        return this;
    }

    public String getProfileID() {
        return profileID;
    }

    public RelativeProfileViewModel setProfileID(String profileID) {
        this.profileID = profileID;
        return this;
    }

    public void onProfileImageClicked() {
        getNavigator().userProfileClicked();
    }

    public void genderClicked() {
        getNavigator().genderClicked();
    }

    public void addNewRelativeClicked() {
        getNavigator().addNewRelativeClicked();
    }

    public void ageClicked() {
        getNavigator().ageClicked();
    }

    public void relationshipClicked() {
        getNavigator().relationshipClicked();
    }

    public void bloodTypeClicked() {
        getNavigator().bloodTypeClicked();
    }

    public void weightClicked() {
        getNavigator().weightClicked();
    }

    public void lengthClicked() {
        getNavigator().lengthClicked();
    }

    public void bloodPressureClicked() {
        getNavigator().bloodPressureClicked();
    }

    public void chronicDiseasesClicked() {
        getNavigator().chronicDiseasesClicked();
    }

    public void isThereHealthInsuranceClicked() {
        getNavigator().isThereHealthInsuranceClicked();
    }

    public void insuranceCompanyNameClicked() {
        getNavigator().insuranceCompanyNameClicked();
    }

    public void typeOfInsuranceClicked() {
        getNavigator().typeOfInsuranceClicked();
    }

    public void insuranceExpirationDateClicked() {
        getNavigator().insuranceExpirationDateClicked();
    }

    public void countriesOfServiceClicked() {
        getNavigator().countriesOfServiceClicked();
    }

    public void birthDateClicked() {
        getNavigator().birthDateClicked();
    }

    public void nationalityClicked() {
        getNavigator().nationalityClicked();
    }

}
