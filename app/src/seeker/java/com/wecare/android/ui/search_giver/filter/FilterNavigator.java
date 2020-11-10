package com.wecare.android.ui.search_giver.filter;

import com.wecare.android.ui.base.BaseNavigator;

public interface FilterNavigator extends BaseNavigator {

    void goBack();

    void doneSaveClicked();

    void resetAllClicked();

    void ageClicked();

    void genderClicked();

    void priceClicked();

    void rateClicked();

    void yearsOfExperienceClicked();
}