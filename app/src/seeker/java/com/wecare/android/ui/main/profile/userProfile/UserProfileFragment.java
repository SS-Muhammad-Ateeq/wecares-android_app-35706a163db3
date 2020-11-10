package com.wecare.android.ui.main.profile.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.FragmentUserProfileBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.create_order.location.LocationActivity;
import com.wecare.android.ui.create_order.relative.RelativeProfileActivity;
import com.wecare.android.ui.create_order.relative.add.AddEditRelativeProfileActivity;
import com.wecare.android.ui.main.profile.userProfile.paymentmethod.PaymentMethodActivity;
import com.wecare.android.ui.search_giver.SearchGiverActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class UserProfileFragment extends BaseFragment<FragmentUserProfileBinding, UserProfileViewModel> implements UserProfileFragmentNavigator {


    @Inject
    ViewModelProviderFactory factory;
    private UserProfileViewModel viewModel;

    FragmentUserProfileBinding binding;


    @Override
    public UserProfileViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(UserProfileViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.getUserInfo();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
    }

    public static UserProfileFragment newInstance() {
        Bundle args = new Bundle();
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        getViewModel().updateUserInfoDate();
    }

    @Override
    public void personalInfoClicked() {
        Intent intent = AddEditRelativeProfileActivity.getStartIntent(getBaseActivity(), getViewModel().getDataManager().getCurrentUserId());
        startActivity(intent);
    }

    @Override
    public void LocationsClicked() {
        Intent intent = LocationActivity.getStartIntent(getBaseActivity());
        startActivity(intent);
    }

    @Override
    public void paymentMethodClicked() {
        Intent intent = PaymentMethodActivity.newIntent(getBaseActivity());
        startActivity(intent);
    }

    @Override
    public void relativeProfilesClicked() {
        Intent i = RelativeProfileActivity.getStartIntent(getBaseActivity());
        startActivity(i);
    }

    @Override
    public void favoriteAndBlockedClicked() {
        Intent i = SearchGiverActivity.getStartIntent(getBaseActivity());
        startActivity(i);
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().setTitle(getString(R.string.profile));
    }

}
