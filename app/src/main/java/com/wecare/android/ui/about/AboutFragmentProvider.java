package com.wecare.android.ui.about;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AboutFragmentProvider {

    @ContributesAndroidInjector(modules = AboutFragmentModule.class)
    abstract AboutFragment provideAboutFragmentFactory();

}
