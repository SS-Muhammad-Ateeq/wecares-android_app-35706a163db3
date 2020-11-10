//package com.wecare.android;
//
//import com.wecare.android.data.DataManager;
//import com.wecare.android.ui.auth.forgetpassword.ForgetPasswordViewModel;
//import com.wecare.android.ui.auth.forgetpassword.verfication.ForgetPasswordVerificationViewModel;
//import com.wecare.android.ui.auth.registration.RegistrationViewModel;
//import com.wecare.android.ui.auth.registration.info.RegistrationInfoViewModel;
//import com.wecare.android.ui.auth.registration.verification.RegistrationVerificationCodeViewModel;
//import com.wecare.android.ui.intro.IntroActivityViewModel;
//import com.wecare.android.ui.intro.fragments.first.FirstIntroFragmentViewModel;
//import com.wecare.android.ui.intro.fragments.forth.ForthIntroFragmentViewModel;
//import com.wecare.android.ui.intro.fragments.second.SecondIntroFragmentViewModel;
//import com.wecare.android.ui.intro.fragments.third.ThirdIntroFragmentViewModel;
//import com.wecare.android.ui.login.LoginViewModel;
//import com.wecare.android.ui.main.MainViewModel;
//import com.wecare.android.ui.sub.ServicesViewModel;
//import com.wecare.android.ui.main.home.sub.SubServicesViewModel;
//import com.wecare.android.ui.main.home.sub.details.SubDetailsViewModel;
//import com.wecare.android.ui.main.order.OrderViewModel;
//import com.wecare.android.ui.main.order.current.CurrentViewModel;
//import com.wecare.android.ui.main.order.previous.PreviousViewModel;
//import com.wecare.android.ui.main.profile.ProfileMainViewModel;
//import com.wecare.android.ui.main.profile.userProfile.UserProfileViewModel;
//import com.wecare.android.ui.main.profile.userProfile.personalInfo.CaregiverPersonalInformationViewModel;
//import com.wecare.android.ui.main.settings.SettingsViewModel;
//import com.wecare.android.ui.splash.SplashViewModel;
//import com.wecare.android.ui.webview.WebViewModel;
//import com.wecare.android.utils.rx.SchedulerProvider;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
///**
// * <p>
// * A provider factory that persists ViewModels{@link ViewModel}.
// * Used if the viewmodel has a parameterized constructor.
// */
//@Singleton
//public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {
//
//    private final DataManager dataManager;
//    private final SchedulerProvider schedulerProvider;
//
//    @Inject
//    public ViewModelProviderFactory(DataManager dataManager, SchedulerProvider schedulerProvider) {
//        this.dataManager = dataManager;
//        this.schedulerProvider = schedulerProvider;
//    }
//
//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
//            //noinspection unchecked
//            return (T) new SplashViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
//            //noinspection unchecked
//            return (T) new LoginViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(IntroActivityViewModel.class)) {
//            //noinspection unchecked
//            return (T) new IntroActivityViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(FirstIntroFragmentViewModel.class)) {
//            //noinspection unchecked
//            return (T) new FirstIntroFragmentViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(SecondIntroFragmentViewModel.class)) {
//            //noinspection unchecked
//            return (T) new SecondIntroFragmentViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(ThirdIntroFragmentViewModel.class)) {
//            //noinspection unchecked
//            return (T) new ThirdIntroFragmentViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(ForthIntroFragmentViewModel.class)) {
//            //noinspection unchecked
//            return (T) new ForthIntroFragmentViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)) {
//            //noinspection unchecked
//            return (T) new ForgetPasswordViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(ForgetPasswordVerificationViewModel.class)) {
//            //noinspection unchecked
//            return (T) new ForgetPasswordVerificationViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(MainViewModel.class)) {
//            //noinspection unchecked
//            return (T) new MainViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(ServicesViewModel.class)) {
//            //noinspection unchecked
//            return (T) new ServicesViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(OrderViewModel.class)) {
//            //noinspection unchecked
//            return (T) new OrderViewModel(dataManager,schedulerProvider);
//        }else if (modelClass.isAssignableFrom(CurrentViewModel.class)) {
//            //noinspection unchecked
//            return (T) new CurrentViewModel(dataManager,schedulerProvider);
//        }else if (modelClass.isAssignableFrom(PreviousViewModel.class)) {
//            //noinspection unchecked
//            return (T) new PreviousViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(ProfileMainViewModel.class)) {
//            //noinspection unchecked
//            return (T) new ProfileMainViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
//            //noinspection unchecked
//            return (T) new SettingsViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(SubServicesViewModel.class)) {
//            //noinspection unchecked
//            return (T) new SubServicesViewModel(dataManager,schedulerProvider);
//        } else if (modelClass.isAssignableFrom(SubDetailsViewModel.class)) {
//            //noinspection unchecked
//            return (T) new SubDetailsViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
//            //noinspection unchecked
//            return (T) new RegistrationViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(RegistrationInfoViewModel.class)) {
//            //noinspection unchecked
//            return (T) new RegistrationInfoViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(RegistrationVerificationCodeViewModel.class)) {
//            //noinspection unchecked
//            return (T) new RegistrationVerificationCodeViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(WebViewModel.class)) {
//            //noinspection unchecked
//            return (T) new WebViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(UserProfileViewModel.class)) {
//            //noinspection unchecked
//            return (T) new UserProfileViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(CaregiverPersonalInformationViewModel.class)) {
//            //noinspection unchecked
//            return (T) new CaregiverPersonalInformationViewModel(dataManager,schedulerProvider);
//        }
//
//        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
//    }
//
//}
