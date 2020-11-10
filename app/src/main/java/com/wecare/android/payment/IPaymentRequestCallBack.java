package com.wecare.android.payment;

/**
 * Created by hussam on 5/27/17.
 */

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);

}
