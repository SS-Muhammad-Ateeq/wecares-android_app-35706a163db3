package com.wecare.android.ui.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecare.android.R;
import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.databinding.ItemNotificationBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<NotificationModel> notificationModels;

    private NotificationsAdapterListener mListener;
    Context context;

    public void setmListener(NotificationsAdapterListener mListener) {
        this.mListener = mListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

     boolean isEditMode = false;

    public boolean isEditMode() {
        return isEditMode;
    }

    public NotificationsAdapter(ArrayList<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
        isEditMode = false;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemNotificationBinding notificationBinding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationsAdapter.notificationViewHolder(notificationBinding);

    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public void addItems(ArrayList<NotificationModel> notificationModels) {
        clearItems();
        this.notificationModels.addAll(notificationModels);
        notifyDataSetChanged();
    }

    public void clearItems() {
        notificationModels.clear();
    }

    public NotificationModel getItemAtPosition(int position) {
        return notificationModels.get(position);
    }

    public class notificationViewHolder extends BaseViewHolder implements NotificationsItemViewModel.NotificationItemViewModelListener {

        private ItemNotificationBinding mBinding;

        private NotificationsItemViewModel notificationsItemViewModel;

        public notificationViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }


        @Override
        public void onBind(int position) {

            final NotificationModel notificationModel = notificationModels.get(position);
            notificationModel.setPosition(position);
            notificationsItemViewModel = new NotificationsItemViewModel(notificationModel, this);

            mBinding.setViewModel(notificationsItemViewModel);

            //handle labeled color on each notification item
            int colorRes = 0;
            switch (position % 3) {
                case 0:
                    colorRes = R.color.notification_light_blue;
                    break;
                case 1:
                    colorRes = R.color.notification_light_gray;
                    break;
                case 2:
                    colorRes = R.color.notification_light_orange;
                    break;
            }
            mBinding.colorView.setBackgroundColor(ContextCompat.getColor(context, colorRes));

//            if (isEditMode)
//                mBinding.itemDeleteImg.setVisibility(View.VISIBLE);
//            else
//                mBinding.itemDeleteImg.setVisibility(View.GONE);


            mBinding.executePendingBindings();
//                mBinding.getRoot().getContext().getString()

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
//            mBinding.executePendingBindings();
        }


        @Override
        public void onNotificationDeleteClicked(NotificationModel notificationModel) {
            mListener.onDeleteClicked(notificationModel);
        }
    }

    public interface NotificationsAdapterListener {
        void onDeleteClicked(NotificationModel notificationModel);
    }
}


