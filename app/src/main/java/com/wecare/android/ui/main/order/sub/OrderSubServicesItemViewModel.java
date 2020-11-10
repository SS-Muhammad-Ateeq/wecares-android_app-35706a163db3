
package com.wecare.android.ui.main.order.sub;

import androidx.databinding.ObservableField;
import androidx.recyclerview.selection.SelectionTracker;
import com.wecare.android.data.model.api.responses.SubServiceResponse;


public class OrderSubServicesItemViewModel {

    private SubServiceResponse subServiceResponse;

    public ObservableField<String> serviceNameTitle;

    private SubServiceItemViewModelListener mListener;

    public ObservableField<Boolean> subServiceCheckedStatusCheckbox;


    public OrderSubServicesItemViewModel(SubServiceResponse subServiceResponse, SelectionTracker<Long> selectionTracker, OrderSubServicesAdapter.SelectorItemDetail details, SubServiceItemViewModelListener listener) {
        this.subServiceResponse = subServiceResponse;
        this.mListener = listener;

        serviceNameTitle = new ObservableField<>(subServiceResponse.getServiceName());

        if (selectionTracker != null) {
            subServiceCheckedStatusCheckbox = new ObservableField<>(selectionTracker.isSelected(details.getSelectionKey()));
        } else {
            subServiceCheckedStatusCheckbox = new ObservableField<>(false);
        }

    }


    public void onCheckedClick() {
        mListener.onCheckedClick(subServiceResponse);
    }

    public interface SubServiceItemViewModelListener {
        void onCheckedClick(SubServiceResponse subServiceResponse);
    }
}
