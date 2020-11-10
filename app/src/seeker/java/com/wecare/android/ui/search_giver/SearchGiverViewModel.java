package com.wecare.android.ui.search_giver;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.data.model.api.responses.SearchGiverResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;

public class SearchGiverViewModel extends BaseViewModel<SearchGiverNavigator> {

    private final ObservableArrayList<SearchGiverResponse> searchGiverObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<SearchGiverResponse>> searchGiverListLiveData;

    private final ObservableBoolean isListEmpty = new ObservableBoolean(false);
    public final ObservableBoolean isListItemSelected = new ObservableBoolean(false);

    public boolean isFromProfileView = false;

    //add new location.
    private ArrayList<CountryModel> countries;
    private ArrayList<CityModel> cities;

    /**
     * count per page to load
     */
    public static final int giversCountPerPage = 10;
    /**
     * when to load more givers.
     */
    public static final int RemainingGiversToLoadMore = (int) (giversCountPerPage * 0.7);
    /**
     * current page number start from 0 for the first page, it will increased by +1 after any load more.
     */
    public int Page = 0;
    /**
     * key to start searching with it
     */
    public String searchKey = "";
    /**
     * used to indicate load more giver or not.
     */
    public boolean loadMore = true;
    /**
     * this boolean to realize that we are in searching mode or not.
     */
    public boolean isSearchingMode = false;


