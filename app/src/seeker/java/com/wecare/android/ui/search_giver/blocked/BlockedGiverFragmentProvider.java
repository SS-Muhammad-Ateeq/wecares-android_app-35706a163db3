package com.wecare.android.ui.search_giver.blocked;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BlockedGiverFragmentProvider {

    @ContributesAndroidInjector(modules = BlockedGiverFragmentModule.class)
    abstract BlockedGiverFragment provideBlockedGiverFragmentFactory();

}
