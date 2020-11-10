package com.wecare.android.ui.main.profile.userProfile;

import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.databinding.FragmentUserProfileBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.main.profile.userProfile.bankinfo.BankInfoActivity;
import com.wecare.android.ui.main.profile.userProfile.educationcertificates.EducationCertificatesActivity;
import com.wecare.android.ui.main.profile.userProfile.personalInfo.CaregiverPersonalInformationActivity;
import com.wecare.android.ui.main.profile.userProfile.schduler.CaregiverServicesSchedulerActivity;
import com.wecare.android.ui.main.profile.userProfile.servicearea.CaregiverServiceAreaActivity;
import com.wecare.android.ui.main.profile.userProfile.services.UserServicesActivity;
import com.wecare.android.utils.AppConstants;

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
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void personalInfoClicked() {
        startActivity(CaregiverPersonalInformationActivity.newIntent(getActivity()));
    }

    @Override
    public void scheduleServicesClicked() {
        startActivity(CaregiverServicesSchedulerActivity.newIntent(getActivity()));

    }

    @Override
    public void servicesClicked() {
        startActivity(UserServicesActivity.newIntent(getActivity()).putExtra(AppConstants.ARGS_KEY_SERVICES_PICKING_TYPE,AppConstants.ARGS_KEY_CAREGIVER_SERVICES));

    }

    @Override
    public void ServiceAreaClicked() {
        startActivity(CaregiverServiceAreaActivity.newIntent(getActivity()));
    }

    @Override
    public void BankInfoClicked() {
        startActivity(BankInfoActivity.newIntent(getActivity()));

    }

    @Override
    public void EducationalCertificatesClicked() {
        startActivity(EducationCertificatesActivity.newIntent(getActivity()));

    }

    @Override
    public void userInfoFetchedSuccessfully(UserModel userModel) {
    viewModel.setWeCareID(String.format("%s %s", getString(R.string.id), userModel.getWecareID()));
    }
}
