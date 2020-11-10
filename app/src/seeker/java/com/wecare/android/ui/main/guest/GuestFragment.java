package com.wecare.android.ui.main.guest;


import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.FragmentGuestBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

public class GuestFragment extends BaseFragment<FragmentGuestBinding, GuestViewModel> implements GuestNavigator {

    public static final String TAG = GuestFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private GuestViewModel viewModel;

    private FragmentGuestBinding binding;
    private String title;

    public static GuestFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(AppConstants.ARGS_KEY_TITLE, title);
        GuestFragment fragment = new GuestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);

        if (getArguments() != null) {
            title = getArguments().getString(AppConstants.ARGS_KEY_TYPE);
        } else {
            title = "";
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public GuestViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(GuestViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guest;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void navigateToLoginScreen() {
        getBaseActivity().openLoginActivity(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().setTitle(title);
    }
}
