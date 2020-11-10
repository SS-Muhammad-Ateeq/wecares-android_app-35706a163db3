
package com.wecare.android.ui.main.settings;


import com.wecare.android.ui.base.BaseNavigator;

public interface SettingsNavigator extends BaseNavigator {

    void goBack();

    void logoutClicked();

    void logoutUserSuccessfully();

    void aboutUsClicked();

    void privacyPolicy();

    void termsConditions();

    void onChangeLanguageClicked();

    void onContactSupportClicked();

    void shareAppClicked();

    void contactUsClicked();

    void rateUsClicked();

    void changePasswordClicked();

    void holdServicesSuccessfully(boolean enabled);
    void holdServicesFailed(boolean enabled);

    void notificationEnableSuccessfully(boolean enabled);
    void notificationEnableFailed(boolean enabled);


}
