package com.wecare.android.ui.main.order.current;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CurrentFragmentProvider {

    @ContributesAndroidInjector(modules = CurrentFragmentModule.class)
    abstract CurrentFragment provideCurrentFragmentFactory();

}
