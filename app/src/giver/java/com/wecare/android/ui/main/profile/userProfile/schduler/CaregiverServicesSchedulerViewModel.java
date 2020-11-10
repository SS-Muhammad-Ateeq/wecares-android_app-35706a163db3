package com.wecare.android.ui.main.profile.userProfile.schduler;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.models.ScheduleDayModel;
import com.wecare.android.data.model.api.requests.CreateScheduleRequest;
import com.wecare.android.data.model.api.responses.BaseResponse;
import com.wecare.android.data.model.api.responses.UserScheduleResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import androidx.databinding.ObservableBoolean;
import io.reactivex.functions.Consumer;

public class CaregiverServicesSchedulerViewModel extends BaseViewModel<CaregiverServicesSchedulerNavigator> {
    private final ObservableBoolean sundayChecked = new ObservableBoolean(false);
    private final ObservableBoolean mondayChecked = new ObservableBoolean(false);
    private final ObservableBoolean tuesdayChecked = new ObservableBoolean(false);
    private final ObservableBoolean wednesdayChecked = new ObservableBoolean(false);
    private final ObservableBoolean thursdayChecked = new ObservableBoolean(false);
    private final ObservableBoolean fridayChecked = new ObservableBoolean(false);
    private final ObservableBoolean saturdayChecked = new ObservableBoolean(false);

    public ObservableBoolean getSundayChecked() {
        return sundayChecked;
    }

    public ObservableBoolean getMondayChecked() {
        return mondayChecked;
    }

    public ObservableBoolean getTuesdayChecked() {
        return tuesdayChecked;
    }

    public ObservableBoolean getWednesdayChecked() {
        return wednesdayChecked;
    }

    public ObservableBoolean getThursdayChecked() {
        return thursdayChecked;
    }

    public ObservableBoolean getFridayChecked() {
        return fridayChecked;
    }

    public ObservableBoolean getSaturdayChecked() {
        return saturdayChecked;
    }

    public CaregiverServicesSchedulerViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void dayItemClicked(int index) {
        switch (index) {
            case AppConstants.SUNDAY_INDEX:
                sundayChecked.set(!sundayChecked.get());
                getNavigator().daySelected(index, sundayChecked.get(), true);
                break;
            case AppConstants.MONDAY_INDEX:
                mondayChecked.set(!mondayChecked.get());
                getNavigator().daySelected(index, mondayChecked.get(), true);
                break;
            case AppConstants.TUESDAY_INDEX:
                tuesdayChecked.set(!tuesdayChecked.get());
                getNavigator().daySelected(index, tuesdayChecked.get(), true);
                break;
            case AppConstants.WEDNSDAY_INDEX:
                wednesdayChecked.set(!wednesdayChecked.get());
                getNavigator().daySelected(index, wednesdayChecked.get(), true);
                break;
            case AppConstants.THURSDAY_INDEX:
                thursdayChecked.set(!thursdayChecked.get());
                getNavigator().daySelected(index, thursdayChecked.get(), true);
                break;
            case AppConstants.FRIDAY_INDEX:
                fridayChecked.set(!fridayChecked.get());
                getNavigator().daySelected(index, fridayChecked.get(), true);
                break;
            case AppConstants.SATURDAY_INDEX:
                saturdayChecked.set(!saturdayChecked.get());
                getNavigator().daySelected(index, saturdayChecked.get(), true);
                break;
        }
    }

    public void sameStartDateClicked() {
        getNavigator().sameStartDateClicked();
    }

    public void sameEndDateClicked() {
        getNavigator().sameEndDatClicked();
    }

    public void dayStartDateClicked(int index) {
        getNavigator().dayStartDateClicked(index);
    }

    public void dayEndDateClicked(int index) {
        getNavigator().dayEndDateClicked(index);

    }

    public void timesOfCaresSelected() {
        getNavigator().timesOfCaresSelected();
    }

    public void createSchedule(ArrayList<ScheduleDayModel> dayModels, boolean is24Available) {
        setIsLoading(true);
        CreateScheduleRequest request = new CreateScheduleRequest();
        request.setDayModels(is24Available ? new ArrayList<ScheduleDayModel>() : dayModels);
        request.setIsAvailable24(is24Available ? 1 : 0);
        getCompositeDisposable().add(getDataManager().createSchedule(JSONBuilderFlavour.getCreateScheduleParams(request))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess()) {
                            getNavigator().scheduleCreatedSuccessfully();
                        }
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }

    public void getUserSchedule() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getUserSchedule()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserScheduleResponse>() {
                    @Override
                    public void accept(UserScheduleResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            if (response.getDayModels() != null)
                                getNavigator().userScheduleFetchedSuccessfully(response);
                            else
                                getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        // incase anything went wrong
                    }
                }));
    }
}
