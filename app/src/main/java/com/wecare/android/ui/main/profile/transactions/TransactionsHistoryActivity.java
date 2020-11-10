package com.wecare.android.ui.main.profile.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.data.model.api.responses.WalletResponse;
import com.wecare.android.databinding.ActivityTransactionsHistoryBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionsHistoryActivity extends BaseActivity<ActivityTransactionsHistoryBinding,TransactionsHistoryViewModel> implements TransactionsHistoryActivityNavigator,TransactionsAdapter.TransactionsAdapterListener {

    @Inject
    ViewModelProviderFactory factory;
    private TransactionsHistoryViewModel viewModel;

    ActivityTransactionsHistoryBinding binding;

    @Inject
    TransactionsAdapter transactionsAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    private WalletResponse walletResponse;

    //pagination
    int pageNumber = 0;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public TransactionsHistoryViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(TransactionsHistoryViewModel.class);
        return viewModel;    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transactions_history;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        addToolbar(R.id.toolbar, getString(R.string.payments_history), true);
        subscribeToLiveData();
        setUp();
        viewModel.fetchTransactions(pageNumber,false);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TransactionsHistoryActivity.class);
        return intent;
    }

    private void subscribeToLiveData() {
        viewModel.getListMutableLiveData().observe(this, new Observer<List<TransactionsModel>>() {
            @Override
            public void onChanged(@Nullable List<TransactionsModel>  transactionsModels) {
                viewModel.addItemsToList(transactionsModels);
            }
        });
    }

    private void setUp() {
        walletResponse  = getIntent().getParcelableExtra(AppConstants.KEY_WALLET_OBJECT);
        binding.cashTxt.setText(String.format("%s %s", walletResponse.getTotalCashPaid(), walletResponse.getCurrency()));
        binding.creditTxt.setText(String.format("%s %s", walletResponse.getTotalCreditcardPaid(), walletResponse.getCurrency()));


        transactionsAdapter.setmListener(this);
        transactionsAdapter.setContext(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.transactionsRecycler.setLayoutManager(mLayoutManager);
        binding.transactionsRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.transactionsRecycler.setAdapter(transactionsAdapter);
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
                            viewModel.fetchTransactions(pageNumber,true);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onItemCLicked(TransactionsModel transactionsModel) {

    }

    @Override
    public void updateTransactionsList(ArrayList<TransactionsModel> transactionsModels) {
        if (transactionsModels != null) {
            transactionsAdapter.addItems(transactionsModels);
            loading = true;
        }
    }
}
