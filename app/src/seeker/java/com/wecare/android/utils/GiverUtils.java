
package com.wecare.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import com.wecare.android.R;

import java.util.Locale;


public final class GiverUtils {

    private GiverUtils() {
        // This class is not publicly instantiable
    }

    /**/


    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static void setLocale(String localeCode, Bundle b, Activity activity, boolean refreshScreen) {
        //  Log.d(TAG+"set location function: "+localeCode);
        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        activity.getApplicationContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        activity.getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        if (refreshScreen) {
            activity.finish();
            activity.startActivity(activity.getIntent())
            ;
        }
    }
}
