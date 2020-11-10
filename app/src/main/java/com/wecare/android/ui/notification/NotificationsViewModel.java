package com.wecare.android.ui.notification;

import android.util.Log;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.NotificationCountResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class NotificationsViewModel extends BaseViewModel<NotificationsActivityNavigator> {
    private final ObservableArrayList<NotificationModel> notificationModelObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<NotificationModel>> listMutableLiveData;

    public NotificationsViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<NotificationModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void addNotificationItemsToList(List<NotificationModel> notificationModels) {
        notificationModelObservableArrayList.clear();
        notificationModelObservableArrayList.addAll(notificationModels);
    }

    public ObservableArrayList<NotificationModel> getNotificationModelObservableArrayList() {
        return notificationModelObservableArrayList;
    }

    public void fetchNotifications() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getNotifications()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<NotificationModel>>() {
                    @Override
                    public void accept(@NonNull List<NotificationModel> notificationModels) throws Exception {
                        listMutableLiveData.setValue(notificationModels);
                        setIsLoading(false);
                        getNavigator().notificationFetched();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void markNotificationsAsMarked(){
        getCompositeDisposable().add(getDataManager().markNotificationsRead()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<NotificationCountResponse>() {
                    @Override
                    public void accept(NotificationCountResponse response) throws Exception {
                        Log.d("Notifications Read",response.getCount()+"");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    public void deleteNotifications(NotificationModel notificationModel) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteNotification(notificationModel.getId() + "")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().onNotificationDeletedSuccessfully(notificationModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void clearNotificationsClicked(){

    }
}
