package com.wecare.android.ui.intro.fragments.third;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ThirdIntroFragmentProvider {
    @ContributesAndroidInjector
    abstract ThirdIntroFragment provideThirdIntroFragmentProviderFactory();

}
