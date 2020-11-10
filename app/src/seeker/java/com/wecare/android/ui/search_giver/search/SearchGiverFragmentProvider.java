package com.wecare.android.ui.search_giver.search;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SearchGiverFragmentProvider {

    @ContributesAndroidInjector(modules = SearchGiverFragmentModule.class)
    abstract SearchGiverFragment provideSearchGiverFragmentFactory();

}
