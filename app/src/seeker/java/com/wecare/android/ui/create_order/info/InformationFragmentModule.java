package com.wecare.android.ui.create_order.info;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.ui.order_info.InformationAttachmentAdapter;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class InformationFragmentModule {

    @Provides
    InformationAttachmentAdapter provideInformationAttachmentAdapter() {
        return new InformationAttachmentAdapter(new ArrayList<InformationAttachmentObj>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(InformationFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
