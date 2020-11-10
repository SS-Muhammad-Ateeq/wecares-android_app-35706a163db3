package com.wecare.android.ui.auth.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.annotation.Subscribe;
import com.wecare.android.BR;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityRegistrationBinding;
import com.wecare.android.ui.auth.registration.info.RegistrationInfoFragment;
import com.wecare.android.ui.auth.registration.verification.RegistrationVerificationCodeFragment;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.webview.WebViewFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.ServerKeys;
import com.wecare.android.utils.WeCareUtils;
import com.wecare.android.utils.events.RegistrationStepperEvent;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationViewModel> implements HasSupportFragmentInjector, RegistrationNavigator {
    @Inject
    ViewModelProviderFactory factory;
    private RegistrationViewModel viewModel;

    int regType;
    String termsURL;


    private ActivityRegistrationBinding mActivityLoginBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public RegistrationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RegistrationViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        regType = getIntent().getIntExtra(AppConstants.KEY_REGISTRATION_TYPE, 0);
        viewModel.setNavigator(this);
        setSupportActionBar(mActivityLoginBinding.included.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.create_new_account));
        NYBus.get().register(this);
        if (getIntent().hasExtra(AppConstants.ARGS_VERIFY_USER))
            doStep(AppConstants.REGISTRATION_SECOND_STEP);
        else
            doStep(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.isSeekerFlavor)
            termsURL = WeCareUtils.getPageURL(AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? AppConstants.SEEKER_TERMS_SERVICE_ENGLISH : AppConstants.SEEKER_TERMS_SERVICE_ARABIC, viewModel.getDataManager().getPages());
        else
            termsURL = WeCareUtils.getPageURL(AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? AppConstants.GIVER_TERMS_SERVICE_ENGLISH : AppConstants.GIVER_TERMS_SERVICE_ARABIC, viewModel.getDataManager().getPages());
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    public void doStep(int index) {
        Bundle args = getIntent().getExtras();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(AppConstants.KEY_REGISTRATION_TYPE, regType);

        switch (index) {
            case AppConstants.REGISTRATION_FIRST_STEP:
                replaceFragment(RegistrationInfoFragment.newInstance(args), false, R.id.content, true);
                mActivityLoginBinding.included.toolbar.setTitle(getString(R.string.create_new_account));
                break;
            case AppConstants.REGISTRATION_SECOND_STEP:
                if (getIntent().hasExtra(AppConstants.ARGS_VERIFY_USER))
                    args.putString(AppConstants.ARGS_VERIFY_USER, AppConstants.ARGS_VERIFY_USER);
                replaceFragment(RegistrationVerificationCodeFragment.newInstance(args), true, true, true, R.id.content);
                mActivityLoginBinding.included.toolbar.setTitle(getString(R.string.activation_code));

                break;
            case AppConstants.REGISTRATION_TERMS_CONDITIONS:
                args.putInt(AppConstants.ARGS_KEY_TYPE, WebViewFragment.TYPE_URI);
                args.putString(AppConstants.ARGS_KEY_TITLE, getString(R.string.terms_and_conditions));
                args.putString(AppConstants.ARGS_KEY_HTML_PAGG_URI, termsURL);
                args.putString(AppConstants.ARGS_KEY_MUST_ACCEPT,AppConstants.PHP_TRUE);
                replaceFragment(WebViewFragment.newInstance(args), true, true, false, R.id.content);
                break;

            case AppConstants.EXIT_STEPPER:
                openLoginActivity(this);
                break;
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Subscribe
    public void onEvent(RegistrationStepperEvent event) {
        doStep(event.registrationStep);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NYBus.get().unregister(this);
    }

    @Override
    public void nextClicked() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().hasExtra(AppConstants.ARGS_VERIFY_USER))
        finish();
    }
}


