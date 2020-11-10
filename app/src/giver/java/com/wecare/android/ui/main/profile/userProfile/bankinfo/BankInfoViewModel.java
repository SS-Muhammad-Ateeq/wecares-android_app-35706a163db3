package com.wecare.android.ui.main.profile.userProfile.bankinfo;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.BankModel;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.requests.BankInfoRequest;
import com.wecare.android.data.model.api.responses.CountriesResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class BankInfoViewModel extends BaseViewModel<BankInfoActivityNavigator> {
    public BankInfoViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    BankInfoRequest bankInfoRequest = new BankInfoRequest();

    public BankInfoRequest getBankInfoRequest() {
        return bankInfoRequest;
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
    public void getCities(String countryID) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCites(countryID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CityModel>>() {
                    @Override
                    public void accept(List<CityModel> cityModels) throws Exception {
                        getNavigator().citiesFinishedSuccessfully(new ArrayList<CityModel>(cityModels));
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

    public void getBankInfo(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getBankInfo()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BankModel>() {
                    @Override
                    public void accept(BankModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess()){
                            getNavigator().bankInfoFetched(model);
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
    public  void updateBankInfo(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .setBankInfo(JSONBuilderFlavour.getBankInfoRequestParams(getBankInfoRequest()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BankModel>() {
                    @Override
                    public void accept(BankModel model) throws Exception {
                        setIsLoading(false);
                        if (model.isSuccess()){
                            getNavigator().bankInfoUpdatedSuccessfully();
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

    public void countriesClicked(){
        getNavigator().countriesClicked();
    }
    public void citiesClicked(){
        getNavigator().citiesClicked();
    }
    public void bankinformationClicked(){getNavigator().bankinformationClicked();}
}
