package com.wecare.android.ui.template;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

public class TemplateViewModel extends BaseViewModel<TemplateNavigator> {
    public TemplateViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
