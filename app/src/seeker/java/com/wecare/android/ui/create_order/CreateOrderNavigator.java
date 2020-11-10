
package com.wecare.android.ui.create_order;

public interface CreateOrderNavigator {

    void handleError(Throwable throwable);

    void onNextClicked();

    void goToPreviousService();
}
