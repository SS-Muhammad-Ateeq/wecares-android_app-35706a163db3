package com.wecare.android.ui.search_giver.blocked;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class BlockedGiverFragmentModule {

    @Provides
    SuggestedGiverAdapter provideSuggestedGiverAdapter() {
        return new SuggestedGiverAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(BlockedGiverFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
