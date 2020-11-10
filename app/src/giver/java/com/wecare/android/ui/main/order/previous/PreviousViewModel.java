
package com.wecare.android.ui.main.order.previous;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class PreviousViewModel extends BaseViewModel<PreviousNavigator> {

    public PreviousViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData = new MutableLiveData<>();
//        fetchServices();
    }

    private final ObservableArrayList<OrderModel> orderModelObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<OrderModel>> listMutableLiveData;


    public void fetchOrders(String status,int offset) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getOrders(status,offset, AppConstants.ORDERS_PAGINATION_LIMIT)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<OrderModel>>() {
                    @Override
                    public void accept(@NonNull List<OrderModel> ordersResponseList) throws Exception {
                        listMutableLiveData.setValue(ordersResponseList);
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
                        getNavigator().updatePreviousOrderList(ordersResponseList);
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


    public MutableLiveData<List<OrderModel>> getOrderListLiveData() {
        return listMutableLiveData;
    }

    public void addServiceItemsToList(List<OrderModel> blogs) {
        orderModelObservableArrayList.clear();
        orderModelObservableArrayList.addAll(blogs);
    }

    public ObservableArrayList<OrderModel> getOrderModelObservableArrayList() {
        return orderModelObservableArrayList;
    }
}

