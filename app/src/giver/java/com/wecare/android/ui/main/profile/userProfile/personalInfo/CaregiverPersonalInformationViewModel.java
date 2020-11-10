package com.wecare.android.ui.main.profile.userProfile.personalInfo;

import android.util.Log;
import android.widget.Toast;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.AttachmentModel;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.requests.PersonalInfoRequestModel;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import io.reactivex.functions.Consumer;

public class CaregiverPersonalInformationViewModel extends BaseViewModel<CaregiverPersonalInformationActivityNavigator> {

    public final ObservableField<String> picURL = new ObservableField<>();
    public final ObservableField<String> idURL = new ObservableField<>();


    PersonalInfoRequestModel personalInfoRequestModel = new PersonalInfoRequestModel();

    public PersonalInfoRequestModel getPersonalInfoRequestModel() {
        return personalInfoRequestModel;
    }

    public void setPersonalInfoRequestModel(PersonalInfoRequestModel personalInfoRequestModel) {
        this.personalInfoRequestModel = personalInfoRequestModel;
    }


    public CaregiverPersonalInformationViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        if (getDataManager().getCurrentUserModel().getAvatar()!=null)
        picURL.set(getDataManager().getCurrentUserModel().getAvatar());

    }
    private void setIDDocIfExist(){
//       add attachment after alaa add url to user object
    }

    public void saveClickedClicked() {

    }

    public void updateUser(){

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().updateUser(JSONBuilderFlavour.getCommonRequestParams(personalInfoRequestModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().informationUpdatedSuccessfully();
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }

    public void countriesClicked() {
        getNavigator().countriesClicked();
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
    //Cities Navigator
    public void personalcitiesClicked() {
        getNavigator().personalcitiesClicked();
    }
    public void onProfileImageClicked() {
        getNavigator().UserProfileClicked();
    }
    public void onIdentityDocumentClicked(){
        getNavigator().identityDocumentClicked();
    }


    public void getActiveCountries() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCountries("1")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CountriesResponse>() {
                    @Override
                    public void accept(CountriesResponse lookupResponse) throws Exception {
                        if (lookupResponse.isSuccess()) {
                            getNavigator().countriesFinishedSuccessfully(lookupResponse.getCountries());
                        } else
                            getNavigator().handleError(lookupResponse.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void getNationalities() {
        getCompositeDisposable().add(getDataManager()
                .getNationality()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CountryModel>>() {
                    @Override
                    public void accept(List<CountryModel> lookUpModels) throws Exception {
                            getNavigator().nationalitiesFinishedSuccessfully(new ArrayList<>(lookUpModels));
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }
    //Get Cities
    public void getpersonalCities(String countryID) {
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

    public void uploadProfilePicture(File file) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().uploadUserProfilePicture(file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess()) {
                            Log.e("picURL", model.getAvatar());
                            picURL.set(model.getAvatar());
                        }
                        else {
                            Log.e("picurerror", model.getError().getMessage());
                            getNavigator().handleError(model.getError().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                        Log.e("picurerror", throwable.getMessage());
                    }
                }));
    }

    public void uploadAttachment(String name, File file){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().uploadCaregiverAttachment(name,"2",file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AttachmentModel>() {
                    @Override
                    public void accept(AttachmentModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess()) {
                            getNavigator().identityDocumentUploaded();
                            Log.e("picurerrorfsldfls", model.getError().getMessage());
                        }
                        else
                            getNavigator().handleError(model.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        Log.e("picurerror", throwable.getMessage());
                        // incase anything went wrong
                    }
                }));
    }




}
