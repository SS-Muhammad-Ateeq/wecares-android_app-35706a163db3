package com.wecare.android.ui.main.order.previous;

import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.FragmentPreviousOrderBinding;
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

    //pagination
    int pageNumber = 0;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

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
    public void onResume() {
        super.onResume();
        pageNumber = 0;
        viewModel.fetchOrders(AppConstants.ORDERS_STATUS_PREVIOUS,pageNumber);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        previousAdapter.setContext(getActivity());
        binding.serviceRecycler.setLayoutManager(layoutManager);
        binding.serviceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.serviceRecycler.setAdapter(previousAdapter);


        //handling pagination
        binding.serviceRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            //Do pagination.. i.e. fetch new data
                            pageNumber = pageNumber + 1;
                            viewModel.loadModeOrders(AppConstants.ORDERS_STATUS_PREVIOUS, pageNumber);

                        }
                    }
                }
            }
        });
    }

    private void subscribeToLiveData() {
        viewModel.getOrderListLiveData().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(@Nullable List<OrderModel> orderModels) {
                viewModel.addServiceItemsToList(orderModels);
            }
        });
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if (viewModel != null ) {
                viewModel.fetchOrders(AppConstants.ORDERS_STATUS_PREVIOUS, 0);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void updatePreviousOrderList(List<OrderModel> orderModels) {
        if (orderModels != null) {
            previousAdapter.addItems(orderModels);
            loading = true;
        }
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onItemClicked(OrderModel orderModel) {
        startActivity(OrderDetailsActivity.newIntent(getActivity()).putExtra(AppConstants.KEY_ORDER_OBJECT,orderModel));

    }
}
