package com.wecare.android.data.model.api.responses;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.ServicesTotalModel;

import java.util.List;

public class UserMainServicesResponse extends BaseResponse implements Parcelable {

    @Expose
    @SerializedName("services")
    List<MainServiceModel> mainServiceModels;

    @Expose
    @SerializedName("total")
    ServicesTotalModel servicesTotalModel;

    public List<MainServiceModel> getMainServiceModels() {
        return mainServiceModels;
    }

    public void setMainServiceModels(List<MainServiceModel> mainServiceModels) {
        this.mainServiceModels = mainServiceModels;
    }

    public ServicesTotalModel getServicesTotalModel() {
        return servicesTotalModel;
    }

    public void setServicesTotalModel(ServicesTotalModel servicesTotalModel) {
        this.servicesTotalModel = servicesTotalModel;
    }
}
