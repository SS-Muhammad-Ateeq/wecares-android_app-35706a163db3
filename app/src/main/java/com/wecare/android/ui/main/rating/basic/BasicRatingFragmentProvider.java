package com.wecare.android.ui.main.rating.basic;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BasicRatingFragmentProvider {
    @ContributesAndroidInjector
    abstract BasicRatingFragment provideBasicRatingFragmentFactory();
}
