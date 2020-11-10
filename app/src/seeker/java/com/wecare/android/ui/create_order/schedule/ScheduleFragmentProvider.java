package com.wecare.android.ui.create_order.schedule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ScheduleFragmentProvider {

    @ContributesAndroidInjector
    abstract ScheduleFragment provideScheduleFragmentFactory();

}
