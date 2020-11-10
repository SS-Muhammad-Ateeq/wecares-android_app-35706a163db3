package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.SubServiceResponse;

import java.util.List;

public class FinishOrderRequest {

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("payment_method")
    @Expose
    private Integer paymentMethod;
    @SerializedName("actual_total_amount")
    @Expose
    private Double actualTotalAmount;
    @SerializedName("paid_amount")
    @Expose
    private double paidAmount;
    @SerializedName("details_of_care_given")
    @Expose
    private String detailsOfCareGiven;
    @SerializedName("services")
    @Expose
    private List<SubServiceRequest> services = null;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getActualTotalAmount() {
        return actualTotalAmount;
    }

    public void setActualTotalAmount(Double actualTotalAmount) {
        this.actualTotalAmount = actualTotalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDetailsOfCareGiven() {
        return detailsOfCareGiven;
    }

    public void setDetailsOfCareGiven(String detailsOfCareGiven) {
        this.detailsOfCareGiven = detailsOfCareGiven;
    }

    public List<SubServiceRequest> getServices() {
        return services;
    }

    public void setServices(List<SubServiceRequest> services) {
        this.services = services;
    }

}