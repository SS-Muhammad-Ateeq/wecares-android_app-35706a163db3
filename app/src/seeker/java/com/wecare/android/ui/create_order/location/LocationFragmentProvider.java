package com.wecare.android.ui.create_order.location;

import com.wecare.android.ui.create_order.schedule.ScheduleFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LocationFragmentProvider {

    @ContributesAndroidInjector(modules = LocationFragmentModule.class)
    abstract LocationFragment provideLocationFragmentFactory();

}
