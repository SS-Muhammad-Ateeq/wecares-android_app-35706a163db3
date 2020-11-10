package com.wecare.android.ui.main.profile;


import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.others.CustomTabItem;
import com.wecare.android.databinding.FragmentProfileBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.custom.CustomTabView;
import com.wecare.android.ui.custom.NoSwipeViewPager;
import com.wecare.android.ui.main.order.OrderPagerAdapter;
import com.wecare.android.ui.main.profile.userProfile.UserProfileFragment;
import com.wecare.android.ui.main.profile.wallet.WalletFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class ProfileMainFragment extends BaseFragment<FragmentProfileBinding, ProfileMainViewModel> implements ProfileMainNavigator, CustomTabView.onTabClickListener {

    public static final String TAG = "ProfileMainFragment";

    @Inject
    ViewModelProviderFactory factory;
    private ProfileMainViewModel viewModel;

    private OrderPagerAdapter mViewPagerAdapter;


    CustomTabView tabsView;
    NoSwipeViewPager pager;

    FragmentProfileBinding binding;

    public static ProfileMainFragment newInstance() {
        Bundle args = new Bundle();
        ProfileMainFragment fragment = new ProfileMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        initViews();
        setupViewPager();
    }

    @Override
    public ProfileMainViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(ProfileMainViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().getSupportActionBar().setBackgroundDrawable(null);
        getBaseActivity().setTitle(getString(R.string.profile));
    }

    private void initViews() {
        tabsView = binding.orderTabs;
        pager = binding.ordersViewPager;
        List<CustomTabItem> tabs = new ArrayList<>();
        tabs.add(new CustomTabItem(0, getString(R.string.profile), true));
        tabs.add(new CustomTabItem(1, getString(R.string.wallet), false));
        tabsView.initializeTabs(getBaseActivity(), tabs, R.color.colorAccent,R.color.colorAccent,R.color.transparent, this);
    }

    /**
     * set recyclerView to be linear row by row.
     */
    private void setupViewPager() {
        mViewPagerAdapter = new OrderPagerAdapter(getChildFragmentManager(), getBaseActivity());
        mViewPagerAdapter.addFragment(UserProfileFragment.newInstance());
        mViewPagerAdapter.addFragment(WalletFragment.newInstance());
        pager.setAdapter(mViewPagerAdapter);
        pager.setOffscreenPageLimit(mViewPagerAdapter.getCount());
    }

    @Override
    public void onTabClick(int tabID) {
        pager.setCurrentItem(tabID, true);
    }
}
