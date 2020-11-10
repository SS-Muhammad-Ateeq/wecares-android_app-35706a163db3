//package com.wecare.android.ui.social;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.Task;
//import com.mizmar.souqmizmar.Activities.MainActivity;
//import com.mizmar.souqmizmar.Base.BaseFragment;
//import com.mizmar.souqmizmar.Presenters.LoginPresenter;
//import com.mizmar.souqmizmar.R;
//import com.mizmar.souqmizmar.Utili.AppUtil;
//import com.mizmar.souqmizmar.Utili.Constants;
//import com.twitter.sdk.android.core.Callback;
//import com.twitter.sdk.android.core.Result;
//import com.twitter.sdk.android.core.TwitterException;
//import com.twitter.sdk.android.core.TwitterSession;
//import com.twitter.sdk.android.core.identity.TwitterAuthClient;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import cn.pedant.SweetAlert.SweetAlertDialog;
//
//public class LoginFragment extends BaseFragment implements LoginPresenter.View {
//    private static final int FACEBOOK_REQUEST_CODE = 64206;
//    private static final int TWITTER_REQUEST_CODE = 140;
//    private static final int GOOGLE_REQUEST_CODE = 15;
//
//    //View
//    @BindView(R.id.textView_login_sign_up)
//    public TextView tvSignUP;
//    @BindView(R.id.editText_login_email)
//    public TextView etEmail;
//    @BindView(R.id.editText_login_password)
//    public TextView etPassword;
//    @BindView(R.id.progressBar_login_progress)
//    public ProgressBar progress;
//    //Variables
//    CallbackManager callbackManager;
//    Callback<TwitterSession> twitterCallback;
//    TwitterAuthClient twitterAuthClient;
//    SweetAlertDialog pDialog;
//    LoginPresenter presenter;
//    GoogleSignInClient mGoogleSignInClient;
//
//    public LoginFragment() {
//        // Required empty public constructor
//    }
//
//    // TODO: Rename and change types and number of parameters
//    public static LoginFragment newInstance() {
//        LoginFragment fragment = new LoginFragment();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        presenter = new LoginPresenter(this);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_login, container, false);
//        ButterKnife.bind(this, view);
//        setupFacebook();
//        setupTwitter();
//        setupGoogle();
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        hideKeyboard();
//    }
//
//    void hideKeyboard() {
//        InputMethodManager inputManager = (InputMethodManager) getActivity()
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        // check if no view has focus:
//        View currentFocusedView = getActivity().getCurrentFocus();
//        if (currentFocusedView != null) {
//            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
//
//    private void setupFacebook() {
//        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        presenter.socialLogin(Constants.FACEBOOK, loginResult.getAccessToken().getToken(), null);
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                    }
//                });
//    }
//
//    private void setupTwitter() {
//        twitterAuthClient = new TwitterAuthClient();
//        twitterCallback = new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                presenter.socialLogin("twitter", result.data.getAuthToken().token, result.data.getAuthToken().secret);
//                twitterAuthClient.cancelAuthorize();
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//
//            }
//        };
//    }
//
//    private void setupGoogle() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.server_client_id))
//                .requestServerAuthCode(getString(R.string.server_client_id))
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
//    }
//
//    private void handleGoogleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            String idToken = account.getIdToken();
//            String serverAuthCode = account.getServerAuthCode();
//            presenter.socialLogin(Constants.GOOGLE, serverAuthCode, null);
//            // TODO(developer): send ID Token to server and validate
//        } catch (ApiException e) {
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == FACEBOOK_REQUEST_CODE) {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//        } else if (requestCode == TWITTER_REQUEST_CODE) {
//            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
//        } else if (requestCode == GOOGLE_REQUEST_CODE) {
//            // This task is always completed immediately, there is no need to attach an
//            // asynchronous listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleGoogleSignInResult(task);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @OnClick(R.id.button_login_facebook)
//    public void onFacebookLoginClick() {
//        LoginManager.getInstance().logInWithReadPermissions(
//                this, Arrays.asList("public_profile", "email"));
//    }
//
//    @OnClick(R.id.button_login_twitter)
//    public void onTwitterLoginClick() {
//        twitterAuthClient.authorize(this.getActivity(), twitterCallback);
//    }
//
//    @OnClick(R.id.button_login_google)
//    public void onGoogleLoginClick() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE);
//    }
//
//    @OnClick(R.id.button_login_email)
//    public void onEmailLoginClick() {
//        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
//            etEmail.setError(getString(R.string.message_insert_email));
//            return;
//        }
//        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
//            etPassword.setError(getString(R.string.message_insert_password));
//            return;
//        }
//        presenter.login(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
//    }
//
//    @OnClick(R.id.textView_login_sign_up)
//    public void onSignUpClick() {
//        AppUtil.addFragment(RegistrationFragment.class.getName(), R.id.frameLayout_login_container, true, null);
//    }
//
//    @OnClick(R.id.textview_login_forgetPassword)
//    public void onForgetPasswordClick() {
//        AppUtil.addFragment(ResetPasswordFragment.class.getName(), R.id.frameLayout_login_container, true, null);
//    }
//
//    @Override
//    public void onLoggedInSuccessfully() {
//        hideProgress();
//        List<Integer> flages = new ArrayList<>();
//        flages.add(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        flages.add(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        AppUtil.startActivity(MainActivity.class.getName(), null, flages);
//        getActivity().finish();
//
//    }
//
//    @Override
//    public void showMessage(String message) {
//        hideProgress();
//        AppUtil.showToastMessage(message, true);
//    }
//
//    @Override
//    public void showProgress() {
//        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
//        pDialog.setTitleText("جاري تسجيل الدخول...");
//        pDialog.setCancelable(false);
//        pDialog.show();
////        progress.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideProgress() {
//        pDialog.hide();
//        pDialog.dismissWithAnimation();
////        progress.setVisibility(View.GONE);
//    }
//
//    @Override
//    public Context context() {
//        return getActivity();
//    }
//}
