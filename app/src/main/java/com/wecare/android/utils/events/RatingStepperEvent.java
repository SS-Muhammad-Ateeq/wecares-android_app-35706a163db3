package com.wecare.android.utils.events;

import com.wecare.android.data.model.api.requests.RatingRequest;

public class RatingStepperEvent {

    public int step;
    public RatingRequest request;

    public void setRequest(RatingRequest request) {
        this.request = request;
    }

    public RatingStepperEvent(int step, RatingRequest request) {
        this.step = step;
        this.request = request;
    }

    public RatingStepperEvent(int step) {
        this.step = step;
    }
}
