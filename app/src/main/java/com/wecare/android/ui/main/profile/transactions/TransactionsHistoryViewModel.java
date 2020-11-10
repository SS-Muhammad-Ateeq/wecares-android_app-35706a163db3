package com.wecare.android.ui.main.profile.transactions;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class TransactionsHistoryViewModel extends BaseViewModel<TransactionsHistoryActivityNavigator> {

    private final ObservableArrayList<TransactionsModel> modelObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<TransactionsModel>> listMutableLiveData;

    public TransactionsHistoryViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<TransactionsModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void addItemsToList(List<TransactionsModel> TransactionsModels) {
        modelObservableArrayList.clear();
        modelObservableArrayList.addAll(TransactionsModels);
    }

    public ObservableArrayList<TransactionsModel> getModelObservableArrayList() {
        return modelObservableArrayList;
    }

    public void fetchTransactions(int offset, boolean isLoadMore) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getTransactions(getDataManager().getCurrentUserModel().getCountryOfService(), offset + "")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<TransactionsModel>>() {
                    @Override
                    public void accept(List<TransactionsModel> transactionsModels) throws Exception {
                        setIsLoading(false);
                        if (isLoadMore)
                            getNavigator().updateTransactionsList(new ArrayList<>(transactionsModels));
                        else
                            listMutableLiveData.setValue(transactionsModels);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }


}
