
package com.wecare.android.ui.sub;


import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.StatisticResponse;
import com.wecare.android.ui.base.BaseNavigator;

import java.util.List;

public interface ServicesNavigator extends BaseNavigator {

    void updateServicesList(List<MainServiceModel> mainServiceModelList);

    void goBack();

    void goToSubServices(MainServiceModel mainServiceModel);

    void statisticsFetched(StatisticResponse statisticResponse);

}
