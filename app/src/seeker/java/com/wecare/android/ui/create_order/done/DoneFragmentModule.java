package com.wecare.android.ui.create_order.done;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.order_info.InformationAttachmentAdapter;
import com.wecare.android.ui.create_order.location.LocationAdapter;
import com.wecare.android.ui.create_order.services.sub_duration.DurationSubServicesAdapter;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class DoneFragmentModule {

    @Provides
    DurationSubServicesAdapter provideDurationSubServicesAdapter() {
        return new DurationSubServicesAdapter(new ArrayList<SubServiceResponse>());
    }

    @Provides
    InformationAttachmentAdapter provideInformationAttachmentAdapter() {
        return new InformationAttachmentAdapter(new ArrayList<InformationAttachmentObj>());
    }

    @Provides
    LocationAdapter provideLocationAdapter() {
        return new LocationAdapter(new ArrayList<>());
    }

    @Provides
    SuggestedGiverAdapter provideSuggestedGiverAdapter() {
        return new SuggestedGiverAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(DoneFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
