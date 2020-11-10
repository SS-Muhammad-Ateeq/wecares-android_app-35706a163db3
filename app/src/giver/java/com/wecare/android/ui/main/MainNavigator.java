
package com.wecare.android.ui.main;

/**
 * Created by amitshekhar on 09/07/17.
 */

public interface MainNavigator {

    void openLoginActivity();

    void handleError(Throwable throwable);

    void notificationsCountFetched(int count);

}
