package com.wecare.android.ui.main.settings;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.requests.ChangePasswordRequest;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class ChangePasswordViewModel extends BaseViewModel<ChangePasswordNavigator> {
    public ChangePasswordViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void onSendClicked() {
        getNavigator().saveClicked();
    }

    public void changePassword(String password, String newPassword) {

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .changePassword(JSONBuilderFlavour.getCommonRequestParams(new ChangePasswordRequest(password, newPassword)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().passwordUpdatedSuccessfully();
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));

    }
}
