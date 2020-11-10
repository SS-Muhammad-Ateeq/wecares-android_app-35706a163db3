package com.wecare.android.ui.about;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public class AboutFragmentModule {

    @Provides
    AboutViewModel provideAboutViewModel(DataManagerFlavour dataManager,
                                         SchedulerProvider schedulerProvider) {
        return new AboutViewModel(dataManager, schedulerProvider);
    }

}
