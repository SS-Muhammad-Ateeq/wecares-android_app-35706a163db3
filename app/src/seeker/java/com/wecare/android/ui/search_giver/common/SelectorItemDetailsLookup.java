package com.wecare.android.ui.search_giver.common;

import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;
import com.wecare.android.utils.WeCareUtils;

public class SelectorItemDetailsLookup extends ItemDetailsLookup<Long> {

    private RecyclerView recyclerView;

    public SelectorItemDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof SuggestedGiverAdapter.SearchGiverViewHolder) {
                if (WeCareUtils.isPointInsideView(e.getRawX(), e.getRawY(), ((SuggestedGiverAdapter.SearchGiverViewHolder) viewHolder).mBinding.relativeUserImg)) {
                    return null;
                }
                return ((SuggestedGiverAdapter.SearchGiverViewHolder) viewHolder).getItemDetail();
            }
        }
        return null;
    }
}