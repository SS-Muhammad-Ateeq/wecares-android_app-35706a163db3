package com.wecare.android.ui.main.order.finishorder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ItemAddedSubServiceBinding;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.ui.main.order.AddedSubServiceItemViewModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AddedSubServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int SUMMARY_VIEWING_TYPE = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<SubServiceResponse> mSubServiceResponseList;

    private SubServiceAdapterListener mListener;

    private boolean isSummaryView;

    String currency;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AddedSubServicesAdapter(ArrayList<SubServiceResponse> subServiceResponses) {
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

                ItemAddedSubServiceBinding subServicesBinding = ItemAddedSubServiceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new SubServiceViewHolder(subServicesBinding);

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

    public class SubServiceViewHolder extends BaseViewHolder implements AddedSubServiceItemViewModel.AddedItemViewModelListener {

        private ItemAddedSubServiceBinding mBinding;

        private AddedSubServiceItemViewModel mOrderSubServicesItemViewModel;

        public SubServiceViewHolder(ItemAddedSubServiceBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }


        @Override
        public void onBind(int position) {

            final SubServiceResponse service = mSubServiceResponseList.get(position);
            service.setPosition(position);
            service.setCurrency(currency);
            service.setHourlyDuration(service.getHours());
            mOrderSubServicesItemViewModel = new AddedSubServiceItemViewModel(service, this);


                mBinding.setViewModel(mOrderSubServicesItemViewModel);
                mBinding.executePendingBindings();
//                mBinding.getRoot().getContext().getString()

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
        public void onPriceChanged(int position, double newValue,boolean isAdd) {
            mListener.onPriceChanged(position,newValue,isAdd);
        }
    }

    public interface SubServiceAdapterListener {
        void onItemClicked(SubServiceResponse subServiceResponse);

        void onNoItemLeft();

        void onPriceChanged(int position, double newValue,boolean isAdd);
    }

}
