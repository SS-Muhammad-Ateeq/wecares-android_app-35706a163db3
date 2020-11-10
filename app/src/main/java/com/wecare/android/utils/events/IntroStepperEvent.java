package com.wecare.android.utils.events;

import com.wecare.android.utils.AppConstants;

public class IntroStepperEvent {
    @AppConstants.IntroStepper
    public int step;
    public String newJobType;

    public IntroStepperEvent(int step) {
        this.step = step;
    }

}
