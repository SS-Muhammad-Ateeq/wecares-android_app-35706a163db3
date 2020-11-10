package com.wecare.android.ui.main.order.details;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.ui.base.BaseNavigator;

public interface OrderDetailsNavigator extends BaseNavigator {
    void showGiverProfile();
    void positiveButtonClicked();
    void negativeButtonClicked();
    void orderCanceledSuccessFully();
    void orderDetailsFetchedSuccessfully(OrderModel orderModel);
}
