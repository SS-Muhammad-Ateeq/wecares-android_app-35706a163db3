package com.wecare.android.ui.webview;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mindorks.nybus.NYBus;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.FragmentWebViewBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.events.RegistrationStepperEvent;
import com.wecare.android.utils.events.TermsAcceptClickedEvent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;


public class WebViewFragment extends BaseFragment<FragmentWebViewBinding,WebViewModel> implements WebViewNavigator {
    public static final int TYPE_URI = 1;
    public static final int TYPE_CONTENT = 2;

    private int type;
    private String fileUri;
    private String htmlContent;


    @Inject
    ViewModelProviderFactory factory;
    private WebViewModel viewModel;


    FragmentWebViewBinding webViewBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web_view;
    }

    @Override
    public WebViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(WebViewModel.class);
        return viewModel;
    }

    public static WebViewFragment newInstance(Bundle args) {
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        if (getArguments() != null) {
            viewModel.setTitle(getArguments().getString(AppConstants.ARGS_KEY_TITLE));
            type = getArguments().getInt(AppConstants.ARGS_KEY_TYPE);
            fileUri = getArguments().getString(AppConstants.ARGS_KEY_HTML_PAGG_URI);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webViewBinding=getViewDataBinding();
        loadWebView();
        if (getArguments().getString(AppConstants.ARGS_KEY_MUST_ACCEPT) !=null)
            webViewBinding.bottomLayout.setVisibility(View.VISIBLE);
    }

    private void loadWebView(){
        webViewBinding.wvContent.getSettings().setBuiltInZoomControls(true);
        webViewBinding.wvContent.getSettings().setDisplayZoomControls(false);
        webViewBinding.wvContent.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });


        if (type == TYPE_URI) {
            webViewBinding.wvContent.loadUrl(fileUri);
        } else if (type == TYPE_CONTENT) {
            webViewBinding.wvContent.loadData(htmlContent, "text/html; charset=UTF-8", "UTF-8");
        }
    }

    @Override
    public void acceptClicked() {
        NYBus.get().post(new TermsAcceptClickedEvent(true));
        getActivity().onBackPressed();
    }
}
