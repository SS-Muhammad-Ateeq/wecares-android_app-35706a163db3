//package com.wecare.android.ui.search_giver.suggested;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.ObservableArrayList;
//import androidx.databinding.ObservableBoolean;
//import androidx.lifecycle.MutableLiveData;
//import com.wecare.android.data.DataManagerFlavour;
//import com.wecare.android.data.model.api.models.CityModel;
//import com.wecare.android.data.model.api.models.CountryModel;
//import com.wecare.android.data.model.api.responses.CountriesResponse;
//import com.wecare.android.data.model.api.responses.SearchGiverResponse;
//import com.wecare.android.ui.base.BaseViewModel;
//import com.wecare.android.ui.search_giver.SearchGiverNavigator;
//import com.wecare.android.utils.JSONBuilderFlavour;
//import com.wecare.android.utils.rx.SchedulerProvider;
//import io.reactivex.functions.Consumer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SuggestedGiverViewModel extends BaseViewModel<SearchGiverNavigator> {
//
//    private final ObservableArrayList<SearchGiverResponse> searchGiverObservableArrayList = new ObservableArrayList<>();
//
//    private final MutableLiveData<List<SearchGiverResponse>> searchGiverListLiveData;
//
//    private final ObservableBoolean isListEmpty = new ObservableBoolean(false);
//
//    //add new location.
//    private ArrayList<CountryModel> countries;
//    private ArrayList<CityModel> cities;
//
//    public SuggestedGiverViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
//        super(dataManager, schedulerProvider);
//
//        searchGiverListLiveData = new MutableLiveData<>();
//    }
//
//
//    void fetchSuggestedGiverResponse(String filterObject) {
//        setIsLoading(true);
//        getCompositeDisposable().add(getDataManager()
//                .getRecommendedCaregivers(JSONBuilderFlavour.getRecommendedCaregiversParams(filterObject))
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<List<SearchGiverResponse>>() {
//                    @Override
//                    public void accept(@NonNull List<SearchGiverResponse> searchGiverResponseList) throws Exception {
//                        if (searchGiverResponseList.size() != 0) {
//                            isListEmpty.set(false);
//                        } else {
//                            isListEmpty.set(true);
//                        }
//                        searchGiverListLiveData.setValue(searchGiverResponseList);
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
//    public void getCountries() {
//        setIsLoading(true);
//        getCompositeDisposable().add(getDataManager()
//                .getCountries("1")
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<CountriesResponse>() {
//                    @Override
//                    public void accept(CountriesResponse lookupResponse) throws Exception {
//                        if (lookupResponse.isSuccess()) {
//                            setCountries(lookupResponse.getCountries());
//                        } else {
//                            getNavigator().handleError(lookupResponse.getError().getMessage());
//                        }
//                        setIsLoading(false);
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        setIsLoading(false);
//                    }
//                }));
//    }
//
//    public void getCities(String countryID) {
//        setIsLoading(true);
//        getCompositeDisposable().add(getDataManager()
//                .getCites(countryID)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<List<CityModel>>() {
//                    @Override
//                    public void accept(List<CityModel> cityModels) throws Exception {
//                        setCities((ArrayList<CityModel>) cityModels);
//                        setIsLoading(false);
//                    }
//
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        setIsLoading(false);
//                        getNavigator().showCommonError();
//                    }
//                }));
//
//    }
//    public ArrayList<CountryModel> getCountries() {
//        return countries;
//    }
//
//    private void setCountries(ArrayList<CountryModel> countries) {
//        this.countries = countries;
//    }
//
//    public ArrayList<CityModel> getCities() {
//        return cities;
//    }
//
//    private void setCities(ArrayList<CityModel> cities) {
//        this.cities = cities;
//    }
//
//    public ObservableBoolean getIsListEmpty() {
//        return isListEmpty;
//    }
//
//    public MutableLiveData<List<SearchGiverResponse>> getSearchGiverListLiveData() {
//        return searchGiverListLiveData;
//    }
//
//    public void addSearchGiverItemsToList(List<SearchGiverResponse> blogs) {
//        searchGiverObservableArrayList.clear();
//        searchGiverObservableArrayList.addAll(blogs);
//    }
//
//    public ObservableArrayList<SearchGiverResponse> getSearchGiverObservableArrayList() {
//        return searchGiverObservableArrayList;
//    }
//
//    public void onNavBackClick() {
//        getNavigator().goBack();
//    }
//
//}
