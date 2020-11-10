package com.wecare.android.ui.auth.registration.verification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wecare.android.R;
import com.wecare.android.interfaces.ResendVerificationCodeCallBack;
import com.wecare.android.utils.WeCareUtils;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

public class ResendVerificationCodeFragment extends DialogFragment implements View.OnClickListener {

    public void setCallBack(ResendVerificationCodeCallBack callBack) {
        this.callBack = callBack;
    }

    ResendVerificationCodeCallBack callBack;
    private TextInputEditText registrationCountryCodeEdt;
    private TextInputEditText registrationMobileNumberEdt;
    private AppCompatButton verificationActivateBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resend_activation_code, container, false);

        registrationCountryCodeEdt = (TextInputEditText) v.findViewById(R.id.registration_countryCode_edt);
        registrationMobileNumberEdt = (TextInputEditText) v.findViewById(R.id.registration_mobileNumber_edt);
        verificationActivateBtn = (AppCompatButton) v.findViewById(R.id.verification_activate_btn);

        verificationActivateBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.verification_activate_btn) {
            if (isFormValid()) {
                callBack.sendClicked(WeCareUtils.getEditTextString(registrationCountryCodeEdt), WeCareUtils.getEditTextString(registrationMobileNumberEdt));
                this.dismiss();
            }
        }
    }

    private boolean isFormValid() {
        if (WeCareUtils.getEditTextString(registrationCountryCodeEdt) == null || WeCareUtils.getEditTextString(registrationCountryCodeEdt).isEmpty()) {
            registrationCountryCodeEdt.setError(getString(R.string.error_field_required));
            return false;
        }
        registrationCountryCodeEdt.setError(null);

        if (WeCareUtils.getEditTextString(registrationMobileNumberEdt) == null || WeCareUtils.getEditTextString(registrationMobileNumberEdt).isEmpty()) {
            registrationMobileNumberEdt.setError(getString(R.string.error_field_required));
            return false;
        }
        registrationMobileNumberEdt.setError(null);

        return true;
    }
}
