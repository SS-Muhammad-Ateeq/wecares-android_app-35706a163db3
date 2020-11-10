
package com.wecare.android.ui.main.guest;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.ui.main.settings.SettingsNavigator;
import com.wecare.android.utils.rx.SchedulerProvider;
import io.reactivex.functions.Consumer;

public class GuestViewModel extends BaseViewModel<GuestNavigator> {

    public GuestViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }


    public void loginUserClicked() {
        getDataManager().setUserAsLoggedOut();
        setIsLoading(false);
        getNavigator().navigateToLoginScreen();
    }

}
