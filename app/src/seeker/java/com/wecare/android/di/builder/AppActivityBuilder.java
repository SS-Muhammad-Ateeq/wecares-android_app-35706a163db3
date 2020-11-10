package com.wecare.android.di.builder;

import com.wecare.android.ui.auth.forgetpassword.ForgetPasswordActivity;
import com.wecare.android.ui.auth.forgetpassword.verfication.ForgetPasswordVerificationActivity;
import com.wecare.android.ui.auth.registration.RegistrationActivity;
import com.wecare.android.ui.auth.registration.info.RegistrationInfoFragmentProvider;
import com.wecare.android.ui.auth.registration.verification.RegistrationVerificationCodeFragmentProvider;
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.create_order.CreateOrderActivityModule;
import com.wecare.android.ui.create_order.done.DoneFragmentProvider;
import com.wecare.android.ui.create_order.info.InformationFragmentProvider;
import com.wecare.android.ui.create_order.location.LocationActivity;
import com.wecare.android.ui.create_order.location.LocationFragmentProvider;
import com.wecare.android.ui.create_order.location.add.AddEditLocationActivity;
import com.wecare.android.ui.create_order.relative.RelativeProfileActivity;
import com.wecare.android.ui.create_order.relative.RelativeProfileActivityModule;
import com.wecare.android.ui.create_order.relative.add.AddEditRelativeProfileActivity;
import com.wecare.android.ui.create_order.schedule.ScheduleFragmentProvider;
import com.wecare.android.ui.create_order.services.OrderServicesFragmentProvider;
import com.wecare.android.ui.intro.IntroActivity;
import com.wecare.android.ui.intro.fragments.first.FirstIntroFragmentProvider;
import com.wecare.android.ui.intro.fragments.forth.ForthIntroFragmentProvider;
import com.wecare.android.ui.intro.fragments.second.SecondIntroFragmentProvider;
import com.wecare.android.ui.intro.fragments.third.ThirdIntroFragmentProvider;
import com.wecare.android.ui.login.LoginActivity;
import com.wecare.android.ui.main.MainActivity;
import com.wecare.android.ui.main.MainActivityModule;
import com.wecare.android.ui.main.guest.GuestFragmentProvider;
import com.wecare.android.ui.main.home.sub.SubServicesActivity;
import com.wecare.android.ui.main.home.sub.SubServicesActivityModule;
import com.wecare.android.ui.main.home.sub.details.SubDetailsFragmentProvider;
import com.wecare.android.ui.main.order.OrderFragmentProvider;
import com.wecare.android.ui.main.order.current.CurrentFragmentProvider;
import com.wecare.android.ui.main.order.previous.PreviousFragmentProvider;
import com.wecare.android.ui.main.order.special.SpecialRequestActivity;
import com.wecare.android.ui.main.profile.ProfileMainFragmentProvider;
import com.wecare.android.ui.main.profile.userProfile.UserProfileFragmentProvider;
import com.wecare.android.ui.main.profile.userProfile.paymentmethod.PaymentMethodActivity;
import com.wecare.android.ui.main.profile.userProfile.personalInfo.EditProfileInformationActivity;
import com.wecare.android.ui.main.profile.wallet.WalletFragmentProvider;
import com.wecare.android.ui.main.settings.SettingsFragmentProvider;
import com.wecare.android.ui.profile.CaregiverShowProfileActivity;
import com.wecare.android.ui.search_giver.SearchGiverActivity;
import com.wecare.android.ui.search_giver.SearchGiverActivityModule;
import com.wecare.android.ui.search_giver.blocked.BlockedGiverFragmentProvider;
import com.wecare.android.ui.search_giver.favourite.FavouriteGiverFragmentProvider;
import com.wecare.android.ui.search_giver.filter.FilterGiverActivity;
import com.wecare.android.ui.search_giver.search.SearchGiverFragmentProvider;
import com.wecare.android.ui.search_giver.suggested.SuggestedGiverFragmentProvider;
import com.wecare.android.ui.splash.SplashActivity;
import com.wecare.android.ui.sub.ServicesFragmentProvider;
import com.wecare.android.ui.webview.WebViewProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class AppActivityBuilder extends ActivityBuilder {
    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class,
            ServicesFragmentProvider.class, OrderFragmentProvider.class,
            PreviousFragmentProvider.class, CurrentFragmentProvider.class,
            ProfileMainFragmentProvider.class, SettingsFragmentProvider.class,
            UserProfileFragmentProvider.class, WalletFragmentProvider.class, GuestFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {SubServicesActivityModule.class, SubDetailsFragmentProvider.class})
    abstract SubServicesActivity bindSubServicesActivity();

    @ContributesAndroidInjector(modules = {FirstIntroFragmentProvider.class,
            SecondIntroFragmentProvider.class,
            ThirdIntroFragmentProvider.class,
            ForthIntroFragmentProvider.class})
    abstract IntroActivity bindIntroActivity();

    @ContributesAndroidInjector
    abstract ForgetPasswordActivity bindForgetPasswordActivity();

    @ContributesAndroidInjector(modules = {RelativeProfileActivityModule.class})
    abstract RelativeProfileActivity bindRelativeProfileActivity();

    @ContributesAndroidInjector
    abstract AddEditRelativeProfileActivity bindAddRelativeProfileActivity();

    @ContributesAndroidInjector
    abstract AddEditLocationActivity bindAddLocationActivity();

    @ContributesAndroidInjector
    abstract ForgetPasswordVerificationActivity bindForgetPasswordVerificationActivity();

    @ContributesAndroidInjector
    abstract EditProfileInformationActivity bindEditProfileInformationActivity();

    @ContributesAndroidInjector
    abstract FilterGiverActivity bindFilterGiverActivity();

    @ContributesAndroidInjector
    abstract SpecialRequestActivity bindSpecialRequestActivity();

    @ContributesAndroidInjector(modules = {SearchGiverActivityModule.class, SuggestedGiverFragmentProvider.class,
            SearchGiverFragmentProvider.class, FavouriteGiverFragmentProvider.class, BlockedGiverFragmentProvider.class})
    abstract SearchGiverActivity bindSearchSeekerActivity();

    @ContributesAndroidInjector(modules = {CreateOrderActivityModule.class, ScheduleFragmentProvider.class,
            InformationFragmentProvider.class, LocationFragmentProvider.class,
            OrderServicesFragmentProvider.class, DoneFragmentProvider.class})
    abstract CreateOrderActivity bindCreateOrderActivity();


    @ContributesAndroidInjector(modules = {LocationFragmentProvider.class})
    abstract LocationActivity bindLocationActivity();

    @ContributesAndroidInjector(modules = {RegistrationInfoFragmentProvider.class,
            RegistrationVerificationCodeFragmentProvider.class, WebViewProvider.class})
    abstract RegistrationActivity bindForgetRegistrationActivity();

    @ContributesAndroidInjector
    abstract CaregiverShowProfileActivity bindCaregiverShowProfileActivity();

    @ContributesAndroidInjector
    abstract PaymentMethodActivity bindPaymentMethodActivity();
}
