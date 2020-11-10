
package com.wecare.android.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivitySplashBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.intro.IntroActivity;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;


public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private SplashViewModel mSplashViewModel;

    boolean isLookupsFinished = false;
    boolean isSplashFinished = false;

    private Handler mHandler;

    // Splash screen timer
    private int SPLASH_TIME_OUT = 2000;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //navigator
        mSplashViewModel.setNavigator(this);

        setInfoHeader();
        mSplashViewModel.getPages();

        //fetch
        mSplashViewModel.fetchLookUps();
        mHandler = new Handler();
        startRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            isSplashFinished = true;
            if (isLookupsFinished) {
                mSplashViewModel.decideNextActivity();
                finish();
            }
            mHandler.postDelayed(mStatusChecker, SPLASH_TIME_OUT);

        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }


    @Override
    public void openLoginActivity() {
        openLoginActivity(SplashActivity.this);
    }

    @Override
    public void openMainActivity() {
        openMainActivity(SplashActivity.this);

    }

    @Override
    public void openIntroActivity() {
        Intent intent = IntroActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void lookupsFinishedSuccessfully() {
        isLookupsFinished = true;
        if (isSplashFinished) ;
        {
            stopRepeatingTask();
            mSplashViewModel.decideNextActivity();
        }
    }


    @Override
    public SplashViewModel getViewModel() {
        mSplashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        return mSplashViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

}
