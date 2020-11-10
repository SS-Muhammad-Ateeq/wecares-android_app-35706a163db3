package com.wecare.android.ui.main.profile.userProfile;

import com.wecare.android.ui.base.BaseNavigator;

public interface UserProfileFragmentNavigator extends BaseNavigator {
    void personalInfoClicked();

    void LocationsClicked();

    void relativeProfilesClicked();

    void paymentMethodClicked();

    void favoriteAndBlockedClicked();
}
