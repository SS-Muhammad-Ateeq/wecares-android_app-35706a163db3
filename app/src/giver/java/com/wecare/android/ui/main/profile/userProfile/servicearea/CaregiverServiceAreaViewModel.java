package com.wecare.android.ui.main.profile.userProfile.servicearea;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.LocationModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class CaregiverServiceAreaViewModel extends BaseViewModel<CaregiverServiceAreaNavigator> {

    LocationModel locationModel = new LocationModel();

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public CaregiverServiceAreaViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void currentLocationClicked(){
        getNavigator().currentLocationClicked();
    }
    public void customLocationClicked(){
        getNavigator().customLocationClicked();
    }

    public  void updateUserServiceLocation(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .setServiceAreaLocation(JSONBuilderFlavour.getCommonRequestParams(locationModel))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LocationModel>() {
                    @Override
                    public void accept(LocationModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess()){
                            getNavigator().serviceLocationUpdatedSuccessfully();
                        }
                        else {
                            getNavigator().handleError(model.getError().getMessage());

                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void getServiceLocation(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getServiceAreaLocation()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LocationModel>() {
                    @Override
                    public void accept(LocationModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess()){
                            locationModel = model;
                            getNavigator().serviceLocationFetchedSuccessfully(model);
                        }
                        else {
                            getNavigator().handleError(model.getError().getMessage());

                        }
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
