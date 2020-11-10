
package com.wecare.android.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wecare.android.R;
import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.TransactionsModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.ui.main.order.scheduled.ScheduleSummaryAdapter;
import com.wecare.android.ui.sub.ServicesAdapter;
import com.wecare.android.ui.main.order.current.CurrentAdapter;
import com.wecare.android.ui.main.order.previous.PreviousAdapter;
import com.wecare.android.ui.main.order.sub.OrderSubServicesAdapter;
import com.wecare.android.ui.main.profile.transactions.TransactionsAdapter;
import com.wecare.android.ui.main.profile.userProfile.services.UserServicesAdapter;
import com.wecare.android.ui.main.profile.userProfile.services.selection.UserSubServicesAdapter;
import com.wecare.android.ui.notification.NotificationsAdapter;

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
        Glide.with(context).load(url != null ? url : R.drawable.default_profile_picture).into(imageView);
    }

    @BindingAdapter({"adapter"})
    public static void addHomeItems(RecyclerView recyclerView, ArrayList<MainServiceModel> mainServiceModelArrayList) {
        ServicesAdapter adapter = (ServicesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(mainServiceModelArrayList);
        }
    }

    @BindingAdapter({"userServicesAdapter"})
    public static void addUserServicesItems(RecyclerView recyclerView, ArrayList<MainServiceModel> mainServiceModelArrayList) {
        UserServicesAdapter adapter = (UserServicesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(mainServiceModelArrayList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addUserSubServicesItems(RecyclerView recyclerView, ArrayList<SubServiceResponse> serviceResponseArrayList) {
        UserSubServicesAdapter adapter = (UserSubServicesAdapter) recyclerView.getAdapter();
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

    @BindingAdapter({"adapter"})
    public static void addCurrentOrderItems(RecyclerView recyclerView, ArrayList<OrderModel> ordersResponseArrayList) {
        CurrentAdapter adapter = (CurrentAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(ordersResponseArrayList);
        }
    }
    @BindingAdapter({"previousAdapter"})
    public static void addPreviousOrderItems(RecyclerView recyclerView, ArrayList<OrderModel> ordersResponseArrayList) {
        PreviousAdapter adapter = (PreviousAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(ordersResponseArrayList);
        }
    }

    @BindingAdapter({"scheduledAdapter"})
    public static void addScheduledOrderItems(RecyclerView recyclerView, ArrayList<OrderModel> ordersResponseArrayList) {
        ScheduleSummaryAdapter adapter = (ScheduleSummaryAdapter) recyclerView.getAdapter();
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
