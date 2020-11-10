package com.wecare.android.ui.main.order.scheduled;

import com.wecare.android.data.model.api.models.OrderModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ScheduleSummaryActivityModule {
    @Provides
    ScheduleSummaryAdapter provideScheduleSummaryAdapter() {
        return new ScheduleSummaryAdapter(new ArrayList<OrderModel>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(ScheduleSummaryActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
