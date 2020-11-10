package com.wecare.android.interfaces;

import com.androidnetworking.error.ANError;

public interface StringAPICallBack {
    void onSuccessfulResponse(String result);
    void onFailureResponse(ANError error);
}