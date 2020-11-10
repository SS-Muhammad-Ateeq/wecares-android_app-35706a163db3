package com.wecare.android.ui.main.home.sub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ActivitySubServicesBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.main.home.sub.details.SubDetailsFragment;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SubServicesActivity extends BaseActivity<ActivitySubServicesBinding, SubServicesViewModel> implements
        SubServicesNavigator, SubServicesAdapter.SubServiceAdapterListener, HasSupportFragmentInjector {

    public static final String TAG = SubServicesActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    SubServicesAdapter subServicesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    SubServicesViewModel viewModel;

    ActivitySubServicesBinding binding;

    MainServiceModel selectedServiceResponse;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SubServicesActivity.class);
    }

    @Override
    public SubServicesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SubServicesViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_services;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();

        addToolbar(R.id.toolbar, getString(R.string.wecare), true);

        viewModel.setNavigator(this);
        subServicesAdapter.setListener(this);

        setUp();

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(AppConstants.ARGS_KEY_SERVICES)) {
            selectedServiceResponse = getIntent().getExtras().getParcelable(AppConstants.ARGS_KEY_SERVICES);

            if (selectedServiceResponse != null) {
                //background rounded color programmatically
                binding.subServicesTitleTxt.setBackgroundResource(R.drawable.rounded_top_corners);
                GradientDrawable drawable = (GradientDrawable) binding.subServicesTitleTxt.getBackground();
                drawable.setColor(Color.parseColor(selectedServiceResponse.getColor()));
                //set title
                binding.subServicesTitleTxt.setText(selectedServiceResponse.getServiceName());
                //update recycler list.
                subServicesAdapter.addItems(selectedServiceResponse.getSubServiceResponseList());
            }
        }
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.subServiceRecycler.setLayoutManager(mLayoutManager);
        binding.subServiceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.subServiceRecycler.setAdapter(subServicesAdapter);
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onOrderNowClick() {

    }

    @Override
    public void onItemClicked(SubServiceResponse subServiceResponse) {
        subServiceResponse.setColor(selectedServiceResponse.getColor());
        replaceFragment(SubDetailsFragment.newInstance(subServiceResponse,selectedServiceResponse), true, true,true, R.id.content_details);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
