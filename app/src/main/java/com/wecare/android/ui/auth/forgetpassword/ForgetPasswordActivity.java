package com.wecare.android.ui.auth.forgetpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityForgetPasswordBinding;
import com.wecare.android.ui.auth.forgetpassword.verfication.ForgetPasswordVerificationActivity;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.DialogFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;

public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordViewModel> implements ForgetPasswordNavigator, RadioGroup.OnCheckedChangeListener {

    ActivityForgetPasswordBinding binding;
    boolean isEmail = true;

    @Inject
    ViewModelProviderFactory factory;
    private ForgetPasswordViewModel viewModel;

    @Override
    public ForgetPasswordViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ForgetPasswordViewModel.class);
        return viewModel;
    }


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();

        binding.countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
//                .setCountryCode((selectedCountry.getPhoneCode()));
            }
        });

//        if (binding.countryCodePicker.getSelectedCountryCode() != null) {//change default
//           .setCountryCode(binding.countryCodePicker.getSelectedCountryCode());
//        }
    }

    private void setUp() {
        binding = getViewDataBinding();
        binding.segmented.setOnCheckedChangeListener(this);
        addToolbar(R.id.toolbar, getString(R.string.forgot_my_password), true);
        viewModel.setNavigator(this);
        validator = new Validator(binding);
        validator.setValidationListener(this);
//        validator.enableFormValidationMode();        //by default
        setEmailSelection();

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.EmailRadioBtn:
                setEmailSelection();
                break;
            case R.id.PhoneRadioBtn:
                setPhoneSelection();
                break;
        }
    }

    private void setEmailSelection() {
        binding.forgetPasswordMessage.setText(R.string.reset_password_email_disclaimer);
        isEmail = true;
        binding.phoneNumberLayout.setVisibility(View.GONE);
        binding.forgetEmailEdtLayout.setVisibility(View.VISIBLE);
        validator.enableValidation(binding.forgetEmailEdt);
//        validator.disableValidation(binding.countryCodeEdt);
        validator.disableValidation(binding.mobileNumberEdt);
    }

    private void setPhoneSelection() {
        binding.forgetPasswordMessage.setText(R.string.reset_password_phone_disclaimer);
        isEmail = false;
        binding.phoneNumberLayout.setVisibility(View.VISIBLE);
        binding.forgetEmailEdtLayout.setVisibility(View.GONE);
        validator.disableValidation(binding.forgetEmailEdt);
//        validator.enableValidation(binding.countryCodeEdt);
        validator.enableValidation(binding.mobileNumberEdt);
    }

    @Override
    public void sendClicked() {
        validator.toValidate();
    }

    @Override
    public void resetSuccessfully() {
        DialogFactory.createFeedBackDialog(this, null, getString(isEmail ? R.string.reset_pass_sent_to_email : R.string.reset_pass_sent_to_phone), getString(R.string.ok), null, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                startActivity(ForgetPasswordVerificationActivity.getIntent(ForgetPasswordActivity.this, isEmail));
            }
        });
    }

    @Override
    public void onValidationError() {
    }

    @Override
    public void onValidationSuccess() {
        if (isEmail) {
            viewModel.resetByEmail(binding.forgetEmailEdt.getText().toString());
        } else {
            if (binding.countryCodePicker.getFullNumber() == null || binding.countryCodePicker.getFullNumber().length() == 0) {
                binding.countryCodeLayout.setError(getString(R.string.error_field_required));
                return;
            }
            binding.countryCodeLayout.setError(null);

            viewModel.resetByPhone(binding.countryCodePicker.getFullNumber(), binding.mobileNumberEdt.getText().toString());
        }


    }
}
