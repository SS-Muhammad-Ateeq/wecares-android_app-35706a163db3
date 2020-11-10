
package com.wecare.android.ui.create_order.services.sub_duration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.wecare.android.R;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ItemDurationSubServicesBinding;
import com.wecare.android.databinding.ItemDurationSubServicesSummaryBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.ArrayList;


public class DurationSubServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int SUMMARY_VIEWING_TYPE = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<SubServiceResponse> mSubServiceResponseList;

    private SubServiceAdapterListener mListener;

    private boolean isSummaryView;

    public DurationSubServicesAdapter(ArrayList<SubServiceResponse> subServiceResponses) {
        this.mSubServiceResponseList = subServiceResponses;
        isSummaryView = false;
    }

    public void setListener(SubServiceAdapterListener listener) {
        this.mListener = listener;
    }

    public void setSummaryView(boolean summaryView) {
        this.isSummaryView = summaryView;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SUMMARY_VIEWING_TYPE:
                ItemDurationSubServicesSummaryBinding summaryViewBinding = ItemDurationSubServicesSummaryBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new SubServiceViewHolder(summaryViewBinding);
            case VIEW_TYPE_NORMAL:
            default:
                ItemDurationSubServicesBinding subServicesBinding = ItemDurationSubServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new SubServiceViewHolder(subServicesBinding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isSummaryView) {
            return SUMMARY_VIEWING_TYPE;
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mSubServiceResponseList.size();
    }

    public void addItems(ArrayList<SubServiceResponse> subServiceResponseList) {
        clearItems();
        mSubServiceResponseList.addAll(subServiceResponseList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mSubServiceResponseList.clear();
    }

    public SubServiceResponse getItemAtPosition(int position) {
        return mSubServiceResponseList.get(position);
    }

    public class SubServiceViewHolder extends BaseViewHolder implements DurationSubServicesItemViewModel.DurationItemViewModelListener {

        private ItemDurationSubServicesBinding mBinding;
        private ItemDurationSubServicesSummaryBinding mSummaryBinding;

        private DurationSubServicesItemViewModel mOrderSubServicesItemViewModel;

        public SubServiceViewHolder(ItemDurationSubServicesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public SubServiceViewHolder(ItemDurationSubServicesSummaryBinding binding) {
            super(binding.getRoot());
            this.mSummaryBinding = binding;
        }

        @Override
        public void onBind(int position) {

            final SubServiceResponse service = mSubServiceResponseList.get(position);

            mOrderSubServicesItemViewModel = new DurationSubServicesItemViewModel(service, this);

            if (isSummaryView) {
                mSummaryBinding.itemTotalTxt.setVisibility(View.VISIBLE);
                mSummaryBinding.itemTaxesTxt.setVisibility(View.VISIBLE);
                mSummaryBinding.itemPricePerHourTxt.setVisibility(View.VISIBLE);

                mSummaryBinding.setViewModel(mOrderSubServicesItemViewModel);
                mSummaryBinding.executePendingBindings();
                mOrderSubServicesItemViewModel.durationText.set(String.format("%s %s", service.getHourlyDuration(),
                        mSummaryBinding.getRoot().getContext().getString(R.string.hours)));

            } else {
                mBinding.setViewModel(mOrderSubServicesItemViewModel);
                mBinding.executePendingBindings();
//                mBinding.getRoot().getContext().getString()
            }

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
//            mBinding.executePendingBindings();
        }

        @Override
        public void onSubDeleteClicked(SubServiceResponse subServiceResponse) {
            mSubServiceResponseList.remove(subServiceResponse);
            notifyItemRemoved(getAdapterPosition());
            if (mSubServiceResponseList.isEmpty()) {
                mListener.onNoItemLeft();
            }
        }

        @Override
        public void onPriceChanged(int position, double newValue, boolean isAdd) {

        }
    }

    public interface SubServiceAdapterListener {
        void onItemClicked(SubServiceResponse subServiceResponse);

        void onNoItemLeft();

    }

}