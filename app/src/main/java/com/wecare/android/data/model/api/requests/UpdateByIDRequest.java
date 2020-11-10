package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateByIDRequest {

    @SerializedName("careseeker_id")
    @Expose
    private String careseekerId;

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    @SerializedName("caregiver_id")
    @Expose
    private String caregiverId;
    @SerializedName("description")
    @Expose
    private String description;

    public String getCareseekerId() {
        return careseekerId;
    }

    public void setCareseekerId(String careseekerId) {
        this.careseekerId = careseekerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpdateByIDRequest(String Id, String description, boolean isGiver) {
        if (isGiver)
            this.caregiverId = Id;
        else
            this.careseekerId = Id;

        this.description = description;
    }

    public UpdateByIDRequest(String id, boolean isGiver) {
        if (isGiver)
            this.caregiverId = id;
        else
            this.careseekerId = id;

    }

    public UpdateByIDRequest() {
    }
}