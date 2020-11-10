package com.wecare.android.ui.main.profile.userProfile.services;

import com.wecare.android.data.model.api.models.ServicesTotalModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.ui.base.BaseNavigator;

public interface UserServicesActivityNavigator extends BaseNavigator {
    void activateNowClicked();
    void goToSubServices(MainServiceModel mainServiceModel);
    void requestToActivateSuccessfully();
    void serviceNumbersFetched(ServicesTotalModel totalModel);

}
