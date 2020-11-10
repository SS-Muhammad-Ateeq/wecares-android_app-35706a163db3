
package com.wecare.android.ui.main;

import android.util.Log;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.responses.NotificationCountResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;


public class MainViewModel extends BaseViewModel<MainNavigator> {

    public MainViewModel(DataManagerFlavour dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
//        questionCardData = new MutableLiveData<>();
//        loadQuestionCards();
    }


    public void goToOrderActivity() {
        getNavigator().openOrderActivity();
    }

    public void logout() {
//        setIsLoading(true);
//        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<LogoutResponse>() {
//                    @Override
//                    public void accept(LogoutResponse response) throws Exception {
//                        getDataManager().setUserAsLoggedOut();
//                        setIsLoading(false);
//                        getNavigator().openLoginActivity();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        setIsLoading(false);
//                        getNavigator().handleError(throwable);
//                    }
//                }));
    }

    public void getNotificationsCount(){
        getCompositeDisposable().add(getDataManager().getNotificationsCount()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<NotificationCountResponse>() {
                    @Override
                    public void accept(NotificationCountResponse response) throws Exception {
                        if (response.isSuccess())
                            getNavigator().notificationsCountFetched(response.getCount());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getNavigator().handleError(throwable);
                    }
                }));
    }

    void getUserInfo() {
        getCompositeDisposable().add(getDataManager()
                .getUserInfo()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        if (response.isSuccess()) {
                            getDataManager().setCurrentUserModel(response);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error",throwable.toString());
                    }
                }));
    }
}
