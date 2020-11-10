//
//package com.wecare.android.ui.main.home;
//
//import android.graphics.Color;
//import androidx.databinding.ObservableField;
//import com.wecare.android.data.model.api.responses.MainServiceModel;
//
//
//public class ServicesItemViewModel {
//
//    private MainServiceModel serviceResponse;
//
//    public ObservableField<Integer> color;
//    public ObservableField<String> iconUrl;
//    public ObservableField<String> imageUrl;
//    public ObservableField<String> serviceNameTitle;
////    public ObservableField<String> serviceDescription;
//
//    public ServiceItemViewModelListener mListener;
//
//    public ServicesItemViewModel(MainServiceModel serviceResponse, ServiceItemViewModelListener listener) {
//        this.serviceResponse = serviceResponse;
//        this.mListener = listener;
//        color = new ObservableField<>(Color.parseColor(serviceResponse.getColor()));
//        iconUrl = new ObservableField<>(serviceResponse.getIcon());
//        imageUrl = new ObservableField<>(serviceResponse.getImage());
//        serviceNameTitle = new ObservableField<>(serviceResponse.getServiceName());
////        serviceDescription = new ObservableField<>(serviceResponse.getServiceDescription());
//    }
//
//    public void onItemClick() {
//        mListener.onItemClick(serviceResponse);
//    }
//
//    public interface ServiceItemViewModelListener {
//        void onItemClick(MainServiceModel serviceResponse);
//    }
//}
