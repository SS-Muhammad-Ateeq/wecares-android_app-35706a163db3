package com.wecare.android.ui.notification;

import com.wecare.android.data.model.api.models.NotificationModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class NotificationsActivityModule {

    @Provides
    NotificationsAdapter provideNotificationsAdapter() {
        return new NotificationsAdapter(new ArrayList<NotificationModel>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(NotificationsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
