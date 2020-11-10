
package com.wecare.android.ui.create_order.services.sub_duration;

import androidx.databinding.ObservableField;
import com.wecare.android.R;
import com.wecare.android.data.model.api.models.TaxRateModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.utils.WeCareUtils;

import java.util.List;


public class DurationSubServicesItemViewModel {

    private SubServiceResponse subServiceResponse;

    public ObservableField<String> serviceNameTitle;
    public ObservableField<String> durationText;
    //tax
    public ObservableField<String> pricePerHourTitle;
    public ObservableField<String> taxesDesc;
    public ObservableField<String> calculatedPricePerHourTitle = new ObservableField<>();

    public ObservableField<Boolean> isDurationInHour;

    private DurationItemViewModelListener mListener;
    double totalRatePercent = 0;


    public DurationSubServicesItemViewModel(SubServiceResponse subServiceResponse, DurationItemViewModelListener listener) {
        this.subServiceResponse = subServiceResponse;
        this.mListener = listener;

        serviceNameTitle = new ObservableField<>(subServiceResponse.getServiceName());
        durationText = new ObservableField<>(String.format("%s", subServiceResponse.getHourlyDuration()));

        if (subServiceResponse.getTaxModel() != null)
            taxesDesc = new ObservableField<>(getTaxesDesc(subServiceResponse.getTaxModel().getTaxRates()));

        if (!WeCareUtils.isNullOrEmpty(subServiceResponse.getPrice())) {
            pricePerHourTitle.set(subServiceResponse.getPrice());
        } else if (!WeCareUtils.isNullOrEmpty(subServiceResponse.getPrice_per_hour())) {
            pricePerHourTitle.set(subServiceResponse.getPrice_per_hour());
        }

        if (!WeCareUtils.isNullOrEmpty(subServiceResponse.getGrand_total_amount()))
            calculatedPricePerHourTitle.set(subServiceResponse.getGrand_total_amount());

        //service_type : quantity = 1, hours = 2
        if (subServiceResponse.getService_type().equals("1")) {
            isDurationInHour = new ObservableField<>(false);
        } else {
            isDurationInHour = new ObservableField<>(true);
        }
    }

    private String getTaxesDesc(List<TaxRateModel> taxRateModels) {

        String taxesDesc = "";

        for (TaxRateModel taxRateModel : taxRateModels) {
            taxesDesc = taxesDesc + taxRateModel.getName() + " " + (Double.parseDouble(taxRateModel.getRate()) * 100) + "%" + " ";
            totalRatePercent = totalRatePercent + (Double.parseDouble(taxRateModel.getRate()));
        }
        return taxesDesc;
    }

    public void calculatePrice(double price, double duration, boolean isPriceChanged, boolean isAdd) {
        double newPrice = price * duration;
        if (subServiceResponse.getTaxModel() != null) {
            double addedTax = newPrice * totalRatePercent;
            newPrice = newPrice + addedTax;
        }

        calculatedPricePerHourTitle.set(newPrice + " " + subServiceResponse.getCurrency());
        //if the duration increased or decreased  then update total amount
        if (isPriceChanged)
            mListener.onPriceChanged(subServiceResponse.getPosition(), newPrice, isAdd);

        subServiceResponse.setCalculatedPrice(newPrice);
    }

    /*
     */

    public void onSubMinusClicked() {
        if (subServiceResponse.getHourlyDuration() > 1) {
            subServiceResponse.setHourlyDuration(subServiceResponse.getHourlyDuration() - 1);
            durationText.set(String.format("%s", subServiceResponse.getHourlyDuration()));
        }
    }

    public void onSubAddClicked() {
        if (subServiceResponse.getHourlyDuration() < 24) {
            subServiceResponse.setHourlyDuration(subServiceResponse.getHourlyDuration() + 1);
            durationText.set(String.format("%s", subServiceResponse.getHourlyDuration()));
        }
    }

    public void onSubDeleteClicked() {
        mListener.onSubDeleteClicked(subServiceResponse);
    }

    public interface DurationItemViewModelListener {
        void onSubDeleteClicked(SubServiceResponse subServiceResponse);

        void onPriceChanged(int position, double newValue, boolean isAdd);
    }
}
