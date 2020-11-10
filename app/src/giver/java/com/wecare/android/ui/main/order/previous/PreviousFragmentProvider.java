package com.wecare.android.ui.main.order.previous;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PreviousFragmentProvider {

    @ContributesAndroidInjector(modules = PreviousFragmentModule.class)
    abstract PreviousFragment providePreviousFragmentFactory();

}
