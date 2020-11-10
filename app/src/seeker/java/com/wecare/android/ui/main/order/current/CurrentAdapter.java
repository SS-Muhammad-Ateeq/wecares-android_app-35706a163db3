
package com.wecare.android.ui.main.order.current;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.ItemCurrentOrderBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class CurrentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<OrderModel> mOrderResponseList;

    private CurrentAdapterListener mListener;

    public CurrentAdapter(List<OrderModel> orderResponseList) {
        this.mOrderResponseList = orderResponseList;
    }

    public void setListener(CurrentAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                ItemOrderMainBinding emptyViewBinding = ItemOrderMainBinding.inflate(LayoutInflater.from(parent.getContext()),
//                        parent, false);
//                return new SubOrderViewHolder(sViewBinding);
//            case VIEW_TYPE_EMPTY:
//            default:
        ItemCurrentOrderBinding currentOrderBinding = ItemCurrentOrderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CurrentOrderViewHolder(currentOrderBinding);
//        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (mOrderResponseList != null && mOrderResponseList.size() > 0) {
        return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_EMPTY;
//        }
    }

    @Override
    public int getItemCount() {
//        if (mOrderResponseList != null && mOrderResponseList.size() > 0) {
        return mOrderResponseList.size();
//        } else {
//            return 1;
//        }
    }

    public void addItems(List<OrderModel> orderResponseList) {
        mOrderResponseList.addAll(orderResponseList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mOrderResponseList.clear();
    }

    public class CurrentOrderViewHolder extends BaseViewHolder implements CurrentItemViewModel.CurrentItemViewModelListener {

        private ItemCurrentOrderBinding mBinding;

        private CurrentItemViewModel mCurrentItemViewModel;

        public CurrentOrderViewHolder(ItemCurrentOrderBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            final OrderModel order = mOrderResponseList.get(position);

            mCurrentItemViewModel = new CurrentItemViewModel(order, this);


            mBinding.setViewModel(mCurrentItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }


        @Override
        public void onItemClicked(OrderModel ordersResponse) {
        mListener.onItemClicked(ordersResponse);
        }
    }

//    public class EmptyViewHolder extends BaseViewHolder implements servicesEmptyItemViewModel.ServicesEmptyItemViewModelListener {
//
//        private ItemServicesEmptyViewBinding mBinding;
//
//        public EmptyViewHolder(ItemServicesEmptyViewBinding binding) {
//            super(binding.getRoot());
//            this.mBinding = binding;
//        }
//
//        @Override
//        public void onBind(int position) {
//            ServicesEmptyItemViewModel emptyItemViewModel = new ServicesEmptyItemViewModel(this);
//            mBinding.setViewModel(emptyItemViewModel);
//        }
//
//        @Override
//        public void onRetryClick() {
//            mListener.onRetryClick();
//        }
//    }

    public interface CurrentAdapterListener {
        void onItemClicked(OrderModel ordersResponse);
    }
}