
package com.wecare.android.ui.search_giver.common;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import com.wecare.android.data.model.api.responses.SearchGiverResponse;
import com.wecare.android.databinding.ItemGiverBinding;
import com.wecare.android.databinding.ItemLoadmoreBinding;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.ui.search_giver.SearchGiverViewModel;
import com.wecare.android.ui.search_giver.suggested.SuggestedGiverItemViewModel;

import java.util.ArrayList;
import java.util.List;


public class SuggestedGiverAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_LOADMORE = -1;

    /**/
    private boolean loadMore;
    /**
     * when to load more givers
     */
    private int loadMorePos = 0;

    private List<SearchGiverResponse> searchGiverResponseList;

    private SearchGiverAdapterListener mListener;

    private boolean isFavouriteView;
    private boolean isSummaryView;
    private SelectionTracker<Long> selectionTracker;

    public SuggestedGiverAdapter(List<SearchGiverResponse> responseList) {
        this.searchGiverResponseList = responseList;
        isSummaryView = false;
        isFavouriteView = false;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    public void setListener(SearchGiverAdapterListener listener) {
        this.mListener = listener;
    }

    public void setSummaryView(boolean summaryView) {
        this.isSummaryView = summaryView;
    }

    public SuggestedGiverAdapter setFavouriteView(boolean favouriteView) {
        isFavouriteView = favouriteView;
        return this;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_LOADMORE:
                ItemLoadmoreBinding loadMoreBinding = ItemLoadmoreBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LoadMoreViewHolder(loadMoreBinding);
            case VIEW_TYPE_NORMAL:
            default:
                ItemGiverBinding itemBinding = ItemGiverBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new SearchGiverViewHolder(itemBinding);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (searchGiverResponseList != null && searchGiverResponseList.size() > 0) {//handle empty.
        if (searchGiverResponseList.size() > 0 && searchGiverResponseList.get(position) == null) {
            return VIEW_TYPE_LOADMORE;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
//        if (searchGiverResponseList != null && searchGiverResponseList.size() > 0) {
        return searchGiverResponseList.size();
//        } else {
//            return 1;
//        }
    }

    public void addOneItem(SearchGiverResponse searchGiverResponse) {
        this.searchGiverResponseList.add(searchGiverResponse);
        notifyDataSetChanged();
    }

    public void addItems(List<SearchGiverResponse> searchGiverResponseList) {
        this.searchGiverResponseList.addAll(searchGiverResponseList);
        notifyDataSetChanged();
    }

    public SearchGiverResponse getItemAtPosition(int position) {
        return getSearchGiverResponseList().get(position);
    }

    public List<SearchGiverResponse> getSearchGiverResponseList() {
        if (searchGiverResponseList == null) {
            searchGiverResponseList = new ArrayList<>();
        }
        return searchGiverResponseList;
    }

    public void clearItems() {
        searchGiverResponseList.clear();
    }

    //////////////////////////////////////
    ////// action method on recyclerView
    //////////////////////////////////////
    private void loadMore(int position) {
        if ((position >= loadMorePos) && loadMore) {
            loadMore = false;
            mListener.onLoadMore();
        }
    }

    public void addLoader() {
        searchGiverResponseList.add(new SearchGiverResponse());
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                try {
                    notifyItemInserted(searchGiverResponseList.size() - 1);
                } catch (IndexOutOfBoundsException e) {
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void removeLoader() {
        final int LastPos = searchGiverResponseList.size() - 1;
        if (searchGiverResponseList.get(LastPos).getCaregiver() != null) {
            searchGiverResponseList.remove(LastPos);
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    try {
                        notifyItemRemoved(LastPos);
                    } catch (IndexOutOfBoundsException e) {
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public boolean updateList(boolean isRefresh, ArrayList<SearchGiverResponse> giverResponseArrayList) {
        try {
            if (isRefresh) {
                searchGiverResponseList.clear();
            }
            int initPos = searchGiverResponseList.size();
            searchGiverResponseList.addAll(giverResponseArrayList);
//            notifyItemRangeInserted(0, searchGiverResponseList.size());
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
            loadMore = true;
            loadMorePos = (searchGiverResponseList.size() - SearchGiverViewModel.RemainingGiversToLoadMore);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * change the loadmore value
     */
    public SuggestedGiverAdapter setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
        return this;
    }

    public class LoadMoreViewHolder extends BaseViewHolder {
        private ItemLoadmoreBinding mBinding;

//        private SuggestedGiverItemViewModel mItemViewModel;

        public LoadMoreViewHolder(ItemLoadmoreBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final SearchGiverResponse obj = searchGiverResponseList.get(position);

//            mItemViewModel = new SuggestedGiverItemViewModel(obj, selectionTracker, details, this);
//
//            mBinding.setViewModel(mItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
//            mBinding.executePendingBindings();

        }
    }

    public class SearchGiverViewHolder extends BaseViewHolder implements SuggestedGiverItemViewModel.ItemViewModelListener {
        //,ViewHolderWithDetails{

        protected ItemGiverBinding mBinding;

        private SuggestedGiverItemViewModel mItemViewModel;

        private SelectorItemDetail details;

        public SearchGiverViewHolder(ItemGiverBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            details = new SelectorItemDetail();
        }

        @Override
        public void onBind(int position) {
            loadMore(position);
            details.position = position;

            final SearchGiverResponse obj = searchGiverResponseList.get(position);

            if (isSummaryView) {
                mBinding.itemCheckedStatusCheckbox.setVisibility(View.GONE);
            } else {
                mBinding.itemCheckedStatusCheckbox.setVisibility(View.VISIBLE);
            }

            if (isFavouriteView) {
                mBinding.favouriteImage.setVisibility(View.VISIBLE);
                mBinding.itemGiverStatusTxt.setVisibility(View.VISIBLE);
                mBinding.itemGiverStatusTxt.setText(obj.getCaregiver().getStatusLabel());
            }

            mItemViewModel = new SuggestedGiverItemViewModel(obj, selectionTracker, details, this);

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
        public void onCheckedClick(SearchGiverResponse searchGiverResponse) {

        }

        @Override
        public void onProfileImageClick(SearchGiverResponse searchGiverResponse) {
            mListener.onItemClicked(searchGiverResponse);
        }
    }

    public interface SearchGiverAdapterListener {
        void onItemClicked(SearchGiverResponse searchGiverResponse);

        void onLoadMore();
    }

}