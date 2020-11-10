package com.wecare.android.ui.main.profile.userProfile.personalInfo;

import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.ArrayList;

public interface CaregiverPersonalInformationActivityNavigator extends BaseNavigator {

    void showAccountTypes();
    void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels);
    void nationalitiesFinishedSuccessfully(ArrayList<CountryModel> lookUpModels);
    void memberShipTypesFetchedSuccessfully(ArrayList<MembershipType> membershipTypes);
    void countriesClicked();
    void personalcitiesClicked();
    void citiesFinishedSuccessfully(ArrayList<CityModel> countryModels);
    void genderClicked();
    void nationalityClicked();
    void membershipTypeClicked();
    void birthDateClicked();
    void informationUpdatedSuccessfully();
    void UserProfileClicked();
    void identityDocumentClicked();
    void identityDocumentUploaded();
}
