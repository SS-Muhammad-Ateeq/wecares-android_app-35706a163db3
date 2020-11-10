package com.wecare.android.ui.main.profile.wallet;

import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.WalletResponse;
import com.wecare.android.databinding.FragmentWalletBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.main.profile.transactions.TransactionsHistoryActivity;
import com.wecare.android.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class WalletFragment extends BaseFragment<FragmentWalletBinding, WalletFragmentViewModel> implements WalletFragmentNavigator {

    List<WalletResponse> walletResponse = new ArrayList<>();
     WalletResponse selectedWallet;

    @Inject
    ViewModelProviderFactory factory;
    private WalletFragmentViewModel viewModel;

    FragmentWalletBinding binding;

    @Override
    public WalletFragmentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(WalletFragmentViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        viewModel.getWallet();
    }

    @Override
    public void walletFetchedSuccessfully(List<WalletResponse> walletResponse) {
        if (walletResponse.size()>0){
            selectedWallet = walletResponse.get(0);
            setUpData(selectedWallet);
            this.walletResponse = walletResponse;
        }
        else {

        }
    }

    private void setUpData(WalletResponse walletResponse){
        binding.cashTxt.setText(walletResponse.getTotalPaidCash()+" "+walletResponse.getCurrency());
        binding.creditTxt.setText(walletResponse.getTotalPaidWallet()+" "+walletResponse.getCurrency());
        binding.totalAmountTxt.setText(walletResponse.getTotalPaid()+" "+walletResponse.getCurrency());
        binding.balanceTxt.setText(walletResponse.getWallet()+" "+walletResponse.getCurrency());
    }

    @Override
    public void showWallets() {
        if (walletResponse.size()>0){
            showWalletsDialog();
        }
    }

    @Override
    public void paymentHistoryClicked() {
        startActivity(TransactionsHistoryActivity.newIntent(getActivity()).putExtra(AppConstants.KEY_WALLET_OBJECT, selectedWallet));

    }

    private void showWalletsDialog(){
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
                getString(R.string.wallet),
                getString(R.string.please_select_a_wallet),
                null, new ArrayList(walletResponse), walletsResultListener);

        simpleSearchDialogCompat.show();
    }

    SearchResultListener walletsResultListener = new SearchResultListener<WalletResponse>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, WalletResponse item, int position) {
            selectedWallet = item;
            setUpData(item);
            dialog.dismiss();
        }
    };
}
