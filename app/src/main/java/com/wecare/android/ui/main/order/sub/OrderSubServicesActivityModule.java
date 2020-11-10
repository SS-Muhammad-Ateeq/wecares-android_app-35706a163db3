package com.wecare.android.ui.main.order.sub;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class OrderSubServicesActivityModule {

    @Provides
    OrderSubServicesAdapter provideSubServicesAdapter() {
        return new OrderSubServicesAdapter(new ArrayList<SubServiceResponse>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderSubServicesActivity activity) {
        return new LinearLayoutManager(activity);
    }


}
