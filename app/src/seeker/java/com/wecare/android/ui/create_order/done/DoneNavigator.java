
package com.wecare.android.ui.create_order.done;


import com.wecare.android.ui.base.BaseNavigator;

public interface DoneNavigator extends BaseNavigator {

    void goBack();

    void onSearchForCaregiverClick();

    void onSubmitOrderClick();

    void showOrderCreateDialog();

    void creditCardClicked();

    void cashClicked();
}
