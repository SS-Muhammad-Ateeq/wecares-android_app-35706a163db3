package com.wecare.android.ui.auth.registration.verification;

import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.ui.auth.registration.RegistrationNavigator;

public interface RegistrationVerificationCodeNavigator extends RegistrationNavigator {
    void resendClicked();
    void accountActivatedSuccessfully(UserModel userModel);
    void codeResentSuccessfully();
}
