
package com.wecare.android.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityLogin2Binding;
import com.wecare.android.ui.auth.forgetpassword.ForgetPasswordActivity;
import com.wecare.android.ui.auth.registration.RegistrationActivity;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;


public class LoginActivity extends BaseActivity<ActivityLogin2Binding, LoginViewModel> implements LoginNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private LoginViewModel viewModel;

    ActivityLogin2Binding mActivityLoginBinding;

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

        mActivityLoginBinding.createAccountLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegistrationActivity.newIntent(LoginActivity.this).putExtra(AppConstants.KEY_REGISTRATION_TYPE, AppConstants.REGISTRATION_TYPE_GIVER));
            }
        });


    }


    private void setTestCredentials() {
//        mActivityLoginBinding.etEmail.setText("farah@caregiver.com");
//        mActivityLoginBinding.etPassword.setText("123456");
//        mActivityLoginBinding.etEmail.setText("Alaahussein1972@gmail.com");
//        mActivityLoginBinding.etPassword.setText("1256zaid");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void openMainActivity() {
        openMainActivity(LoginActivity.this);
    }

    @Override
    public void openVerificationCodeActivity() {
        startActivity(RegistrationActivity.newIntent(LoginActivity.this).putExtra(AppConstants.KEY_REGISTRATION_TYPE, AppConstants.REGISTRATION_TYPE_GIVER).putExtra(AppConstants.ARGS_VERIFY_USER,AppConstants.ARGS_VERIFY_USER));

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
        return R.layout.activity_login2;
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


}
