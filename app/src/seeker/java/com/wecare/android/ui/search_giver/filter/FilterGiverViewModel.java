package com.wecare.android.ui.search_giver.filter;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.ui.template.TemplateNavigator;
import com.wecare.android.utils.rx.SchedulerProvider;

public class FilterGiverViewModel extends BaseViewModel<FilterNavigator> {

    private FilterObject filterObject;

    public FilterGiverViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        filterObject = new FilterObject();
    }

    public FilterObject getFilterObject() {
        return filterObject;
    }

    public FilterGiverViewModel setFilterObject(FilterObject filterObject) {
        this.filterObject = filterObject;
        return this;
    }

    public void genderClicked() {
        getNavigator().genderClicked();
    }

    public void doneSaveClicked() {
        getNavigator().doneSaveClicked();
    }

    public void resetAllClicked() {
        getNavigator().resetAllClicked();
    }

    public void ageClicked() {
        getNavigator().ageClicked();
    }

    public void priceClicked() {
        getNavigator().priceClicked();
    }

    public void rateClicked() {
        getNavigator().rateClicked();
    }

    public void yearsOfExperienceClicked() {
        getNavigator().yearsOfExperienceClicked();
    }
}
