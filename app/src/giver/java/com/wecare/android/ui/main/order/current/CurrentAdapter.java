
package com.wecare.android.ui.main.order.current;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecare.android.R;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.ItemCurrentOrderBinding;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class CurrentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

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


        ItemCurrentOrderBinding currentOrderBinding = ItemCurrentOrderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CurrentOrderViewHolder(currentOrderBinding);
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
                order.setPosition(position);
            mCurrentItemViewModel = new CurrentItemViewModel(order, this);

            mBinding.setViewModel(mCurrentItemViewModel);

//            if (ordersType.equals(AppConstants.ORDERS_STATUS_PENDING)){
//                mBinding.orderCancelTxt.setText(context.getResources().getString(R.string.reject_order));
//                mBinding.orderAcceptTxt.setText(context.getResources().getString(R.string.accept));
//            }
//            else {
//                mBinding.orderCancelTxt.setText(context.getResources().getString(R.string.cancel_order));
//
//                if (order.getOrderStatusModel().getId()== AppConstants.ORDER_STATUS_ACCEPTED){
//                    mBinding.orderAcceptTxt.setText(context.getResources().getString(R.string.start));
//                }
//                else if (order.getOrderStatusModel().getId()== AppConstants.ORDER_STATUS_CARING){
//                    mBinding.orderAcceptTxt.setText(context.getResources().getString(R.string.finish_order));
//                }
//            }


            switch (order.getOrderStatusModel().getId()) {
                case AppConstants.ORDER_STATUS_PENDING:
                    mBinding.orderCancelTxt.setText(context.getResources().getString(R.string.reject_order));
                    mBinding.orderAcceptTxt.setText(context.getResources().getString(R.string.accept));
                    mBinding.orderStatusTxt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_corner_button_orange));

                    break;
                case AppConstants.ORDER_STATUS_ACCEPTED:
                    mBinding.orderCancelTxt.setText(context.getResources().getString(R.string.cancel_order));
                    mBinding.orderAcceptTxt.setText(context.getResources().getString(R.string.start));
                    mBinding.orderStatusTxt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_corners_button_green));

                    break;
                case AppConstants.ORDER_STATUS_CARING:
                    mBinding.orderCancelTxt.setText(context.getResources().getString(R.string.cancel_order));
                    mBinding.orderAcceptTxt.setText(context.getResources().getString(R.string.finish_order));
                    mBinding.orderStatusTxt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_corners_button_green));



                    break;
            }



            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }


        @Override
        public void onCancelOrRejectClick(OrderModel ordersResponse) {
            if (ordersType.equals(AppConstants.ORDERS_STATUS_PENDING))
                mListener.onRejectClicked(ordersResponse.getId()+"",ordersResponse.getPosition());

            else
                mListener.onCancelClicked(ordersResponse.getId()+"",ordersResponse.getPosition());
        }

        @Override
        public void onStartOrAcceptClick(OrderModel ordersResponse) {
            if (ordersType.equals(AppConstants.ORDERS_STATUS_PENDING))
                mListener.onAcceptClicked(ordersResponse.getId()+"",ordersResponse.getPosition());

            else
                mListener.onStartClicked(ordersResponse.getId()+"",ordersResponse.getPosition());
        }

        @Override
        public void onItemClicked(OrderModel ordersResponse) {
            mListener.onItemClicked(ordersResponse);
        }
    }

    public interface CurrentAdapterListener {
        void onItemClicked(OrderModel ordersResponse);
        void onCancelClicked(String orderID,int position);
        void onRejectClicked(String orderID,int position);
        void onStartClicked(String orderID,int position);
        void onAcceptClicked(String orderID,int position);
    }
}