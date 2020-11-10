
package com.wecare.android.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.wecare.android.R;
import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.data.model.api.responses.SearchGiverResponse;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.data.model.api.responses.UserLocationResponse;
import com.wecare.android.ui.create_order.location.LocationAdapter;
import com.wecare.android.ui.create_order.relative.RelativeProfileAdapter;
import com.wecare.android.ui.create_order.services.OrderServicesAdapter;
import com.wecare.android.ui.main.order.current.CurrentAdapter;
import com.wecare.android.ui.main.order.sub.OrderSubServicesAdapter;
import com.wecare.android.ui.main.profile.transactions.TransactionsAdapter;
import com.wecare.android.ui.notification.NotificationsAdapter;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import com.wecare.android.ui.sub.ServicesAdapter;

import java.util.ArrayList;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;


public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(!WeCareUtils.isNullOrEmpty(url) ? url : R.drawable.ic_image_placeholder).into(imageView);
    }

    @BindingAdapter("imageUrlWithAuthorization")
    public static void setImageUrlAuthorization(ImageView imageView, String url) {
        Context context = imageView.getContext();

        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", "")
                .build());

        Glide.with(context).load(!WeCareUtils.isNullOrEmpty(url) ? glideUrl : R.drawable.default_profile_picture).into(imageView);

    }

    @BindingAdapter({"adapter"})
    public static void addHomeItems(RecyclerView recyclerView, ArrayList<MainServiceModel> serviceResponseArrayList) {
        ServicesAdapter adapter = (ServicesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(serviceResponseArrayList);
        }
    }

    @BindingAdapter({"subServiceSelectionAdapter"})
    public static void addOrderSubServicesItems(RecyclerView recyclerView, ArrayList<SubServiceResponse> serviceResponseArrayList) {
        OrderSubServicesAdapter adapter = (OrderSubServicesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(serviceResponseArrayList);
        }
    }

    @BindingAdapter({"orderAdapter"})
    public static void addOrderServicesItems(RecyclerView recyclerView, ArrayList<MainServiceModel> serviceResponseArrayList) {
        OrderServicesAdapter adapter = (OrderServicesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(serviceResponseArrayList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRelativeProfileItems(RecyclerView recyclerView, ArrayList<RelativeProfileResponse> responseArrayList) {
        RelativeProfileAdapter adapter = (RelativeProfileAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(responseArrayList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addUserLocationItems(RecyclerView recyclerView, ArrayList<UserLocationResponse> responseArrayList) {
        LocationAdapter adapter = (LocationAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(responseArrayList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addSearchGiverResponseItems(RecyclerView recyclerView, ArrayList<SearchGiverResponse> responseArrayList) {
        SuggestedGiverAdapter adapter = (SuggestedGiverAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(responseArrayList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addCurrentOrderItems(RecyclerView recyclerView, ArrayList<OrderModel> ordersResponseArrayList) {
        CurrentAdapter adapter = (CurrentAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(ordersResponseArrayList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addNotificationsItems(RecyclerView recyclerView, ArrayList<NotificationModel> notificationModels) {
        NotificationsAdapter adapter = (NotificationsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(notificationModels);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addTransactionsItems(RecyclerView recyclerView, ArrayList<TransactionsModel> notificationModels) {
        TransactionsAdapter adapter = (TransactionsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(notificationModels);
        }
    }
}
