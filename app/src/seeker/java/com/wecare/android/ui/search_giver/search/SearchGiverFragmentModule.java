package com.wecare.android.ui.search_giver.search;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class SearchGiverFragmentModule {

    @Provides
    SuggestedGiverAdapter provideSuggestedGiverAdapter() {
        return new SuggestedGiverAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(SearchGiverFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
