
package com.wecare.android.ui.create_order.relative;

import androidx.databinding.ObservableField;
import androidx.recyclerview.selection.SelectionTracker;

import com.wecare.android.data.model.api.responses.RelativeProfileResponse;

import java.util.Locale;


public class RelativeProfileItemViewModel {

    private RelativeProfileResponse relativeProfileResponseItem;

    public ObservableField<String> relativeNameTitle;
    public ObservableField<String> relativeRelationshipTitle;
    public ObservableField<String> relativeYearTitle;
    public ObservableField<String> relativeGenderTitle;
    public ObservableField<String> relativePhoneTitle;
    public ObservableField<Boolean> relativeCheckedStatusCheckbox;

    private ItemViewModelListener mListener;

    public RelativeProfileItemViewModel(RelativeProfileResponse relativeProfileResponse, SelectionTracker<Long> selectionTracker, RelativeProfileAdapter.RelativeItemDetail details, ItemViewModelListener listener) {
        this.relativeProfileResponseItem = relativeProfileResponse;
        this.mListener = listener;

        relativeNameTitle = new ObservableField<>(relativeProfileResponseItem.getName());
        relativeRelationshipTitle = new ObservableField<>(relativeProfileResponseItem.getRelationship().getTitle());
        relativeYearTitle = new ObservableField<>(relativeProfileResponseItem.getAge());
        relativeGenderTitle = new ObservableField<>(relativeProfileResponseItem.getGender().toString());
        relativePhoneTitle = new ObservableField<>(relativeProfileResponseItem.getMobile_number());

        if (selectionTracker != null) {
            relativeCheckedStatusCheckbox = new ObservableField<>(selectionTracker.isSelected(details.getSelectionKey()));
        } else {
            relativeCheckedStatusCheckbox = new ObservableField<>(false);
        }
    }

    public void onCheckedClick() {
        mListener.onCheckedClick(relativeProfileResponseItem);
    }

    public void onDeleteClicked() {
        mListener.onDeleteClicked(relativeProfileResponseItem);
    }

    public void onEditClicked() {
        mListener.onImageClicked(relativeProfileResponseItem);
    }

    public void onImageClicked() {
        mListener.onImageClicked(relativeProfileResponseItem);
    }

    public interface ItemViewModelListener {
        void onCheckedClick(RelativeProfileResponse relativeProfileResponse);

        void onImageClicked(RelativeProfileResponse relativeProfileResponseItem);

        void onDeleteClicked(RelativeProfileResponse relativeProfileResponseItem);
    }
}
