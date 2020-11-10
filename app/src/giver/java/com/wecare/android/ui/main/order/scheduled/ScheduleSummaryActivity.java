package com.wecare.android.ui.main.order.scheduled;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.ActivityScheduleSummaryBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScheduleSummaryActivity extends BaseActivity<ActivityScheduleSummaryBinding,ScheduleSummaryViewModel>implements ScheduleSummaryActivityNavigator,ScheduleSummaryAdapter.ScheduleSummaryAdapterListener  {

    @Inject
    ViewModelProviderFactory factory;
    private ScheduleSummaryViewModel viewModel;

    ActivityScheduleSummaryBinding binding;

    @Inject
    ScheduleSummaryAdapter scheduleSummaryAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;


    //pagination
    int pageNumber = 0;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public ScheduleSummaryViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ScheduleSummaryViewModel.class);
        return viewModel;     }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_schedule_summary;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        addToolbar(R.id.toolbar, getString(R.string.schedule_summary), true);
        subscribeToLiveData();
        setUp();
        viewModel.fetchOrders(AppConstants.ORDERS_STATUS_SCHEDULING, pageNumber);
    }

    private void setUp() {


        scheduleSummaryAdapter.setListener(this);
        scheduleSummaryAdapter.setContext(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.transactionsRecycler.setLayoutManager(mLayoutManager);
        binding.transactionsRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.transactionsRecycler.setAdapter(scheduleSummaryAdapter);
        //handling pagination
        binding.transactionsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            viewModel.loadModeOrders(AppConstants.ORDERS_STATUS_SCHEDULING,pageNumber);
                        }
                    }
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ScheduleSummaryActivity.class);
        return intent;
    }

    private void subscribeToLiveData() {
        viewModel.getOrdersListLiveData().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(@Nullable List<OrderModel>  orderModels) {
                viewModel.addOrdersItemsToList(orderModels);
            }
        });
    }



    @Override
    public void updateCurrentOrderList(List<OrderModel> ordersResponseList) {
        if (ordersResponseList != null) {
            scheduleSummaryAdapter.addItems(ordersResponseList);
            loading = true;
        }
    }

    @Override
    public void onItemClicked(OrderModel orderModel) {
//        startActivity(OrderDetailsActivity.newIntent(this).putExtra(AppConstants.KEY_ORDER_OBJECT, orderModel).putExtra(AppConstants.KEY_ORDERS_STATUS, AppConstants.ORDERS_STATUS_ACCEPTED));
    }
}
