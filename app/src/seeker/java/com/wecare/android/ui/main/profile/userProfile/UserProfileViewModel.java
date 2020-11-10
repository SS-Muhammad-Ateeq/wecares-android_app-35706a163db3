package com.wecare.android.ui.main.profile.userProfile;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;

import io.reactivex.functions.Consumer;

public class UserProfileViewModel extends BaseViewModel<UserProfileFragmentNavigator> {

    public final ObservableField<String> picURL = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();

    public final ObservableField<String> weCareID = new ObservableField<>();
    public final ObservableField<Float> rating = new ObservableField<>();

    public UserProfileViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void personalInfoClicked() {
        getNavigator().personalInfoClicked();
    }

    public void LocationsClicked() {
        getNavigator().LocationsClicked();
    }

    public void paymentMethodClicked() {
        getNavigator().paymentMethodClicked();
    }

    public void relativeProfilesClicked() {
        getNavigator().relativeProfilesClicked();
    }

    public void favoriteAndBlockedClicked() {
        getNavigator().favoriteAndBlockedClicked();
    }


    void updateUserInfoDate() {

        if (getDataManager().getCurrentUserModel() != null) {
            getDataManager().setCurrentUserName(String.format("%s %s", getDataManager().getCurrentUserModel().getFirstName(),
                    getDataManager().getCurrentUserModel().getLastName()));
            getDataManager().setCurrentUserEmail(getDataManager().getCurrentUserModel().getEmail());
        }

        email.set(getDataManager().getCurrentUserEmail());
        userName.set(getDataManager().getCurrentUserName());

        if (getDataManager().getCurrentUserModel() != null) {
            picURL.set(getDataManager().getCurrentUserModel().getAvatar());
        }
    }

    void getUserInfo() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserInfo()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess()) {
                            getDataManager().setCurrentUserModel(response);
                            rating.set((float) response.getRating());
                            if (response.getAvatar() != null) {
                                picURL.set(response.getAvatar());
                            }
                            weCareID.set(response.getWecareID());
                        } else {
                            getNavigator().handleError(response.getError().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

}
