//package com.wecare.android.ui.create_order.relative;
//
//import android.view.MotionEvent;
//import android.view.View;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.selection.ItemDetailsLookup;
//import androidx.recyclerview.widget.RecyclerView;
//import com.google.android.material.checkbox.MaterialCheckBox;
//
//public class RelativeItemLookup extends ItemDetailsLookup {
//
//    private final RecyclerView recyclerView;
//
//    public RelativeItemLookup(RecyclerView recyclerView) {
//        this.recyclerView = recyclerView;
//    }
//
//    @Nullable
//    @Override
//    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
//        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
//        if (view != null) {
//            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
////            if (viewHolder instanceof RelativeProfileAdapter.RelativeProfileViewHolder) {
////                return ((RelativeProfileAdapter.RelativeProfileViewHolder) viewHolder).getItemDetails();
////            }
//            if (view instanceof MaterialCheckBox) {
//                return ((RelativeProfileAdapter.RelativeProfileViewHolder) viewHolder).getItemDetailsWithObject();
//            }
//        }
//
//        return null;
//    }
//}