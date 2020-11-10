package com.wecare.android.ui.main.profile.transactions;

import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.ArrayList;

public interface TransactionsHistoryActivityNavigator extends BaseNavigator {

    void updateTransactionsList(ArrayList<TransactionsModel> transactionsModels);

}
