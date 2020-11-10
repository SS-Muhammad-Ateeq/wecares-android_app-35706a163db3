package com.wecare.android.ui.auth.registration.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wecare.android.R;
import com.wecare.android.interfaces.MembershipTypeCallBack;
import com.wecare.android.utils.AppConstants;

import androidx.fragment.app.DialogFragment;

public class MembershipTypeDialogFragment extends DialogFragment implements View.OnClickListener {
    MembershipTypeCallBack callBack;
    private LinearLayout feesPerOrderLayout;
    private LinearLayout yearlyLayout;
    private LinearLayout midTermLayout;
    private LinearLayout quarterlyLayout;


    public void setCallBack(MembershipTypeCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_member_ship_type, container, false);
        feesPerOrderLayout = (LinearLayout) v.findViewById(R.id.feesPerOrderLayout);
        yearlyLayout = (LinearLayout) v.findViewById(R.id.yearlyLayout);
        midTermLayout = (LinearLayout) v.findViewById(R.id.midTermLayout);
        quarterlyLayout = (LinearLayout) v.findViewById(R.id.quarterlyLayout);
        feesPerOrderLayout.setOnClickListener(this);
        yearlyLayout.setOnClickListener(this);
        midTermLayout.setOnClickListener(this);
        quarterlyLayout.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feesPerOrderLayout:
                callBack.membershipTypeSelected(AppConstants.MEMBERSHIP_FEES_PER_ORDER,getString(R.string.fees_per_order));
                break;
            case R.id.yearlyLayout:
                callBack.membershipTypeSelected(AppConstants.MEMBERSHIP_YEARLY,getString(R.string.yearly));
                break;
            case R.id.midTermLayout:
                callBack.membershipTypeSelected(AppConstants.MEMBERSHIP_MID_TERM,getString(R.string.midterm));
                break;
            case R.id.quarterlyLayout:
                callBack.membershipTypeSelected(AppConstants.MEMBERSHIP_QUARTERLY,getString(R.string.quarterly));
                break;
        }
        this.dismiss();
    }
}
