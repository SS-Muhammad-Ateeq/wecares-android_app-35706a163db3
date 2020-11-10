package com.wecare.android.ui.main.rating.overall;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OverallRatingFragmentProvider {
    @ContributesAndroidInjector
    abstract OverallRatingFragment provideOverallRatingFragmentFactory();
}
