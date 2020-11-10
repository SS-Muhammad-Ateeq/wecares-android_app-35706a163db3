//package com.wecare.android.ui.create_order.relative;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.selection.ItemKeyProvider;
//import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
//
//import java.util.List;
//
//public class RelativeItemKeyProvider extends ItemKeyProvider {
//
//    private final List<RelativeProfileResponse> itemList;
//
//    public RelativeItemKeyProvider(int scope, List<RelativeProfileResponse> itemList) {
//        super(scope);
//        this.itemList = itemList;
//    }
//
//    @Nullable
//    @Override
//    public RelativeProfileResponse getKey(int position) {
//        return itemList.get(position);
//    }
//
//    @Override
//    public int getPosition(@NonNull Object key) {
//        return itemList.indexOf(key);
//    }
//}