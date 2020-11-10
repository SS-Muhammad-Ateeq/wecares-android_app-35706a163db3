package com.wecare.android.ui.auth.registration.info;

import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.ui.auth.registration.RegistrationNavigator;

import java.util.ArrayList;

public interface RegistrationInfoFragmentNavigator extends RegistrationNavigator {
    void showAccountTypes();
    void countriesFinishedSuccessfully(String status,ArrayList<CountryModel> countryModels);
    void countriesClicked();
    void citiesFinishedSuccessfully(ArrayList<CityModel> countryModels);
    void citiesClicked();
    void genderClicked();
    void nationalityClicked();
    void membershipTypeClicked();
    void birthDateClicked();
    void termsConditionsClicked();
    void userRegisteredSuccessfully(UserModel userModel);
    void memberShipTypesFetchedSuccessfully(ArrayList<MembershipType> membershipTypes);


}
