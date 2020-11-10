package com.wecare.android.ui.intro.fragments.second;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SecondIntroFragmentProvider {
    @ContributesAndroidInjector
    abstract SecondIntroFragment provideSecondIntroFragmentFactory();
}
