
package com.wecare.android.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityMainBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.base.BaseViewPagerAdapter;
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.login.LoginActivity;
import com.wecare.android.ui.main.guest.GuestFragment;
import com.wecare.android.ui.main.order.OrderFragment;
import com.wecare.android.ui.main.profile.ProfileMainFragment;
import com.wecare.android.ui.main.settings.SettingsFragment;
import com.wecare.android.ui.notification.NotificationsActivity;
import com.wecare.android.ui.sub.ServicesFragment;
import com.wecare.android.utils.WeCareUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    ViewModelProviderFactory factory;
    private MainViewModel viewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    BaseViewPagerAdapter viewPagerAdapter;

    /**/
    private BottomNavigationView bottomNavigationView;
    /**/
    private ViewPager mViewPager;

    /**/
    private int numberOfNotification;
    private TextView toolbarNotificationNumTv;
    private ImageButton toolbarNotificationBtn;

    ActivityMainBinding mActivityMainBinding;
    private MenuItem prevMenuItem;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        viewModel.setNavigator(this);
        setUp();
        viewModel.getUserInfo();
        getCurrentToken();
    }


    private void getCurrentToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        viewModel.getDataManager().updateApiInfoHeader(token);
                        viewModel.getDataManager().setPushToken(token);
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getNotificationsCount();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//}
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Drawable drawable = item.getIcon();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//        }
//        switch (item.getItemId()) {
//            case R.id.action_cut:
//                return true;
//            case R.id.action_copy:
//                return true;
//            case R.id.action_share:
//                return true;
//            case R.id.action_delete:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void setUp() {
        addToolbar(R.id.toolbar, getString(R.string.services), false);

        toolbarNotificationNumTv = mActivityMainBinding.toolbar.toolbarNotificationNumTv;
        toolbarNotificationBtn = mActivityMainBinding.toolbar.toolbarNotificationBtn;

        updateNotificationToolbarNumber();
        toolbarNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotificationClicked(v);
            }
        });
        mActivityMainBinding.toolbar.toolbarScheduleBtn.setVisibility(View.GONE);

        bottomNavigationView = mActivityMainBinding.mainBottomView;
        mViewPager = mActivityMainBinding.mainViewPager;

        viewPagerAdapter.addFrag(ServicesFragment.newInstance(), getString(R.string.wecare));
        if (!WeCareUtils.isNullOrEmpty(getViewModel().getDataManager().getCurrentUserId())) {
            viewPagerAdapter.addFrag(ProfileMainFragment.newInstance(), getString(R.string.profile));
            viewPagerAdapter.addFrag(OrderFragment.newInstance(), getString(R.string.order));
        } else {
            viewPagerAdapter.addFrag(GuestFragment.newInstance(getString(R.string.profile)), getString(R.string.profile));
            viewPagerAdapter.addFrag(GuestFragment.newInstance(getString(R.string.order)), getString(R.string.order));
        }
        viewPagerAdapter.addFrag(SettingsFragment.newInstance(), getString(R.string.settings));

        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());
        viewPagerAdapter.setupAdapter(mViewPager);

        setupListenerViewPager();
    }

    private void setupListenerViewPager() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);

                if (position > 1) {
                    position = position + 1;
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

//                viewPagerAdapter.getCurrentFragment().OnUpdateView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_service:
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.action_profile:
                                mViewPager.setCurrentItem(1);
                                break;
                            case R.id.action_order:
                                mViewPager.setCurrentItem(2);
                                break;
                            case R.id.action_settings:
                                mViewPager.setCurrentItem(3);
                                break;
                            case R.id.action_create_order:
                                openOrderActivity();
                                break;
                        }
                        return false;
                    }
                });
    }

    /**/
    private void updateNotificationToolbarNumber() {
        if (numberOfNotification > 0) {
            toolbarNotificationNumTv.setVisibility(View.VISIBLE);
            toolbarNotificationNumTv.setText(numberOfNotification + "");
        } else {
            toolbarNotificationNumTv.setVisibility(View.INVISIBLE);
        }
    }

    public void onNotificationClicked(View v) {
        if (!WeCareUtils.isNullOrEmpty(getViewModel().getDataManager().getCurrentUserId())) {

            startActivity(NotificationsActivity.getStartIntent(this));

//        if (numberOfNotification > 0) {
////            getViewModel().getNavigator()
////            startActivity(new Intent(this, NotificationActivity.class));
//        } else {
//            showToast(getString(R.string.empty));
//        }
        } else {
            showToast(getString(R.string.Guest_to_your_account));
        }
    }

    @Override
    public void onBackPressed() {
        preventInstantBackPress();
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void openOrderActivity() {
        if (!WeCareUtils.isNullOrEmpty(getViewModel().getDataManager().getCurrentUserId())) {
            Intent intent = CreateOrderActivity.getStartIntent(mContext);
            startActivity(intent);
        } else {
            showToast(getString(R.string.Guest_to_your_account));
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void notificationsCountFetched(int count) {
        numberOfNotification = count;
        updateNotificationToolbarNumber();
    }

    @Override
    public MainViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
