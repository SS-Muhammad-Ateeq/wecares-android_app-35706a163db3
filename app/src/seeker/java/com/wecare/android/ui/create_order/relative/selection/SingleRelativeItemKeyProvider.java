//package com.wecare.android.ui.create_order.relative;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.selection.ItemKeyProvider;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class SingleRelativeItemKeyProvider extends ItemKeyProvider<Long> {
//
//    private final RecyclerView recyclerView;
//
//    public SingleRelativeItemKeyProvider(RecyclerView recyclerView) {
//        super(ItemKeyProvider.SCOPE_MAPPED);
//        this.recyclerView = recyclerView;
//    }
//
//    @Nullable
//    @Override
//    public Long getKey(int position) {
////        return itemList.get(position);
//        return recyclerView.getAdapter().getItemId(position);
//    }
//
//    @Override
//    public int getPosition(@NonNull Long key) {
//        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForItemId(key);
//        return viewHolder != null ? viewHolder.getLayoutPosition() : RecyclerView.NO_POSITION;
//    }
//}