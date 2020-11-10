package com.wecare.android.ui.main.profile.wallet;

import com.wecare.android.data.model.api.responses.GiverWalletResponse;
import com.wecare.android.ui.base.BaseNavigator;

public interface WalletFragmentNavigator extends BaseNavigator {
    void walletFetchedSuccessfully(GiverWalletResponse walletResponse);
    void paymentHistoryClicked();
    void isExpanded(boolean isExpanded);
}
