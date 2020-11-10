
package com.wecare.android.ui.create_order.location;


import com.wecare.android.data.model.api.responses.UserLocationResponse;
import com.wecare.android.ui.base.BaseNavigator;

public interface LocationNavigator extends BaseNavigator {

    void goBack();

    void addNewLocationClicked();

    void onCountryClick();

    void onCityClick();

    void onMapPickerClick();

    void onDeletedSuccessfully(UserLocationResponse locationResponse);
}
