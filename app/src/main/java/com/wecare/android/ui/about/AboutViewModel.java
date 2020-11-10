
package com.wecare.android.ui.about;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

/**
 * Created by amitshekhar on 09/07/17.
 */

public class AboutViewModel extends BaseViewModel<AboutNavigator> {

    public AboutViewModel(DataManagerFlavour dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }
}
