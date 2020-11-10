package com.wecare.android.ui.auth.forgetpassword.verfication;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.responses.ResetPassResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class ForgetPasswordVerificationViewModel extends BaseViewModel<ForgetPasswordVerificationNavigator> {
    public ForgetPasswordVerificationViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onSendClicked() {
        getNavigator().saveClicked();
    }

    public void onReSendCodeClicked() {
        getNavigator().resendCode();
    }

    public void updatePassByEmail(String code, String pass) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updatePassByEmail(JSONBuilderFlavour.getUpdatePasswordByEmailParams(code, pass))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel userModel) throws Exception {
                        setIsLoading(false);
                        if (userModel.isSuccess()) {
                            getNavigator().passwordUpdatedSuccessfully();
                        } else {
                            getNavigator().handleError(userModel.getError().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void updatePassByPhone(String code, String pass) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updatePassByPhone(JSONBuilderFlavour.getUpdatePasswordByPhoneParams(code, pass))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess()) {
                            getNavigator().passwordUpdatedSuccessfully();
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
                            getNavigator().resentSuccessfully();
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
                            getNavigator().resentSuccessfully();
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
