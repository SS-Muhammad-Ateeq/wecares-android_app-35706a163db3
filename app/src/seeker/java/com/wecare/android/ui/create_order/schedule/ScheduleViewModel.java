
package com.wecare.android.ui.create_order.schedule;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

public class ScheduleViewModel extends BaseViewModel<ScheduleNavigator> {

    public ScheduleViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void selectDate() {
        getNavigator().selectDate();
    }

    public void selectTime() {
        getNavigator().selectTime();
    }

}
