package com.wecare.android.interfaces;

import com.wecare.android.data.model.api.models.LocationModel;

import java.io.Serializable;

public interface CustomMapLocationCallBack extends Serializable {
    void locationPicked(LocationModel locationModel);

}
