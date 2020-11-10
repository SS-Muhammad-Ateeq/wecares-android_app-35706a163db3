package com.wecare.android.ui.main.profile.userProfile.servicearea;

import com.wecare.android.data.model.api.models.LocationModel;
import com.wecare.android.ui.base.BaseNavigator;

public interface CaregiverServiceAreaNavigator extends BaseNavigator {
    void currentLocationClicked();
    void customLocationClicked();
    void serviceLocationUpdatedSuccessfully();
    void serviceLocationFetchedSuccessfully(LocationModel locationModel);
}
