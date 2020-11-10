package com.wecare.android.ui.sub;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.StatisticResponse;
import com.wecare.android.databinding.FragmentServiceBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.main.home.sub.SubServicesActivity;
import com.wecare.android.ui.main.home.sub.details.SubDetailsFragment;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesFragment extends BaseFragment<FragmentServiceBinding, ServicesViewModel> implements ServicesNavigator, ServicesAdapter.ServiceAdapterListener {

    public static final String TAG = SubDetailsFragment.class.getSimpleName();

    @Inject
    ServicesAdapter mServicesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProviderFactory factory;
    private ServicesViewModel viewModel;


    FragmentServiceBinding mFragmentServiceBinding;

    public static ServicesFragment newInstance() {
        Bundle args = new Bundle();
        ServicesFragment fragment = new ServicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        mServicesAdapter.setListener(this);
        viewModel.fetchStatistics();
    }

    @Override
    public void onResume() {
        super.onResume();
        //request

        if (getViewModel().getDataManager().getCurrentUserModel() != null) {
            getViewModel().fetchServices(getViewModel().getDataManager().getCurrentUserModel().getCountryID());
        } else {
            getViewModel().fetchServices("");
        }

    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            //update statistics and services upon country id updated
            if (viewModel != null && getViewModel().getDataManager().getCurrentUserModel() != null) {
                getViewModel().fetchServices(String.valueOf(getViewModel().getDataManager().getCurrentUserModel().getCountryID()));
                viewModel.fetchStatistics();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentServiceBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    @Override
    public ServicesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ServicesViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void goToSubServices(MainServiceModel mainServiceModel) {
        Intent intent = SubServicesActivity.getStartIntent(getBaseActivity());
        intent.putExtra(AppConstants.ARGS_KEY_SERVICES, mainServiceModel);
        startActivity(intent);
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentServiceBinding.serviceRecycler.setLayoutManager(mLayoutManager);
        mFragmentServiceBinding.serviceRecycler.setItemAnimator(new DefaultItemAnimator());
        mFragmentServiceBinding.serviceRecycler.setAdapter(mServicesAdapter);
    }

    private void subscribeToLiveData() {
        viewModel.getServiceListLiveData().observe(this, new Observer<List<MainServiceModel>>() {
            @Override
            public void onChanged(@Nullable List<MainServiceModel> mainServiceRespons) {
                viewModel.addServiceItemsToList(mainServiceRespons);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void updateServicesList(List<MainServiceModel> mainServiceModelList) {
        mServicesAdapter.addItems(mainServiceModelList);
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        if (BuildConfig.isSeekerFlavor) {
            getBaseActivity().getSupportActionBar().setBackgroundDrawable(null);
        }
        getBaseActivity().setTitle(getString(R.string.wecare));
    }

    @Override
    public void onItemClicked(MainServiceModel mainServiceModel) {
        goToSubServices(mainServiceModel);
    }

    @Override
    public void statisticsFetched(StatisticResponse statisticResponse) {
        mFragmentServiceBinding.serviceUsersTxt.setText(String.format("%s", statisticResponse.getCareseekers().getNumber()));
        mFragmentServiceBinding.usersTxt.setText(statisticResponse.getCareseekers().getEnglishName());
        mFragmentServiceBinding.servicesCaregiverTxt.setText(String.format("%s", statisticResponse.getCaregivers().getNumber()));
        mFragmentServiceBinding.caregiverTxt.setText(statisticResponse.getCaregivers().getEnglishName());
        mFragmentServiceBinding.servicesServicesTxt.setText(String.format("%s", statisticResponse.getServices().getNumber()));
        mFragmentServiceBinding.servicesTxt.setText(statisticResponse.getServices().getEnglishName());
    }
}
