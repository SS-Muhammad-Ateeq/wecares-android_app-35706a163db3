package com.wecare.android.ui.main.order;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.ui.base.BaseNavigator;

public interface FinishOrderActivityNavigator extends BaseNavigator {

    void addNewServiceClicked();
    void onConfirmClicked();
    void onCashClicked();
    void onCreditClicked();
    void finishOrderSubmittedSuccessfully(OrderModel orderModel);
    void onNegativeClicked();
}
