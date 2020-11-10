package com.wecare.android.ui.main.profile.userProfile.paymentmethod;

import com.wecare.android.ui.base.BaseNavigator;

public interface PaymentMethodNavigator extends BaseNavigator {
    void paymentMethodUpdatedSuccessfully(int method);

    void onCashClicked();

    void onCreditClicked();
}
