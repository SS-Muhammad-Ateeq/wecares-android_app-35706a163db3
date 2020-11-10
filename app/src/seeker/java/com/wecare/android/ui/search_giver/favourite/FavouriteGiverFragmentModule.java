package com.wecare.android.ui.search_giver.favourite;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import com.wecare.android.ui.search_giver.search.SearchGiverFragment;
import dagger.Module;
import dagger.Provides;

import java.util.ArrayList;

@Module
public class FavouriteGiverFragmentModule {

    @Provides
    SuggestedGiverAdapter provideSuggestedGiverAdapter() {
        return new SuggestedGiverAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(FavouriteGiverFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
