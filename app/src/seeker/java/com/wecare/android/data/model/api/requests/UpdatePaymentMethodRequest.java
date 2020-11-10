package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePaymentMethodRequest {
    @SerializedName("payment_method")
    @Expose
    private Integer paymentMethod;

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public UpdatePaymentMethodRequest(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
