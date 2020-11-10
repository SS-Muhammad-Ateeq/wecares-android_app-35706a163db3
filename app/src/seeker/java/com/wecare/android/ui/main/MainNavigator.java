
package com.wecare.android.ui.main;

public interface MainNavigator {

    void openLoginActivity();

    void openOrderActivity();

    void handleError(Throwable throwable);

    void notificationsCountFetched(int count);


}
