package com.wecare.android.ui.create_order.relative;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class RelativeProfileActivityModule {

    @Provides
    RelativeProfileAdapter provideRelativeProfileAdapter() {
        return new RelativeProfileAdapter(new ArrayList<RelativeProfileResponse>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(RelativeProfileActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
