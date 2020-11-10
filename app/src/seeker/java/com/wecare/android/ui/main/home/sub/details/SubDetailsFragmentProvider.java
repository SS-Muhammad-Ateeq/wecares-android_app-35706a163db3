package com.wecare.android.ui.main.home.sub.details;

import com.wecare.android.ui.main.settings.SettingsFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SubDetailsFragmentProvider {

    @ContributesAndroidInjector
    abstract SubDetailsFragment provideSubDetailsFragmentFactory();

}
