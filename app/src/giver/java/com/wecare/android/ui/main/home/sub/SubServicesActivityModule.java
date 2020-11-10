package com.wecare.android.ui.main.home.sub;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class SubServicesActivityModule {

    @Provides
    SubServicesAdapter provideSubServicesAdapter() {
        return new SubServicesAdapter(new ArrayList<SubServiceResponse>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(SubServicesActivity activity) {
        return new LinearLayoutManager(activity);
    }


}
