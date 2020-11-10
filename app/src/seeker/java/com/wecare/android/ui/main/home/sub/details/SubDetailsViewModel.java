
package com.wecare.android.ui.main.home.sub.details;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.ui.main.home.sub.SubServicesNavigator;
import com.wecare.android.utils.rx.SchedulerProvider;

public class SubDetailsViewModel extends BaseViewModel<SubServicesNavigator> {

    public SubDetailsViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onCloseClick() {
        getNavigator().goBack();
    }

    public void onOrderNowClick() {
        getNavigator().onOrderNowClick();
    }
}
