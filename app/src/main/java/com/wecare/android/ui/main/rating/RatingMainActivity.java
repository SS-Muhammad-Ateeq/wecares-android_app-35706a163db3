package com.wecare.android.ui.main.rating;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.annotation.Subscribe;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.requests.RatingRequest;
import com.wecare.android.databinding.ActivityMainRatingBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.main.rating.basic.BasicRatingFragment;
import com.wecare.android.ui.main.rating.overall.OverallRatingFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.events.RatingStepperEvent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RatingMainActivity extends BaseActivity<ActivityMainRatingBinding, RatingMainViewModel> implements RatingMainNavigator, HasSupportFragmentInjector {

    @Inject
    ViewModelProviderFactory factory;
    private RatingMainViewModel viewModel;

    ActivityMainRatingBinding binding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    @Override
    public RatingMainViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RatingMainViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_rating;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        NYBus.get().register(this);
        //init first rating fragment
        doStep(new RatingStepperEvent(AppConstants.RATING_FIRST));
    }

    public void doStep(RatingStepperEvent event) {
        Bundle args = new Bundle();
        switch (event.step) {
            case AppConstants.RATING_FIRST:
                replaceFragment(BasicRatingFragment.newInstance(args), false, R.id.content, true);
                break;
            case AppConstants.RATING_SECOND:
                if (event.request.getBaseRating().equals("5")) {
                    doRating(event.request);
                } else {
                    args.putString(AppConstants.KEY_BASE_RATING,event.request.getBaseRating());
                    replaceFragment(OverallRatingFragment.newInstance(args), true, true, true, R.id.content);
                }
                break;
            case AppConstants.FINISH_RATING:
                doRating(event.request);
                break;
            case AppConstants.EXIT_STEPPER:
                openMainActivity(this);
                break;
        }
    }

    private void doRating(RatingRequest ratingRequest) {
        ratingRequest.setOrderId(getIntent().getStringExtra(AppConstants.ARGS_KEY_ORDER_ID));
        viewModel.doRating(ratingRequest);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RatingMainActivity.class);
        return intent;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    @Subscribe
    public void onEvent(RatingStepperEvent event) {
        doStep(event);
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
    public void exitStepperClicked() {

    }

    @Override
    public void ratingDoneSuccessfully() {
        DialogFactory.createSuccessDialog(this, getString(R.string.thank_you), "", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                openMainActivity(RatingMainActivity.this);
                finish();
            }
        });
    }
}
