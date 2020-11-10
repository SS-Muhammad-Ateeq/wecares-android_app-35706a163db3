package com.wecare.android.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;
import android.view.ContextThemeWrapper;

import com.wecare.android.R;

import java.util.Locale;

public class LocalizedContextWrapper extends ContextThemeWrapper {

    public LocalizedContextWrapper(Context base) {
        super(base, R.style.AppTheme);
    }

    public static ContextWrapper wrap(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale;
            context.getResources().updateConfiguration(
                    configuration,
                    context.getResources().getDisplayMetrics()
            );
        }

        return new LocalizedContextWrapper(context);
    }
}