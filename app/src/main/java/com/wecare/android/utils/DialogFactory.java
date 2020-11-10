package com.wecare.android.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wecare.android.R;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.interfaces.AccountTypeCallBack;
import com.wecare.android.interfaces.DateTimePickerSelectedValueListener;
import com.wecare.android.interfaces.PickerSelectedValueListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;

/**
 * Created by hussam on 5/21/18.
 */

public class DialogFactory {
    private DialogFactory() {
    }

    public static Dialog createListPickerDialog(Context context, boolean showTitle, ArrayAdapter adapter, String title, final PickerSelectedValueListener valueSelectedListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                valueSelectedListener.onValueSelected(i);
            }
        });
        Dialog dialog = builder.create();
        if (showTitle) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleLayout = inflater.inflate(R.layout.custom_title, null);
            TextView titleView = (TextView) titleLayout.findViewById(R.id.title);
            titleView.setText(title);
            builder.setCustomTitle(titleView);
            dialog = builder.create();
        }
        return dialog;
    }

    public static void createMultiChoiceMenu(Context context, String title, CharSequence[] charSequences, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener, DialogInterface.OnClickListener oKClickListener) {

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMultiChoiceItems(charSequences, checkedItems, listener)
                .setPositiveButton(context.getResources().getString(android.R.string.ok), oKClickListener)
                .create();
        dialog.show();
    }

    public static CharSequence[] listToCharSequence(List<LookUpModel> lookupModels) {

        List<String> listing = new ArrayList<String>();

        for (int i = 0; i < lookupModels.size(); i++) {
            listing.add(lookupModels.get(i).toString());
        }
        final CharSequence[] cs = listing.toArray(new CharSequence[listing.size()]);

        return cs;
    }


    public static List<String> listToStringList(List<LookUpModel> lookupModels) {

        List<String> listing = new ArrayList<String>();

        for (int i = 0; i < lookupModels.size(); i++) {
            listing.add(lookupModels.get(i).toString());
        }

        return listing;
    }

    public static void createDatePickerDialog(Context context, final View view, final DateTimePickerSelectedValueListener valueSelectedListener, final Calendar minDate, final Calendar maxDate, boolean hasMinDate) {
        createDatePickerDialog(context, view, null, valueSelectedListener, minDate, maxDate, hasMinDate);
    }

    public static void createDatePickerDialog(Context context, final View view, Date selectedDateTime, final DateTimePickerSelectedValueListener valueSelectedListener, final Calendar minDate, final Calendar maxDate, boolean hasMinDate) {
        final Calendar defaultDateTime = Calendar.getInstance();

        if (selectedDateTime != null) {
            defaultDateTime.setTime(selectedDateTime);
        }

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                if (minDate != null && minDate.getTime().compareTo(newDate.getTime()) > 0) {
                    return;
                }

                setDateToView(view, valueSelectedListener, newDate);

            }

        }, defaultDateTime.get(Calendar.YEAR), defaultDateTime.get(Calendar.MONTH), defaultDateTime.get(Calendar.DAY_OF_MONTH));

        // set current time as minimum for picker
        if (hasMinDate)
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        if (maxDate != null) {
            initCalender(maxDate);
            dialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
        }
        dialog.show();
    }

    public static void initCalender(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setDateToView(final View view, final DateTimePickerSelectedValueListener valueSelectedListener, Calendar newDate) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(AppConstants.DATE_FORMAT_DISPLAY, Locale.US);

        if (view != null) {
            ((AppCompatTextView) view).setText(dateFormatter.format(newDate.getTime()));
        }
        valueSelectedListener.onValueSelected(newDate);
    }

    public static void showTimePickerDialog(Context context, FragmentManager fragmentManager, String title, Integer minHour, Integer minMinute, int minutesInterval, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener listener) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog;
        timePickerDialog = TimePickerDialog.newInstance(
                listener,
                hour,
                minute,
                false
        );
        timePickerDialog.setTitle(title);
        if (minHour != null)
            timePickerDialog.setMinTime(minHour, minMinute, 0);
        timePickerDialog.setTimeInterval(1, minutesInterval, 60);
