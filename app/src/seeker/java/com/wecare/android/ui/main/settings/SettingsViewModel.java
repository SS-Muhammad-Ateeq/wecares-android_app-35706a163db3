
package com.wecare.android.ui.main.settings;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator> {

    public SettingsViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void doLogoutUser() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse success) throws Exception {
                        setIsLoading(false);
                        logUserOut();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                        logUserOut();
                    }
                }));


    }

    public void enableNotifications(String active){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().enableNotification(active)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().notificationEnableSuccessfully(active.equals(AppConstants.PHP_TRUE));
                        else{
                            getNavigator().notificationEnableFailed(active.equals(AppConstants.PHP_TRUE));
                            getNavigator().handleError(response.getError().getMessage());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().notificationEnableFailed(active.equals(AppConstants.PHP_TRUE));
                    }
                }));
    }

    private void logUserOut() {
        getDataManager().setUserAsLoggedOut();
        setIsLoading(false);
        getNavigator().logoutUserSuccessfully();
    }

    public void logoutClicked() {
        getNavigator().logoutClicked();
    }

    public void aboutUsClicked() {
        getNavigator().aboutUsClicked();
    }

    public void termsConditions() {
        getNavigator().termsConditions();
    }

    public void privacyPolicyClicked() {
        getNavigator().privacyPolicy();
    }
    public void onChangeLanguageClicked() {
        getNavigator().onChangeLanguageClicked();
    }

    public void shareAppClicked(){
        getNavigator().shareAppClicked();
    }

    public void contactUSClicked(){
        getNavigator().contactUsClicked();
    }

    public void rateUsClicked(){
        getNavigator().rateUsClicked();
    }
    public void changePasswordClicked(){
        getNavigator().changePasswordClicked();
    }
    public void onContactSupportClicked(){
        getNavigator().onContactSupportClicked();
    }

}
