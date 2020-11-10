package com.wecare.android.ui.search_giver.suggested;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SuggestedGiverFragmentProvider {

    @ContributesAndroidInjector(modules = SuggestedGiverFragmentModule.class)
    abstract SuggestedGiverFragment provideSuggestedGiverFragmentFactory();

}
