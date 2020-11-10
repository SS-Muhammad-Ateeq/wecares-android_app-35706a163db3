package com.wecare.android.di.module;

import android.app.Application;
import android.content.Context;

import com.wecare.android.R;
import com.wecare.android.data.AppDataManagerFlavour;
import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.local.db.AppDatabase;
import com.wecare.android.data.local.db.AppDbHelper;
import com.wecare.android.data.local.db.DbHelper;
import com.wecare.android.data.local.prefs.AppPreferencesHelper;
import com.wecare.android.data.local.prefs.PreferencesHelper;
import com.wecare.android.data.remote.*;
import com.wecare.android.di.ApiInfo;
import com.wecare.android.di.DatabaseInfo;
import com.wecare.android.di.PreferenceInfo;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.ServerKeys;
import com.wecare.android.utils.rx.AppSchedulerProvider;
import com.wecare.android.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return Credentials.basic(ServerKeys.AUTH_USER, ServerKeys.AUTH_PASS);
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

//    @Provides
//    @Singleton
//    DataManager provideDataManager(AppDataManager appDataManager) {
//        return appDataManager;
//    }

    @Provides
    @Singleton
    DataManagerFlavour provideDataManager(AppDataManagerFlavour appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

//    @Provides
//    @Singleton
//    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
//        return appApiHelper;
//    }

    @Provides
    @Singleton
    ApiHelperFlavour provideApiHelperFlavour(AppApiHelperFlavour appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey, PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(preferencesHelper.getAccessToken());
    }
    @Provides
    @Singleton
    ApiHeader.InfoApiHeader provideInfoApiHeader() {
        return new ApiHeader.InfoApiHeader();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
