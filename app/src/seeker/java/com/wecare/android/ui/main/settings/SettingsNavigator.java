
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

    void shareAppClicked();

    void contactUsClicked();

    void rateUsClicked();

    void changePasswordClicked();

    void notificationEnableSuccessfully(boolean enabled);
    void notificationEnableFailed(boolean enabled);

    void onContactSupportClicked();

}
