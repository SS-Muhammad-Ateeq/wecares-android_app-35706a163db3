
package com.wecare.android.ui.sub;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.StatisticResponse;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.functions.Consumer;

public class ServicesViewModel extends BaseViewModel<ServicesNavigator> {

    private MainServiceModel selectedServiceResponse;
    private ArrayList<SubServiceResponse> selectedSubServiceResponseList;

    public ObservableField<Boolean> isSubServicesSelected;

    private final ObservableArrayList<MainServiceModel> mainServiceModelObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<MainServiceModel>> serviceListLiveData;

    public ServicesViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //live data
        serviceListLiveData = new MutableLiveData<>();
        //observable
        isSubServicesSelected = new ObservableField<>(Boolean.FALSE);
    }

    public void fetchServices(String countryID) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getServicesApiCall(countryID)
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


    public void fetchStatistics() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getStatistics(getDataManager().getCurrentUserModel() != null ? getDataManager().getCurrentUserModel().getCountryID() : getDataManager().getCountryID())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<StatisticResponse>() {
                    @Override
                    public void accept(@NonNull StatisticResponse response) throws Exception {
                        if (response.isSuccess())
                            getNavigator().statisticsFetched(response);

                        else
                            getNavigator().handleError(response.getError().getMessage());


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


    public void addServiceItemsToList(List<MainServiceModel> blogs) {
        mainServiceModelObservableArrayList.clear();
        mainServiceModelObservableArrayList.addAll(blogs);
    }

    public MutableLiveData<List<MainServiceModel>> getServiceListLiveData() {
        return serviceListLiveData;
    }

    public void setServiceListLiveDataValue(List<MainServiceModel> list) {
        serviceListLiveData.setValue(list);
    }

    public ObservableArrayList<MainServiceModel> getMainServiceModelObservableArrayList() {
        return mainServiceModelObservableArrayList;
    }

    public MainServiceModel getSelectedServiceResponse() {
        return selectedServiceResponse;
    }

    public ServicesViewModel setSelectedServiceResponse(MainServiceModel selectedServiceResponse) {
        this.selectedServiceResponse = selectedServiceResponse;
        return this;
    }

    public ArrayList<SubServiceResponse> getSelectedSubServiceResponseList() {
        return selectedSubServiceResponseList;
    }

    public ServicesViewModel setSelectedSubServiceResponseList(ArrayList<SubServiceResponse> selectedSubServiceResponseList) {
        this.selectedSubServiceResponseList = selectedSubServiceResponseList;
        return this;
    }
}

