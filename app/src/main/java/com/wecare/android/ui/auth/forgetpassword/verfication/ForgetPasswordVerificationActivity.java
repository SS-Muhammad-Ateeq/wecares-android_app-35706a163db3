package com.wecare.android.ui.auth.forgetpassword.verfication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityForgetPasswordVerficationBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;

public class ForgetPasswordVerificationActivity extends BaseActivity<ActivityForgetPasswordVerficationBinding,ForgetPasswordVerificationViewModel> implements ForgetPasswordVerificationNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private ForgetPasswordVerificationViewModel viewModel;

    ActivityForgetPasswordVerficationBinding binding;

    public  boolean isEmail;

    @Override
    public ForgetPasswordVerificationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(ForgetPasswordVerificationViewModel.class);
        return viewModel;
    }

    public static Intent getIntent(Context context,boolean isEmail) {
        Intent intent = new Intent(context, ForgetPasswordVerificationActivity.class);
        intent.putExtra(AppConstants.ARGS_IS_EMAIL,isEmail);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password_verfication;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
    }


    private void setUp() {
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.forgot_my_password), true);
        viewModel.setNavigator(this);
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();        //by default
        isEmail = getIntent().getBooleanExtra(AppConstants.ARGS_IS_EMAIL,true);

    }

    @Override
    public void saveClicked() {
    validator.toValidate();
    }

    @Override
    public void resendCode() {
        if (isEmail)
            viewModel.resetByEmail("");
        else
            viewModel.resetByPhone("","");
    }

    @Override
    public void passwordUpdatedSuccessfully() {
        DialogFactory.createFeedBackDialog(this, null, getString(R.string.password_updated_title), getString(R.string.logout_force), null, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                openLoginActivity(ForgetPasswordVerificationActivity.this);
            }
        });
    }

    @Override
    public void resentSuccessfully() {

    }

    @Override
    public void openMainActivity() {
        openMainActivity(this);
    }

    @Override
    public void onValidationSuccess() {
        if (isEmail)
            viewModel.updatePassByEmail(binding.confirmationCodeEdt.getText().toString(),binding.etPassword.getText().toString());
        else
            viewModel.updatePassByPhone(binding.confirmationCodeEdt.getText().toString(),binding.etPassword.getText().toString());
    }
}