//        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_1);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        timePickerDialog.show(fragmentManager, "Timepickerdialog");

    }


    public static void createFeedBackDialog(Context context, String title, String message, String buttonDesc, Drawable drawable, MaterialDialog.SingleButtonCallback neutralCallBack) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(context.getResources().getColor(R.color.black))
                .content(message)
                .contentColor(context.getResources().getColor(R.color.black))
                .neutralText(buttonDesc)
                .neutralColor(context.getResources().getColor(R.color.colorPrimary))
                .onNeutral(neutralCallBack)
                .icon(drawable)
                .show();
    }

    public static void createFeedBackDialog(Context context, String title, String message, String buttonDesc, Drawable drawable, MaterialDialog.SingleButtonCallback neutralCallBack, boolean cancelable) {
        new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(context.getResources().getColor(R.color.black))
                .content(message)
                .contentColor(context.getResources().getColor(R.color.black))
                .neutralText(buttonDesc)
                .neutralColor(context.getResources().getColor(R.color.colorPrimary))
                .onNeutral(neutralCallBack)
                .icon(drawable)
                .cancelable(cancelable)
                .show();
    }

    //error dialog
    public static void createErrorDialog(Context context, String title, String message) {
        createFeedBackDialog(context, title,
                message,
                context.getString(android.R.string.ok),
                context.getResources().getDrawable(R.drawable.ic_dialog_fail), null);
    }

    public static void createErrorDialog(Context context, String title, String message, MaterialDialog.SingleButtonCallback neutralCallBack) {
        createFeedBackDialog(context, title,
                message,
                context.getString(android.R.string.ok),
                context.getResources().getDrawable(R.drawable.ic_dialog_fail), neutralCallBack);
    }

    public static void createSuccessDialog(Context context, String title, String message, MaterialDialog.SingleButtonCallback neutralCallBack) {
        createFeedBackDialog(context, title,
                message,
                context.getString(android.R.string.ok),
                context.getResources().getDrawable(R.drawable.ic_success_big), neutralCallBack);
    }

    public static void createReactDialog(Context context, String title, String message, String positiveText, String negativeText, Drawable drawable,
                                         MaterialDialog.SingleButtonCallback positiveCallBack, MaterialDialog.SingleButtonCallback negativeCallBack) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(context.getResources().getColor(R.color.black))
                .content(message)
                .contentColor(context.getResources().getColor(R.color.black))
                .positiveText(positiveText)
                .negativeText(negativeText)
                .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                .negativeColor(context.getResources().getColor(R.color.colorPrimary))
                .onPositive(positiveCallBack)
                .onNegative(negativeCallBack);

        if (drawable != null) {
            builder.icon(drawable);
        }
        builder.show();
    }

    public static void createRadioGroupPicker(Context context, String title, String message, String positiveText, MaterialDialog.ListCallbackSingleChoice singleChoice
            , ArrayList<LookUpModel> models) {
        int position;

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.titleColor(context.getResources().getColor(R.color.black));
        builder.positiveColor(context.getResources().getColor(R.color.colorPrimary));
        builder.negativeColor(context.getResources().getColor(R.color.colorPrimary));
        builder.positiveText(positiveText);


        builder.items(models);
        //-1 means no selections by default
        builder.itemsCallbackSingleChoice(-1, singleChoice);

        builder.show();

    }

    public static void createInputDialog(Context context, String title, String message, String hint, String positiveText, MaterialDialog.InputCallback callback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.titleColor(context.getResources().getColor(R.color.black));
        builder.positiveColor(context.getResources().getColor(R.color.colorPrimary));
        builder.negativeColor(context.getResources().getColor(R.color.colorPrimary));
        builder.input(hint, "", false, callback);
        builder.inputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        builder.positiveText(positiveText);
//        builder.input(50,1000);
        builder.show();
    }


    public static void createPickNumberDialog(Context context, String title, int min, int max,
                                              NumberPicker.OnValueChangeListener callback) {
        final Dialog d = new Dialog(context);
        d.setTitle(title);
        d.setContentView(R.layout.dialog_pick_number);
        Button b1 = d.findViewById(R.id.button_ok);
        Button b2 = d.findViewById(R.id.button_cancel);

        final NumberPicker np = d.findViewById(R.id.numberPicker);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(callback);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv.setText(String.valueOf(np.getValue())); //set the value to textview
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();

    }


    public static void showMenuOrganizationTypeSheet(final Context mContext, final AccountTypeCallBack callBack, final int defaultValue) {
        final BottomSheetDialog mDialog = new BottomSheetDialog(mContext);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_menu_organization_type, null);

        final TextView menu_organization_tv = (TextView) view.findViewById(R.id.menu_organization_tv);
        final TextView menu_individual_tv = (TextView) view.findViewById(R.id.menu_individual_tv);
        TextView menu_header_cancel_tv = (TextView) view.findViewById(R.id.menu_header_cancel_tv);
        TextView menu_header_done_tv = (TextView) view.findViewById(R.id.menu_header_done_tv);

        menu_organization_tv.setTypeface(null, defaultValue == AppConstants.ACCOUNT_TYPE_ORGANIZATION ? Typeface.BOLD : Typeface.NORMAL);
        menu_individual_tv.setTypeface(null, defaultValue == AppConstants.ACCOUNT_TYPE_INDIVIDUAL ? Typeface.BOLD : Typeface.NORMAL);


        mDialog.setContentView(view);
        mDialog.show();
        mDialog.setCancelable(true);

        menu_organization_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_organization_tv.setTypeface(null, Typeface.BOLD);
                menu_individual_tv.setTypeface(null, Typeface.NORMAL);
                callBack.accountTypeSelected(AppConstants.ACCOUNT_TYPE_ORGANIZATION);
                mDialog.dismiss();
            }
        });


        menu_individual_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_individual_tv.setTypeface(null, Typeface.BOLD);
                menu_organization_tv.setTypeface(null, Typeface.NORMAL);
                callBack.accountTypeSelected(AppConstants.ACCOUNT_TYPE_INDIVIDUAL);
                mDialog.dismiss();
            }
        });

        menu_header_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        menu_header_done_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.accountTypeSelected(defaultValue);
                mDialog.dismiss();
            }
        });

    }

}