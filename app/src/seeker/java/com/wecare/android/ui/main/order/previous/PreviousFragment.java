package com.wecare.android.ui.main.order.previous;

import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.databinding.FragmentPreviousOrderBinding;
import com.wecare.android.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PreviousFragment extends BaseFragment<FragmentPreviousOrderBinding, PreviousViewModel> implements
        PreviousNavigator, PreviousAdapter.previousAdapterListener {
//    ,HasSupportFragmentInjector

    public static final String TAG = PreviousFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    PreviousAdapter previousAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    PreviousViewModel viewModel;

    FragmentPreviousOrderBinding binding;

    public static PreviousFragment newInstance() {
        Bundle args = new Bundle();
        PreviousFragment fragment = new PreviousFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_previous_order;
    }

    @Override
    public PreviousViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(PreviousViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        previousAdapter.setListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.serviceRecycler.setLayoutManager(mLayoutManager);
        binding.serviceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.serviceRecycler.setAdapter(previousAdapter);
    }

    private void subscribeToLiveData() {
        viewModel.getServiceListLiveData().observe(this, new Observer<List<MainServiceModel>>() {
            @Override
            public void onChanged(@Nullable List<MainServiceModel> serviceResponses) {
                viewModel.addServiceItemsToList(serviceResponses);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void updatePreviousOrderList(List<MainServiceModel> serviceResponseList) {
        previousAdapter.addItems(serviceResponseList);
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onItemClicked(MainServiceModel serviceResponse) {

    }
}
