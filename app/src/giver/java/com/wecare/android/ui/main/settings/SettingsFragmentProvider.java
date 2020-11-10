package com.wecare.android.ui.main.settings;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsFragmentProvider {

    @ContributesAndroidInjector
    abstract SettingsFragment provideSettingsFragmentFactory();

}
