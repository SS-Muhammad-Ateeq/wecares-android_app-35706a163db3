//package com.wecare.android.utils;
//
///**
// * Created by oqadomi on 2016-12-30.
// */
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.os.Build;
//import android.preference.PreferenceManager;
//import androidx.annotation.RequiresApi;
//
//import java.util.Locale;
//
///**
// * This class is used to change your application locale and persist this change for the next time
// * that your app is going to be used.
// * <p/>
// * You can also change the locale of your application on the fly by using the setLocale method.
// * <p/>
// * Created by gunhansancar on 07/10/15.
// */
//public class LocaleHelper {
//
//    public static Context setLocale(Context context, String language) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            return updateResources(context, language);
//        }
//        return updateResourcesLegacy(context, language);
//    }
//
//    @SuppressWarnings("deprecation")
//    private static Context updateResourcesLegacy(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//
//        Resources resources = context.getResources();
//
//        Configuration configuration = resources.getConfiguration();
//        configuration.locale = locale;
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//
//        return context;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private static Context updateResources(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//
//        Resources resources = context.getResources();
//
//        Configuration configuration = resources.getConfiguration();
//        configuration.locale = locale;
//        configuration.setLayoutDirection(locale);
//        return context.createConfigurationContext(configuration);
//    }
//}
