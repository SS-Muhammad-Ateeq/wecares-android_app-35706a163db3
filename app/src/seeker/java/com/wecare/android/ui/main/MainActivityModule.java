
package com.wecare.android.ui.main;

import com.wecare.android.ui.base.BaseTabsViewPagerAdapter;

import com.wecare.android.ui.base.BaseViewPagerAdapter;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

//    @Provides
//    BaseTabsViewPagerAdapter provideBaseTabsPagerAdapter(MainActivity activity) {
//        return new BaseTabsViewPagerAdapter(activity, activity.getSupportFragmentManager());
//    }

    @Provides
    BaseViewPagerAdapter provideViewPagerAdapter(MainActivity activity) {
        return new BaseViewPagerAdapter(activity, activity.getSupportFragmentManager());
    }

}
