
package com.wecare.android.ui.create_order.location;

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

import com.wecare.android.data.model.api.responses.UserLocationResponse;
import com.wecare.android.databinding.ItemUserLocationBinding;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.utils.WeCareUtils;

import java.util.ArrayList;
import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<UserLocationResponse> mUserLocationResponseList;

    private LocationAdapterListener mListener;

    private boolean isSummaryView;
    private SelectionTracker<Long> selectionTracker;


    public LocationAdapter(List<UserLocationResponse> responseList) {
        this.mUserLocationResponseList = responseList;
        isSummaryView = false;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    public void setListener(LocationAdapterListener listener) {
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

        ItemUserLocationBinding itemBinding = ItemUserLocationBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LocationViewHolder(itemBinding);
    }

    @Override
    public int getItemViewType(int position) {
//        if (mUserLocationResponseList != null && mUserLocationResponseList.size() > 0) {
        return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_EMPTY;
//        }
    }

    @Override
    public int getItemCount() {
//        if (mUserLocationResponseList != null && mUserLocationResponseList.size() > 0) {
        return mUserLocationResponseList.size();
//        } else {
//            return 1;
//        }
    }

    public void addOneItem(UserLocationResponse userLocationResponses) {
        mUserLocationResponseList.add(userLocationResponses);
        notifyDataSetChanged();
    }

    public void addItems(List<UserLocationResponse> userLocationResponses) {
        mUserLocationResponseList.addAll(userLocationResponses);
        notifyDataSetChanged();
    }

    public UserLocationResponse getItemAtPosition(int position) {
        return getUserLocationResponseList().get(position);
    }

    public List<UserLocationResponse> getUserLocationResponseList() {
        if (mUserLocationResponseList == null) {
            mUserLocationResponseList = new ArrayList<>();
        }
        return mUserLocationResponseList;
    }

    public void clearItems() {
        mUserLocationResponseList.clear();
    }

    public class LocationViewHolder extends BaseViewHolder implements LocationItemViewModel.ItemViewModelListener {
        //,ViewHolderWithDetails{

        private ItemUserLocationBinding mBinding;

        private LocationItemViewModel mItemViewModel;

        private SelectorItemDetail details;

        public LocationViewHolder(ItemUserLocationBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            details = new SelectorItemDetail();
        }

        @Override
        public void onBind(int position) {
            details.position = position;

            final UserLocationResponse obj = mUserLocationResponseList.get(position);

            if (isSummaryView) {
                mBinding.itemCheckedStatusCheckbox.setVisibility(View.GONE);
                mBinding.itemLocationNameTxt.setVisibility(View.GONE);
                mBinding.locationIcon.setVisibility(View.GONE);
                mBinding.locationDeleteImg.setVisibility(View.GONE);
                mBinding.locationEditImg.setVisibility(View.GONE);
            }
//            else {
//                mBinding.itemCheckedStatusCheckbox.setVisibility(View.VISIBLE);
//            }

            mItemViewModel = new LocationItemViewModel(obj, selectionTracker, details, this);

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

        SelectorItemDetail getItemDetail() {
            return details;
        }

        @Override
        public void onCheckedClick(UserLocationResponse userLocationResponseItem) {

        }

        @Override
        public void onItemClicked(UserLocationResponse userLocationResponseItem) {
            mListener.onItemClicked(userLocationResponseItem);
        }

        @Override
        public void onDeleteClicked(UserLocationResponse userLocationResponseItem) {
            mListener.onDeleteClicked(userLocationResponseItem);
        }

        @Override
        public void onEditClicked(UserLocationResponse userLocationResponseItem) {
            mListener.onEditClicked(userLocationResponseItem);
        }
    }

    public interface LocationAdapterListener {
        void onItemClicked(UserLocationResponse userLocationResponseItem);

        void onDeleteClicked(UserLocationResponse userLocationResponseItem);

        void onEditClicked(UserLocationResponse userLocationResponseItem);
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
                if (viewHolder instanceof LocationViewHolder) {
                    if (WeCareUtils.isPointInsideView(e.getRawX(), e.getRawY(), ((LocationViewHolder) viewHolder).mBinding.itemLocationNameTxt) ||
                            WeCareUtils.isPointInsideView(e.getRawX(), e.getRawY(), ((LocationViewHolder) viewHolder).mBinding.locationIcon) ||
                            WeCareUtils.isPointInsideView(e.getRawX(), e.getRawY(), ((LocationViewHolder) viewHolder).mBinding.locationDeleteImg)) {
                        return null;
                    }
                    return ((LocationViewHolder) viewHolder).getItemDetail();
                }
            }
            return null;
        }
    }


}