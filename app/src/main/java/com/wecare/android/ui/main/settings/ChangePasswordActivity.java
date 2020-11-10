package com.wecare.android.ui.main.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityChangePasswordBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.WeCareUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;

public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding, ChangePasswordViewModel> implements ChangePasswordNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private ChangePasswordViewModel viewModel;


    ActivityChangePasswordBinding binding;


    @Override
    public ChangePasswordViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ChangePasswordViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void saveClicked() {
        validator.toValidate();
    }

    @Override
    public void passwordUpdatedSuccessfully() {
        DialogFactory.createSuccessDialog(this, "", getString(R.string.pass_changed_succussfully), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
    }

    private void setUp() {
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.set_new_password), true);
        viewModel.setNavigator(this);
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();        //by default
    }

    @Override
    public void onValidationSuccess() {
        super.onValidationSuccess();
        viewModel.changePassword(WeCareUtils.getEditTextString(binding.etCurrentPassword),WeCareUtils.getEditTextString(binding.etNewPassword));
    }
}
