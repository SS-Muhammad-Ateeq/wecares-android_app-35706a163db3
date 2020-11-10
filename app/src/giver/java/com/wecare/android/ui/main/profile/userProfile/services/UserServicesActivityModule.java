package com.wecare.android.ui.main.profile.userProfile.services;

import com.wecare.android.data.model.api.responses.MainServiceModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class UserServicesActivityModule {

    @Provides
    UserServicesAdapter provideServicesAdapter() {
        return new UserServicesAdapter(new ArrayList<MainServiceModel>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(UserServicesActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
