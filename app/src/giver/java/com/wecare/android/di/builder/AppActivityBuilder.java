package com.wecare.android.di.builder;

import com.wecare.android.ui.auth.forgetpassword.ForgetPasswordActivity;
import com.wecare.android.ui.auth.forgetpassword.verfication.ForgetPasswordVerificationActivity;
import com.wecare.android.ui.auth.registration.RegistrationActivity;
import com.wecare.android.ui.auth.registration.info.RegistrationInfoFragmentProvider;
import com.wecare.android.ui.auth.registration.verification.RegistrationVerificationCodeFragmentProvider;
import com.wecare.android.ui.intro.IntroActivity;
import com.wecare.android.ui.intro.fragments.first.FirstIntroFragmentProvider;
import com.wecare.android.ui.intro.fragments.forth.ForthIntroFragmentProvider;
import com.wecare.android.ui.intro.fragments.second.SecondIntroFragmentProvider;
import com.wecare.android.ui.intro.fragments.third.ThirdIntroFragmentProvider;
import com.wecare.android.ui.login.LoginActivity;
import com.wecare.android.ui.main.MainActivity;
import com.wecare.android.ui.main.MainActivityModule;
import com.wecare.android.ui.main.home.sub.SubServicesActivity;
import com.wecare.android.ui.main.home.sub.SubServicesActivityModule;
import com.wecare.android.ui.main.home.sub.details.SubDetailsFragmentProvider;
import com.wecare.android.ui.main.order.FinishOrderActivity;
import com.wecare.android.ui.main.order.OrderFragmentProvider;
import com.wecare.android.ui.main.order.current.CurrentFragmentProvider;
import com.wecare.android.ui.main.order.finishorder.FinishOrderActivityModule;
import com.wecare.android.ui.main.order.previous.PreviousFragmentProvider;
import com.wecare.android.ui.main.order.scheduled.ScheduleSummaryActivity;
import com.wecare.android.ui.main.order.scheduled.ScheduleSummaryActivityModule;
import com.wecare.android.ui.main.profile.ProfileMainFragmentProvider;
import com.wecare.android.ui.main.profile.userProfile.UserProfileFragmentProvider;
import com.wecare.android.ui.main.profile.userProfile.bankinfo.BankInfoActivity;
import com.wecare.android.ui.main.profile.userProfile.educationcertificates.EducationCertificatesActivity;
import com.wecare.android.ui.main.profile.userProfile.personalInfo.CaregiverPersonalInformationActivity;
import com.wecare.android.ui.main.profile.userProfile.schduler.CaregiverServicesSchedulerActivity;
import com.wecare.android.ui.main.profile.userProfile.servicearea.CaregiverServiceAreaActivity;
import com.wecare.android.ui.main.profile.userProfile.services.UserServicesActivity;
import com.wecare.android.ui.main.profile.userProfile.services.UserServicesActivityModule;
import com.wecare.android.ui.main.profile.userProfile.services.selection.UserServicesSelectionActivity;
import com.wecare.android.ui.main.profile.userProfile.services.selection.UserServicesSelectionActivityModule;
import com.wecare.android.ui.main.profile.wallet.WalletFragmentProvider;
import com.wecare.android.ui.main.settings.SettingsFragmentProvider;
import com.wecare.android.ui.profile.UserShowProfileActivity;
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
            ProfileMainFragmentProvider.class, SettingsFragmentProvider.class, UserProfileFragmentProvider.class, WalletFragmentProvider.class})
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

    @ContributesAndroidInjector
    abstract ForgetPasswordVerificationActivity bindForgetPasswordVerificationActivity();

    @ContributesAndroidInjector(modules = {RegistrationInfoFragmentProvider.class,
            RegistrationVerificationCodeFragmentProvider.class,
            WebViewProvider.class})
    abstract RegistrationActivity bindForgetRegistrationActivity();

    @ContributesAndroidInjector
    abstract CaregiverPersonalInformationActivity bindCaregiverPersonalInformationActivity();

    @ContributesAndroidInjector
    abstract CaregiverServicesSchedulerActivity bindCaregiverServicesSchedulerActivity();

    @ContributesAndroidInjector
    abstract EducationCertificatesActivity bindEducationCertificatesActivity();

    @ContributesAndroidInjector(modules = UserServicesActivityModule.class)
    abstract UserServicesActivity bindUserServicesActivity();

    @ContributesAndroidInjector(modules = UserServicesSelectionActivityModule.class)
    abstract UserServicesSelectionActivity bindUserServicesSelectionActivity();

    @ContributesAndroidInjector
    abstract CaregiverServiceAreaActivity bindCaregiverServiceAreaActivity();

    @ContributesAndroidInjector
    abstract BankInfoActivity bindBankInfoActivity();

    @ContributesAndroidInjector(modules = {ScheduleSummaryActivityModule.class})
    abstract ScheduleSummaryActivity bindScheduleSummaryActivity();


    @ContributesAndroidInjector
    abstract UserShowProfileActivity bindUserShowProfileActivity();

    @ContributesAndroidInjector(modules = FinishOrderActivityModule.class)
    abstract FinishOrderActivity bindFinishOrderActivity();

}
