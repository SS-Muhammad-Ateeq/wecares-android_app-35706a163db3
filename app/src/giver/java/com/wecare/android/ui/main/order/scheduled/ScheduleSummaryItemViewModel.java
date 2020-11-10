package com.wecare.android.ui.main.order.scheduled;

import com.wecare.android.data.model.api.models.OrderModel;

import androidx.databinding.ObservableField;

public class ScheduleSummaryItemViewModel {
    OrderModel orderModel;
    private ScheduleSummaryItemViewModelListener mListener;

    public ObservableField<String> orderUserImg = new ObservableField<>();
    public ObservableField<String> orderNameTxt = new ObservableField<>();
    public ObservableField<String> requiredServiceTxt = new ObservableField<>();
    public ObservableField<String> orderDateTxt = new ObservableField<>();
    public ObservableField<String> orderTimeTxt = new ObservableField<>();
    public ObservableField<String> orderStatusTxt = new ObservableField<>();

    public ScheduleSummaryItemViewModel(OrderModel orderModel, ScheduleSummaryItemViewModelListener mListener) {
        this.orderModel = orderModel;
        this.mListener = mListener;

        this.orderNameTxt.set(orderModel.getCareseeker() != null ? (orderModel.getCareseeker().getFirstName()+" "+orderModel.getCareseeker().getLastName()) : "");
        this.requiredServiceTxt.set(orderModel.getServices().get(0).getSubService().getServiceName());
        this.orderDateTxt.set(orderModel.getDate());
        this.orderTimeTxt.set(orderModel.getStartTime());
        this.orderUserImg.set(orderModel.getCareseeker() != null ? orderModel.getCareseeker().getAvatar() : "");
        this.orderStatusTxt.set(orderModel.getOrderStatusModel().getEnglishName());
    }

    public void onItemClicked(){
        mListener.onItemClicked(orderModel);
    }

    public interface ScheduleSummaryItemViewModelListener {
        void onItemClicked(OrderModel  orderModel);
    }
}
