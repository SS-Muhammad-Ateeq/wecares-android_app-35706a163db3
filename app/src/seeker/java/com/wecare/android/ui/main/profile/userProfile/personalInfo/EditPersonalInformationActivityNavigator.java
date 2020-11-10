package com.wecare.android.ui.main.profile.userProfile.personalInfo;

import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.ArrayList;

public interface EditPersonalInformationActivityNavigator extends BaseNavigator {

    void showAccountTypes();
    void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels);
    void countriesClicked();
    void citiesFinishedSuccessfully(ArrayList<CityModel> countryModels);
    void citiesClicked();
    void genderClicked();
    void nationalityClicked();
    void membershipTypeClicked();
    void birthDateClicked();
    void informationUpdatedSuccessfully();
}
