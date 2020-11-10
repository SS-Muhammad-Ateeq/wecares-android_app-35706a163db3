//
//package com.wecare.android.ui.main.home;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.ObservableArrayList;
//import androidx.databinding.ObservableField;
//import androidx.lifecycle.MutableLiveData;
//import com.wecare.android.data.DataManagerFlavour;
//import com.wecare.android.data.model.api.responses.MainServiceModel;
//import com.wecare.android.data.model.api.responses.SubServiceResponse;
//import com.wecare.android.ui.base.BaseViewModel;
//import com.wecare.android.utils.rx.SchedulerProvider;
//import io.reactivex.functions.Consumer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ServicesViewModel extends BaseViewModel<ServicesNavigator> {
//
//    private MainServiceModel selectedServiceResponse;
//    private ArrayList<SubServiceResponse> selectedSubServiceResponseList;
//
//    public ObservableField<Boolean> isSubServicesSelected;
//
//
//    public ServicesViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
//        super(dataManager, schedulerProvider);
//        //live data
//        serviceListLiveData = new MutableLiveData<>();
//        //observable
//        isSubServicesSelected = new ObservableField<>(Boolean.FALSE);
//    }
//
//    private final ObservableArrayList<MainServiceModel> serviceResponseObservableArrayList = new ObservableArrayList<>();
//    private final MutableLiveData<List<MainServiceModel>> serviceListLiveData;
//
//    public void fetchServices() {
//        setIsLoading(true);
//        getCompositeDisposable().add(getDataManager()
//                .getServicesApiCall("")
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<List<MainServiceModel>>() {
//                    @Override
//                    public void accept(@NonNull List<MainServiceModel> serviceResponseList) throws Exception {
//                        serviceListLiveData.setValue(serviceResponseList);
//                        setIsLoading(false);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        setIsLoading(false);
//                        getNavigator().showCommonError();
//                    }
//                }));
//    }
//
//
//    public MutableLiveData<List<MainServiceModel>> getServiceListLiveData() {
//        return serviceListLiveData;
//    }
//
//    public void setServiceListLiveDataValue(List<MainServiceModel> list) {
//        serviceListLiveData.setValue(list);
//    }
//
//
//    public void addServiceItemsToList(List<MainServiceModel> serviceResponseList) {
//        serviceResponseObservableArrayList.clear();
//        serviceResponseObservableArrayList.addAll(serviceResponseList);
//    }
//
//    public ObservableArrayList<MainServiceModel> getServiceResponseObservableArrayList() {
//        return serviceResponseObservableArrayList;
//    }
//
//    public MainServiceModel getSelectedServiceResponse() {
//        return selectedServiceResponse;
//    }
//
//    public ServicesViewModel setSelectedServiceResponse(MainServiceModel selectedServiceResponse) {
//        this.selectedServiceResponse = selectedServiceResponse;
//        return this;
//    }
//
//    public ArrayList<SubServiceResponse> getSelectedSubServiceResponseList() {
//        return selectedSubServiceResponseList;
//    }
//
//    public ServicesViewModel setSelectedSubServiceResponseList(ArrayList<SubServiceResponse> selectedSubServiceResponseList) {
//        this.selectedSubServiceResponseList = selectedSubServiceResponseList;
//        return this;
//    }
//}
//
