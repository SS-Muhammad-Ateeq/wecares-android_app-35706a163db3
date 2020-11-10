package com.wecare.android.data.local.prefs;

import com.wecare.android.data.DataManagerFlavour;


public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManagerFlavour.LoggedInMode mode);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);

    String getAccessToken();

    void setAccessToken(String accessToken);

    Boolean isIntroViewed();

    void setIntroViewed(Boolean isViewed);

    String getUserBio();

    void setCurrentUserBio(String userBio);

    String getCountryID();

    void setCountryID(String countryID);

    void setAppLocale(String locale);

    String getAppLocale();

    void setPushToken(String pushToken);

    String getPushToken();

}
