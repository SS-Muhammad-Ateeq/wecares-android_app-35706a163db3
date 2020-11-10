package com.wecare.android.ui.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.annotation.Subscribe;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityIntroBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.intro.fragments.first.FirstIntroFragment;
import com.wecare.android.ui.intro.fragments.forth.FourthIntroFragment;
import com.wecare.android.ui.intro.fragments.second.SecondIntroFragment;
import com.wecare.android.ui.intro.fragments.third.ThirdIntroFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.events.IntroStepperEvent;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class IntroActivity extends BaseActivity<ActivityIntroBinding, IntroActivityViewModel> implements IntroNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ViewModelProviderFactory factory;
    private IntroActivityViewModel viewModel;

    ActivityIntroBinding binding;


    @Override
    public IntroActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(IntroActivityViewModel.class);
        return viewModel;    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_intro;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, IntroActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        setUp();

    }

    private void setUp() {
        viewModel.setNavigator(this);
        NYBus.get().register(this);
        doStep(new IntroStepperEvent(AppConstants.INTRO_FIRST));
        viewModel.getDataManager().setIntroViewed(true);

    }

    @Subscribe
    public void onEvent(IntroStepperEvent event) {
        doStep(event);
    }


    public void doStep(IntroStepperEvent event) {
        Bundle args = new Bundle();
        switch (event.step) {
            case AppConstants.INTRO_FIRST:
                replaceFragment(FirstIntroFragment.newInstance(args), false,true,true, R.id.content);
                break;
            case AppConstants.INTRO_SECOND:
                replaceFragment(SecondIntroFragment.newInstance(args), false,true,true, R.id.content);
                break;
            case AppConstants.INTRO_THIRD:
                replaceFragment(ThirdIntroFragment.newInstance(args), false,true,true, R.id.content);
                break;
            case AppConstants.INTRO_FOURTH:
                replaceFragment(FourthIntroFragment.newInstance(args), false, true,true, R.id.content);
                break;
            case AppConstants.EXIT_STEPPER:
                openLoginActivity(this);
                break;
            case AppConstants.BACK_STEPPER:
                getSupportFragmentManager().popBackStack();
                break;
        }

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
    public void backClicked() {

    }

    @Override
    public void skipClicked() {

    }
}
