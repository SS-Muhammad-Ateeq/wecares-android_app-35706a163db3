package com.wecare.android.ui.webview.activity;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

public class WebViewActivityViewModel extends BaseViewModel<WebViewActivityNavigator> {
    public WebViewActivityViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
