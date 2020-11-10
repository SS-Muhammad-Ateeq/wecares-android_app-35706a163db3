package com.wecare.android.ui.main.profile.userProfile;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;
import io.reactivex.functions.Consumer;

public class UserProfileViewModel extends BaseViewModel<UserProfileFragmentNavigator> {

    public final ObservableField<String> picURL = new ObservableField<>();
    public final ObservableField<String> bio = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> weCareID = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<Float> rating = new ObservableField<>();


    public UserProfileViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
//        if (getDataManager().getCurrentUserProfilePicUrl()!=null)
//        picURL.set(getDataManager().getCurrentUserProfilePicUrl());
        email.set(getDataManager().getCurrentUserEmail());
        userName.set(getDataManager().getCurrentUserName());
        bio.set(getDataManager().getUserBio());
    }

    public void personalInfoClicked() {
        getNavigator().personalInfoClicked();
    }

    public void scheduleServicesClicked() {
        getNavigator().scheduleServicesClicked();
    }

    public void EducationalCertificatesClicked() {
        getNavigator().EducationalCertificatesClicked();
    }

    public void servicesClicked() {
        getNavigator().servicesClicked();
    }

    public void serviceAreaClicked() {
        getNavigator().ServiceAreaClicked();
    }

    public void BankInfoClicked() {
        getNavigator().BankInfoClicked();
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
                        getDataManager().setCurrentUserModel(response);
                        updateUserData(response);
                        getNavigator().userInfoFetchedSuccessfully(response);
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);

                    }
                }));
    }

    private void updateUserData(UserModel response){
            rating.set((float) response.getRating());

        if (response.getFirstName() != null && response.getLastName()  != null)
            userName.set(response.getFirstName() + " " + response.getLastName());

        if (response.getBio()!=null)
            bio.set(response.getBio());

        if (response.getAvatar() != null)
            picURL.set(response.getAvatar());
    }

    public void setWeCareID(String idDesc) {
        weCareID.set(idDesc);

    }

}
