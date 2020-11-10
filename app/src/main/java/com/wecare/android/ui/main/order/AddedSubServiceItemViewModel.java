package com.wecare.android.ui.main.order;

import com.wecare.android.data.model.api.models.TaxRateModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;

import java.util.List;

import androidx.databinding.ObservableField;

public class AddedSubServiceItemViewModel {

    private SubServiceResponse subServiceResponse;

    public ObservableField<String> serviceNameTitle;
    public ObservableField<String> pricePerHourTitle;
    public ObservableField<String> taxesDesc;
    public ObservableField<String> durationText;
    public ObservableField<String> calculatedPricePerHourTitle = new ObservableField<>();


    public ObservableField<Boolean> isDurationInHour;

    private AddedItemViewModelListener mListener;
    double totalRatePercent = 0;


    public AddedSubServiceItemViewModel(SubServiceResponse subServiceResponse, AddedItemViewModelListener listener) {
        this.subServiceResponse = subServiceResponse;
        this.mListener = listener;

        serviceNameTitle = new ObservableField<>(subServiceResponse.getServiceName());
        durationText = new ObservableField<>(String.format("%s", subServiceResponse.getHourlyDuration()));
        if (subServiceResponse.getTaxModel() != null)
            taxesDesc = new ObservableField<>(getTaxesDesc(subServiceResponse.getTaxModel().getTaxRates()));

            pricePerHourTitle = new ObservableField<>(subServiceResponse.getPrice() + " " + subServiceResponse.getCurrency());
        calculatePrice(Double.parseDouble(subServiceResponse.getPrice()), (double) subServiceResponse.getHourlyDuration(), false, true);

        //service_type : quantity = 1, hours = 2
        if (subServiceResponse.getService_type().equals("1")) {
            isDurationInHour = new ObservableField<>(false);
        } else {
            isDurationInHour = new ObservableField<>(true);
        }
    }

    private String getTaxesDesc(List<TaxRateModel>taxRateModels){

        String taxesDesc = "";

        for (TaxRateModel taxRateModel:taxRateModels){
            taxesDesc = taxesDesc + taxRateModel.getName()+" "+(Double.parseDouble(taxRateModel.getRate())*100)+ "%"+" ";
            totalRatePercent = totalRatePercent+ (Double.parseDouble(taxRateModel.getRate()));
        }
        return  taxesDesc;
    }

    /*
     */

    public void onSubMinusClicked() {
        if (subServiceResponse.getHourlyDuration() > 1) {
            subServiceResponse.setHourlyDuration(subServiceResponse.getHourlyDuration() - 1);
            durationText.set(String.format("%s", subServiceResponse.getHourlyDuration()));
            calculatePrice(Double.parseDouble(subServiceResponse.getPrice()), (double) subServiceResponse.getHourlyDuration(), true, false);

        }
    }

    public void onSubAddClicked() {
        if (subServiceResponse.getHourlyDuration() < 24) {
            subServiceResponse.setHourlyDuration(subServiceResponse.getHourlyDuration() + 1);
            durationText.set(String.format("%s", subServiceResponse.getHourlyDuration()));
            calculatePrice(Double.parseDouble(subServiceResponse.getPrice()), (double) subServiceResponse.getHourlyDuration(), true, true);
        }
    }

    public void calculatePrice(double price, double duration, boolean isPriceChanged, boolean isAdd) {
        double newPrice = price * duration;
        if (subServiceResponse.getTaxModel() != null){
            double addedTax = newPrice * totalRatePercent;
                newPrice = newPrice + addedTax;
        }

        calculatedPricePerHourTitle.set(newPrice + " " + subServiceResponse.getCurrency());
        //if the duration increased or decreased  then update total amount
        if (isPriceChanged)
            mListener.onPriceChanged(subServiceResponse.getPosition(), newPrice, isAdd);

        subServiceResponse.setCalculatedPrice(newPrice);
    }

    public void onSubDeleteClicked() {
        mListener.onSubDeleteClicked(subServiceResponse);
    }

    public interface AddedItemViewModelListener {
        void onSubDeleteClicked(SubServiceResponse subServiceResponse);

        void onPriceChanged(int position, double newValue, boolean isAdd);
    }
}
