package com.wecare.android.ui.main.order.current;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.OrdersResponse;

import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class CurrentFragmentModule {

    @Provides
    CurrentAdapter provideCurrentAdapter() {
        return new CurrentAdapter(new ArrayList<OrderModel>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CurrentFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
