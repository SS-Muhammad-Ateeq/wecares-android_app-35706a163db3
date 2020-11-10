package com.wecare.android.ui.main.order.previous;

import com.wecare.android.data.model.api.models.OrderModel;

import androidx.databinding.ObservableField;

public class PreviousItemViewModel {

    private OrderModel ordersResponseItem;

    previousItemViewModelListener listener;

    public PreviousItemViewModel(OrderModel orderModel, previousItemViewModelListener listener) {
        this.ordersResponseItem = orderModel;
        this.listener = listener;

        this.orderNameTxt.set(String.format("%s %s", orderModel.getCareseeker()!= null ?orderModel.getCareseeker().getFirstName() : "" , orderModel.getCareseeker()!= null ? orderModel.getCareseeker().getLastName() : ""));
        if (orderModel.getServices().size()>0)
        this.requiredServiceTxt.set(orderModel.getServices().get(0).getSubService().getServiceName());
        this.orderDateTimeTxt.set(orderModel.getDate()+" "+ orderModel.getStartTime()+"-"+orderModel.getEndTime());
        this.orderTotalTxt.set(orderModel.getEstimatedTotalAmount()+" "+ orderModel.getCountry().getCurrencyCode());
        this.orderUserImg.set(orderModel.getCareseeker()!= null ? orderModel.getCareseeker().getAvatar(): "");
        this.orderStatusTxt.set(orderModel.getOrderStatusModel().getEnglishName());
    }

    public ObservableField<String> orderUserImg = new ObservableField<>();
    public ObservableField<String> orderNameTxt = new ObservableField<>();
    public ObservableField<String> requiredServiceTxt = new ObservableField<>();
    public ObservableField<String> orderDateTimeTxt = new ObservableField<>();
    public ObservableField<String> orderTotalTxt = new ObservableField<>();
    public ObservableField<String> orderStatusTxt = new ObservableField<>();


    public void onItemClick(){
        listener.onItemClick(ordersResponseItem);
    }


    public interface previousItemViewModelListener {
        void onItemClick(OrderModel orderModel);


    }


}
