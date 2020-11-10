package com.wecare.android.ui.profile;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.requests.UpdateByIDRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;
import io.reactivex.functions.Consumer;

//import com.wecare.android.data.model.api.requests.UpdateByIDRequest;

public class UserShowProfileViewModel extends BaseViewModel<UserShowProfileActivityNavigator> {

    public final ObservableField<Float> rating = new ObservableField<>();
    public final ObservableField<String> picURL = new ObservableField<>();

    public ObservableField<Float> getRating() {
        return rating;
    }

    public ObservableField<String> getPicURL() {
        return picURL;
    }

    public void setRating(double rating) {
        this.rating.set((float) rating);
    }

    public void setURL(String url) {
        picURL.set(url);
    }

    public UserShowProfileViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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
                            getNavigator().countriesFinishedSuccessfully(lookupResponse.getCountries());
                        } else
                            getNavigator().handleError(lookupResponse.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void getCaregiverProfile(String giverID) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCaregiverProfile(giverID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CaregiverUser>() {
                    @Override
                    public void accept(CaregiverUser caregiverUser) throws Exception {
                        if (caregiverUser.isSuccess()) {
                            getNavigator().giverProfileFetchedSuccessfully(caregiverUser);
                        } else
                            getNavigator().handleError(caregiverUser.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void blockSeeker(String id,String reason) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .blockSeeker(JSONBuilderFlavour.getCommonRequestParams(new UpdateByIDRequest(id,reason, false)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isSuccess()) {
                            getNavigator().userBlocked();
                        } else
                            getNavigator().handleError(response.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));

    }

    public void unBlockSeeker(String id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .unBlockSeeker(JSONBuilderFlavour.getCommonRequestParams(new UpdateByIDRequest(id,false)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isSuccess()) {
                            getNavigator().userUnBlocked();
                        } else
                            getNavigator().handleError(response.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void blockGiver(String id, String reason) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .blockGiver(JSONBuilderFlavour.getCommonRequestParams(new UpdateByIDRequest(id,reason,true)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isSuccess()) {
                            getNavigator().userBlocked();
                        } else
                            getNavigator().handleError(response.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));

    }

    public void unblockGiver(String id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .unBlockGiver(JSONBuilderFlavour.getCommonRequestParams(new UpdateByIDRequest(id, true)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isSuccess()) {
                            getNavigator().userUnBlocked();
                        } else
                            getNavigator().handleError(response.getError().getMessage());
                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void favoriteGiver(String id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .favoriteCaregiver(JSONBuilderFlavour.getCommonRequestParams(new UpdateByIDRequest(id,true)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isSuccess()) {
                            getNavigator().userFavorite();
                        } else
                            getNavigator().handleError(response.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));

    }

    public void unFavoriteGiver(String id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .unFavoriteCaregiver(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isSuccess()) {
                            getNavigator().userUnFavorite();
                        } else
                            getNavigator().handleError(response.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));

    }

}
