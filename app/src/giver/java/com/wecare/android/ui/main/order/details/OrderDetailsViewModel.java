package com.wecare.android.ui.main.order.details;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.requests.UpdateOrderRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.functions.Consumer;

public class OrderDetailsViewModel extends BaseViewModel<OrderDetailsNavigator> {

    public ObservableField<String> orderUserImg = new ObservableField<>();

    private ArrayList<InformationAttachmentObj> selectedInformationAttachmentList;


    public ObservableField<String> getRelativeUserImg() {
        return relativeUserImg;
    }

    public void setRelativeUserImg(ObservableField<String> relativeUserImg) {
        this.relativeUserImg = relativeUserImg;
    }

    public ObservableField<String> relativeUserImg = new ObservableField<>();


    public OrderDetailsViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public ObservableField<String> getOrderUserImg() {
        return orderUserImg;
    }

    public void setOrderUserImg(String orderUserImg) {
        this.orderUserImg.set(orderUserImg);
    }

    public void callSeekerClicked() {
    getNavigator().callSeekerClicked();
    }
    public void showSeekerProfile() {
    getNavigator().showSeekerProfile();
    }
    public void positiveClicked(){
        getNavigator().positiveButtonClicked();
    }
    public void negativeClicked(){
        getNavigator().negativeButtonClicked();
    }
    public void getDirectionsClicked(){
        getNavigator().getDirectionsClicked();
    }

    public void changeOrderStatus(String orderID,int status) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .changeOrderStatus(JSONBuilderFlavour.getCommonRequestParams(new UpdateOrderRequest(orderID,status+"")))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<OrderModel>() {
                    @Override
                    public void accept(@NonNull OrderModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().orderStatusChangedSuccessFully(response,status);
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

    public void rejectOrder(String orderID,int reasonID,String otherReason) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .rejectOrder(JSONBuilderFlavour.getCommonRequestParams(new UpdateOrderRequest(orderID,reasonID,otherReason,true)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().orderRejectedSuccessFully();
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

    public void cancelOrder(String orderID,int reasonID,String otherReason) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .cancelOrder(JSONBuilderFlavour.getCommonRequestParams(new UpdateOrderRequest(orderID,reasonID,otherReason,false)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().orderCanceledSuccessFully();
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

    public void getOrderDetails(String orderID){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getOrderDetails(orderID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<OrderModel>() {
                    @Override
                    public void accept(OrderModel response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().orderDetailsFetchedSuccessfully(response);
                        else
                            getNavigator().handleError(response.getError().getMessage());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public ArrayList<InformationAttachmentObj> getSelectedInformationAttachmentList() {
        return selectedInformationAttachmentList;
    }

    public void setSelectedInformationAttachmentList(ArrayList<InformationAttachmentObj> selectedInformationAttachmentList) {
        this.selectedInformationAttachmentList = selectedInformationAttachmentList;
    }
}
