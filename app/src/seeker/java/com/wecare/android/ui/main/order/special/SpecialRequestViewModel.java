package com.wecare.android.ui.main.order.special;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.ui.search_giver.filter.FilterNavigator;
import com.wecare.android.utils.rx.SchedulerProvider;

public class SpecialRequestViewModel extends BaseViewModel<SpecialRequestNavigator> {

    private FilterObject filterObject;

    public SpecialRequestViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        filterObject = new FilterObject();
    }

    public FilterObject getFilterObject() {
        return filterObject;
    }

    public SpecialRequestViewModel setFilterObject(FilterObject filterObject) {
        this.filterObject = filterObject;
        return this;
    }

    public void genderClicked() {
        getNavigator().genderClicked();
    }

    public void ageClicked() {
        getNavigator().ageClicked();
    }
    public void doneSaveClicked() {
        getNavigator().doneClicked();
    }


    public void languageClicked() {
        getNavigator().languageClicked();
    }

}
