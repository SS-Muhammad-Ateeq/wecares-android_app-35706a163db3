package com.wecare.android.ui.intro.fragments.second;

import android.os.Bundle;
import android.view.View;

import com.mindorks.nybus.NYBus;
import com.wecare.android.BR;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.FragmentTutorial2Binding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.intro.IntroNavigator;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.events.IntroStepperEvent;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

public class SecondIntroFragment extends BaseFragment<FragmentTutorial2Binding, SecondIntroFragmentViewModel> implements IntroNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private SecondIntroFragmentViewModel viewModel;

    FragmentTutorial2Binding binding;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=getViewDataBinding();
        viewModel.setNavigator(this);
        setUp();
    }

    private void setUp(){
        if (BuildConfig.isSeekerFlavor){
            binding.introTitleTxt.setText(getString(R.string.seeker_intro_first_title));
            binding.introDescTxt.setText(getString(R.string.seeker_intro_first_desc));
        }
        else {
            binding.introTitleTxt.setVisibility(View.GONE);
            binding.introDescTxt.setText(getString(R.string.giver_intro_first_title));
        }
    }

    @Override
    public SecondIntroFragmentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(SecondIntroFragmentViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tutorial_2;
    }

    public static SecondIntroFragment newInstance(Bundle args) {
        SecondIntroFragment fragment = new SecondIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void nextClicked() {
        NYBus.get().post(new IntroStepperEvent(AppConstants.INTRO_THIRD));

    }

    @Override
    public void backClicked() {
        NYBus.get().post(new IntroStepperEvent(AppConstants.BACK_STEPPER));

    }

    @Override
    public void skipClicked() {
        NYBus.get().post(new IntroStepperEvent(AppConstants.EXIT_STEPPER));
    }
}
