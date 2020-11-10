package com.wecare.android.ui.webview;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;



@Module
public abstract class WebViewProvider {
    @ContributesAndroidInjector
    abstract WebViewFragment provideWebViewFragmentFactory();
}
