package com.wecare.android.ui.webview;


import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

public class WebViewModel extends BaseViewModel<WebViewNavigator> {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WebViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void acceptClicked(){
        getNavigator().acceptClicked();
    }
}
