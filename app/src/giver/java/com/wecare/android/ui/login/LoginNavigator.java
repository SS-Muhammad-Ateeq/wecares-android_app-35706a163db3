
package com.wecare.android.ui.login;


import com.wecare.android.ui.base.BaseNavigator;

public interface LoginNavigator extends BaseNavigator {

    void openMainActivity();

    void openVerificationCodeActivity();

    void login();

    void openForgetPassword();

}
