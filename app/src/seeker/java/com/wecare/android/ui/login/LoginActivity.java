
package com.wecare.android.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.wecare.android.BR;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityLoginBinding;
import com.wecare.android.ui.auth.forgetpassword.ForgetPasswordActivity;
import com.wecare.android.ui.auth.registration.RegistrationActivity;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;

import java.util.Arrays;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;


public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private static final int FACEBOOK_REQUEST_CODE = 64206;
    private static final int TWITTER_REQUEST_CODE = 140;
    private static final int GOOGLE_REQUEST_CODE = 15;
    private static final String TAG = LoginActivity.class.getSimpleName();

    //Variables
    CallbackManager callbackManager;

    Callback<TwitterSession> twitterCallback;
    TwitterAuthClient twitterAuthClient;
    GoogleSignInClient mGoogleSignInClient;

    @Inject
    ViewModelProviderFactory factory;
    private LoginViewModel viewModel;

//    private FirebaseAuth mAuth;
    private ActivityLoginBinding mActivityLoginBinding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        viewModel.setNavigator(this);
        setTestCredentials();
        validator = new Validator(mActivityLoginBinding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();

//        mAuth = FirebaseAuth.getInstance();
//        mAuth.signOut();

        setupFacebook();
        setupTwitter();
        setUpGoogle();

        mActivityLoginBinding.loginSkipTxt.setVisibility(View.VISIBLE);
//        mActivityLoginBinding.etEmail.setText("alaahussein1972@gmail.com");
//        mActivityLoginBinding.etPassword.setText("1256zaid");


        mActivityLoginBinding.createAccountLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegistrationActivity.newIntent(LoginActivity.this).putExtra(AppConstants.KEY_REGISTRATION_TYPE, AppConstants.REGISTRATION_TYPE_SEEKER));
            }
        });


    }


    private void setTestCredentials() {
        if (BuildConfig.DEBUG) {
//        mActivityLoginBinding.etEmail.setText("m.mihyar1991@hotmail.com");
//        mActivityLoginBinding.etPassword.setText("miho1234");
//            mActivityLoginBinding.etEmail.setText("mihyar@gmail.com");
//            mActivityLoginBinding.etPassword.setText("123456");
//        mActivityLoginBinding.etEmail.setText("farah7@seeker.com");
//        mActivityLoginBinding.etPassword.setText("123456");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FACEBOOK_REQUEST_CODE) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == TWITTER_REQUEST_CODE) {
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == GOOGLE_REQUEST_CODE) {
            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void openMainActivity() {
        openMainActivity(LoginActivity.this);
    }

    @Override
    public void openVerificationCodeActivity() {
        startActivity(RegistrationActivity.newIntent(LoginActivity.this).putExtra(AppConstants.KEY_REGISTRATION_TYPE, AppConstants.REGISTRATION_TYPE_SEEKER).putExtra(AppConstants.ARGS_VERIFY_USER, AppConstants.ARGS_VERIFY_USER));
    }

    @Override
    public void login() {
        validator.toValidate();
    }

    @Override
    public void openForgetPassword() {
        startActivity(ForgetPasswordActivity.getIntent(this));
    }

    @Override
    public void onSocialAccountRegistrationNeeded(String tokenHash, String argsSocialKey) {
        Intent i = RegistrationActivity.newIntent(LoginActivity.this);
        i.putExtra(AppConstants.KEY_REGISTRATION_TYPE, AppConstants.REGISTRATION_TYPE_SEEKER);
        i.putExtra(AppConstants.ARGS_KEY_IS_REGISTRATION_USING_SOCIAL, true);
        i.putExtra(argsSocialKey, tokenHash);
        startActivity(i);
    }

    @Override
    public void onFacebookClicked() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    public void onTwitterClicked() {
        twitterAuthClient.authorize(this, twitterCallback);
    }

    @Override
    public void onGoogleClicked() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE);
    }

    @Override
    public LoginViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onValidationSuccess() {
        if (isNetworkConnected())
            viewModel.login(mActivityLoginBinding.etEmail.getText().toString(), mActivityLoginBinding.etPassword.getText().toString());
        else
            showErrorMessage(getString(R.string.error_no_connection));
    }

    @Override
    public void onValidationError() {
    }


    private void setupFacebook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getViewModel().onFbLoginClick(loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        showToast(getString(R.string.general_Cancel));
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("facebook", exception.toString());
                        // App code
                        showErrorMessage(exception.getMessage());
                    }
                });
    }

    private void setupTwitter() {
        twitterAuthClient = new TwitterAuthClient();
        twitterCallback = new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                getViewModel().doTwitterLoginClick(result.data.getAuthToken().token, result.data.getAuthToken().secret);
                twitterAuthClient.cancelAuthorize();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("twitter", exception.toString());
                // App code
                showErrorMessage(exception.getMessage());
            }
        };
    }

    private void setUpGoogle() {
        String serverKey = getString(R.string.server_client_id_27_8);
//        if (BuildConfig.DEBUG) {
//            serverKey = getString(R.string.server_client_id_debug);
//        }
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(serverKey)
//                .requestScopes(new Scope(Scopes.PROFILE))
//                .requestServerAuthCode(serverKey)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void handleGoogleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            String idToken = account.getIdToken();
//            String serverAuthCode = account.getServerAuthCode();
            getViewModel().onGoogleLoginClick(idToken);
            // TODO(developer): send ID Token to server and validate
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GoogleSignIn:: ", "signInResult:failed code=" + e.getStatusCode());
            e.printStackTrace();
            showErrorMessage(e.getMessage());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            showErrorMessage("Authentication Failed.");
//                        }
//
//                        // ...
//                    }
//                });
    }

}
