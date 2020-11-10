
package com.wecare.android.ui.main.home.sub;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ItemSubServicesBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.List;


public class SubServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    public static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<SubServiceResponse> mSubServiceResponseList;

    private SubServiceAdapterListener mListener;

    public SubServicesAdapter(List<SubServiceResponse> subServiceResponses) {
        this.mSubServiceResponseList = subServiceResponses;
    }

    public void setListener(SubServiceAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemSubServicesBinding subServicesBinding = ItemSubServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SubServiceViewHolder(subServicesBinding);
    }

    @Override
    public int getItemViewType(int position) {
//        if (mSubServiceResponseList != null && mSubServiceResponseList.size() > 0) {
        return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_EMPTY;
//        }
    }

    @Override
    public int getItemCount() {
//        if (mSubServiceResponseList != null && mSubServiceResponseList.size() > 0) {
        return mSubServiceResponseList.size();
//        } else {
//            return 1;
//        }
    }

    public void addItems(List<SubServiceResponse> subServiceResponseList) {
        mSubServiceResponseList.addAll(subServiceResponseList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mSubServiceResponseList.clear();
    }

    public class SubServiceViewHolder extends BaseViewHolder implements SubServicesItemViewModel.SubServiceItemViewModelListener {

        private ItemSubServicesBinding mBinding;

        private SubServicesItemViewModel mSubServicesItemViewModel;

        public SubServiceViewHolder(ItemSubServicesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            final SubServiceResponse service = mSubServiceResponseList.get(position);

            mSubServicesItemViewModel = new SubServicesItemViewModel(service, this);

            mBinding.setViewModel(mSubServicesItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }

        @Override
        public void onItemClick(SubServiceResponse subServiceResponse) {
            //navigate to sub view.
            mListener.onItemClicked(subServiceResponse);
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

    public interface SubServiceAdapterListener {
        void onItemClicked(SubServiceResponse subServiceResponse);
    }
}