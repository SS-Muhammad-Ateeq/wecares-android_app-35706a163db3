package com.wecare.android.ui.main.profile.userProfile.services.selection;

import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.utils.AppConstants;

import androidx.databinding.ObservableField;

public class ServicesSelectionItemViewModel {

    private SubServiceResponse serviceResponse;


    public ObservableField<String> subServiceNameTitle;

    public serviceSelectionListener mListener;

    public ServicesSelectionItemViewModel(SubServiceResponse serviceResponse, serviceSelectionListener listener) {
        this.serviceResponse = serviceResponse;
        this.mListener = listener;
        subServiceNameTitle = new ObservableField<>(serviceResponse.getServiceName());
    }

    public void onMainActionClicked() {
        if (serviceResponse.getSelected().equals(AppConstants.PHP_TRUE)) {
            mListener.onDeleteClicked(serviceResponse.getPosition(), serviceResponse);
        } else
            mListener.onAddClicked(serviceResponse.getPosition(), serviceResponse);

    }

    public void onSubActionClicked() {
        mListener.onEditClicked(serviceResponse.getPosition(), serviceResponse);
    }


    public interface serviceSelectionListener {
        void onEditClicked(int position, SubServiceResponse serviceResponse);

        void onDeleteClicked(int position, SubServiceResponse serviceResponse);

        void onAddClicked(int position, SubServiceResponse serviceResponse);
    }
}
