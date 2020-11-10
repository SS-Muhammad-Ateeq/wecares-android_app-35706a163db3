package com.wecare.android.ui.main.profile.userProfile;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserProfileFragmentProvider {
    @ContributesAndroidInjector
    abstract UserProfileFragment provideUserProfileFragmentFactory();
}
