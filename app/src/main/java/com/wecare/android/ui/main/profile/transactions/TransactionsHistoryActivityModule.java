package com.wecare.android.ui.main.profile.transactions;

import com.wecare.android.data.model.api.models.TransactionsModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class TransactionsHistoryActivityModule {
    @Provides
    TransactionsAdapter provideTransactionsAdapter() {
        return new TransactionsAdapter(new ArrayList<TransactionsModel>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(TransactionsHistoryActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
