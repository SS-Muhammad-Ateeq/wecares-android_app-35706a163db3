package com.wecare.android.ui.main.order;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.requests.FinishOrderRequest;
import com.wecare.android.data.model.api.requests.UpdateOrderRequest;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import io.reactivex.functions.Consumer;

public class FinishOrderViewModel extends BaseViewModel<com.wecare.android.ui.main.order.FinishOrderActivityNavigator> {

    private ArrayList<SubServiceResponse> selectedSubServiceResponseList;

    public FinishOrderRequest getFinishOrderRequest() {
        return finishOrderRequest;
    }

    FinishOrderRequest finishOrderRequest = new FinishOrderRequest();

    public void setFinishOrderRequest(FinishOrderRequest finishOrderRequest) {
        this.finishOrderRequest = finishOrderRequest;
    }

    public FinishOrderViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void addNewService(){
        getNavigator().addNewServiceClicked();
    }

    public ArrayList<SubServiceResponse> getSelectedSubServiceResponseList() {
        return selectedSubServiceResponseList;
    }

    public void setSelectedSubServiceResponseList(ArrayList<SubServiceResponse> selectedSubServiceResponseList) {
        this.selectedSubServiceResponseList = selectedSubServiceResponseList;
    }

    public void finishOrder() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .finishOrder(JSONBuilderFlavour.getCommonRequestParams(finishOrderRequest))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<OrderModel>() {
                    @Override
                    public void accept(@NonNull OrderModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().finishOrderSubmittedSuccessfully(response);
                        else
                            getNavigator().handleError(response.getError().getMessage());
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
    public void onConfirmClicked(){
        getNavigator().onConfirmClicked();
    }
    public void negativeClicked(){
        getNavigator().onNegativeClicked();
    }


}
