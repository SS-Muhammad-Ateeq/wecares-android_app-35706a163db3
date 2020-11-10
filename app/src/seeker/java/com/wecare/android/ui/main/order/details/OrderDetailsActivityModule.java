package com.wecare.android.ui.main.order.details;

import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.ui.order_info.InformationAttachmentAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class OrderDetailsActivityModule {


    @Provides
    InformationAttachmentAdapter provideNotificationsAdapter() {
        return new InformationAttachmentAdapter(new ArrayList<InformationAttachmentObj>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderDetailsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}