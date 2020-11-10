package com.wecare.android.ui.intro.fragments.forth;

import android.os.Bundle;
import android.view.View;

import com.mindorks.nybus.NYBus;
import com.wecare.android.BR;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.FragmentTutorial4Binding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.intro.IntroNavigator;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.events.IntroStepperEvent;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

public class FourthIntroFragment extends BaseFragment<FragmentTutorial4Binding, ForthIntroFragmentViewModel> implements IntroNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private ForthIntroFragmentViewModel viewModel;

    FragmentTutorial4Binding binding;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=getViewDataBinding();
        viewModel.setNavigator(this);
        setUp();
    }
    private void setUp(){
        if (BuildConfig.isSeekerFlavor){
            binding.introTitleTxt.setText(getString(R.string.seeker_intro_third_title));
            binding.introDescTxt.setText(getString(R.string.seeker_intro_third_desc));
        }
        else {
            binding.introTitleTxt.setVisibility(View.GONE);
            binding.introDescTxt.setText(getString(R.string.giver_intro_third_title));
        }
    }
    @Override
    public ForthIntroFragmentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(ForthIntroFragmentViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tutorial_4;
    }

    public static FourthIntroFragment newInstance(Bundle args) {
        FourthIntroFragment fragment = new FourthIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void nextClicked() {

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