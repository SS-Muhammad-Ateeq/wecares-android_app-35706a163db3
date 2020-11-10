
package com.wecare.android.ui.create_order;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;


public class CreateOrderViewModel extends BaseViewModel<CreateOrderNavigator> {

    public CreateOrderViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNextClicked() {
        getNavigator().onNextClicked();
    }
    public void goToPreviousService(){
        getNavigator().goToPreviousService();
    }

}
