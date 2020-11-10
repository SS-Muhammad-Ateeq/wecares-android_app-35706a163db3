
package com.wecare.android.ui.create_order.done;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.responses.*;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class DoneViewModel extends BaseViewModel<DoneNavigator> {

    private UserLocationResponse selectedLocation;
    private SearchGiverResponse selectedSearchGiverResponse;
    private RelativeProfileResponse selectedRelativeResponse;
    private ArrayList<SubServiceResponse> selectedSubServiceResponseList;
    //has been uploaded.
    private ArrayList<Integer> imagesUploadedIDList;
    //to be uploaded
    private ArrayList<InformationAttachmentObj> selectedInformationAttachmentList;
    //used only in reOrder flow
    private OrderModel reOrderModel;
    //used in caregiver search
    private FilterObject filterObject;

    public ObservableField<String> userInfoOrderFor;
    public ObservableField<String> userInfoYears;
    public ObservableField<String> userInfoGender;
    public ObservableField<String> userInfoMobile;
    public ObservableField<String> userInfoNeedSomeMaterial;

    public ObservableField<String> userScheduleTime;
    public ObservableField<String> userScheduleDate;

    public final ObservableBoolean isCreditCardSelected = new ObservableBoolean(false);
    public final ObservableBoolean isGiverSelected = new ObservableBoolean(false);

    public DoneViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        userInfoOrderFor = new ObservableField<>("");
        userInfoYears = new ObservableField<>("");
        userInfoGender = new ObservableField<>("");
        userInfoMobile = new ObservableField<>("");
        userInfoNeedSomeMaterial = new ObservableField<>("");

        userScheduleTime = new ObservableField<>("");
        userScheduleDate = new ObservableField<>("");

        imagesUploadedIDList = new ArrayList<>();
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void onSearchForCaregiverClick() {
        getNavigator().onSearchForCaregiverClick();
    }

    public void creditCardClicked() {
        getNavigator().creditCardClicked();
    }

    public void cashClicked() {
        getNavigator().cashClicked();
    }

    public void onSubmitOrderClick() {
        getNavigator().onSubmitOrderClick();
    }

    public String getIsCreditCardSelectedValue() {
        return isCreditCardSelected.get() ? "2" : "1";
    }

    //request order creation

    public void CreateOrderRequest() {
        setIsLoading(true);

        String paymentMethod = getIsCreditCardSelectedValue();

        JSONObject jsonObject = JSONBuilderFlavour.getCreateOrderParams(selectedSearchGiverResponse.getCaregiver(),
                userScheduleDate.get(), userScheduleTime.get(), selectedRelativeResponse.getMoreDescription(), paymentMethod, selectedLocation.getId(),
                selectedRelativeResponse, imagesUploadedIDList, selectedSubServiceResponseList, getReOrderModel(), getFilterObject());

        getCompositeDisposable().add(getDataManager()
                .createOrderApiCall(jsonObject)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse baseResponse) throws Exception {
                        setIsLoading(false);
                        if (baseResponse.isSuccess()) {
                            getNavigator().showOrderCreateDialog();
                        } else {
                            getNavigator().handleError(baseResponse.getError().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void uploadCreateOrderImages() {

        for (InformationAttachmentObj informationAttachmentObj : selectedInformationAttachmentList) {
            //image file
            File file = new File(informationAttachmentObj.getUrl());

            setIsLoading(true);
            getCompositeDisposable().add(getDataManager().uploadOrderImageApiCall(file.getName(), file)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<InformationAttachmentObj>() {
                        @Override
                        public void accept(InformationAttachmentObj model) throws Exception {
                            setIsLoading(false);
                            if (model.isSuccess()) {
                                imagesUploadedIDList.add(model.getId());
                                if (imagesUploadedIDList.size() == selectedInformationAttachmentList.size()) {
                                    //yes we finish uploading all photos then create order and finish.
                                    CreateOrderRequest();
                                }
                            } else {
                                getNavigator().handleError(model.getError().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            setIsLoading(false);
                            // in-case anything went wrong
                        }
                    }));
        }
    }


    ///////////////////////
    //user selected services
    ///////////////////////
    public FilterObject getFilterObject() {
        if (filterObject == null) {
            filterObject = new FilterObject();
        }
        return filterObject;
    }

    public DoneViewModel setFilterObject(FilterObject filterObject) {
        this.filterObject = filterObject;
        return this;
    }

    ///////////////////
    //user selected services
    ///////////////////////

    public OrderModel getReOrderModel() {
        return reOrderModel;
    }

    public DoneViewModel setReOrderModel(OrderModel reOrderModel) {
        this.reOrderModel = reOrderModel;
        return this;
    }


    ///end

    ///////////////////////
    //user selected services
    ///////////////////////

    public ArrayList<SubServiceResponse> getSelectedSubServiceResponseList() {
        return selectedSubServiceResponseList;
    }

    public DoneViewModel setSelectedSubServiceResponseList(ArrayList<SubServiceResponse> selectedSubServiceResponseList) {
        this.selectedSubServiceResponseList = selectedSubServiceResponseList;
        return this;
    }
    ///////////////////////end


    ///////////////////////
    /////user information
    ///////////////////////

    public RelativeProfileResponse getSelectedRelativeResponse() {
        return selectedRelativeResponse;
    }

    public DoneViewModel setSelectedRelativeResponse(RelativeProfileResponse selectedRelativeResponse) {
        this.selectedRelativeResponse = selectedRelativeResponse;
        return this;
    }

    public ArrayList<InformationAttachmentObj> getSelectedInformationAttachmentList() {
        return selectedInformationAttachmentList;
    }

    public DoneViewModel setSelectedInformationAttachmentList(ArrayList<InformationAttachmentObj> selectedInformationAttachmentList) {
        this.selectedInformationAttachmentList = selectedInformationAttachmentList;
        return this;
    }
    ///////////////////////end


    ///////////////////////
    /////user location
    ///////////////////////

    public UserLocationResponse getSelectedLocation() {
        return selectedLocation;
    }

    public DoneViewModel setSelectedLocation(UserLocationResponse selectedLocation) {
        this.selectedLocation = selectedLocation;
        return this;
    }
    ///////////////////////end


    ///////////////////////
    /////user giver profile
    ///////////////////////

    public SearchGiverResponse getSelectedSearchGiverResponse() {
        return selectedSearchGiverResponse;
    }

    public DoneViewModel setSelectedSearchGiverResponse(SearchGiverResponse selectedSearchGiverResponse) {
        this.selectedSearchGiverResponse = selectedSearchGiverResponse;
        return this;
    }

    public ObservableBoolean getIsGiverSelected() {
        return isGiverSelected;
    }

///////////////////////end

}
