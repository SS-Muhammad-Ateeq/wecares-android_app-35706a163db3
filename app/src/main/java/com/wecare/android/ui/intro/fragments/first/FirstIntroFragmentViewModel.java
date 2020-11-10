package com.wecare.android.ui.intro.fragments.first;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.ui.intro.IntroNavigator;
import com.wecare.android.utils.rx.SchedulerProvider;

public class FirstIntroFragmentViewModel extends BaseViewModel<IntroNavigator> {
    public FirstIntroFragmentViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNextClicked(){
       getNavigator().nextClicked();
    }
    public void onBackClicked(){
        getNavigator().backClicked();
    }
}
