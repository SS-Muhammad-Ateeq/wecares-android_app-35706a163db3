
package com.wecare.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.wecare.android.R;
import com.wecare.android.data.model.api.responses.PageContentResponse;

import java.util.List;


public final class WeCareUtils {


    public static final int SNACK_BAR_DURATION = 5000;
    private static boolean isRequestDone = false;
    private static Handler mHandler;

    private WeCareUtils() {
        // This class is not publicly instantiable
    }

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

    /**
     * @return true if network available otherwise false
     */
    public static boolean isNetworkAvailable(final Context me) {
        ConnectivityManager connectivityManager = (ConnectivityManager) me
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    /**
     * Extract EditText string
     *
     * @param editText EditText contains string
     * @return Extracted string value
     */
    public static String getEditTextString(EditText editText) {
        return editText.getText().toString().trim();
    }


    /**
     * @param str any string
     * @return true if the string is null or empty otherwise returns false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String getDayNameByIndex(Context context, int dayIndex) {
        switch (dayIndex) {
            case AppConstants.SUNDAY_INDEX:
                return context.getString(R.string.sunday);
            case AppConstants.MONDAY_INDEX:
                return context.getString(R.string.monday);
            case AppConstants.TUESDAY_INDEX:
                return context.getString(R.string.tuesday);
            case AppConstants.WEDNSDAY_INDEX:
                return context.getString(R.string.wednesday);
            case AppConstants.THURSDAY_INDEX:
                return context.getString(R.string.thursday);
            case AppConstants.FRIDAY_INDEX:
                return context.getString(R.string.friday);
            case AppConstants.SATURDAY_INDEX:
                return context.getString(R.string.saturday);
        }
        return "";
    }

    public static String getPageURL(int pageID, List<PageContentResponse> pageContentResponses){
        //default is we care url
        String url = "https://wecare-app.net/";
        for (PageContentResponse pageContentResponse:
             pageContentResponses) {
            if (pageContentResponse.getId()==pageID){
                url = pageContentResponse.getUrl();
                return url;
            }
        }
        return url;
    }

//    /*show and check */
//    public static boolean showNoInternetSnackBar(Context context) {
//        //ShowDialog(activity, activity.getString(R.string.error), activity.getString(R.string.error_NO_INTERNET_CONNECTION), activity.getString(R.string.general_OK), null, null, null);
//        //((BaseFragmentActivity)activity).showErrorDialogForNoInternet();
//
//        ///.check if available
//        boolean isAvailable = isNetworkAvailable(context);
//        if (!isAvailable) {
//            BaseActivity activity = (BaseActivity) context;
//            Snackbar snackBar = Snackbar.make(context);
//            snackBar.applyStyle(R.style.SnackBarSingleLine);
//
//            snackBar.text(activity.getResources().getString(R.string.error_message_no_internet))
//                    .actionText(activity.getResources().getString(R.string.general_close))
//                    //.singleLine(true)
//                    .textColor(activity.getResources().getColor(R.color.icons))
//                    .actionTextColor(Color.parseColor("#FF03A9F4"))
//                    .duration(SNACK_BAR_DURATION);
//
//            snackBar.show(activity);
//        }
//        return isAvailable;
//    }
//
//    public static void hideNoInternetSnackBar(Context context) {
//        SnackBar snackBar = SnackBar.make(context);
//        if (snackBar.getState() == SnackBar.STATE_SHOWN) {
//            snackBar.dismiss();
//        }
//    }

    public static void setLocale(String localeCode, Activity activity, boolean refreshScreen) {
        //  Log.d(TAG+"set location function: "+localeCode);
        LocaleManager.setNewLocale(activity, localeCode);
//        Locale locale = new Locale(localeCode);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
//        activity.getApplicationContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
//        activity.getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        if (refreshScreen) {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }

    /**
     * Determines if given points are inside view
     *
     * @param x    - x coordinate of point
     * @param y    - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isPointInsideView(float x, float y, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }
}
