
package com.wecare.android.ui.main.order.previous;


import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.List;

public interface PreviousNavigator extends BaseNavigator {

    void updatePreviousOrderList(List<OrderModel> orderModels);

    void goBack();

}
