package com.wecare.android.ui.main.rating.overall;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

public class OverallRatingFragmentViewModel extends BaseViewModel<OverallRatingFragmentNavigator> {
    public OverallRatingFragmentViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void nextClicked(){
        getNavigator().nextClicked();
    }
    public void exitClicked(){
        getNavigator().exitStepperClicked();
    }


}
