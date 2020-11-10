package com.wecare.android.ui.search_giver;

import com.wecare.android.ui.base.BaseTabsViewPagerAdapter;
import dagger.Module;
import dagger.Provides;

@Module
public class SearchGiverActivityModule {

    @Provides
    BaseTabsViewPagerAdapter provideSearchSeekerPagerAdapter(SearchGiverActivity activity) {
        return new BaseTabsViewPagerAdapter(activity, activity.getSupportFragmentManager());
    }
}
