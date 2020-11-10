package com.wecare.android.ui.auth.registration.verification;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.DummyVerificationCode;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class RegistrationVerificationCodeViewModel extends BaseViewModel<RegistrationVerificationCodeNavigator> {
    public RegistrationVerificationCodeViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void resendClicked() {
        getNavigator().resendClicked();
    }

    public void nextClicked() {
        getNavigator().nextClicked();
    }

    public void sendVerificationCode(String verificationCode) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .sendVerificationCode(verificationCode, JSONBuilderFlavour.getSendVerificationCodeParams(verificationCode))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel response) throws Exception {
                        if (response.isSuccess()) {
                            getNavigator().accountActivatedSuccessfully(response);
                        } else {
                            getNavigator().handleError(response.getError().getMessage());
                        }
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

    public void resendVerificationCode(String code, String mobile) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .resendVerificationCode(JSONBuilderFlavour.getResendVerificationCodeParams(code, mobile))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<DummyVerificationCode>() {
                    @Override
                    public void accept(DummyVerificationCode response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess()) {
                            getDataManager().setVerificationCode(response.getVerification_code());
                            getNavigator().codeResentSuccessfully();
                        } else {
                            getNavigator().handleError(response.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }
}
