package com.wecare.android.ui.main.home.sub;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;

public class SubServicesViewModel extends BaseViewModel<SubServicesNavigator> {
    private final ObservableArrayList<SubServiceResponse> subServiceResponseObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<SubServiceResponse>> subServiceListLiveData;

    public SubServicesViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        subServiceListLiveData = new MutableLiveData<>();
    }

    public ObservableArrayList<SubServiceResponse> getSubServiceResponseObservableArrayList() {
        return subServiceResponseObservableArrayList;
    }

    public void addSubServicesItemsToList(List<SubServiceResponse> blogs) {
        subServiceResponseObservableArrayList.clear();
        subServiceResponseObservableArrayList.addAll(blogs);
    }

    public MutableLiveData<List<SubServiceResponse>> getSubServiceListLiveData() {
        return subServiceListLiveData;
    }

    public void setSubServiceListLiveDataValue(List<SubServiceResponse> list) {
        subServiceListLiveData.setValue(list);
    }
}
