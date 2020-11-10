
package com.wecare.android.ui.main.order.current;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.requests.UpdateOrderRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class CurrentViewModel extends BaseViewModel<CurrentNavigator> {

    private final ObservableArrayList<OrderModel> ordersResponseObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<OrderModel>> ordersListLiveData;


    public CurrentViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        ordersListLiveData = new MutableLiveData<>();
//        fetchCurrentOrders();
    }


    public void fetchOrders(String status,int offset) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getOrders(status,offset, AppConstants.ORDERS_PAGINATION_LIMIT)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<OrderModel>>() {
                    @Override
                    public void accept(@NonNull List<OrderModel> ordersResponseList) throws Exception {
                        ordersListLiveData.setValue(ordersResponseList);
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }
    public void loadModeOrders(String status,int offset) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getOrders(status,offset,AppConstants.ORDERS_PAGINATION_LIMIT)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<OrderModel>>() {
                    @Override
                    public void accept(@NonNull List<OrderModel> ordersResponseList) throws Exception {
                        getNavigator().updateCurrentOrderList(ordersResponseList);
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void changeOrderStatus(String orderID,int status,int position) {
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
                            getNavigator().orderStatusChangedSuccessFully(response,status,position);
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

    public void rejectOrder(String orderID,int reasonID,String otherReason,int position) {
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
                            getNavigator().orderRejectedSuccessFully(position);
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

    public void cancelOrder(String orderID,int reasonID,String otherReason,int position) {
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
                            getNavigator().orderCanceledSuccessFully(position);
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





    public MutableLiveData<List<OrderModel>> getOrdersListLiveData() {
        return ordersListLiveData;
    }

    public void addOrdersItemsToList(List<OrderModel> blogs) {
        ordersResponseObservableArrayList.clear();
        ordersResponseObservableArrayList.addAll(blogs);
    }

    public ObservableArrayList<OrderModel> getOrdersResponseObservableArrayList() {
        return ordersResponseObservableArrayList;
    }
}

