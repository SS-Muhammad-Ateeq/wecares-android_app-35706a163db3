package com.wecare.android.ui.main.profile.userProfile.paymentmethod;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.requests.UpdatePaymentMethodRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.functions.Consumer;

public class PaymentMethodViewModel extends BaseViewModel<PaymentMethodNavigator> {

    public final ObservableField<Boolean> enableFieldsEditMode = new ObservableField<>();

    public boolean isEditMode = false;

    public PaymentMethodViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public void updatePaymentMethod(int paymentMethod) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updateMyProfilesApiCall(JSONBuilderFlavour.getCommonRequestParams(new UpdatePaymentMethodRequest(paymentMethod)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse success) throws Exception {
                        setIsLoading(false);
                        if (success.isSuccess()) {
                            getNavigator().paymentMethodUpdatedSuccessfully(paymentMethod);
                        } else {
                            getNavigator().handleError(success.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void onCashClicked(){
        getNavigator().onCashClicked();
    }
    public void onCreditClicked(){
        getNavigator().onCreditClicked();
    }

}
