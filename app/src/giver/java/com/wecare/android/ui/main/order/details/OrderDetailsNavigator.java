package com.wecare.android.ui.main.order.details;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.ui.base.BaseNavigator;

public interface OrderDetailsNavigator extends BaseNavigator {
    void callSeekerClicked();
    void showSeekerProfile();
    void positiveButtonClicked();
    void negativeButtonClicked();

    void orderRejectedSuccessFully();
    void orderCanceledSuccessFully();
    void orderStatusChangedSuccessFully(OrderModel Model, int status);
    void orderDetailsFetchedSuccessfully(OrderModel orderModel);
    void getDirectionsClicked();
}
