package com.wecare.android.ui.main.profile.userProfile.bankinfo;

import com.wecare.android.data.model.api.models.BankModel;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.ArrayList;

public interface BankInfoActivityNavigator extends BaseNavigator {
    void countriesClicked();
    void citiesClicked();
    void bankinformationClicked();
    void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels);
    void citiesFinishedSuccessfully(ArrayList<CityModel> cityModels);
    void bankInfoFetched(BankModel model);
    void bankInfoUpdatedSuccessfully();
}
