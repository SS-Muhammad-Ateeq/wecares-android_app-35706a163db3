package com.wecare.android.ui.auth.forgetpassword.verfication;

import com.wecare.android.ui.base.BaseNavigator;

public interface ForgetPasswordVerificationNavigator extends BaseNavigator {

    void saveClicked();
    void resendCode();
    void passwordUpdatedSuccessfully();
    void resentSuccessfully();
    void openMainActivity();
}
