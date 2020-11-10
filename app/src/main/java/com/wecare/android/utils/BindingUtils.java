//
//package com.wecare.android.utils;
//
//import android.content.Context;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.wecare.android.R;
//import com.wecare.android.data.model.api.responses.OrdersResponse;
//import com.wecare.android.data.model.api.responses.MainServiceModel;
//import com.wecare.android.ui.sub.ServicesAdapter;
//import com.wecare.android.ui.main.order.current.CurrentAdapter;
//
//import java.util.ArrayList;
//
//import androidx.databinding.BindingAdapter;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//public final class BindingUtils {
//
//    private BindingUtils() {
//        // This class is not publicly instantiable
//    }
//
//    @BindingAdapter("imageUrl")
//    public static void setImageUrl(ImageView imageView, String url) {
//        Context context = imageView.getContext();
//        Glide.with(context).load(!url.isEmpty() ? url : R.drawable.default_profile_picture).into(imageView);
//    }
//
//    @BindingAdapter({"adapter"})
//    public static void addHomeItems(RecyclerView recyclerView, ArrayList<MainServiceModel> serviceResponseArrayList) {
//        ServicesAdapter adapter = (ServicesAdapter) recyclerView.getAdapter();
//        if (adapter != null) {
//            adapter.clearItems();
//            adapter.addItems(serviceResponseArrayList);
//        }
//    }
//
////    @BindingAdapter({"adapter"})
////    public static void addRelativeProfileItems(RecyclerView recyclerView, ArrayList<RelativeProfileResponse> responseArrayList) {
////        RelativeProfileAdapter adapter = (RelativeProfileAdapter) recyclerView.getAdapter();
////        if (adapter != null) {
////            adapter.clearItems();
////            adapter.addItems(responseArrayList);
////        }
////    }
//
//    @BindingAdapter({"adapter"})
//    public static void addCurrentOrderItems(RecyclerView recyclerView, ArrayList<OrdersResponse> ordersResponseArrayList) {
//        CurrentAdapter adapter = (CurrentAdapter) recyclerView.getAdapter();
//        if (adapter != null) {
//            adapter.clearItems();
//            adapter.addItems(ordersResponseArrayList);
//        }
//    }
//}
