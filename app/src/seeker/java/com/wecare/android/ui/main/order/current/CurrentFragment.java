package com.wecare.android.ui.main.order.current;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.FragmentCurrentOrderBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.main.order.details.OrderDetailsActivity;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentFragment extends BaseFragment<FragmentCurrentOrderBinding, CurrentViewModel> implements CurrentNavigator, CurrentAdapter.CurrentAdapterListener {

    public static final String TAG = CurrentFragment.class.getSimpleName();


    //pagination
    int pageNumber = 0;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    String ordersType;


    @Inject
    ViewModelProviderFactory factory;

    @Inject
    CurrentAdapter currentAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    CurrentViewModel viewModel;

    FragmentCurrentOrderBinding binding;

    public static CurrentFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(AppConstants.ARGS_KEY_TYPE,type);
        CurrentFragment fragment = new CurrentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CurrentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CurrentViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_current_order;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        currentAdapter.setListener(this);
        ordersType = getArguments().getString(AppConstants.ARGS_KEY_TYPE);

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
        binding.serviceRecycler.setAdapter(currentAdapter);

        //handling pagination
        binding.serviceRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            //Do pagination.. i.e. fetch new data
                            pageNumber = pageNumber + 1;
                            viewModel.loadModeOrders(ordersType, pageNumber);

                        }
                    }
                }
            }
        });
    }

    private void subscribeToLiveData() {
        viewModel.getOrdersListLiveData().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(@Nullable List<OrderModel> ordersResponseList) {
                viewModel.addOrdersItemsToList(ordersResponseList);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void updateCurrentOrderList(List<OrderModel> ordersResponseList) {
        if (ordersResponseList != null) {
            currentAdapter.addItems(ordersResponseList);
            loading = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNumber = 0;
        viewModel.fetchOrders(ordersType, pageNumber);

    }
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if (viewModel != null && ordersType != null)
                viewModel.fetchOrders(ordersType, 0);
        }
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onItemClicked(OrderModel ordersResponse) {
        startActivity(OrderDetailsActivity.newIntent(getActivity()).putExtra(AppConstants.KEY_ORDER_OBJECT, ordersResponse).putExtra(AppConstants.ARGS_KEY_TYPE, ordersType));

    }
}
