package com.wecare.android.ui.main.home.sub.details;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.FragmentSubDetailsBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.main.home.sub.SubServicesNavigator;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

public class SubDetailsFragment extends BaseFragment<FragmentSubDetailsBinding, SubDetailsViewModel> implements SubServicesNavigator, Toolbar.OnMenuItemClickListener {

    public static final String TAG = SubDetailsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    private SubDetailsViewModel viewModel;

    private FragmentSubDetailsBinding binding;
    private SubServiceResponse subServiceResponse;

    public static SubDetailsFragment newInstance(SubServiceResponse subServiceResponse) {
        Bundle args = new Bundle();
        args.putParcelable(AppConstants.ARGS_KEY_SUB_SERVICES, subServiceResponse);
        SubDetailsFragment fragment = new SubDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);

        //get args
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(AppConstants.ARGS_KEY_SUB_SERVICES)) {
            subServiceResponse = bundle.getParcelable(AppConstants.ARGS_KEY_SUB_SERVICES);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        if (subServiceResponse != null) {
//            getBaseActivity().addToolbar(R.id.toolbar, getString(R.string.wecare), true);
//            binding.toolbarInclude.toolbar.setOnMenuItemClickListener(this);

            //background rounded color programmatically
            binding.subDetailsTitleTxt.setBackgroundResource(R.drawable.rounded_top_corners);
            GradientDrawable drawable = (GradientDrawable) binding.subDetailsTitleTxt.getBackground();
            drawable.setColor(Color.parseColor(subServiceResponse.getColor()));
            //set title
            binding.subDetailsTitleTxt.setText(subServiceResponse.getServiceName());
            //set description
            binding.subDetailsDesTxt.setText(subServiceResponse.getServiceDescription());
        }
    }

    @Override
    public SubDetailsViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SubDetailsViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sub_details;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onOrderNowClick() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getBaseActivity().onBackPressed();
                return false;
        }
        return false;
    }
}
