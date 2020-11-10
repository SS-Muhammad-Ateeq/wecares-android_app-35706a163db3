package com.wecare.android.ui.main.order.previous;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;

import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class PreviousFragmentModule {

    @Provides
    PreviousAdapter providePreviousAdapter() {
        return new PreviousAdapter(new ArrayList<OrderModel>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(PreviousFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
