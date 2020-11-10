
package com.wecare.android.ui.main;

import com.wecare.android.ui.base.BaseTabsViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    BaseTabsViewPagerAdapter provideFeedPagerAdapter(MainActivity activity) {
        return new BaseTabsViewPagerAdapter(activity, activity.getSupportFragmentManager());
    }

}
