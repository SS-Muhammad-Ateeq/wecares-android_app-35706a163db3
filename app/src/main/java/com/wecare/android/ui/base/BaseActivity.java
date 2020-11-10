
package com.wecare.android.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jaredrummler.android.device.DeviceName;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.WeCareApplication;
import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.local.prefs.AppPreferencesHelper;
import com.wecare.android.ui.login.LoginActivity;
import com.wecare.android.ui.main.MainActivity;
import com.wecare.android.utils.*;
import com.wecare.android.utils.dialogs.GeneralDialogHandler;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

import br.com.ilhasoft.support.validation.Validator;
import dagger.android.AndroidInjection;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity implements BaseFragment.Callback, Validator.ValidationListener {

    protected Context mContext;
    private boolean mIsRunning = false;
    private boolean backPressed = false;
    protected int Backtimeout = 2000;

    public FortCallBackManager fortCallback = null;
    public String deviceId = "", sdkToken = "";

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;

    protected GeneralDialogHandler mGeneralDialogHandler;

    private T mViewDataBinding;
    private V mViewModel;
    public Validator validator;

    //center title
    private TextView toolbarTxt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);

        //set language and font init.
//        setDefaultAppLocale(); //old way.

        //keyboard hidden by default.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        performDataBinding();
        this.mContext = this;

        mGeneralDialogHandler = new GeneralDialogHandler(this);
    }

    public void setInfoHeader() {
        mViewModel.getDataManager().updateApiInfoHeader(DeviceName.getDeviceName(), Build.VERSION.RELEASE + "", BuildConfig.VERSION_NAME);

    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        //set language and font init.
        setDefaultAppLocale();
        Context localeContextWrapper = LocaleManager.setNewLocale(newBase, AppConstants.CURRENT_LOCALE);
        ContextWrapper calligraphyContextWrapper = CalligraphyContextWrapper.wrap(localeContextWrapper);
        super.attachBaseContext(calligraphyContextWrapper);

//        super.attachBaseContext(SLocaleHelper.onAttach(newBase, AppConstants.CURRENT_LOCALE));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    public void replaceFragment(BaseFragment fragment, Boolean animation, int fragmentContainer, Boolean isReplace) {
        replaceFragment(fragment, animation, false, isReplace, fragmentContainer);
    }

    public void replaceFragment(BaseFragment fragment, Boolean animation, Boolean backstack, Boolean isReplace, int fragmentContainer) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();

        String fragmentIdentifier = !TextUtils.isEmpty(fragment.getFragmentIdentifier()) ? fragment.getFragmentIdentifier() : fragment.getClass().getSimpleName();

        if (animation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                fragment.setTransitionType(AppConstants.SLIDING_ANIMATION);
            else
                transaction.setCustomAnimations(R.anim.slide_in_from_rigth, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        }
        if (backstack)
            transaction.addToBackStack(fragmentIdentifier);

        if (isReplace)
            transaction.replace(fragmentContainer, fragment, fragmentIdentifier);
        else
            transaction.add(fragmentContainer, fragment, fragmentIdentifier);

        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void preventInstantBackPress() {
        if (!backPressed) {
            showToast(getString(R.string.press_again_to_exit));
            backPressed = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Backtimeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    backPressed = false;
                }
            }).start();
            return;
        }
        super.onBackPressed();
    }

    /*need to test it*/
    public void vUpdateProgressDialogMessage(String sMessage) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(sMessage);
        }
    }

    public void vShowProgressDialog(String sMessage, boolean bIsRemovable) {
        vShowProgressDialog(null, sMessage, bIsRemovable);
    }

    public void vShowProgressDialog(String sTitle, String sMessage, boolean bIsRemovable) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new ProgressDialog(BaseActivity.this);
        mProgressDialog.setMessage(sMessage);
        mProgressDialog.setCancelable(bIsRemovable);
        mProgressDialog.setCanceledOnTouchOutside(bIsRemovable);
        if (!WeCareUtils.isNullOrEmpty(sTitle)) {
            mProgressDialog.setTitle(sTitle);
        }
        try {
            if (mProgressDialog != null && mIsRunning) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void vRemoveProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing() && mIsRunning) {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDefaultAppLocale() {
        //set device language as default first lunch
        if (WeCareApplication.getInstance().getAppPreferencesHelper().getAppLocale().equals(AppPreferencesHelper.KEY_NO_VALUE)) {
            WeCareApplication.getInstance().getAppPreferencesHelper().setAppLocale(Locale.getDefault().getLanguage().equals(AppConstants.LANGUAGE_LOCALE_ARABIC) ? AppConstants.LANGUAGE_LOCALE_ARABIC : AppConstants.LANGUAGE_LOCALE_ENGLISH);
        }
        AppConstants.CURRENT_LOCALE = WeCareApplication.getInstance().getAppPreferencesHelper().getAppLocale();

        //old way::: set local after retrieve it from share preferences
//        WeCareUtils.setLocale(getViewModel().getDataManager().getAppLocale(), this, false);
    }

    public void showToast(String Msg) {
        showToast(Msg, false);
    }

    public void showToast(String Msg, boolean isLong) {
        int duration = Toast.LENGTH_SHORT;
        if (isLong) {
            duration = Toast.LENGTH_LONG;
        }
        Toast.makeText(mContext, Msg, duration).show();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (toolbarTxt == null) {
//		SpannableString s = new SpannableString(title);
//		s.setSpan(new TypefaceSpan(this, ), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(title);

        } else {
            toolbarTxt.setText(title);
        }
    }

    protected Toolbar addToolbar(int toolbarId) {
        return addToolbar(toolbarId, "", false, null);
    }

    protected Toolbar addToolbar(int toolbarId, String title) {
        return addToolbar(toolbarId, title, false, null);
    }

    protected Toolbar addToolbar(int toolbarId, boolean enableHomeasUp) {
        return addToolbar(toolbarId, "", enableHomeasUp, null);
    }

    // Utility methods for layouting.

    public Toolbar addToolbar(int toolbarId, String title, boolean enableHomeasUp) {
        return addToolbar(toolbarId, title, enableHomeasUp, null);
    }

    protected Toolbar addToolbar(int toolbarId, String title, boolean enableHomeasUp, Drawable homeAsUpDrawable) {
        Toolbar toolbar = null;
        toolbarTxt = (TextView) findViewById(R.id.toolbar_title);

        try {
            toolbar = (Toolbar) findViewById(toolbarId);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            throw new IllegalStateException("Toolbar Id is not correct, Please make sure that the id sent is for support toolbar");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(enableHomeasUp);
        if (homeAsUpDrawable != null) {
            getSupportActionBar().setHomeAsUpIndicator(homeAsUpDrawable);
        }

        setTitle(title);
        return toolbar;
    }

    public void openLoginActivity(Context context) {
        Intent intent = LoginActivity.getStartIntent(context);
        startActivity(intent);
        finish();
    }

    public void openMainActivity(Context context) {
        Intent intent = MainActivity.getStartIntent(context);
        startActivity(intent);
        finish();
    }

    @Override
    public void onValidationSuccess() {
    }

    @Override
    public void onValidationError() {

    }

    public void showCommonError() {
        showErrorMessage(getString(R.string.error_common));
    }

    public void handleError(String errorMessage) {
        showErrorMessage(errorMessage);
    }

    public void showErrorMessage(String errorMessage) {

        DialogFactory.createFeedBackDialog(this, getString(R.string.error),
                errorMessage,
                getString(android.R.string.ok),
                getResources().getDrawable(R.drawable.ic_dialog_fail), null);
    }


    MaterialDialog.SingleButtonCallback singleButtonCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(MaterialDialog dialog, DialogAction which) {
//            resetCredentials();
        }
    };
}

