
package com.wecare.android.ui.main.home.sub;

import androidx.databinding.ObservableField;
import com.wecare.android.data.model.api.responses.SubServiceResponse;


public class SubServicesItemViewModel {

    private SubServiceResponse subServiceResponse;

    public ObservableField<String> serviceNameTitle;

    private SubServiceItemViewModelListener mListener;

    public SubServicesItemViewModel(SubServiceResponse subServiceResponse, SubServiceItemViewModelListener listener) {
        this.subServiceResponse = subServiceResponse;
        this.mListener = listener;
        serviceNameTitle = new ObservableField<>(subServiceResponse.getServiceName());
    }

    public void onItemClick() {
        mListener.onItemClick(subServiceResponse);
    }

    public interface SubServiceItemViewModelListener {
        void onItemClick(SubServiceResponse subServiceResponse);
    }
}
