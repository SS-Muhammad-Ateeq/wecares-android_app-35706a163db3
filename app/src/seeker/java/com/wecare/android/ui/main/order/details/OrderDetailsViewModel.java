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

import androidx.databinding.ObservableBoolean;
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

    public ObservableField<Float> getRating() {
        return rating;
    }

    public final ObservableField<Float> rating = new ObservableField<>();

    public ObservableBoolean getIsFavorite() {
        return isFavorite;
    }

    private final ObservableBoolean isFavorite = new ObservableBoolean(false);


    public OrderDetailsViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public ObservableField<String> getOrderUserImg() {
        return orderUserImg;
    }

    public void setOrderUserImg(String orderUserImg) {
        this.orderUserImg.set(orderUserImg);
    }

    public void setGiverRating(double rating) {
        this.rating.set((float)rating);
    }

    public void isFavoriteGiver(boolean isFavorite) {
        this.isFavorite.set(isFavorite);
    }



    public void showGiverProfile() {
        getNavigator().showGiverProfile();
    }

    public void positiveClicked() {
        getNavigator().positiveButtonClicked();
    }

    public void negativeClicked() {
        getNavigator().negativeButtonClicked();
    }


    public void cancelOrder(String orderID, int reasonID, String otherReason) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .cancelOrder(JSONBuilderFlavour.getCommonRequestParams(new UpdateOrderRequest(orderID,reasonID,otherReason,false)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept( BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().orderCanceledSuccessFully();
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
