package com.wecare.android.ui.main.profile.transactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecare.android.R;
import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.databinding.ItemTransactionBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class TransactionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<TransactionsModel> transactionsModels;


    private TransactionsAdapterListener mListener;
    Context context;

    public void setmListener(TransactionsAdapterListener mListener) {
        this.mListener = mListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    boolean isEditMode = false;

    public boolean isEditMode() {
        return isEditMode;
    }

    public TransactionsAdapter(ArrayList<TransactionsModel> transactionsModels) {
        this.transactionsModels = transactionsModels;
        isEditMode = false;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemTransactionBinding notificationBinding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionsAdapter.transactionsViewHolder(notificationBinding);

    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return transactionsModels.size();
    }

    public void addItems(ArrayList<TransactionsModel> transactionsModels) {
        clearItems();
        this.transactionsModels.addAll(transactionsModels);
        notifyDataSetChanged();
    }

    public void clearItems() {
        transactionsModels.clear();
    }

    public TransactionsModel getItemAtPosition(int position) {
        return transactionsModels.get(position);
    }

    public class transactionsViewHolder extends BaseViewHolder implements TransactionsItemViewModel.ItemViewModelListener {

        private ItemTransactionBinding mBinding;

        private TransactionsItemViewModel transactionsItemViewModel;

        public transactionsViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }


        @Override
        public void onBind(int position) {

            final TransactionsModel transactionsModel = transactionsModels.get(position);
            transactionsItemViewModel = new TransactionsItemViewModel(transactionsModel, this);

            mBinding.setViewModel(transactionsItemViewModel);
            mBinding.orderNumberTxt.setText(String.format("%s #%d", context.getString(R.string.order), transactionsModel.getModelRef()));


            mBinding.executePendingBindings();
//                mBinding.getRoot().getContext().getString()

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
//            mBinding.executePendingBindings();
        }


        @Override
        public void onItemClicked(TransactionsModel transactionsModel) {

        }
    }

    public interface TransactionsAdapterListener {
        void onItemCLicked(TransactionsModel transactionsModel);
    }
}

