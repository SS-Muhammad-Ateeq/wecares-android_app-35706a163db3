
package com.wecare.android.ui.main.rating;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;


public class RateUsViewModel extends BaseViewModel<RateUsCallback> {

    public RateUsViewModel(DataManagerFlavour dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        getNavigator().dismissDialog();
    }

}
