package com.wecare.android.ui.auth.registration.verification;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegistrationVerificationCodeFragmentProvider {
    @ContributesAndroidInjector
    abstract RegistrationVerificationCodeFragment provideVerificationCodeFragmentFactory();
}
