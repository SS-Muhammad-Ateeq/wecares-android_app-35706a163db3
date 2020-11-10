package com.wecare.android.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    public static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_USER_BIO = "PREF_KEY_CURRENT_USER_BIO";
    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_COUNTRY_ID = "PREF_KEY_COUNTRY_ID";
    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL
            = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_PUSH_TOKEN = "PREF_KEY_PUSH_TOKEN";
    private static final String PREF_KEY_INTRO_VIEWED = "KEY_INTRO_VIEWED";

    private static final String PREFS_KEY_CURRENT_LOCALIZATION = "PREF_KEY_CURRENT_LOCALIZATION";

    public static String KEY_NO_VALUE = "KEY_NO_VALUE";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrentUserId() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_ID, null);

    }

    @Override
    public void setCurrentUserId(String userId) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ID, userId).apply();

    }

    @Override
    public String getCurrentUserName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManagerFlavour.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManagerFlavour.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getAccessToken() {
        return "Bearer "+ mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public Boolean isIntroViewed() {
        return mPrefs.getBoolean(PREF_KEY_INTRO_VIEWED, false);
    }

    @Override
    public void setIntroViewed(Boolean isViewed) {
        mPrefs.edit().putBoolean(PREF_KEY_INTRO_VIEWED, isViewed).apply();

    }

    @Override
    public String getUserBio() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_BIO, null);
    }

    @Override
    public void setCurrentUserBio(String userBio) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_BIO, userBio).apply();

    }

    @Override
    public String getCountryID() {
        return mPrefs.getString(PREF_KEY_COUNTRY_ID, null);
    }

    @Override
    public void setCountryID(String countryID) {
        mPrefs.edit().putString(PREF_KEY_COUNTRY_ID, countryID).apply();

    }

    @Override
    public void setAppLocale(String locale) {
        mPrefs.edit().putString(PREFS_KEY_CURRENT_LOCALIZATION, locale).apply();

    }

    @Override
    public String getAppLocale() {
        return mPrefs.getString(PREFS_KEY_CURRENT_LOCALIZATION, KEY_NO_VALUE);
    }

    @Override
    public void setPushToken(String pushToken) {
        mPrefs.edit().putString(PREF_KEY_PUSH_TOKEN, pushToken).apply();

    }

    @Override
    public String getPushToken() {
        return mPrefs.getString(PREF_KEY_PUSH_TOKEN, KEY_NO_VALUE);
    }
}
