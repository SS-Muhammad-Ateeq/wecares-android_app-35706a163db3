package com.wecare.android.ui.main.order.current;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.OrdersResponse;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class CurrentFragmentModule {

    @Provides
    CurrentAdapter provideCurrentAdapter() {
//        ArrayList<OrderModel> ordersResponses = new ArrayList<OrderModel>();
////        ordersResponses.add(new OrderModel());
////        ordersResponses.add(new OrderModel());
//        return new CurrentAdapter(ordersResponses);
        return new CurrentAdapter(new ArrayList<OrderModel>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CurrentFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
