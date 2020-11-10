
package com.wecare.android.ui.main.order.current;


import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.List;

public interface CurrentNavigator extends BaseNavigator {

    void updateCurrentOrderList(List<OrderModel> ordersResponseList);
    void orderRejectedSuccessFully(int position);
    void orderCanceledSuccessFully(int position);
    void orderStatusChangedSuccessFully(OrderModel Model,int status,int position);

    void goBack();

}
