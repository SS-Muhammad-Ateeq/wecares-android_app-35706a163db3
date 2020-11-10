package com.wecare.android.ui.search_giver;

import com.wecare.android.ui.base.BaseNavigator;

public interface SearchGiverNavigator extends BaseNavigator {

    void goBack();

    void onGiverPicked();

    void toggleSwipeToRefresh(boolean b);
}