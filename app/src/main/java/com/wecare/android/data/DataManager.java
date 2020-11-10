package com.wecare.android.data;

import com.wecare.android.data.local.db.DbHelper;
import com.wecare.android.data.local.prefs.PreferencesHelper;
import com.wecare.android.data.model.api.models.RegistrationModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.data.model.api.responses.LookupResponse;

import com.wecare.android.data.model.api.responses.PageContentResponse;
import com.wecare.android.data.remote.ApiHelperFlavour;

import java.util.List;

import io.reactivex.Observable;


interface DataManager extends DbHelper, PreferencesHelper, ApiHelperFlavour {

    void updateApiHeader(String accessToken);
    void updateApiInfoHeader(String deviceType,String osVersion,String appVersion);
    void updateApiInfoHeader(String pushToken);

    void setUserAsLoggedOut();

    Observable<Boolean> seedDatabaseQuestions();

    Observable<Boolean> seedDatabaseOptions();

    void setLookupsModel(LookupResponse lookupsModel);

    LookupResponse getLookupsModel();

    void setPages(List<PageContentResponse> pages);

    List<PageContentResponse> getPages();

    //this is for development purposes, delete after go live
    void setVerificationCode(String code);

    String getVerificationCode();

    void setRegistrationModel(RegistrationModel registrationModel);

    RegistrationModel getRegistrationModel();

    void setCurrentUserModel(UserModel userModel);

    UserModel getCurrentUserModel();

    void updateUserInfo(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }

    }
}
