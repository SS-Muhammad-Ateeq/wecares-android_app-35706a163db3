package com.wecare.android.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.NotificationModel;
import com.wecare.android.databinding.ActivityNotificationsBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.DialogFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsActivity extends BaseActivity<ActivityNotificationsBinding, NotificationsViewModel> implements NotificationsActivityNavigator, NotificationsAdapter.NotificationsAdapterListener {

    @Inject
    ViewModelProviderFactory factory;
    private NotificationsViewModel viewModel;

    @Inject
    NotificationsAdapter notificationsAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;


    ActivityNotificationsBinding binding;

    @Override
    public NotificationsViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(NotificationsViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notifications;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.notifications), true);
        viewModel.setNavigator(this);
        subscribeToLiveData();
        setUp();
        viewModel.fetchNotifications();
    }

    @Override
    public void onClearNotifications() {

    }

    @Override
    public void onNotificationDeletedSuccessfully(NotificationModel notificationModel) {
        viewModel.getNotificationModelObservableArrayList().remove(notificationModel.getPosition());
        setUp();
    }

    @Override
    public void notificationFetched() {
        viewModel.markNotificationsAsMarked();
    }

    private void subscribeToLiveData() {
        viewModel.getListMutableLiveData().observe(this, new Observer<List<NotificationModel>>() {
            @Override
            public void onChanged(@Nullable List<NotificationModel> notificationModels) {
                viewModel.addNotificationItemsToList(notificationModels);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editItem:

                notificationsAdapter.setEditMode(!notificationsAdapter.isEditMode());
                item.setTitle(notificationsAdapter.isEditMode() ? getString(R.string.done) : getString(R.string.edit));
//                binding.removeNotificationsTxt.setVisibility(notificationsAdapter.isEditMode() ? View.VISIBLE : View.GONE);
                setUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUp() {
        notificationsAdapter.setmListener(this);
        notificationsAdapter.setContext(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.notificationsRecycler.setLayoutManager(mLayoutManager);
        binding.notificationsRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.notificationsRecycler.setAdapter(notificationsAdapter);


    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, NotificationsActivity.class);
    }

    @Override
    public void onDeleteClicked(NotificationModel notificationModel) {
        DialogFactory.createErrorDialog(this, getString(R.string.delete_notification), getString(R.string.delete_notification_disclaimer), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                viewModel.deleteNotifications(notificationModel);
            }
        });
    }
}
