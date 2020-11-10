//package com.wecare.android.ui.create_order.relative;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.selection.ItemDetailsLookup;
//
//import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
//
//public class RelativeItemDetail extends ItemDetailsLookup.ItemDetails {
//
//    private final int adapterPosition;
//    private final RelativeProfileResponse selectionKey;
//
//    public RelativeItemDetail(int adapterPosition, RelativeProfileResponse selectionKey) {
//        this.adapterPosition = adapterPosition;
//        this.selectionKey = selectionKey;
//    }
//
//    @Override
//    public int getPosition() {
//        return adapterPosition;
//    }
//
//    @Nullable
//    @Override
//    public Object getSelectionKey() {
//        return selectionKey;
//    }
//}