package com.wecare.android.ui.notification;

import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.utils.DateUtils;

import androidx.databinding.ObservableField;


public class NotificationsItemViewModel {
    NotificationModel notificationModel;
    NotificationItemViewModelListener listener;
    public ObservableField<String> notificationTitle;
    public ObservableField<String> notificationDescription;
    public ObservableField<String> notificationTime;

    public NotificationsItemViewModel(NotificationModel notificationModel, NotificationItemViewModelListener listener) {
        this.notificationModel = notificationModel;
        this.listener = listener;

        notificationTitle = new ObservableField<>(notificationModel.getTitleEn());
        notificationDescription = new ObservableField<>(notificationModel.getMessageEn());
        notificationTime = new ObservableField<>(DateUtils.getTimeAgo(DateUtils.convertDateToMillis(notificationModel.getCreatedAt())));

    }

    public void ooDeleteClicked(){
        listener.onNotificationDeleteClicked(notificationModel);
    }

    public interface NotificationItemViewModelListener {
        void onNotificationDeleteClicked(NotificationModel notificationModel);
    }
}
