
package com.wecare.android.ui.create_order.location;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.data.model.api.responses.UserLocationResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class LocationViewModel extends BaseViewModel<LocationNavigator> {

    private final ObservableArrayList<UserLocationResponse> locationObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<UserLocationResponse>> locationListLiveData;

    private final ObservableBoolean isListEmpty = new ObservableBoolean(false);

    //edit or add mode
    public boolean isEditMode = false;

    //add new location.
    private ArrayList<CountryModel> countries;
    private ArrayList<CityModel> cities;
    private UserLocationResponse toBeCreatedLocationResponse;

    public LocationViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        locationListLiveData = new MutableLiveData<>();
        toBeCreatedLocationResponse = new UserLocationResponse();
    }

    void fetchLocations() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserLocationApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<UserLocationResponse>>() {
                    @Override
                    public void accept(@NonNull List<UserLocationResponse> locationResponses) throws Exception {
                        if (locationResponses.size() != 0) {
                            isListEmpty.set(false);
                        } else {
                            isListEmpty.set(true);
                        }
                        locationListLiveData.setValue(locationResponses);
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

    public void createLocationRequest() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .createUserLocationApiCall(JSONBuilderFlavour.getUserLocationParams(getToBeCreatedLocationResponse()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse success) throws Exception {
                        setIsLoading(false);
                        if (success.isSuccess()) {
                            getNavigator().goBack();
                        } else {
                            getNavigator().handleError(success.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void updateLocationData() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .updateUserLocationApiCall(JSONBuilderFlavour.getUserLocationParams(getToBeCreatedLocationResponse()), getToBeCreatedLocationResponse().getId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse success) throws Exception {
                        setIsLoading(false);
                        if (success.isSuccess()) {
                            getNavigator().goBack();
                        } else {
                            getNavigator().handleError(success.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void deleteLocationData(UserLocationResponse locationResponse) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .deleteUserLocationApiCall(locationResponse.getId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse success) throws Exception {
                        setIsLoading(false);
                        if (success.isSuccess()) {
                            getNavigator().onDeletedSuccessfully(locationResponse);
                        } else {
                            getNavigator().handleError(success.getError().getMessage());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }


    public void getActiveCountries() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCountries("1")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CountriesResponse>() {
                    @Override
                    public void accept(CountriesResponse lookupResponse) throws Exception {
                        if (lookupResponse.isSuccess()) {
                            setCountries(lookupResponse.getCountries());
                        } else {
                            getNavigator().handleError(lookupResponse.getError().getMessage());
                        }
                        setIsLoading(false);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void getCities(String countryID) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCites(countryID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CityModel>>() {
                    @Override
                    public void accept(List<CityModel> cityModels) throws Exception {
                        setCities((ArrayList<CityModel>) cityModels);
                        setIsLoading(false);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));

    }

    public UserLocationResponse getToBeCreatedLocationResponse() {
        return toBeCreatedLocationResponse;
    }

    public LocationViewModel setToBeCreatedLocationResponse(UserLocationResponse toBeCreatedLocationResponse) {
        this.toBeCreatedLocationResponse = toBeCreatedLocationResponse;
        return this;
    }

    public ArrayList<CountryModel> getCountries() {
        return countries;
    }

    private void setCountries(ArrayList<CountryModel> countries) {
        this.countries = countries;
    }

    public ArrayList<CityModel> getCities() {
        return cities;
    }

    private void setCities(ArrayList<CityModel> cities) {
        this.cities = cities;
    }

    public ObservableBoolean getIsListEmpty() {
        return isListEmpty;
    }

    public MutableLiveData<List<UserLocationResponse>> getLocationListLiveData() {
        return locationListLiveData;
    }

    public void addLocationItemsToList(List<UserLocationResponse> blogs) {
        locationObservableArrayList.clear();
        locationObservableArrayList.addAll(blogs);
    }

    public ObservableArrayList<UserLocationResponse> getLocationObservableArrayList() {
        return locationObservableArrayList;
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void onCountryClick() {
        getNavigator().onCountryClick();
    }

    public void onCityClick() {
        getNavigator().onCityClick();
    }

    public void onMapPickerClick() {
        getNavigator().onMapPickerClick();
    }

    public void addNewLocationClicked() {
        getNavigator().addNewLocationClicked();
    }


}
