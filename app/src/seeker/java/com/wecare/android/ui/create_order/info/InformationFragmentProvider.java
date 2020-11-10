package com.wecare.android.ui.create_order.info;

import com.wecare.android.ui.create_order.schedule.ScheduleFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InformationFragmentProvider {

    @ContributesAndroidInjector(modules = InformationFragmentModule.class)
    abstract InformationFragment provideInformationFragmentFactory();

}
