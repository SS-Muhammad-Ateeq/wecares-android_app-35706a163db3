package com.wecare.android.ui.create_order.services;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.create_order.services.sub_duration.DurationSubServicesAdapter;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class OrderServicesFragmentModule {

    @Provides
    OrderServicesAdapter provideServicesAdapter() {
        return new OrderServicesAdapter(new ArrayList<MainServiceModel>());
    }

    @Provides
    DurationSubServicesAdapter provideDurationSubServicesAdapter() {
        return new DurationSubServicesAdapter(new ArrayList<SubServiceResponse>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderServicesFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
