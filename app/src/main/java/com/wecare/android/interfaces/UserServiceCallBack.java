package com.wecare.android.interfaces;

import com.wecare.android.data.model.api.requests.UserServiceRequestModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;

public interface UserServiceCallBack {
    void doneClicked(SubServiceResponse serviceResponse, UserServiceRequestModel requestModel);
}
