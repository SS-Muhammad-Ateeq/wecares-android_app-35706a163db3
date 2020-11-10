
package com.wecare.android.ui.order_info;

import androidx.databinding.ObservableBoolean;
import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

public class InformationViewModel extends BaseViewModel<InformationNavigator> {

    private RelativeProfileResponse profileResponse;

    private ObservableBoolean isRelativeProfileSelected = new ObservableBoolean(false);


    public InformationViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void onImagePickerClicked() {
        getNavigator().onImagePickerClicked();
    }

    public ObservableBoolean getIsRelativeProfileSelected() {
        return isRelativeProfileSelected;
    }

    public void onRelativeProfileClicked() {
        getNavigator().onRelativeProfileClicked();
    }

    public void onNeedSomeMaterialClicked() {
        getNavigator().onNeedSomeMaterialClicked();
    }

    public void onSpecialRequestClicked() {
        getNavigator().onSpecialRequestClicked();
    }

    public RelativeProfileResponse getProfileResponse() {
        if (profileResponse == null) {
            profileResponse = new RelativeProfileResponse();
        }
        return profileResponse;
    }

    public InformationViewModel setProfileResponse(RelativeProfileResponse profileResponse) {
        if (profileResponse != null) {
            isRelativeProfileSelected.set(true);
        } else {
            isRelativeProfileSelected.set(false);
        }
        this.profileResponse = profileResponse;
        return this;
    }


}
