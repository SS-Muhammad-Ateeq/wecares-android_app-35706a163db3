
package com.wecare.android.ui.main.order.current;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.utils.AppConstants;

import androidx.core.view.TintableBackgroundView;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;


public class CurrentItemViewModel {

    private OrderModel orderModel;
    private static final String TAG = "MyActivity";
    public ObservableField<String> orderUserImg = new ObservableField<>();
    public ObservableField<String> seekerNameTxt = new ObservableField<>();
    public ObservableField<String> requiredServiceTxt = new ObservableField<>();
    public ObservableField<String> orderDateTimeTxt = new ObservableField<>();
    public ObservableField<String> orderTotalTxt = new ObservableField<>();
    public ObservableField<String> orderStatusTxt = new ObservableField<>();
    public  int orderStatusClr;


    public ObservableField<Float> getRating() {
        return rating;
    }

    public final ObservableField<Float> rating = new ObservableField<>();


    public ObservableBoolean getIsFavorite() {
        return isFavorite;
    }

    private final ObservableBoolean isFavorite = new ObservableBoolean(false);


    private CurrentItemViewModelListener mListener;

    public CurrentItemViewModel(OrderModel ordersResponse, CurrentItemViewModelListener listener) {
        this.orderModel = ordersResponse;
        this.mListener = listener;
        this.seekerNameTxt.set(orderModel.getCaregiver().getFirstName() + " " + orderModel.getCaregiver().getLastName());
        if (orderModel.getServices().size() > 0)
            this.requiredServiceTxt.set(orderModel.getServices().get(0).getSubService().getServiceName());
        this.orderDateTimeTxt.set(orderModel.getDate());
        this.orderTotalTxt.set(orderModel.getEstimatedTotalAmount());
        this.orderUserImg.set(orderModel.getCaregiver().getAvatar());
        Log.i(TAG, "MyClass.getView() — get item status " + orderModel.getOrderStatusModel().getEnglishName());

        if ((orderModel.getOrderStatusModel().getEnglishName()).equals("Pending")){
            this.orderStatusClr = Color.rgb(255,127,80);
        }
        else if ((orderModel.getOrderStatusModel().getEnglishName()).equals("Rejected")){
            this.orderStatusClr = Color.rgb(255,0,0);
        }
        else if ((orderModel.getOrderStatusModel().getEnglishName()).equals("Caring")){
            this.orderStatusClr = Color.rgb(0,0,139);
        }
        else if ((orderModel.getOrderStatusModel().getEnglishName()).equals("Canceled")){
            this.orderStatusClr = Color.rgb(0,0,139);
        }
        else if ((orderModel.getOrderStatusModel().getEnglishName()).equals("Fulfilled")){
            this.orderStatusClr = Color.rgb(34,139,34);
        }else if ((orderModel.getOrderStatusModel().getEnglishName()).equals("Accepted")){
            this.orderStatusClr = Color.rgb(173,216,230);
        }

        this.orderStatusTxt.set(orderModel.getOrderStatusModel().getEnglishName());
        this.isFavorite.set(ordersResponse.getCaregiver().getIsFavorite() == AppConstants.PHP_TRUE_RAW);
        rating.set((float) orderModel.getCaregiver().getRating());
    }


    public void onItemClicked() {
        mListener.onItemClicked(orderModel);
    }

    public interface CurrentItemViewModelListener {


        void onItemClicked(OrderModel ordersResponse);
    }
}
