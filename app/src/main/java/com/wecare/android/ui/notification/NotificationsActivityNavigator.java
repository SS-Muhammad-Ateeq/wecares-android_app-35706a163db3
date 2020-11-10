package com.wecare.android.ui.notification;

import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.ui.base.BaseNavigator;

public interface NotificationsActivityNavigator extends BaseNavigator {
    void onNotificationDeletedSuccessfully(NotificationModel notificationModel);
    void notificationFetched();
    void onClearNotifications();
}
