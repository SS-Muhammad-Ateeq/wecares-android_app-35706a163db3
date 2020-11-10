package com.wecare.android.ui.main.profile.transactions;

import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.utils.DateUtils;

import androidx.databinding.ObservableField;

public class TransactionsItemViewModel {
    private TransactionsModel transactionsModel;
     public ItemViewModelListener listener;
    public ObservableField<String> dayName;
    public ObservableField<String> dayDateNumber;
    public ObservableField<String> date;
    public ObservableField<String> amount;
     String amountValue;


    public TransactionsItemViewModel(TransactionsModel transactionsModel, ItemViewModelListener itemViewModelListener) {
        this.transactionsModel = transactionsModel;
        this.listener = itemViewModelListener;
        amountValue = transactionsModel.getCreditAmount()!=null ? transactionsModel.getCreditAmount() : transactionsModel.getDebitAmount();
        amount = new ObservableField<>(amountValue +" "+ transactionsModel.getCurrency());
        dayName = new ObservableField<>(DateUtils.getDayNameByDate(transactionsModel.getDate()));
        dayDateNumber = new ObservableField<>(transactionsModel.getDate().split("-")[2]);
        date = new ObservableField<>(transactionsModel.getDate().split("-")[0]+" "+transactionsModel.getDate().split("-")[1]);
    }

    public void onItemClicked(){
        listener.onItemClicked(transactionsModel);
    }

    public interface ItemViewModelListener {
        void onItemClicked(TransactionsModel transactionsModel);
    }
}
