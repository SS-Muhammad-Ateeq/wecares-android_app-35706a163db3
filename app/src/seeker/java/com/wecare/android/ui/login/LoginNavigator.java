
package com.wecare.android.ui.login;


import com.wecare.android.ui.base.BaseNavigator;

public interface LoginNavigator extends BaseNavigator {

    void openMainActivity();

    void openVerificationCodeActivity();

    void login();

    void openForgetPassword();

    void onSocialAccountRegistrationNeeded(String tokenHash, String argsSocialKey);

    void onFacebookClicked();

    void onTwitterClicked();

    void onGoogleClicked();
}
