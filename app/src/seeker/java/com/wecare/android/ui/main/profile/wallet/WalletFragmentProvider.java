package com.wecare.android.ui.main.profile.wallet;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WalletFragmentProvider {
    @ContributesAndroidInjector
    abstract WalletFragment provideWalletFragmentFactory();
}
