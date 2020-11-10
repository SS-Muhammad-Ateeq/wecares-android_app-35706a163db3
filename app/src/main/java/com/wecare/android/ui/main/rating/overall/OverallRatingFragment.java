package com.wecare.android.ui.main.rating.overall;

import android.os.Bundle;
import android.view.View;

import com.mindorks.nybus.NYBus;
import com.wecare.android.BR;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.requests.OverallRating;
import com.wecare.android.data.model.api.requests.RatingRequest;
import com.wecare.android.databinding.FragmentOverallRatingBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.events.RatingStepperEvent;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

public class OverallRatingFragment extends BaseFragment<FragmentOverallRatingBinding, OverallRatingFragmentViewModel> implements OverallRatingFragmentNavigator {


    @Inject
    ViewModelProviderFactory factory;
    private OverallRatingFragmentViewModel viewModel;

    FragmentOverallRatingBinding binding;

    @Override
    public OverallRatingFragmentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(OverallRatingFragmentViewModel.class);
        return viewModel;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=getViewDataBinding();
        viewModel.setNavigator(this);
        if (BuildConfig.isSeekerFlavor){
        binding.firstTV.setText(R.string.appearance);
        binding.secondTV.setText(R.string.cleanliness);
        binding.thirdTV.setText(R.string.performance);
        binding.forthTV.setText(R.string.time_attendance);
        binding.fifthTV.setText(R.string.quality_of_service);
        }
        else {
            binding.firstTV.setText(getString(R.string.commitment_to_the_appointment));
            binding.secondTV.setText(getString(R.string.location_readiness));
            binding.thirdTV.setText(getString(R.string.providing_customized_tools));
            binding.forthTV.setText(getString(R.string.hospitality_literature));
            binding.fifthTV.setText(getString(R.string.address_accuracy));
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_overall_rating;
    }

    @Override
    public void nextClicked() {
        RatingStepperEvent stepperEvent =new RatingStepperEvent(AppConstants.FINISH_RATING);

        RatingRequest ratingRequest = new RatingRequest();

        OverallRating overallRating = new OverallRating();

        if (BuildConfig.isSeekerFlavor){
            overallRating.setAppointmentCommitment((int) binding.commitmentToTheAppointmentRatingBar.getRating());
            overallRating.setLocationReadiness((int) binding.locationReadinessRating.getRating());
            overallRating.setProvidingCustomizedTools((int) binding.providingCustomizedToolsRating.getRating());
            overallRating.setHospitalityLiterature((int) binding.hospitalityLiteratureRating.getRating());
            overallRating.setAddressAccuracy((int) binding.addressAccuracyRating.getRating());
        }
        else {
            overallRating.setAppearance((int) binding.commitmentToTheAppointmentRatingBar.getRating());
            overallRating.setCleanliness((int) binding.locationReadinessRating.getRating());
            overallRating.setPerformance((int) binding.providingCustomizedToolsRating.getRating());
            overallRating.setTimeAttendance((int) binding.hospitalityLiteratureRating.getRating());
            overallRating.setQualityOfService((int) binding.addressAccuracyRating.getRating());
        }

        ratingRequest.setOverallRating(overallRating);
        ratingRequest.setBaseRating(getArguments().getString(AppConstants.KEY_BASE_RATING));
        ratingRequest.setDescription(binding.descriptionEdt.getText().toString());
        stepperEvent.setRequest(ratingRequest);

        NYBus.get().post(stepperEvent);
    }
    public static OverallRatingFragment newInstance(Bundle args) {
        OverallRatingFragment fragment = new OverallRatingFragment();
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
