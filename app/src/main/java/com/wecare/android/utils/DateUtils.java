package com.wecare.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String formatDateServer(Calendar calendar) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(AppConstants.DATE_FORMAT_DISPLAY, Locale.ENGLISH);

        String date = dateFormatter.format(calendar.getTime());
        return date;

    }

    public static String formatDateServer(String dateString) {
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

        try {

            String reformattedStr = myFormat.format(fromUser.parse(dateString));
            return reformattedStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;

    }

    public static String updateEndTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(pad(hours)).append(':')
                .append(pad(mins)).append(" ").append(timeSet).toString();
        return aTime;
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static String getDayNameByDate(String dayDate) {
        String goal = "";
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        Date date = null;
        try {
            date = inFormat.parse(dayDate);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            goal = outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return goal;
    }

    public static long convertDateToMillis(String date) {
        long timeInMilliseconds = System.currentTimeMillis();

        //sample date 2019-06-10 18:23:31
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        try {
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? "just now" : "الان";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? "a minute ago" : "قبل دقيقة";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + (AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? " minutes ago" : " دقائق");
        } else if (diff < 90 * MINUTE_MILLIS) {
            return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? "an hour ago" : "قبل ساعة";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + (AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? " hours ago" :  " ساعات");

        } else if (diff < 48 * HOUR_MILLIS) {
            return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? "yesterday" : "البارحة";

        } else {
            return diff / DAY_MILLIS + (AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? " days ago" : " ايام");
        }
    }
}