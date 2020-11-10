
package com.wecare.android.ui.create_order.location;

import androidx.databinding.ObservableField;
import androidx.recyclerview.selection.SelectionTracker;

import com.wecare.android.data.model.api.responses.UserLocationResponse;


public class LocationItemViewModel {

    private UserLocationResponse userLocationResponseItem;

    public ObservableField<String> locationNameTitle;
    public ObservableField<String> locationCountry;
    public ObservableField<String> locationCity;
    public ObservableField<String> locationStreetName;
    public ObservableField<String> locationArea;
    public ObservableField<String> locationBuildingNumber;
    public ObservableField<String> locationFloor;
    public ObservableField<Boolean> locationCheckedStatusCheckbox;

    private ItemViewModelListener mListener;

    public LocationItemViewModel(UserLocationResponse userLocationResponse, SelectionTracker<Long> selectionTracker, LocationAdapter.SelectorItemDetail details, ItemViewModelListener listener) {
        this.userLocationResponseItem = userLocationResponse;
        this.mListener = listener;

        locationNameTitle = new ObservableField<>(userLocationResponseItem.getName());
        locationCountry = new ObservableField<>(userLocationResponseItem.getCountry().getTitle());
        locationCity = new ObservableField<>(userLocationResponseItem.getCity().getTitle());
        locationStreetName = new ObservableField<>(userLocationResponseItem.getStreet_name()!= null ? userLocationResponseItem.getStreet_name() : "");
        locationArea = new ObservableField<>(userLocationResponseItem.getArea() != null ? userLocationResponseItem.getArea() : "");
        locationBuildingNumber = new ObservableField<>(userLocationResponseItem.getBuilding_number()!= null ? userLocationResponseItem.getBuilding_number() : "");
        locationFloor = new ObservableField<>(userLocationResponseItem.getFloor_number()!= null ? userLocationResponseItem.getFloor_number() : "");

        if (selectionTracker != null) {
            locationCheckedStatusCheckbox = new ObservableField<>(selectionTracker.isSelected(details.getSelectionKey()));
        } else {
            locationCheckedStatusCheckbox = new ObservableField<>(false);
        }
    }

    public void onCheckedClick() {
        mListener.onCheckedClick(userLocationResponseItem);
    }

    public void onItemClicked() {
        mListener.onItemClicked(userLocationResponseItem);
    }

    public void onDeleteClicked() {
        mListener.onDeleteClicked(userLocationResponseItem);
    }

    public void onEditClicked() {
        mListener.onEditClicked(userLocationResponseItem);
    }

    public interface ItemViewModelListener {
        void onCheckedClick(UserLocationResponse userLocationResponseItem);

        void onItemClicked(UserLocationResponse userLocationResponseItem);

        void onDeleteClicked(UserLocationResponse userLocationResponseItem);

        void onEditClicked(UserLocationResponse userLocationResponseItem);

    }
}
