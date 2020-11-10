package com.wecare.android.di.builder;

import com.wecare.android.ui.main.order.details.OrderDetailsActivity;
import com.wecare.android.ui.main.order.details.OrderDetailsActivityModule;
import com.wecare.android.ui.main.order.sub.OrderSubServicesActivity;
import com.wecare.android.ui.main.order.sub.OrderSubServicesActivityModule;
import com.wecare.android.ui.main.profile.transactions.TransactionsHistoryActivity;
import com.wecare.android.ui.main.profile.transactions.TransactionsHistoryActivityModule;
import com.wecare.android.ui.main.rating.RatingMainActivity;
import com.wecare.android.ui.main.rating.basic.BasicRatingFragmentProvider;
import com.wecare.android.ui.main.rating.overall.OverallRatingFragmentProvider;
import com.wecare.android.ui.main.settings.ChangePasswordActivity;
import com.wecare.android.ui.notification.NotificationsActivity;
import com.wecare.android.ui.notification.NotificationsActivityModule;
import com.wecare.android.ui.webview.activity.WebViewActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {OrderSubServicesActivityModule.class})
    abstract OrderSubServicesActivity bindOrderSubServicesActivity();

    @ContributesAndroidInjector(modules = {BasicRatingFragmentProvider.class,
            OverallRatingFragmentProvider.class})
    abstract RatingMainActivity bindRatingMainActivity();

    @ContributesAndroidInjector(modules = {NotificationsActivityModule.class})
    abstract NotificationsActivity bindNotificationsActivity();

    @ContributesAndroidInjector
    abstract ChangePasswordActivity bindChangePasswordActivity();

    @ContributesAndroidInjector(modules = {OrderDetailsActivityModule.class})
    abstract OrderDetailsActivity bindOrderDetailsActivity();

    @ContributesAndroidInjector
    abstract WebViewActivity bindWebViewActivity();

    @ContributesAndroidInjector(modules = {TransactionsHistoryActivityModule.class})
    abstract TransactionsHistoryActivity bindTransactionsHistoryActivity();


}
