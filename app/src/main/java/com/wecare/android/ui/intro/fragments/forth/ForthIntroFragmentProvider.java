package com.wecare.android.ui.intro.fragments.forth;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ForthIntroFragmentProvider {
    @ContributesAndroidInjector
    abstract FourthIntroFragment provideFourthIntroFragmentFactory();
}
