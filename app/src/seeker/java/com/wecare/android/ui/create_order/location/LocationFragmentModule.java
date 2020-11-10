package com.wecare.android.ui.create_order.location;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.wecare.android.data.model.api.responses.UserLocationResponse;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationFragmentModule {

    @Provides
    LocationAdapter provideRelativeProfileAdapter() {
        return new LocationAdapter(new ArrayList<UserLocationResponse>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(LocationFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
