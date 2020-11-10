package com.wecare.android.ui.main.profile.userProfile.schduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.ScheduleDayModel;
import com.wecare.android.data.model.api.responses.UserScheduleResponse;
import com.wecare.android.databinding.ActivityServicesSchedulerBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DateUtils;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.WeCareUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CaregiverServicesSchedulerActivity extends BaseActivity<ActivityServicesSchedulerBinding, CaregiverServicesSchedulerViewModel> implements CaregiverServicesSchedulerNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private CaregiverServicesSchedulerViewModel viewModel;
    int lastSelected=-1;

    ArrayList<ScheduleDayModel> selectedDays = new ArrayList<ScheduleDayModel>(30);
    ArrayList<ScheduleDayModel> scheduleDayModels = new ArrayList<ScheduleDayModel>(30);

    Map<String,ArrayList<ScheduleDayModel>> schedulemap = new HashMap<>();
    ScheduleDayModel sameDayModel = new ScheduleDayModel(0);
    ArrayList<String> arrayList=new ArrayList<>();
    ActivityServicesSchedulerBinding binding;

    //by default
    boolean isSameTimes = true;
    boolean is24Available;

    @Override
    public CaregiverServicesSchedulerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CaregiverServicesSchedulerViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_scheduler;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CaregiverServicesSchedulerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar,  getString(R.string.schedule_services), true);
        viewModel.getUserSchedule();
        binding.scheduledDayRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        binding.fullTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                handle24AvailabilityUI(b);
            }
        });
    }

    private void handle24AvailabilityUI(boolean is24Available) {
        if (is24Available) {
            binding.sameTimeSelectionLayout.setVisibility(View.GONE);
            binding.allDaysScrollView.setVisibility(View.VISIBLE);
            binding.scheduledDaysScrollView.setVisibility(View.GONE);
            this.is24Available = true;
        } else {
            binding.sameTimeSelectionLayout.setVisibility(View.VISIBLE);
            binding.allDaysScrollView.setVisibility(View.GONE);
            binding.scheduledDaysScrollView.setVisibility(View.VISIBLE);
            this.is24Available = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveItem:
                if (is24Available) {
                    viewModel.createSchedule(selectedDays, is24Available);
                    return true;
                }
                if (selectedDays.size() > 0) {
                    if (validateTimeSelection())
                        viewModel.createSchedule(selectedDays, is24Available);
                } else
                    DialogFactory.createErrorDialog(this, "", getString(R.string.error_select_days_times));
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private boolean validateTimeSelection() {
        for (ScheduleDayModel model : selectedDays) {
            if (model.getStartTime() == null || model.getStartTime().isEmpty()) {
                DialogFactory.createErrorDialog(this, "", getString(R.string.error_select_start_time) + " " + WeCareUtils.getDayNameByIndex(this, model.getCurrentDay()));
                return false;
            } else if (model.getEndTime() == null || model.getEndTime().isEmpty()) {
                DialogFactory.createErrorDialog(this, "", getString(R.string.error_select_end_time) + " " + WeCareUtils.getDayNameByIndex(this, model.getCurrentDay()));
                return false;
            }
        }
        return true;
    }

    @Override
    public void daySelected(int dayIndex, boolean isSelected, boolean isUserSelection) {
        String dayname=CaregiverPersonalInformationAdapter.GetDayName(String.valueOf(dayIndex));
        if (isUserSelection) {
            //add day object in case selected from UI
            if (isSelected) {
                lastSelected=dayIndex;
                if (dayIndex==1){
                    if(schedulemap.containsKey(dayname)) {
                        arrayList.add(dayname);
                        UpdateRecycler();
                    }
                }
                else if(dayIndex==2) {
                    if(schedulemap.containsKey(dayname)) {
                        arrayList.add(dayname);
                        UpdateRecycler();
                    }
                }
                else if(dayIndex==3) {
                    if(schedulemap.containsKey(dayname)) {
                        arrayList.add(dayname);
                        UpdateRecycler();
                    }
                }
                else if(dayIndex==4) {
                    if(schedulemap.containsKey(dayname)) {
                        arrayList.add(dayname);
                        UpdateRecycler();
                    }
                }
                else if(dayIndex==5) {
                    if(schedulemap.containsKey(dayname)) {
                        arrayList.add(dayname);
                        UpdateRecycler();
                    }
                }
                else if(dayIndex==6) {
                    if(schedulemap.containsKey(dayname)) {
                        arrayList.add(dayname);
                        UpdateRecycler();
                    }
                }
                else if(dayIndex==7) {
                    if(schedulemap.containsKey(dayname)) {
                        arrayList.add(dayname);
                        UpdateRecycler();
                    }

                }
            }
            else {
                lastSelected = -1;
                if (arrayList.size() > 0 && arrayList.contains(CaregiverPersonalInformationAdapter.GetDayName(String.valueOf(dayIndex)))) {
                    int index = arrayList.indexOf(CaregiverPersonalInformationAdapter.GetDayName(String.valueOf(dayIndex)));
                    arrayList.remove(index);
                }
                UpdateRecycler();
            }
//            selectedDays.remove(getSelectedDayByIndex(dayIndex));

        }

        switch (dayIndex) {
            case AppConstants.SUNDAY_INDEX:
                handleDayWeekButtonSelection(binding.sundayBtn, binding.sundayLayout, dayIndex, isSelected);
                handleDayWeekButtonSelection(binding.sundayBtn, binding.sundayLayouttimeslot1, AppConstants.SUNDAY_TIMESLOT_1_INDEX, isSelected);
                handleDayWeekButtonSelection(binding.sundayBtn, binding.sundayLayouttimeslot2, AppConstants.SUNDAY_TIMESLOT_2_INDEX, isSelected);
                viewModel.getSundayChecked().set(isSelected);
                break;
            case AppConstants.MONDAY_INDEX:
                handleDayWeekButtonSelection(binding.monBtn, binding.mondayLayout, dayIndex, isSelected);
                handleDayWeekButtonSelection(binding.monBtn, binding.mondayLayouttimeslot1, AppConstants.MONDAY_TIMESLOT_1_INDEX, isSelected);
                handleDayWeekButtonSelection(binding.monBtn, binding.mondayLayouttimeslot2, AppConstants.MONDAY_TIMESLOT_2_INDEX, isSelected);
                viewModel.getMondayChecked().set(isSelected);
                break;
            case AppConstants.TUESDAY_INDEX:
                handleDayWeekButtonSelection(binding.tuesdayBtn, binding.tuesdayLayout, dayIndex, isSelected);
                handleDayWeekButtonSelection(binding.tuesdayBtn, binding.tuesdayLayouttimeslot1, AppConstants.TUESDAY_TIMESLOT_1_INDEX, isSelected);
                handleDayWeekButtonSelection(binding.tuesdayBtn, binding.tuesdayLayouttimeslot2, AppConstants.TUESDAY_TIMESLOT_2_INDEX, isSelected);
                viewModel.getTuesdayChecked().set(isSelected);
                break;
            case AppConstants.WEDNSDAY_INDEX:
                handleDayWeekButtonSelection(binding.wendsdayBtn, binding.wednesdayLayout, dayIndex, isSelected);
                handleDayWeekButtonSelection(binding.wendsdayBtn, binding.wednesdayLayouttimeslot1, AppConstants.WEDNSDAY_TIMESLOT_1_INDEX, isSelected);
                handleDayWeekButtonSelection(binding.wendsdayBtn, binding.wednesdayLayouttimeslot2, AppConstants.WEDNSDAY_TIMESLOT_2_INDEX, isSelected);
                viewModel.getWednesdayChecked().set(isSelected);
                break;
            case AppConstants.THURSDAY_INDEX:
                handleDayWeekButtonSelection(binding.thursdayBtn, binding.thursdayLayout, dayIndex, isSelected);
                handleDayWeekButtonSelection(binding.thursdayBtn, binding.thursdayLayouttimeslot1, AppConstants.THURSDAY_TIMESLOT_1_INDEX, isSelected);
                handleDayWeekButtonSelection(binding.thursdayBtn, binding.thursdayLayouttimeslot2, AppConstants.THURSDAY_TIMESLOT_2_INDEX, isSelected);
                viewModel.getThursdayChecked().set(isSelected);
                break;
            case AppConstants.FRIDAY_INDEX:
                handleDayWeekButtonSelection(binding.fridayBtn, binding.fridayLayout, dayIndex, isSelected);
                handleDayWeekButtonSelection(binding.fridayBtn, binding.fridayLayouttimeslot1, AppConstants.FRIDAY_TIMESLOT_1_INDEX, isSelected);
                handleDayWeekButtonSelection(binding.fridayBtn, binding.fridayLayouttimeslot2, AppConstants.FRIDAY_TIMESLOT_2_INDEX, isSelected);
                viewModel.getFridayChecked().set(isSelected);
                break;
            case AppConstants.SATURDAY_INDEX:
                handleDayWeekButtonSelection(binding.saturdayBtn, binding.saturdayLayout, dayIndex, isSelected);
                handleDayWeekButtonSelection(binding.saturdayBtn, binding.saturdayLayouttimeslot1, AppConstants.SATURDAY_TIMESLOT_1_INDEX, isSelected);
                handleDayWeekButtonSelection(binding.saturdayBtn, binding.saturdayLayouttimeslot2, AppConstants.SATURDAY_TIMESLOT_2_INDEX, isSelected);
                viewModel.getSaturdayChecked().set(isSelected);
                break;
        }
    }

    @Override
    public void timesOfCaresSelected() {
        isSameTimes = !isSameTimes;
        handleTimeOfCareChanged();
    }

    @Override
    public void sameStartDateClicked() {
        showTimePickerDialog(false,getString(R.string.start_times), binding.startDateTV, 0, null, null, true);
    }

    @Override
    public void sameEndDatClicked() {
        if (sameDayModel.getStartHour() != null)
            showTimePickerDialog(false,getString(R.string.start_times), binding.endDateTV, 0, sameDayModel.getStartHour(), sameDayModel.getStartMinute(), false);
        else {
            DialogFactory.createErrorDialog(this, "", getString(R.string.error_select_start_time));
        }
    }

    @Override
    public void dayStartDateClicked(int index) {
        if (isSameTimes)
            return;
        showTimePickerDialog(false,getString(R.string.start_times), getTextViewByTag("start_" + index), index, null, null, true);

    }

    @Override
    public void dayEndDateClicked(int index) {
        if (isSameTimes)
            return;
        if (getSelectedDayByIndex(index).getStartHour() != null)
            showTimePickerDialog(false,getString(R.string.start_times), getTextViewByTag("end_" + index), index, getSelectedDayByIndex(index).getStartHour(), getSelectedDayByIndex(index).getStartMinute(), false);
        else {
            DialogFactory.createErrorDialog(this, "", getString(R.string.error_select_start_time) + " " + WeCareUtils.getDayNameByIndex(this, index));

        }
    }

    @Override
    public void scheduleCreatedSuccessfully() {
        DialogFactory.createFeedBackDialog(this, "", getString(R.string.success_service_schedule), getString(R.string.ok), getResources().getDrawable(R.drawable.success_img), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {

            }
        });
        MapDays(true);
    }

    @Override
    public void userScheduleFetchedSuccessfully(UserScheduleResponse userScheduleResponse) {
        if ((userScheduleResponse.getIsAvailable24() + "").equals(AppConstants.PHP_TRUE)) {
            handle24AvailabilityUI(true);
            binding.fullTimeSwitch.setChecked(true);
            return;
        }
        selectedDays = userScheduleResponse.getDayModels();
        isSameTimes = userScheduleResponse.getSameTime() != null ? userScheduleResponse.getSameTime() : false;
        if (isSameTimes) {
            String startTime = userScheduleResponse.getDayModels().get(0).getStartTime();
            String endTime = userScheduleResponse.getDayModels().get(0).getEndTime();
            sameDayModel.setStartTime(startTime);
            sameDayModel.setEndTime(endTime);
            binding.startDateTV.setText(DateUtils.updateEndTime(Integer.parseInt(startTime.split(":")[0]), Integer.parseInt(startTime.split(":")[1])));
            binding.endDateTV.setText(DateUtils.updateEndTime(Integer.parseInt(endTime.split(":")[0]), Integer.parseInt(endTime.split(":")[1])));
        }
         ArrayList<ScheduleDayModel> temp=null;
          String dayname="";
          MapDays(false);
        if(schedulemap.containsKey("Sunday")) {
            arrayList.add("Sunday");
            lastSelected=1;
            daySelected(lastSelected, true, false);
        }
        handleTimeOfCareChanged();
    }
    public void MapDays(boolean iSActionperform){
        schedulemap=new HashMap<>();
        Collections.sort(selectedDays, (o1, o2) -> Integer.valueOf(o1.getCurrentDay()).compareTo(o2.getCurrentDay()));
        for (ScheduleDayModel model : selectedDays) {
            String newday=CaregiverPersonalInformationAdapter.GetDayName(String.valueOf(model.getCurrentDay()));
            Log.e("dayselected",newday);
            ArrayList<ScheduleDayModel> arrayList=new ArrayList<>();
            if(schedulemap.containsKey(newday)){
                if(!model.getStartTime().isEmpty()&&!model.getEndTime().isEmpty()) {
                    arrayList = schedulemap.get(newday);
                    arrayList.add(model);
                    schedulemap.remove(newday);
                }else{
                    continue;
                }
            }else{
                if(!model.getStartTime().isEmpty()&&!model.getEndTime().isEmpty()) {
                    arrayList = new ArrayList<>();
                    arrayList.add(model);
                }else{
                    continue;
                }
            }

            schedulemap.put(newday,arrayList);
        }
        if(iSActionperform){
            UpdateRecycler();
        }
    }

    public void showTimePickerDialog(boolean isList,String title, TextView textView, int index, Integer minHour, Integer minMinute, boolean isStart) {
        DialogFactory.showTimePickerDialog(this, getSupportFragmentManager(), title, minHour, minMinute, 30, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                textView.setText(DateUtils.updateEndTime(hourOfDay, minute));
                if (isSameTimes) {
                    if (isStart) {
                        sameDayModel.setStartTime(hourOfDay + ":" + minute);
                        sameDayModel.setStartHour(hourOfDay);
                        sameDayModel.setStartMinute(minute);
                        setAllSelectedStartDaysAsSame(sameDayModel.getStartTime() != null ? sameDayModel.getStartTime() : "");
                        handleTimeOfCareChanged();
                        if(isList){
                            ScheduleDayModel scheduleDayModel=selectedDays.get(index);
//                            scheduleDayModel.setStartTime(sameDayModel.getStartTime());
                            sameDayModel.setCurrentDay(scheduleDayModel.getCurrentDay());
                            selectedDays.set(index,sameDayModel);
                            UpdateRecycler();
                        }
                    } else {
                        sameDayModel.setEndTime(hourOfDay + ":" + minute);
                        setAllSelectedEndDaysAsSame(sameDayModel.getEndTime() != null ? sameDayModel.getEndTime() : "");
                        if(isList){
                            ScheduleDayModel scheduleDayModel=selectedDays.get(index);
                            scheduleDayModel.setEndTime(sameDayModel.getEndTime());
                            sameDayModel.setCurrentDay(scheduleDayModel.getCurrentDay());
                            selectedDays.set(index,sameDayModel);
                            UpdateRecycler();
                        }
                    }
                } else {
                    if (isStart) {
//                        getSelectedDayByIndex(index).setStartTime(hourOfDay + ":" + minute);
//                        getSelectedDayByIndex(index).setStartHour(hourOfDay);
//                        getSelectedDayByIndex(index).setStartMinute(minute);
                        if(isList){

                            ScheduleDayModel scheduleDayModel=selectedDays.get(index);
                            Log.e("hsdkfh", String.valueOf(scheduleDayModel.getCurrentDay()));
                            scheduleDayModel.setStartTime(hourOfDay + ":" + minute);
                            scheduleDayModel.setStartHour(hourOfDay);
                            scheduleDayModel.setStartMinute(minute);
                            selectedDays.set(index,scheduleDayModel);

                        }
                    } else
//                        getSelectedDayByIndex(index).setEndTime(hourOfDay + ":" + minute);
                    if(isList){
                        ScheduleDayModel scheduleDayModel=selectedDays.get(index);
                        scheduleDayModel.setEndTime(hourOfDay + ":" + minute);
                        selectedDays.set(index,scheduleDayModel);
                        Log.e("hsdkfh", String.valueOf(scheduleDayModel.getCurrentDay()));
                    }
                }
            }
        });
    }


    private void handleDayWeekButtonSelection(AppCompatButton button, LinearLayout dayView, int index, boolean isSelected) {
        button.setTextColor(getResources().getColor(isSelected ? R.color.white : R.color.colorPrimary));
        button.setBackgroundDrawable(getResources().getDrawable(isSelected ? R.drawable.rounded_dayweek_selected : R.drawable.rounded_dayweek_unselected));
        dayView.setVisibility(isSelected ? View.GONE : View.GONE);
    }

    private ScheduleDayModel getSelectedDayByIndex(int index) {
        for (ScheduleDayModel dayModel : selectedDays) {
            if (dayModel.getCurrentDay() == index){
                return dayModel;}
        }
        return null;
    }

    private void handleTimeOfCareChanged() {
        if (isSameTimes) {
            binding.timesTV.setText(getString(R.string.different_times));
            binding.sameTimeLayout.setVisibility(View.VISIBLE);
            if (selectedDays.size() == 0)
                return;
            setAllSelectedStartDaysAsSame(sameDayModel.getStartTime() != null ? sameDayModel.getStartTime() : "");
            setAllSelectedEndDaysAsSame(sameDayModel.getEndTime() != null ? sameDayModel.getEndTime() : "");
            UpdateRecycler();
        } else {
            binding.timesTV.setText(getString(R.string.same_times));
            binding.sameTimeLayout.setVisibility(View.GONE);

//            for (ScheduleDayModel dayModel : selectedDays) {
//                TextView start = getTextViewByTag("start_" + dayModel.getCurrentDay());
//                if(!dayModel.getStartTime().isEmpty())
//                start.setText(dayModel.getStartTime() != null ? DateUtils.updateEndTime(Integer.parseInt(dayModel.getStartTime().split(":")[0]), Integer.parseInt(dayModel.getStartTime().split(":")[1])) : getString(R.string.select));
////                start.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_right), null);
//                TextView end = getTextViewByTag("end_" + dayModel.getCurrentDay());
//                if(!dayModel.getEndTime().isEmpty())
//                end.setText(dayModel.getEndTime() != null ? DateUtils.updateEndTime(Integer.parseInt(dayModel.getEndTime().split(":")[0]), Integer.parseInt(dayModel.getEndTime().split(":")[1])) : getString(R.string.select));
////                end.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_right), null);
//            }
            UpdateRecycler();
        }
    }

    private void setAllSelectedStartDaysAsSame(String startTime) {

        for (ScheduleDayModel dayModel : selectedDays) {
            if(startTime.equals("")){
                startTime="00:00";
            }
            dayModel.setStartTime(startTime);
//            TextView start = getTextViewByTag("start_" + dayModel.getCurrentDay());
//            start.setText(binding.startDateTV.getText());
//            start.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        }
    }

    private void setAllSelectedEndDaysAsSame(String endTime) {

        for (ScheduleDayModel dayModel : selectedDays) {
            if(endTime.equals("")){
                endTime="00:00";
            }
            dayModel.setEndTime(endTime);
            //update UI
//            TextView end = getTextViewByTag("end_" + dayModel.getCurrentDay());
//            end.setText(binding.endDateTV.getText());
//            end.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }


    private TextView getTextViewByTag(String tag) {
        return binding.bottomLayout.findViewWithTag(tag);
    }

    private TextView getTextViewByTag(String tag, LinearLayout layout) {
        return layout.findViewWithTag(tag);
    }
    public void UpdateRecycler(){
        scheduleDayModels=new ArrayList<>();
        if(schedulemap.size()>0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if(schedulemap.containsKey(arrayList.get(i))){
                    scheduleDayModels.addAll(schedulemap.get(arrayList.get(i)));
                }
            }
        }
        if(scheduleDayModels.size()>0) {
            Collections.sort(scheduleDayModels, (o1, o2) -> Integer.valueOf(o1.getCurrentDay()).compareTo(o2.getCurrentDay()));
            binding.scheduledDayRecyclerView.setAdapter(new CaregiverPersonalInformationAdapter(this, scheduleDayModels, arrayList,isSameTimes));
            binding.scheduledDayRecyclerView.setVisibility(View.VISIBLE);
            }else{
            binding.scheduledDayRecyclerView.setAdapter(null);

        }
    }
    public void btnAdd(View view){
        String name="";
        if(lastSelected==-1&&scheduleDayModels.size()>0){
            lastSelected=scheduleDayModels.get(scheduleDayModels.size()-1).getCurrentDay();
           name=CaregiverPersonalInformationAdapter.GetDayName(String.valueOf(lastSelected));
        }else{
            name=CaregiverPersonalInformationAdapter.GetDayName(String.valueOf(lastSelected));
        }
        if(schedulemap.size()>0&&schedulemap.containsKey(name)){
            if(!arrayList.contains(name)){
                arrayList.add(name);
            }
            ArrayList<ScheduleDayModel> scheduleDayModels=schedulemap.get(name);
            ScheduleDayModel scheduleDayModel=new ScheduleDayModel();
            scheduleDayModel.setCurrentDay(lastSelected);
            scheduleDayModel.setEndTime("00:00");
            scheduleDayModel.setStartTime("00:00");
            scheduleDayModels.add(scheduleDayModel);
            schedulemap.remove(name);
            schedulemap.put(name,scheduleDayModels);
            selectedDays.add(scheduleDayModel);
        }else {
            if(lastSelected!=-1) {
                arrayList.add(name);
                ArrayList<ScheduleDayModel> scheduleDayModels = new ArrayList<>();
                ScheduleDayModel scheduleDayModel = new ScheduleDayModel();
                scheduleDayModel.setCurrentDay(lastSelected);
                scheduleDayModel.setEndTime("00:00");
                scheduleDayModel.setStartTime("00:00");
                scheduleDayModels.add(scheduleDayModel);
                schedulemap.put(name, scheduleDayModels);
                selectedDays.add(scheduleDayModel);
            }else{
                Toast.makeText(this,"Select Day",Toast.LENGTH_LONG).show();
            }
        }
        UpdateRecycler();
    }

    @Override
    public void deleteSchedule(int index) {
        if(selectedDays.size()>0){
            selectedDays.remove(index);
        }
        viewModel.createSchedule(selectedDays,false);

    }
}
