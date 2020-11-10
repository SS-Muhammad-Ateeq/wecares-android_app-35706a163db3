package com.wecare.android.ui.main.order;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderFragmentModule {

    @Provides
    OrderPagerAdapter provideOrderPagerAdapter(OrderFragment fragment) {
        return new OrderPagerAdapter(fragment.getChildFragmentManager(), fragment.getBaseActivity());
    }

}
