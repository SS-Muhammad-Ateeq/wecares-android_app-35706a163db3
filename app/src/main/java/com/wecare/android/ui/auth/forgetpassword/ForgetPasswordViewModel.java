package com.wecare.android.ui.auth.forgetpassword;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.ResetPassResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class ForgetPasswordViewModel extends BaseViewModel<ForgetPasswordNavigator> {
    public ForgetPasswordViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onSendClicked() {
        getNavigator().sendClicked();
    }

    public void resetByEmail(String email) {

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .resetPassByEmail(JSONBuilderFlavour.getResetPasswordByEmailParams(email))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ResetPassResponse>() {
                    @Override
                    public void accept(ResetPassResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess()) {
                            getNavigator().resetSuccessfully();
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

    public void resetByPhone(String countryCode, String number) {

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .resetPassByPhone(JSONBuilderFlavour.getResetPasswordByPhoneParams(number, countryCode))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ResetPassResponse>() {
                    @Override
                    public void accept(ResetPassResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess()) {
                            getNavigator().resetSuccessfully();
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