    public SearchGiverViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        searchGiverListLiveData = new MutableLiveData<>();
    }

    /**
     * Get giver's initial page by resetting the correct flags/parameters
     * called when user open the view for the first time,
     * or make pollToRefresh.
     */
    public void refreshGiverRequest() {
        Page = 0;
        refreshAllCurrentGiverRequest();
    }

    /**
     * Get Giver's updates requesting for all current quantity in the list.
     * Here we calculate the current quantity by factor the page by GiversCount,
     * then replace all current list items by new one.
     */
    private void refreshAllCurrentGiverRequest() {
        int count = Page * giversCountPerPage;
        loadMore = true;
        //getGiversRequest(true, count, "", "", "");
    }

    public void fetchSuggestedGiverResponse(FilterObject filterObject) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRecommendedCaregivers(JSONBuilderFlavour.getRecommendedCaregiversParams(filterObject))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<SearchGiverResponse>>() {
                    @Override
                    public void accept(@NonNull List<SearchGiverResponse> searchGiverResponseList) throws Exception {
                        if (searchGiverResponseList.size() != 0) {
                            isListEmpty.set(false);
                        } else {
                            isListEmpty.set(true);
                        }
                        searchGiverListLiveData.setValue(searchGiverResponseList);
                        setIsLoading(false);
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }));
    }

    public void fetchFavouriteGiverResponse(FilterObject filterObject) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getFavouriteCaregivers(JSONBuilderFlavour.getFavouriteCaregiversParams(filterObject))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<SearchGiverResponse>>() {
                    @Override
                    public void accept(@NonNull List<SearchGiverResponse> searchGiverResponseList) throws Exception {
                        if (searchGiverResponseList.size() != 0) {
                            isListEmpty.set(false);
                        } else {
                            isListEmpty.set(true);
                        }
                        searchGiverListLiveData.setValue(searchGiverResponseList);
                        setIsLoading(false);
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }));
    }

    public void fetchGiverByWeCareID(String weCareId, FilterObject filterObject) {
        //add search key
        filterObject.setWecare_id(weCareId);

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getSearchForCaregivers(JSONBuilderFlavour.getFavouriteCaregiversParams(filterObject))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<SearchGiverResponse>>() {
                    @Override
                    public void accept(@NonNull List<SearchGiverResponse> searchGiverResponseList) throws Exception {
                        if (searchGiverResponseList.size() != 0) {
                            isListEmpty.set(false);
                        } else {
                            isListEmpty.set(true);
                        }
                        searchGiverListLiveData.setValue(searchGiverResponseList);
                        setIsLoading(false);
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                        getNavigator().toggleSwipeToRefresh(false);
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

    ///////////////////////////////////////
    ////// used in profile viewing only
    ///////////////////////////////////////

    public void fetchAllFavouriteGiverResponse() {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .getAllFavouriteCaregiverProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<SearchGiverResponse>>() {
                    @Override
                    public void accept(@NonNull List<SearchGiverResponse> searchGiverResponseList) throws Exception {

//                        ArrayList<SearchGiverResponse> searchGiverResponseList = new ArrayList<>();
                        if (searchGiverResponseList.size() != 0) {
                            isListEmpty.set(false);

                            //add to new type to use the same viewing adapter.
//                            for (SearchGiverResponse.CaregiverBean caregiverBean : caregiverBeanList) {
//                                SearchGiverResponse searchGiverResponse = new SearchGiverResponse();
//                                searchGiverResponse.setCaregiver(caregiverBean);
//                                searchGiverResponseList.add(searchGiverResponse);
//                            }
                        } else {
                            isListEmpty.set(true);
                        }
                        //update adapter
                        searchGiverListLiveData.setValue(searchGiverResponseList);

                        //stop refreshing
                        setIsLoading(false);
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }));
    }

    public void fetchAllBlockedGiverResponse() {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .getAllBlockedCaregiverProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<SearchGiverResponse>>() {
                    @Override
                    public void accept(@NonNull List<SearchGiverResponse> searchGiverResponseList) throws Exception {
//                        ArrayList<SearchGiverResponse> searchGiverResponseList = new ArrayList<>();
                        if (searchGiverResponseList.size() != 0) {
                            isListEmpty.set(false);

                            //add to new type to use the same viewing adapter.
//                            for (SearchGiverResponse.CaregiverBean caregiverBean : caregiverBeanList) {
//                                SearchGiverResponse searchGiverResponse = new SearchGiverResponse();
//                                searchGiverResponse.setCaregiver(caregiverBean);
//                                searchGiverResponseList.add(searchGiverResponse);
//                            }
                        } else {
                            isListEmpty.set(true);
                        }
                        //update adapter
                        searchGiverListLiveData.setValue(searchGiverResponseList);

                        //stop refreshing
                        setIsLoading(false);
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }));
    }

    public void fetchAllGiverByWeCareID(String weCareId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllSearchCaregiverProfile(weCareId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CaregiverUser>() {
                    @Override
                    public void accept(@NonNull CaregiverUser caregiverBean) throws Exception {

                        ArrayList<SearchGiverResponse> searchGiverResponseList = new ArrayList<>();

                        if (caregiverBean.isSuccess() && caregiverBean.getId() != null) {
                            SearchGiverResponse searchGiverResponse = new SearchGiverResponse();
                            searchGiverResponse.setCaregiver(caregiverBean);
                            searchGiverResponseList.add(searchGiverResponse);
                            isListEmpty.set(false);
                            //update adapter
                            searchGiverListLiveData.setValue(searchGiverResponseList);

                        } else {
                            isListEmpty.set(true);
                            getNavigator().handleError(caregiverBean.getError().getMessage());
                        }

                        //stop refreshing
                        setIsLoading(false);
                        getNavigator().toggleSwipeToRefresh(false);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                        getNavigator().toggleSwipeToRefresh(false);
                    }
                }));
    }

    /////end
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

    public ObservableBoolean getIsListItemSelected() {
        return isListItemSelected;
    }

    public MutableLiveData<List<SearchGiverResponse>> getSearchGiverListLiveData() {
        return searchGiverListLiveData;
    }

    public void addSearchGiverItemsToList(List<SearchGiverResponse> blogs) {
        searchGiverObservableArrayList.clear();
        searchGiverObservableArrayList.addAll(blogs);
    }

    public ObservableArrayList<SearchGiverResponse> getSearchGiverObservableArrayList() {
        return searchGiverObservableArrayList;
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void onGiverPicked() {
        getNavigator().onGiverPicked();
    }

    public String getSearchKey() {
        return searchKey;
    }

    public SearchGiverViewModel setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        return this;
    }

    /**
     * reset searchKey to refresh giver list.
     */
    public void resetSearchKey() {
        this.searchKey = "";
    }
}
