
package com.wecare.android.ui.create_order;

import com.wecare.android.ui.base.BaseViewPagerAdapter;
import dagger.Module;
import dagger.Provides;

@Module
public class CreateOrderActivityModule {

//    @Provides
//    BaseTabsViewPagerAdapter provideBaseTabsPagerAdapter(CreateOrderActivity activity) {
//        return new BaseTabsViewPagerAdapter(activity, activity.getSupportFragmentManager());
//    }

    @Provides
    BaseViewPagerAdapter provideViewPagerAdapter(CreateOrderActivity activity) {
        return new BaseViewPagerAdapter(activity, activity.getSupportFragmentManager());
    }

}
