package com.wecare.android.ui.main.profile.wallet;

import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.GiverWalletResponse;
import com.wecare.android.data.model.api.responses.WalletResponse;
import com.wecare.android.databinding.FragmentWalletBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.main.profile.transactions.TransactionsHistoryActivity;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

public class WalletFragment extends BaseFragment<FragmentWalletBinding, WalletFragmentViewModel> implements WalletFragmentNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private WalletFragmentViewModel viewModel;

    FragmentWalletBinding binding;
    GiverWalletResponse walletResponse;

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
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if (viewModel.getDataManager().getCurrentUserModel() != null && viewModel.getDataManager().getCurrentUserModel().getMembershipType() != null)
                binding.membershipTypeTV.setText(viewModel.getDataManager().getCurrentUserModel().getMembershipType().getTitle());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        viewModel.getWallet();
    }

    @Override
    public void walletFetchedSuccessfully(GiverWalletResponse walletResponse) {
        this.walletResponse = walletResponse;

        //headers
        binding.fromStartAmountTv.setText(walletResponse.getGrandTotal() != null ? walletResponse.getGrandTotal() + " " + walletResponse.getCountry().getCurrencyCode() : getZeroValue());
        binding.thisMonthTv.setText(walletResponse.getPeriodTotal() != null ? walletResponse.getPeriodTotal() + " " + walletResponse.getCountry().getCurrencyCode() : getZeroValue());
        binding.cashTxt.setText(walletResponse.getPeriodTotalCash() != null ? walletResponse.getPeriodTotalCash() + " " + walletResponse.getCountry().getCurrencyCode() : getZeroValue());
        binding.creditTxt.setText(walletResponse.getPeriodTotalOnline() != null ? walletResponse.getPeriodTotalOnline() + " " + walletResponse.getCountry().getCurrencyCode() : getZeroValue());
        binding.balanceTv.setText(walletResponse.getPeriodSettlementBalance() != null ? getString(R.string.balance) +"  "+ walletResponse.getPeriodSettlementBalance() + " " + walletResponse.getCountry().getCurrencyCode() : getString(R.string.balance) + "- " + getZeroValue());


        //table
        binding.cashCaregiverTv.setText(walletResponse.getPeriodCaregiverTotalCash() != null ? walletResponse.getPeriodCaregiverTotalCash() : getZeroValue());
        binding.creditCaregiverTv.setText(walletResponse.getPeriodCaregiverTotalOnline() != null ? walletResponse.getPeriodCaregiverTotalOnline() : getZeroValue());
        binding.totalCaregiverTv.setText(walletResponse.getPeriodCaregiverTotal() != null ? walletResponse.getPeriodCaregiverTotal() : getZeroValue());
        binding.grandTotalCaregiverTv.setText(walletResponse.getPeriodCaregiverTotal() != null ? walletResponse.getPeriodCaregiverTotal() : getZeroValue());
        binding.cashVatTaxTv.setText(walletResponse.getPeriodTaxTotalCash() != null ? walletResponse.getPeriodTaxTotalCash() : getZeroValue());
        binding.creditVatTaxTv.setText(walletResponse.getPeriodTaxTotalOnline() != null ? walletResponse.getPeriodTaxTotalOnline() : getZeroValue());
        binding.totalVatTaxTv.setText(walletResponse.getPeriodTaxTotal() != null ? walletResponse.getPeriodTaxTotal() : getZeroValue());
        binding.cashFeeTv.setText(walletResponse.getPeriodAgentTotalCash() != null ? walletResponse.getPeriodAgentTotalCash() : getZeroValue());
        binding.creditFeeTv.setText(walletResponse.getPeriodAgentTotalOnline() != null ? walletResponse.getPeriodAgentTotalOnline() : getZeroValue());
        binding.totalFeeTv.setText(walletResponse.getPeriodAgentTotal() != null ? walletResponse.getPeriodAgentTotal() : getZeroValue());
        binding.grandTotalWeCareTv.setText(walletResponse.getPeriodAgentGrandTotal() != null ? walletResponse.getPeriodAgentGrandTotal() : getZeroValue());

        binding.oldBalanceTV.setText(walletResponse.getOldBalance() != null ? getString(R.string.old_balance) + "  " + walletResponse.getOldBalance() : getString(R.string.old_balance) + " " + getZeroValue());

    }

    private String getZeroValue() {
        return String.format("%s %s", getString(R.string.zero_value), walletResponse.getCurrency());
    }

    @Override
    public void paymentHistoryClicked() {
       WalletResponse walletResponse =  new WalletResponse();
        walletResponse.setTotalCashPaid(this.walletResponse.getPeriodTotalCash() != null ? this.walletResponse.getPeriodTotalCash()  : getZeroValue());
        walletResponse.setTotalCreditcardPaid(this.walletResponse.getPeriodTotalOnline() != null ? this.walletResponse.getPeriodTotalOnline() : getZeroValue());
        walletResponse.setCurrency(this.walletResponse.getCurrency());
        startActivity(TransactionsHistoryActivity.newIntent(getActivity()).putExtra(AppConstants.KEY_WALLET_OBJECT, walletResponse));
    }

    @Override
    public void isExpanded(boolean isExpanded) {
        binding.balanceTv.setCompoundDrawablesWithIntrinsicBounds(null, null, isExpanded ? getResources().getDrawable(R.drawable.ic_expand_close) : getResources().getDrawable(R.drawable.ic_expand_open), null);
    }
}
