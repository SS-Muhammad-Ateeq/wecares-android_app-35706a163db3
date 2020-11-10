package com.wecare.android.ui.main.profile.userProfile.personalInfo;

import androidx.databinding.ObservableField;
import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class EditPersonalInformationViewModel extends BaseViewModel<EditPersonalInformationActivityNavigator> {

    public final ObservableField<String> picURL = new ObservableField<>();

    public EditPersonalInformationViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        picURL.set(getDataManager().getCurrentUserProfilePicUrl());
    }

    public void saveClickedClicked() {
    }

    public void countriesClicked() {
        getNavigator().countriesClicked();
    }

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


}
