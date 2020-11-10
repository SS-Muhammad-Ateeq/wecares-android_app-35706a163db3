
package com.wecare.android.ui.sub;

import android.graphics.Color;

import androidx.databinding.ObservableField;

import com.wecare.android.data.model.api.responses.MainServiceModel;


public class ServicesItemViewModel {

    private MainServiceModel mainServiceModel;

    public ObservableField<Integer> color;
    public ObservableField<String> iconUrl;
    public ObservableField<String> imageUrl;
    public ObservableField<String> serviceNameTitle;
//    public ObservableField<String> serviceDescription;

    public ServicesItemViewModel.ServiceItemViewModelListener mListener;

    public ServicesItemViewModel(MainServiceModel mainServiceModel, ServicesItemViewModel.ServiceItemViewModelListener listener) {
        this.mainServiceModel = mainServiceModel;
        this.mListener = listener;
        color = new ObservableField<>(Color.parseColor(mainServiceModel.getColor()));
        iconUrl = new ObservableField<>(mainServiceModel.getIcon());
        imageUrl = new ObservableField<>(mainServiceModel.getImage());
        serviceNameTitle = new ObservableField<>(mainServiceModel.getServiceName());
//        serviceDescription = new ObservableField<>(mainServiceModel.getServiceDescription());
    }

    public void onItemClick() {
        mListener.onItemClick(mainServiceModel);
    }

    public interface ServiceItemViewModelListener {
        void onItemClick(MainServiceModel mainServiceModel);
    }
}
