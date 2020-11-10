package com.wecare.android.ui.main.rating.basic;

import android.os.Bundle;
import android.view.View;

import com.mindorks.nybus.NYBus;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.requests.RatingRequest;
import com.wecare.android.databinding.FragmentBasicRatingBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.events.RatingStepperEvent;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

public class BasicRatingFragment extends BaseFragment<FragmentBasicRatingBinding,BasicRatingFragmentViewModel> implements BasicRatingFragmentNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private BasicRatingFragmentViewModel viewModel;

    FragmentBasicRatingBinding binding;

    @Override
    public BasicRatingFragmentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(BasicRatingFragmentViewModel.class);
        return viewModel;      }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_basic_rating;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=getViewDataBinding();
        viewModel.setNavigator(this);}


    @Override
    public void nextClicked() {
        RatingStepperEvent stepperEvent =new RatingStepperEvent(AppConstants.RATING_SECOND);
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setBaseRating((int) binding.ratingBarFeedback.getRating()+"");
        stepperEvent.setRequest(ratingRequest);
        NYBus.get().post(stepperEvent);

    }

    public static BasicRatingFragment newInstance(Bundle args) {
        BasicRatingFragment fragment = new BasicRatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void exitStepperClicked() {
        NYBus.get().post(new RatingStepperEvent(AppConstants.EXIT_STEPPER));

    }

    @Override
    public void ratingDoneSuccessfully() {

    }
}
