package com.wecare.android.ui.main.profile.wallet;

import com.wecare.android.data.model.api.responses.WalletResponse;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.List;

public interface WalletFragmentNavigator extends BaseNavigator {
    void walletFetchedSuccessfully(List<WalletResponse> walletResponse);
    void showWallets();
    void paymentHistoryClicked();
}
