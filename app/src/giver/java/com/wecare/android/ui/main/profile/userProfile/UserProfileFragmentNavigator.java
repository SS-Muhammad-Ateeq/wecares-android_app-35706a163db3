package com.wecare.android.ui.main.profile.userProfile;

import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.ui.base.BaseNavigator;

public interface UserProfileFragmentNavigator extends BaseNavigator {
    void personalInfoClicked();
    void scheduleServicesClicked();
    void servicesClicked();
    void ServiceAreaClicked();
    void BankInfoClicked();
    void EducationalCertificatesClicked();
    void userInfoFetchedSuccessfully(UserModel userModel);
}
