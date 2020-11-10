
package com.wecare.android.ui.main.order.current;

import com.wecare.android.data.model.api.models.OrderModel;

import androidx.databinding.ObservableField;


public class CurrentItemViewModel {

    private OrderModel ordersResponseItem;

    public ObservableField<String> orderUserImg = new ObservableField<>();
    public ObservableField<String> orderNameTxt = new ObservableField<>();;
    public ObservableField<String> requiredServiceTxt = new ObservableField<>();;
    public ObservableField<String> orderDateTimeTxt = new ObservableField<>();;
    public ObservableField<String> orderTotalTxt = new ObservableField<>();;
    public ObservableField<String> orderStatusTxt = new ObservableField<>();;

    private CurrentItemViewModelListener mListener;

    public CurrentItemViewModel(OrderModel orderModel, CurrentItemViewModelListener listener) {
        this.ordersResponseItem = orderModel;
        this.mListener = listener;
        this.orderNameTxt.set(orderModel.getCareseeker() != null ? (orderModel.getCareseeker().getFirstName()+" "+orderModel.getCareseeker().getLastName()) : "");
        this.requiredServiceTxt.set(orderModel.getServices().get(0).getSubService().getServiceName());
        this.orderDateTimeTxt.set(orderModel.getDate()+" "+ orderModel.getStartTime()+"-"+orderModel.getEndTime());
        this.orderTotalTxt.set(orderModel.getEstimatedTotalAmount()+" "+ orderModel.getCountry().getCurrencyCode());
        this.orderUserImg.set(orderModel.getCareseeker() != null ? orderModel.getCareseeker().getAvatar() : "");
        this.orderStatusTxt.set(orderModel.getOrderStatusModel().getEnglishName());
    }

    public void onCancelOrRejectClick() {
        mListener.onCancelOrRejectClick(ordersResponseItem);
    }

    public void onStartOrAcceptClick() {
        mListener.onStartOrAcceptClick(ordersResponseItem);
    }

    public void onItemClicked(){
        mListener.onItemClicked(ordersResponseItem);
    }

    public interface CurrentItemViewModelListener {


        void onCancelOrRejectClick(OrderModel ordersResponse);

        void onStartOrAcceptClick(OrderModel ordersResponse);

        void onItemClicked(OrderModel ordersResponse);
    }
}
