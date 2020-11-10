
package com.wecare.android.ui.main.order.current;


import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.OrdersResponse;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.List;

public interface CurrentNavigator extends BaseNavigator {

    void updateCurrentOrderList(List<OrderModel> ordersResponseList);

    void goBack();

}
