package com.wecare.android.ui.profile;

import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.ArrayList;

public interface UserShowProfileActivityNavigator extends BaseNavigator {
    void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels);
    void userBlocked();
    void userUnBlocked();
    void userFavorite();
    void userUnFavorite();
    void giverProfileFetchedSuccessfully(CaregiverUser caregiverUser);

}
