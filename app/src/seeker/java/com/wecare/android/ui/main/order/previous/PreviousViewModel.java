
package com.wecare.android.ui.main.order.previous;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;
import io.reactivex.functions.Consumer;

import java.util.List;

public class PreviousViewModel extends BaseViewModel<PreviousNavigator> {

    public PreviousViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        serviceListLiveData = new MutableLiveData<>();
//        fetchServices();
    }

    private final ObservableArrayList<MainServiceModel> serviceResponseObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<MainServiceModel>> serviceListLiveData;

    public void fetchServices() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getServicesApiCall("")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<MainServiceModel>>() {
                    @Override
                    public void accept(@NonNull List<MainServiceModel> serviceResponseList) throws Exception {
                        serviceListLiveData.setValue(serviceResponseList);
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


    public MutableLiveData<List<MainServiceModel>> getServiceListLiveData() {
        return serviceListLiveData;
    }

    public void addServiceItemsToList(List<MainServiceModel> blogs) {
        serviceResponseObservableArrayList.clear();
        serviceResponseObservableArrayList.addAll(blogs);
    }

    public ObservableArrayList<MainServiceModel> getServiceResponseObservableArrayList() {
        return serviceResponseObservableArrayList;
    }
}

