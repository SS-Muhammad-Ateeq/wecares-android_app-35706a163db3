package com.wecare.android.ui.main.guest;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class GuestFragmentProvider {

    @ContributesAndroidInjector
    abstract GuestFragment provideGuestFragmentFactory();

}
