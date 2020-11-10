package com.wecare.android.ui.sub;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServicesFragmentProvider {

    @ContributesAndroidInjector(modules = ServicesFragmentModule.class)
    abstract ServicesFragment provideServicesFragmentFactory();

}
