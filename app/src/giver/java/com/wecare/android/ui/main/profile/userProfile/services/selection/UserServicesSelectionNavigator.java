package com.wecare.android.ui.main.profile.userProfile.services.selection;

import com.wecare.android.data.model.api.requests.UserServiceRequestModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.base.BaseNavigator;

public interface UserServicesSelectionNavigator extends BaseNavigator {
    void serviceAddedSuccessfully(SubServiceResponse response);
    void serviceEditedSuccessfully(UserServiceRequestModel model, SubServiceResponse response);
    void serviceDeletedSuccessfully(SubServiceResponse response);

}
