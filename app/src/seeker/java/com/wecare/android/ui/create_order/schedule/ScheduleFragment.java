package com.wecare.android.ui.create_order.schedule;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.FragmentScheduleBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.WeCareUtils;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;

public class ScheduleFragment extends BaseFragment<FragmentScheduleBinding, ScheduleViewModel> implements ScheduleNavigator {

    public static final String TAG = ScheduleFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ScheduleViewModel viewModel;

    private FragmentScheduleBinding binding;

    private boolean isTodaySelected;
    private String selectedDate = "";
    private String selectedTime = "";


    public static ScheduleFragment newInstance() {
        Bundle args = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        //validation
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();
    }

    @Override
    public void onValidationSuccess() {
//        if (isDropDownsValid()) {
//            viewModel.getDataManager()..setValue(WeCareUtils.getEditTextString(binding.edt));
        //your action here;
//        }
    }

    @Override
    public void onValidationError() {
        super.onValidationError();
    }

    @Override
    public ScheduleViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ScheduleViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void selectDate() {
        pickerDate();
    }

    @Override
    public void selectTime() {
        if (WeCareUtils.isNullOrEmpty(selectedDate)) {
            getBaseActivity().showToast("Please select schedule date first");
            return;
        }
//        pickerTime();
        pickerTimeWithTimeInterval();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getBaseActivity().setTitle(getString(R.string.appointment));
    }

    public void pickerTimeWithTimeInterval() {

        Integer mHour = null;
        Integer mMinute = null;
        if (isTodaySelected) {
            final Calendar c = Calendar.getInstance();
            //business asked to allow the order only after 3 hours from now
            mHour = c.get(Calendar.HOUR_OF_DAY) + 3;
            mMinute = c.get(Calendar.MINUTE);
        }

        DialogFactory.showTimePickerDialog(getBaseActivity(), getChildFragmentManager(), getString(R.string.time_of_service),
                mHour, mMinute, 15, new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {

                        selectedTime = String.format(Locale.ENGLISH, "%s:%s", hourOfDay, minute);
                        if (minute == 0) {
                            selectedTime = String.format(Locale.ENGLISH, "%s%s", selectedTime, "0");
                        }

                        int hour = hourOfDay % 12;
                        String representingText = String.format(Locale.ENGLISH, "%02d:%02d %s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");
                        binding.scheduleTimeTxt.setText(representingText);
                    }
                });
    }


    public void pickerTime() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    //                Calendar datetime = Calendar.getInstance();
//                Calendar c = Calendar.getInstance();
//                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                datetime.set(Calendar.MINUTE, minute);
//                if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
//                    //it's after current
//                    int hour = hourOfDay % 12;
//                    String s = String.format(Locale.ENGLISH, "%02d:%02d %s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");

                    selectedTime = String.format(Locale.ENGLISH, "%d:%d", hourOfDay, minute);
                    binding.scheduleTimeTxt.setText(selectedTime);

//                } else {
//                    //it's before current'
//                    getBaseActivity().showToast(getString(R.string.invalid_time_please_select_greater_than), true);
//                }
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, mHour, mMinute, false);
        timePickerDialog.setTitle(getString(R.string.time_of_service));

        // Launch Time Picker Dialog
        timePickerDialog.show();
    }

    public void pickerDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getBaseActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                final Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.set(Calendar.YEAR, year);
                selectedDateCalendar.set(Calendar.MONTH, monthOfYear + 1);
                selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
                selectedDateCalendar.set(Calendar.MINUTE, 0);
                selectedDateCalendar.set(Calendar.SECOND, 0);
                selectedDateCalendar.set(Calendar.MILLISECOND, 0);

                //is today selected
                if (c.get(Calendar.DATE) == selectedDateCalendar.get(Calendar.DATE)) {
                    isTodaySelected = true;
                }
                else {
                    isTodaySelected = false;
                }
                selectedDate = String.format(Locale.ENGLISH, "%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                binding.scheduleDateTxt.setText(selectedDate);
                selectedTime = "";
                binding.scheduleTimeTxt.setText(getString(R.string.select));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public class TimeIgnoringComparator implements Comparator<Calendar> {
        public int compare(Calendar c1, Calendar c2) {
            if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
                return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
            if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
                return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
        }
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

}
