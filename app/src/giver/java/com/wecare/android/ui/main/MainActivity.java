

package com.wecare.android.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityMainBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.base.BaseTabsViewPagerAdapter;
import com.wecare.android.ui.login.LoginActivity;
import com.wecare.android.ui.main.order.OrderFragment;
import com.wecare.android.ui.main.order.scheduled.ScheduleSummaryActivity;
import com.wecare.android.ui.main.profile.ProfileMainFragment;
import com.wecare.android.ui.main.settings.SettingsFragment;
import com.wecare.android.ui.notification.NotificationsActivity;
import com.wecare.android.ui.sub.ServicesFragment;

import javax.inject.Inject;

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
    BaseTabsViewPagerAdapter mViewPagerAdapter;
    /**/
    private TabLayout mTabLayout;
    /**/
    private ViewPager mViewPager;

    /**/
    private int numberOfNotification;
    private TextView toolbarNotificationNumTv;
    private ImageButton toolbarNotificationBtn;

    /**/
    private int[] tabsIcons = {R.drawable.ic_home,
            R.drawable.ic_profile,
            R.drawable.ic_order,
            R.drawable.ic_settigs};

    @Inject
    BaseTabsViewPagerAdapter mPagerAdapter;


    ActivityMainBinding mActivityMainBinding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        viewModel.setNavigator(this);
        setUp();
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
        addToolbar(R.id.toolbar, getString(R.string.wecare), false);

        toolbarNotificationNumTv = mActivityMainBinding.toolbar.toolbarNotificationNumTv;
        toolbarNotificationBtn = mActivityMainBinding.toolbar.toolbarNotificationBtn;

        updateNotificationToolbarNumber();
        toolbarNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotificationClicked(v);
            }
        });
        mActivityMainBinding.toolbar.toolbarScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ScheduleSummaryActivity.newIntent(MainActivity.this));
            }
        });

        mTabLayout = mActivityMainBinding.mainTabLayout;
        mViewPager = mActivityMainBinding.mainViewPager;

        mViewPagerAdapter.addFrag(ServicesFragment.newInstance(), getString(R.string.services), tabsIcons[0]);
        mViewPagerAdapter.addFrag(ProfileMainFragment.newInstance(), getString(R.string.profile), tabsIcons[1]);
        mViewPagerAdapter.addFrag(OrderFragment.newInstance(), getString(R.string.order), tabsIcons[2]);
        mViewPagerAdapter.addFrag(SettingsFragment.newInstance(), getString(R.string.settings), tabsIcons[3]);

        setUpToolbarTabs();
        //mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext, R.color.blue));

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount());
        mViewPagerAdapter.setupAdapter(mViewPager, mTabLayout, 0);
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
//        if (numberOfNotification > 0) {
//            getViewModel().getNavigator()
        startActivity(NotificationsActivity.getStartIntent(this));
//        } else {
//            showToast("You don't have any notifications");
//        }
    }

    private void setUpToolbarTabs() {
//        int selectedTab = 0;
//        for (int i = 0, count = mViewPagerAdapter.getCount(); i < count; i++) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.tab_custom_view, null);
//            ImageView imageViewTab = ((ImageView) view.findViewById(R.id.tab_icon));
//            imageViewTab.setImageResource(tabsIcons[i]);
//            mTabLayout.addTab(mTabLayout.newTab().setCustomView(view));
//            //mTabLayout.addTab(mTabLayout.newTab().setIcon(tabsIcons[i]), i == selectedTab ? true : false);
//        }
//        for (int i = 0; i < mTabLayout.getTabCount(); i++)
//            mTabLayout.getTabAt(i).setIcon(tabsIcons[i]);
    }
//    private void showAboutFragment() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .disallowAddToBackStack()
//                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
//                .commit();
//    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
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
