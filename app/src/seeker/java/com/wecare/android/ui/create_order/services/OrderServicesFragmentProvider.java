package com.wecare.android.ui.create_order.services;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OrderServicesFragmentProvider {

    @ContributesAndroidInjector(modules = OrderServicesFragmentModule.class)
    abstract OrderServicesFragment provideServicesFragmentFactory();

}
