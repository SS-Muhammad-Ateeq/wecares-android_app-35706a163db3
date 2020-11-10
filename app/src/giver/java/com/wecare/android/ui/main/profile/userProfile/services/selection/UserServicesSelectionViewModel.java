package com.wecare.android.ui.main.profile.userProfile.services.selection;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.requests.UserServiceRequestModel;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class UserServicesSelectionViewModel extends BaseViewModel<UserServicesSelectionNavigator> {

    private final ObservableArrayList<SubServiceResponse> subServicesObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<SubServiceResponse>> subServicesListLiveData;

    public UserServicesSelectionViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        subServicesListLiveData = new MutableLiveData<>();
    }

    public ObservableArrayList<SubServiceResponse> getSubServicesObservableArrayList() {
        return subServicesObservableArrayList;
    }

    public void addService(UserServiceRequestModel model,SubServiceResponse subServiceResponse) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().addUserService(JSONBuilderFlavour.getAddEditDeleteServiceParams(model))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().serviceAddedSuccessfully(subServiceResponse);
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

    public void editService(UserServiceRequestModel model, SubServiceResponse subServiceResponse) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().editUserService(JSONBuilderFlavour.getAddEditDeleteServiceParams(model))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().serviceEditedSuccessfully(model,subServiceResponse);
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

    public void deleteService(UserServiceRequestModel model,SubServiceResponse subServiceResponse) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().deleteUserService(JSONBuilderFlavour.getAddEditDeleteServiceParams(model))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().serviceDeletedSuccessfully(subServiceResponse);
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


}
