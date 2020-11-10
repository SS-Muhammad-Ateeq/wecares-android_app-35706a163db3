package com.wecare.android.ui.auth.registration;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

public class RegistrationViewModel extends BaseViewModel<RegistrationNavigator> {
    public RegistrationViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
