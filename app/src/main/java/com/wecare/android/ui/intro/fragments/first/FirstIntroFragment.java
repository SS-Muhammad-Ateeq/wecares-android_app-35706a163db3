package com.wecare.android.ui.intro.fragments.first;

import android.os.Bundle;
import android.view.View;

import com.mindorks.nybus.NYBus;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.FragmentTutorial1Binding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.intro.IntroNavigator;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.WeCareUtils;
import com.wecare.android.utils.events.IntroStepperEvent;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

public class FirstIntroFragment extends BaseFragment<FragmentTutorial1Binding, FirstIntroFragmentViewModel> implements IntroNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private FirstIntroFragmentViewModel viewModel;

    FragmentTutorial1Binding binding;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        if (viewModel.getDataManager().getAppLocale().equals(AppConstants.LANGUAGE_LOCALE_ARABIC))
            setArabicButtonChecked();
        else
            setEnglishButtonChecked();

        binding.arabicLangLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.getDataManager().setAppLocale(AppConstants.LANGUAGE_LOCALE_ARABIC);
                WeCareUtils.setLocale(AppConstants.LANGUAGE_LOCALE_ARABIC, getBaseActivity(), true);

            }
        });
        binding.englishLangLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDataManager().setAppLocale(AppConstants.LANGUAGE_LOCALE_ENGLISH);
                WeCareUtils.setLocale(AppConstants.LANGUAGE_LOCALE_ENGLISH, getBaseActivity(), true);
            }
        });
    }

    private void setArabicButtonChecked(){
        binding.arabicCheckImg.setVisibility(View.VISIBLE);
        binding.englishCheckImg.setVisibility(View.GONE);
        binding.arabicLangLayout.setBackground(getResources().getDrawable(R.drawable.background_tutorial_language_selected));
        binding.englishLangLayout.setBackground(getResources().getDrawable(R.drawable.background_tutorial_language_unselected));
    }
    private void setEnglishButtonChecked(){
        binding.englishCheckImg.setVisibility(View.VISIBLE);
        binding.arabicCheckImg.setVisibility(View.GONE);
        binding.englishLangLayout.setBackground(getResources().getDrawable(R.drawable.background_tutorial_language_selected));
        binding.arabicLangLayout.setBackground(getResources().getDrawable(R.drawable.background_tutorial_language_unselected));
    }

    @Override
    public FirstIntroFragmentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(FirstIntroFragmentViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tutorial_1;
    }

    public static FirstIntroFragment newInstance(Bundle args) {
        FirstIntroFragment fragment = new FirstIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void nextClicked() {
        NYBus.get().post(new IntroStepperEvent(AppConstants.INTRO_SECOND));

    }

    @Override
    public void backClicked() {

    }

    @Override
    public void skipClicked() {

    }
}
