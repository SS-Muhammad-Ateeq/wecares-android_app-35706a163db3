package com.wecare.android.ui.main.order.special;

import com.wecare.android.ui.base.BaseNavigator;

public interface SpecialRequestNavigator extends BaseNavigator {

    void goBack();

    void doneClicked();

    void ageClicked();

    void genderClicked();

    void languageClicked();
}