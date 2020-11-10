package com.wecare.android.ui.main.order.finishorder;

import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.main.order.FinishOrderActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class FinishOrderActivityModule {

    @Provides
    AddedSubServicesAdapter provideDurationSubServicesAdapter() {
        return new AddedSubServicesAdapter(new ArrayList<SubServiceResponse>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(FinishOrderActivity orderActivity) {
        return new LinearLayoutManager(orderActivity);
    }
}
