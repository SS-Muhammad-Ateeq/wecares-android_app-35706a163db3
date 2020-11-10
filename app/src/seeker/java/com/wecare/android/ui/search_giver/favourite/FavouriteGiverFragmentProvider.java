package com.wecare.android.ui.search_giver.favourite;

import com.wecare.android.ui.search_giver.suggested.SuggestedGiverFragmentModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavouriteGiverFragmentProvider {

    @ContributesAndroidInjector(modules = FavouriteGiverFragmentModule.class)
    abstract FavouriteGiverFragment provideFavouriteGiverFragmentFactory();

}
