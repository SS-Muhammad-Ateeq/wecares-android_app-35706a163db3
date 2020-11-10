package com.wecare.android.ui.create_order.done;

import com.wecare.android.ui.create_order.schedule.ScheduleFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DoneFragmentProvider {

    @ContributesAndroidInjector(modules = DoneFragmentModule.class)
    abstract DoneFragment provideDoneFragmentFactory();

}
