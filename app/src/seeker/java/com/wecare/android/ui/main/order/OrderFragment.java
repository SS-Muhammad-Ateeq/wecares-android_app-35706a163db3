package com.wecare.android.ui.main.order;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.others.CustomTabItem;
import com.wecare.android.databinding.FragmentOrderBinding;
import com.wecare.android.databinding.FragmentSubDetailsBinding;
import com.wecare.android.ui.base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import com.wecare.android.ui.custom.CustomTabView;
import com.wecare.android.ui.custom.NoSwipeViewPager;
import com.wecare.android.ui.main.order.current.CurrentFragment;
import com.wecare.android.ui.main.order.previous.PreviousFragment;
import com.wecare.android.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment<FragmentOrderBinding, OrderViewModel> implements OrderNavigator, CustomTabView.onTabClickListener {

    public static final String TAG = OrderFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private OrderViewModel viewModel;

    CustomTabView tabsView;
    NoSwipeViewPager pager;

    private FragmentOrderBinding binding;

    private OrderPagerAdapter mViewPagerAdapter;

    public static OrderFragment newInstance() {
        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
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
    public OrderViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
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
        getBaseActivity().setTitle(getString(R.string.order));
        getBaseActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

    }

    @Override
    protected void OnViewRemoved() {
        super.OnViewRemoved();
        getBaseActivity().getSupportActionBar().setBackgroundDrawable(null);
    }

    private void initViews() {
        tabsView = binding.orderTabs;
        pager = binding.ordersViewPager;
        List<CustomTabItem> tabs = new ArrayList<>();
        tabs.add(new CustomTabItem(0, getString(R.string.current_orders), true));
        tabs.add(new CustomTabItem(1, getString(R.string.previous_orders), false));
        tabsView.initializeTabs(getBaseActivity(), tabs , R.color.colorAccent,R.color.colorAccent,R.color.colorPrimary, this);
    }


    /**
     * set recyclerView to be linear row by row.
     */
    private void setupViewPager() {
        mViewPagerAdapter = new OrderPagerAdapter(getChildFragmentManager(), getBaseActivity());
        mViewPagerAdapter.addFragment(CurrentFragment.newInstance(AppConstants.ORDERS_STATUS_CURRENT));
        mViewPagerAdapter.addFragment(CurrentFragment.newInstance(AppConstants.ORDERS_STATUS_PREVIOUS));
        pager.setAdapter(mViewPagerAdapter);
        pager.setOffscreenPageLimit(mViewPagerAdapter.getCount());
    }

    @Override
    public void onTabClick(int tabID) {
        pager.setCurrentItem(tabID, true);
    }


}
