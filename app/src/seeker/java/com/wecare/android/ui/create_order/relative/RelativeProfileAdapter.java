
package com.wecare.android.ui.create_order.relative;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.databinding.ItemRelativeProfileBinding;
import com.wecare.android.interfaces.ViewHolderWithDetails;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.utils.WeCareUtils;

import java.util.ArrayList;
import java.util.List;


public class RelativeProfileAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<RelativeProfileResponse> mRelativeProfileResponseList;

    private RelativeProfileAdapterListener mListener;

    private boolean isSummaryView;
    private SelectionTracker<Long> selectionTracker;


    public RelativeProfileAdapter(List<RelativeProfileResponse> responseList) {
        this.mRelativeProfileResponseList = responseList;
        isSummaryView = false;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    public void setListener(RelativeProfileAdapterListener listener) {
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

        ItemRelativeProfileBinding itemBinding = ItemRelativeProfileBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new RelativeProfileViewHolder(itemBinding);
    }

    @Override
    public int getItemViewType(int position) {
//        if (mRelativeProfileResponseList != null && mRelativeProfileResponseList.size() > 0) {
        return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_EMPTY;
//        }
    }

    @Override
    public int getItemCount() {
//        if (mRelativeProfileResponseList != null && mRelativeProfileResponseList.size() > 0) {
        return mRelativeProfileResponseList.size();
//        } else {
//            return 1;
//        }
    }

    public void addItems(List<RelativeProfileResponse> profileResponseList) {
        mRelativeProfileResponseList.addAll(profileResponseList);
        notifyDataSetChanged();
    }

    public RelativeProfileResponse getItemAtPosition(int position) {
        return getRelativeProfileResponseList().get(position);
    }

    public List<RelativeProfileResponse> getRelativeProfileResponseList() {
        if (mRelativeProfileResponseList == null) {
            mRelativeProfileResponseList = new ArrayList<>();
        }
        return mRelativeProfileResponseList;
    }

    public void clearItems() {
        mRelativeProfileResponseList.clear();
    }

    public class RelativeProfileViewHolder extends BaseViewHolder implements RelativeProfileItemViewModel.ItemViewModelListener {
        //,ViewHolderWithDetails{

        private ItemRelativeProfileBinding mBinding;

        private RelativeProfileItemViewModel mItemViewModel;

        private RelativeItemDetail details;

        public RelativeProfileViewHolder(ItemRelativeProfileBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            details = new RelativeItemDetail();
        }

        @Override
        public void onBind(int position) {
            details.position = position;

            final RelativeProfileResponse obj = mRelativeProfileResponseList.get(position);

            if (isSummaryView) {
//                mBinding.itemCheckedStatusCheckbox.setVisibility(View.GONE);
//                mBinding.itemDeleteImg.setVisibility(View.GONE);
//                mBinding.itemEditImg.setVisibility(View.GONE);
            }

            mItemViewModel = new RelativeProfileItemViewModel(obj, selectionTracker, details, this);

            mBinding.setViewModel(mItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

//            if (selectionTracker != null) {
//                mBinding.getViewModel().relativeCheckedStatusCheckbox = new ObservableField<>(selectionTracker.isSelected(details.getSelectionKey()));
//                if (selectionTracker.isSelected(details.getSelectionKey())) {
//                    mBinding.getRoot().setActivated(true);
//                } else {
//                    mBinding.getRoot().setActivated(false);
//                }
//            }
        }

        @Override
        public void onCheckedClick(RelativeProfileResponse relativeProfileResponse) {

        }

        @Override
        public void onImageClicked(RelativeProfileResponse relativeProfileResponseItem) {
            mListener.onItemClicked(relativeProfileResponseItem);
        }

        @Override
        public void onDeleteClicked(RelativeProfileResponse relativeProfileResponseItem) {
            mListener.onDeleteClicked(relativeProfileResponseItem);
        }

        RelativeItemDetail getRelativeItemDetail() {
            return details;
        }

//        @Override
//        public RelativeItemLookup.ItemDetails getItemDetails() {
//            return new com.wecare.android.ui.create_order.relative.RelativeItemDetail(getAdapterPosition(), mRelativeProfileResponseList.get(getAdapterPosition()));
//        }
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

    public interface RelativeProfileAdapterListener {
        void onItemClicked(RelativeProfileResponse relativeProfileResponse);

        void onDeleteClicked(RelativeProfileResponse relativeProfileResponse);
    }

    //////////////////////////////////////////////////
    //////// classes for selector mode.
    //////////////////////////////////////////////////

    static class RelativeItemDetail extends ItemDetailsLookup.ItemDetails<Long> {

        long position;

        RelativeItemDetail() {
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

    static class RelativeItemDetailsLookup extends ItemDetailsLookup<Long> {

        private RecyclerView recyclerView;

        RelativeItemDetailsLookup(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if (viewHolder instanceof RelativeProfileViewHolder) {
                    if (WeCareUtils.isPointInsideView(e.getRawX(), e.getRawY(), ((RelativeProfileViewHolder) viewHolder).mBinding.relativeUserImg) ||
                            WeCareUtils.isPointInsideView(e.getRawX(), e.getRawY(), ((RelativeProfileViewHolder) viewHolder).mBinding.itemDeleteImg)) {

                        return null;
                    }
                    return ((RelativeProfileViewHolder) viewHolder).getRelativeItemDetail();
                }
            }
            return null;
        }
    }

}