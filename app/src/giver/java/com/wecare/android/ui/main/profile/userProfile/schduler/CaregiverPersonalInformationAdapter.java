package com.wecare.android.ui.main.profile.userProfile.schduler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Index;

import com.google.android.gms.common.util.ArrayUtils;
import com.wecare.android.R;
import com.wecare.android.data.model.api.models.ScheduleDayModel;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.utils.DateUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class CaregiverPersonalInformationAdapter extends RecyclerView.Adapter<CaregiverPersonalInformationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ScheduleDayModel> scheduleDayModels;
    private ArrayList<String> arrayList;
    View view;
    private boolean isSameTimes;

    public CaregiverPersonalInformationAdapter(Context context, ArrayList<ScheduleDayModel> scheduleDayModels, ArrayList<String> selectedDay, boolean isSameTimes) {
        this.context = context;
        this.scheduleDayModels = scheduleDayModels;
        this.arrayList=selectedDay;
        this.isSameTimes=isSameTimes;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schdule_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String selectedDay = GetDayName(String.valueOf(scheduleDayModels.get(position).getCurrentDay()));
        if (arrayList.contains(selectedDay)) {
            String start = DateUtils.updateEndTime(Integer.parseInt(scheduleDayModels.get(position).getStartTime().split(":")[0]), Integer.parseInt(scheduleDayModels.get(position).getStartTime().split(":")[1]));
            String endTime = DateUtils.updateEndTime(Integer.parseInt(scheduleDayModels.get(position).getEndTime().split(":")[0]), Integer.parseInt(scheduleDayModels.get(position).getEndTime().split(":")[1]));
            holder.startTV.setText(start);
            holder.EndTV.setText(endTime);
            holder.weakday.setText(GetDayName(String.valueOf(scheduleDayModels.get(position).getCurrentDay())));
            holder.dayLayout.setVisibility(View.VISIBLE);
           // if(!isSameTimes){
                holder.startTV.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_right), null);
                holder.EndTV.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_right), null);
           // }
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CaregiverServicesSchedulerActivity)context).deleteSchedule(position);
                }
            });

            holder.startTV.setOnClickListener(v ->
            ((CaregiverServicesSchedulerActivity)context).showTimePickerDialog(true,context.getString(R.string.start_times), holder.startTV, position, null, null, true));
            holder.EndTV.setOnClickListener(v -> ((CaregiverServicesSchedulerActivity)context).showTimePickerDialog(true,context.getString(R.string.end_times), holder.EndTV, position, null, null, false));
        } else {
            Log.e("dksfhdk",selectedDay);
            holder.dayLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return scheduleDayModels.size();
    }

    public void UpdateAdapter(ArrayList<ScheduleDayModel> scheduleDayModels1,String day) {
        scheduleDayModels=scheduleDayModels1;
        if(arrayList.size()>0&&arrayList.contains(day)){
            arrayList.remove(arrayList.indexOf(day));
        }else{
            arrayList.add(day);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView weakday, startTV, EndTV;
        ImageView delete;
        RelativeLayout dayLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weakday = itemView.findViewById(R.id.weakday);
            startTV = itemView.findViewById(R.id.startTV);
            EndTV = itemView.findViewById(R.id.EndTV);
            delete = itemView.findViewById(R.id.delete);

            dayLayout = itemView.findViewById(R.id.sundayLayout);

        }
    }

    public static String GetDayName(String dayName) {
        if (dayName.startsWith("1")) {
            return "Sunday";
        } else if (dayName.startsWith("2")) {
            return "Monday";
        } else if (dayName.startsWith("3")) {
            return "Tuesday";
        } else if (dayName.startsWith("4")) {
            return "Wednesday";
        } else if (dayName.startsWith("5")) {
            return "Thursday";
        } else if (dayName.startsWith("6")) {
            return "Friday";
        } else if (dayName.startsWith("7")) {
            return "Saturday";
        }
        return "";
    }
}
