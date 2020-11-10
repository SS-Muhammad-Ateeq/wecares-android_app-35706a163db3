
package com.wecare.android.ui.main.order.sub;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ItemOrderSubServicesBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.List;


public class OrderSubServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    public static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<SubServiceResponse> mSubServiceResponseList;

    private SubServiceAdapterListener mListener;

    private SelectionTracker<Long> selectionTracker;

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    public OrderSubServicesAdapter(List<SubServiceResponse> subServiceResponses) {
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

        ItemOrderSubServicesBinding subServicesBinding = ItemOrderSubServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
    public SubServiceResponse getItemAtPosition(int position) {
        return mSubServiceResponseList.get(position);
    }

    public List<SubServiceResponse> getmSubServiceResponseList() {
        return mSubServiceResponseList;
    }

    public void setmSubServiceResponseList(List<SubServiceResponse> mSubServiceResponseList) {
        this.mSubServiceResponseList = mSubServiceResponseList;
    }

    public class SubServiceViewHolder extends BaseViewHolder implements OrderSubServicesItemViewModel.SubServiceItemViewModelListener {

        private ItemOrderSubServicesBinding mBinding;

        private OrderSubServicesItemViewModel mOrderSubServicesItemViewModel;

        private SelectorItemDetail details;

        public SubServiceViewHolder(ItemOrderSubServicesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

            details = new SelectorItemDetail();
        }

        @Override
        public void onBind(int position) {
            details.position = position;

            final SubServiceResponse service = mSubServiceResponseList.get(position);

            mOrderSubServicesItemViewModel = new OrderSubServicesItemViewModel(service, selectionTracker, details, this);

            mBinding.setViewModel(mOrderSubServicesItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }

        SelectorItemDetail getItemDetail() {
            return details;
        }

        @Override
        public void onCheckedClick(SubServiceResponse subServiceResponse) {
            //navigate to sub view.
//            mListener.onItemClicked(subServiceResponse);
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


    //////////////////////////////////////////////////
    //////// classes for selector mode.
    //////////////////////////////////////////////////

    static class SelectorItemDetail extends ItemDetailsLookup.ItemDetails<Long> {

        long position;

        SelectorItemDetail() {
        }

        @Override
        public int getPosition() {
            return (int) position;
        }

        @Nullable
        @Override
        public Long getSelectionKey() {
            return position;
        }

        @Override
        public boolean inSelectionHotspot(@NonNull MotionEvent e) {
            return true;
        }
    }

    static class KeyProvider extends ItemKeyProvider<Long> {

        KeyProvider(RecyclerView.Adapter adapter) {
            super(ItemKeyProvider.SCOPE_MAPPED);
        }

        @Nullable
        @Override
        public Long getKey(int position) {
            return (long) position;
        }

        @Override
        public int getPosition(@NonNull Long key) {
            long value = key;
            return (int) value;
        }
    }

    static class SelectorItemDetailsLookup extends ItemDetailsLookup<Long> {

        private RecyclerView recyclerView;

        SelectorItemDetailsLookup(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if (viewHolder instanceof SubServiceViewHolder) {
                    return ((SubServiceViewHolder) viewHolder).getItemDetail();
                }
            }
            return null;
        }
    }
}