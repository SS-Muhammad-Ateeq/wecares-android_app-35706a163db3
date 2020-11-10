
package com.wecare.android.ui.main.rating;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public class RateUsDialogModule {

    @Provides
    RateUsViewModel provideRateUsViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        return new RateUsViewModel(dataManager, schedulerProvider);
    }

}
