package com.wecare.android.ui.main.profile.userProfile.services.selection;

import com.wecare.android.data.model.api.responses.SubServiceResponse;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class UserServicesSelectionActivityModule {
    @Provides
    UserSubServicesAdapter provideServicesAdapter() {
        return new UserSubServicesAdapter(new ArrayList<SubServiceResponse>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(UserServicesSelectionActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
