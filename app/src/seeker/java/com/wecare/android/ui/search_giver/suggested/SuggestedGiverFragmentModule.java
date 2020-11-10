package com.wecare.android.ui.search_giver.suggested;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import com.wecare.android.ui.search_giver.suggested.SuggestedGiverFragment;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class SuggestedGiverFragmentModule {

    @Provides
    SuggestedGiverAdapter provideSuggestedGiverAdapter() {
        return new SuggestedGiverAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(SuggestedGiverFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
