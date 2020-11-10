package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateOrderRequest {




    public UpdateOrderRequest() {
    }

    public UpdateOrderRequest(String orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public UpdateOrderRequest(String orderId, Integer reasonId, String otherReason,boolean isReject) {
        this.orderId = orderId;
        if (isReject){
            this.rejectReasonId = reasonId;
            this.rejectOtherReason = otherReason;
        }
        else {
            this.cancelReasonId = reasonId;
            this.cancelOtherReason = otherReason;
        }
    }


    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("cancel_reason_id")
    @Expose
    private Integer cancelReasonId;
    @SerializedName("cancel_other_reason")
    @Expose
    private String cancelOtherReason;
    @SerializedName("rejected_reason_id")
    @Expose
    private Integer rejectReasonId;
    @SerializedName("rejected_other_reason")
    @Expose
    private String rejectOtherReason;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(Integer cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getCancelOtherReason() {
        return cancelOtherReason;
    }

    public void setCancelOtherReason(String cancelOtherReason) {
        this.cancelOtherReason = cancelOtherReason;
    }

    public Integer getRejectReasonId() {
        return rejectReasonId;
    }

    public void setRejectReasonId(Integer rejectReasonId) {
        this.rejectReasonId = rejectReasonId;
    }

    public String getRejectOtherReason() {
        return rejectOtherReason;
    }

    public void setRejectOtherReason(String rejectOtherReason) {
        this.rejectOtherReason = rejectOtherReason;
    }

}