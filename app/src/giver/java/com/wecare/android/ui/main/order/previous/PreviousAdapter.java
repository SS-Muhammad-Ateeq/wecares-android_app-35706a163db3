
package com.wecare.android.ui.main.order.previous;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecare.android.R;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.ItemPreviousOrderBinding;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class PreviousAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<OrderModel> mMainServiceModelList;

    private previousAdapterListener mListener;

    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public PreviousAdapter(List<OrderModel> mainServiceModelList) {
        this.mMainServiceModelList = mainServiceModelList;
    }

    public void setListener(previousAdapterListener listener) {
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
//                ItemServiceMainBinding emptyViewBinding = ItemServiceMainBinding.inflate(LayoutInflater.from(parent.getContext()),
//                        parent, false);
//                return new SubServiceViewHolder(sViewBinding);
//            case VIEW_TYPE_EMPTY:
//            default:
        ItemPreviousOrderBinding binding = ItemPreviousOrderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new PrevoiusViewHolder(binding);
//        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (mMainServiceModelList != null && mMainServiceModelList.size() > 0) {
        return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_EMPTY;
//        }
    }

    @Override
    public int getItemCount() {
//        if (mMainServiceModelList != null && mMainServiceModelList.size() > 0) {
        return mMainServiceModelList.size();
//        } else {
//            return 1;
//        }
    }

    public void addItems(List<OrderModel> mainServiceModelList) {
        mMainServiceModelList.addAll(mainServiceModelList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mMainServiceModelList.clear();
    }

    public class PrevoiusViewHolder extends BaseViewHolder implements PreviousItemViewModel.previousItemViewModelListener {

        private ItemPreviousOrderBinding mBinding;

        private PreviousItemViewModel itemViewModel;

        public PrevoiusViewHolder(ItemPreviousOrderBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            final OrderModel orderModel = mMainServiceModelList.get(position);

            itemViewModel = new PreviousItemViewModel(orderModel, this);

            mBinding.setViewModel(itemViewModel);

            if (orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CANCELED || orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_SEEKER_CANCELED ) {
                mBinding.orderStatusTxt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_corners_button_red));
            } else {
                mBinding.orderStatusTxt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_corners_button_green));
            }

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }

        @Override
        public void onItemClick(OrderModel orderModel) {
            //navigate to sub view.
            mListener.onItemClicked(orderModel);
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

    public interface previousAdapterListener {
        void onItemClicked(OrderModel mainServiceModel);
    }
}