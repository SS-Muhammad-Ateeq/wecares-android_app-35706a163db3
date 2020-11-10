package com.wecare.android.ui.webview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityWebViewBinding;
import com.wecare.android.ui.auth.registration.RegistrationActivity;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.ui.webview.WebViewModel;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

public class WebViewActivity extends BaseActivity<ActivityWebViewBinding, WebViewActivityViewModel> implements WebViewActivityNavigator {

    private WebView mWebView;

    @Inject
    ViewModelProviderFactory factory;
    private WebViewActivityViewModel viewModel;

    ActivityWebViewBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getViewDataBinding();
        String url = "";
        String title = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(AppConstants.ARGS_KEY_URL)) {
                url = bundle.getString(AppConstants.ARGS_KEY_URL);
            }
            if (bundle.containsKey(AppConstants.ARGS_KEY_TITLE)) {
                title = bundle.getString(AppConstants.ARGS_KEY_TITLE);
            }
        }

        addToolbar(R.id.toolbar, title, true);

        vShowProgressDialog(getString(R.string.general_PleaseWait), true);

        mWebView = (WebView) findViewById(R.id.mWebView);

        mWebView.getSettings().setJavaScriptEnabled(true);

//        mWebView.setInitialScale(1);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
//        mWebView.getSettings().setUseWideViewPort(true);
//        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.getSettings().setDefaultTextEncodingName("utf-8");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                vRemoveProgressDialog();
            }
        });

        mWebView.loadUrl(url);
    }
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        return intent;
    }


    @Override
    public WebViewActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(WebViewActivityViewModel.class);
        return viewModel;    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }
}
