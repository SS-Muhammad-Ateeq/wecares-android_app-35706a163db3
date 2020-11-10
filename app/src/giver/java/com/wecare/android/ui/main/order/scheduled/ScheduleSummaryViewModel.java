package com.wecare.android.ui.main.order.scheduled;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class ScheduleSummaryViewModel extends BaseViewModel<ScheduleSummaryActivityNavigator> {


    private final ObservableArrayList<OrderModel> ordersResponseObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<OrderModel>> ordersListLiveData;


    public ScheduleSummaryViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        ordersListLiveData = new MutableLiveData<>();

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
