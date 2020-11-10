package com.wecare.android.ui.main.profile.userProfile.schduler;

import com.wecare.android.data.model.api.responses.UserScheduleResponse;
import com.wecare.android.ui.base.BaseNavigator;

public interface CaregiverServicesSchedulerNavigator extends BaseNavigator {
    void daySelected(int dayIndex,boolean isSelected,boolean isUserSelection);
    void timesOfCaresSelected();
    void sameStartDateClicked();
    void sameEndDatClicked();
    void dayStartDateClicked(int index);
    void dayEndDateClicked(int index);
    void scheduleCreatedSuccessfully();
    void userScheduleFetchedSuccessfully(UserScheduleResponse userScheduleResponse);
    void deleteSchedule(int index);
}
