package com.wecare.android.ui.main.order.scheduled;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.List;

public interface ScheduleSummaryActivityNavigator extends BaseNavigator {
    void updateCurrentOrderList(List<OrderModel> ordersResponseList);

}
