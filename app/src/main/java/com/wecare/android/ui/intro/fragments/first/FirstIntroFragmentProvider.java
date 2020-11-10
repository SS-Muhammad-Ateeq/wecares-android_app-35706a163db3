package com.wecare.android.ui.intro.fragments.first;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FirstIntroFragmentProvider {
    @ContributesAndroidInjector
    abstract FirstIntroFragment provideFirstIntroFragmentFactory();

}
