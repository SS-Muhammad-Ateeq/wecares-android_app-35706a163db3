package com.wecare.android.ui.intro.fragments.third;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.ui.intro.IntroNavigator;
import com.wecare.android.utils.rx.SchedulerProvider;

public class ThirdIntroFragmentViewModel extends BaseViewModel<IntroNavigator> {
    public ThirdIntroFragmentViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void onNextClicked(){
        getNavigator().nextClicked();
    }
    public void onBackClicked(){
        getNavigator().backClicked();
    }
    public void onSkipClicked(){
        getNavigator().skipClicked();
    }
}