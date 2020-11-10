package com.wecare.android.ui.main.profile;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProfileMainFragmentProvider {

    @ContributesAndroidInjector
    abstract ProfileMainFragment provideProfileFragmentFactory();

}
