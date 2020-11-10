package com.wecare.android.ui.main.order.scheduled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecare.android.R;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.ItemScheduleSummaryBinding;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ScheduleSummaryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<OrderModel> mOrderResponseList;

    String ordersType;

    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setOrdersType(String ordersType) {
        this.ordersType = ordersType;
    }

    private ScheduleSummaryAdapter.ScheduleSummaryAdapterListener mListener;

    public ScheduleSummaryAdapter(List<OrderModel> orderResponseList) {
        this.mOrderResponseList = orderResponseList;
    }

    public void setListener(ScheduleSummaryAdapter.ScheduleSummaryAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        ItemScheduleSummaryBinding currentOrderBinding = ItemScheduleSummaryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ScheduleSummaryViewHolder(currentOrderBinding);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mOrderResponseList.size();

    }

    public void addItems(List<OrderModel> orderResponseList) {
        mOrderResponseList.addAll(orderResponseList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mOrderResponseList.clear();
    }

    public class ScheduleSummaryViewHolder extends BaseViewHolder implements ScheduleSummaryItemViewModel.ScheduleSummaryItemViewModelListener {

        private ItemScheduleSummaryBinding mBinding;

        private ScheduleSummaryItemViewModel scheduleSummaryViewModel;

        public ScheduleSummaryViewHolder(ItemScheduleSummaryBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            final OrderModel order = mOrderResponseList.get(position);
            order.setPosition(position);
            scheduleSummaryViewModel = new ScheduleSummaryItemViewModel(order, this);

            mBinding.setViewModel(scheduleSummaryViewModel);

            if (order.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_ACCEPTED)
                mBinding.colorView.setBackgroundColor(context.getResources().getColor(R.color.green));
            else if (order.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_REJECTED)
                mBinding.colorView.setBackgroundColor(context.getResources().getColor(R.color.red));


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

    public interface ScheduleSummaryAdapterListener {
        void onItemClicked(OrderModel ordersResponse);

    }
}