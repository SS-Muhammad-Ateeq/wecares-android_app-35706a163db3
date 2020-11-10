package com.wecare.android.ui.auth.registration.info;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegistrationInfoFragmentProvider {
    @ContributesAndroidInjector
    abstract RegistrationInfoFragment provideRegistrationInfoFragmentFactory();
}
