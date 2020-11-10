package com.wecare.android.ui.create_order.relative;

import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.List;

public interface RelativeProfileNavigator extends BaseNavigator {

    void goBack();

    void updateRelativeProfilesList(List<RelativeProfileResponse> responseList);

    void onDeletedSuccessfully(RelativeProfileResponse profileResponse);

    void addNewRelativeClicked();

    void userProfileClicked();

    void genderClicked();

    void relationshipClicked();

    void ageClicked();

    void bloodTypeClicked();

    void weightClicked();

    void lengthClicked();

    void chronicDiseasesClicked();

    void bloodPressureClicked();

    void isThereHealthInsuranceClicked();

    void typeOfInsuranceClicked();

    void insuranceExpirationDateClicked();

    void countriesOfServiceClicked();

    void birthDateClicked();

    void nationalityClicked();

    void fillUserInfoForEdit();

    void insuranceCompanyNameClicked();
}