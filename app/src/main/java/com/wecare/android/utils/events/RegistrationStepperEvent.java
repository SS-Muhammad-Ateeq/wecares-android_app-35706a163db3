package com.wecare.android.utils.events;

import com.wecare.android.utils.AppConstants;

public class RegistrationStepperEvent {

    @AppConstants.RegistrationStepper
    public int registrationStep;

    public RegistrationStepperEvent(int registrationStep) {
        this.registrationStep = registrationStep;
    }

}