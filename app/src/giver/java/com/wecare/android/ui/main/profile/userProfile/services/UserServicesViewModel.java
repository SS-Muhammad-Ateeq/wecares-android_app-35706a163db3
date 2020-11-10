package com.wecare.android.ui.main.profile.userProfile.services;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.UserMainServicesResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class UserServicesViewModel extends BaseViewModel<UserServicesActivityNavigator> {
    private final ObservableArrayList<MainServiceModel> mainServiceModelObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<MainServiceModel>> serviceListLiveData;

    public UserServicesViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        serviceListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<MainServiceModel>> getServiceListLiveData() {
        return serviceListLiveData;
    }

    public void addServiceItemsToList(List<MainServiceModel> blogs) {
        mainServiceModelObservableArrayList.clear();
        mainServiceModelObservableArrayList.addAll(blogs);
    }

    public ObservableArrayList<MainServiceModel> getMainServiceModelObservableArrayList() {
        return mainServiceModelObservableArrayList;
    }

    public void fetchServices() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserMainServices()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserMainServicesResponse>() {
                    @Override
                    public void accept(@NonNull UserMainServicesResponse response) throws Exception {
                        if (response.getMainServiceModels()!=null)
                        serviceListLiveData.setValue(response.getMainServiceModels());
                        if (response.getServicesTotalModel()!=null)
                            getNavigator().serviceNumbersFetched(response.getServicesTotalModel());
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

    public void fetchOrderServices(String orderID) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getOrderServices(orderID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<MainServiceModel>>() {
                    @Override
                    public void accept(@NonNull List<MainServiceModel> mainServiceModelList) throws Exception {
                        serviceListLiveData.setValue(mainServiceModelList);
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

    public void requestToActivate() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .requestToActivate()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().requestToActivateSuccessfully();
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

    public void activateNowClicked(){
        getNavigator().activateNowClicked();
    }
}
