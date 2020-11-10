package com.wecare.android.ui.main.profile.userProfile.services.selection;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.wecare.android.R;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.requests.UserServiceRequestModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.interfaces.UserServiceCallBack;
import com.wecare.android.utils.WeCareUtils;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class UserServiceDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView titleTV;
    private TextInputEditText priceEdt;
    private AppCompatTextView priceTV, genderTV;
    private AppCompatButton cancelBtn;
    private AppCompatButton okBtn;
    private SubServiceResponse subServiceResponse;
    private UserServiceCallBack callBack;
    private UserServiceRequestModel requestModel = new UserServiceRequestModel();


    private boolean isEdit = false;

    private String currencyCode = "JOD";

    public void setCurrencyCode(String currencyCode) {
        if (currencyCode==null || currencyCode.equals(""))
            return;
        this.currencyCode = currencyCode;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setCallBack(UserServiceCallBack callBack) {
        this.callBack = callBack;
    }

    public void setGenderArrayList(ArrayList<LookUpModel> genderArrayList) {
        this.genderArrayList = genderArrayList;
    }

    private ArrayList<LookUpModel> genderArrayList;

    public void setSubServiceResponse(SubServiceResponse subServiceResponse) {
        this.subServiceResponse = subServiceResponse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_user_service, container, false);
        titleTV = (TextView) v.findViewById(R.id.titleTV);
        titleTV = (TextView) v.findViewById(R.id.titleTV);
        priceEdt = (TextInputEditText) v.findViewById(R.id.price_edt);
        priceTV = (AppCompatTextView) v.findViewById(R.id.priceTV);
        genderTV = (AppCompatTextView) v.findViewById(R.id.genderTV);
        cancelBtn = (AppCompatButton) v.findViewById(R.id.cancel_btn);
        okBtn = (AppCompatButton) v.findViewById(R.id.ok_btn);
        init();
        if (isEdit)
            initEdit();

        return v;
    }

    private void init() {
        titleTV.setText(subServiceResponse.getServiceName());
        genderTV.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        SpannableString minPrice = new SpannableString(subServiceResponse.getMinCost());
        SpannableString maxPrice = new SpannableString(subServiceResponse.getMaxCost());

        minPrice.setSpan(new ForegroundColorSpan(Color.BLUE), 0, minPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        maxPrice.setSpan(new ForegroundColorSpan(Color.BLUE), 0, maxPrice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getString(R.string.min_price_desc));
        sb.append(String.valueOf(minPrice)).append(currencyCode);
        sb.append(getString(R.string.max_price_desc));
        sb.append(String.valueOf(maxPrice)).append(currencyCode);

        priceTV.setText(sb);
    }

    private void initEdit() {
        priceEdt.setText(subServiceResponse.getPrice());
        requestModel.setGenderID(subServiceResponse.getGender());
        requestModel.setCaregiverServiceID(subServiceResponse.getCaregiverServiceID()+"");
        requestModel.setSubServiceID(subServiceResponse.getId() + "");
        genderTV.setText(getLookDesc());
    }

    private String getLookDesc() {
        for (LookUpModel gender : genderArrayList) {
            if (subServiceResponse.getGender().equals(gender.getId()))
                return gender.getTitle();
        }
        return "";
    }

    private void showGenderPicker() {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
                getString(R.string.gender),
                getString(R.string.general_search),
                null, genderArrayList, genderResultListener);

        simpleSearchDialogCompat.show();
    }

    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            genderTV.setText(item.getTitle());
            requestModel.setGenderID(item.getId());
            dialog.dismiss();
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.genderTV:
                showGenderPicker();
                break;

            case R.id.ok_btn:
                if (isFormValid()) {
                    requestModel.setSubServiceID(subServiceResponse.getId() + "");
                    requestModel.setPrice(WeCareUtils.getEditTextString(priceEdt));
                    this.dismiss();
                    callBack.doneClicked(subServiceResponse, requestModel);
                }

                break;
            case R.id.cancel_btn:
                this.dismiss();
                break;
        }
    }

    private boolean isFormValid() {
        if (WeCareUtils.getEditTextString(priceEdt) == null || WeCareUtils.getEditTextString(priceEdt).isEmpty()) {
            priceEdt.setError(getString(R.string.error_field_required));
            return false;
        }
        priceEdt.setError(null);

        if (Double.parseDouble(WeCareUtils.getEditTextString(priceEdt)) < Double.parseDouble(subServiceResponse.getMinCost())) {
            priceEdt.setError(getString(R.string.min_length_exceeded));
            return false;
        }
        priceEdt.setError(null);

        if (Double.parseDouble(WeCareUtils.getEditTextString(priceEdt)) > Double.parseDouble(subServiceResponse.getMaxCost())) {
            priceEdt.setError(getString(R.string.max_length_exceeded));
            return false;
        }
        priceEdt.setError(null);

        if (requestModel.getGenderID() == null || requestModel.getGenderID().isEmpty()) {
            genderTV.setError(getString(R.string.error_field_required));
            return false;
        }
        genderTV.setError(null);

        return true;

    }

}
