package com.wecare.android.ui.main.order.special;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.databinding.ActivitySpecialRequestBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.PickerDialogFactory;
import com.wecare.android.utils.WeCareUtils;

import javax.inject.Inject;

import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class SpecialRequestActivity extends BaseActivity<ActivitySpecialRequestBinding, SpecialRequestViewModel> implements SpecialRequestNavigator {

    public static final String TAG = SpecialRequestActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    /**/
    SpecialRequestViewModel viewModel;
    ActivitySpecialRequestBinding binding;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SpecialRequestActivity.class);
    }

    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedGenderTxt.setText(item.getTitle());
            viewModel.getFilterObject().setCaregiver_gender_id(item.getId());
            viewModel.getFilterObject().setCaregiverGenderValue(item.getTitle());
            dialog.dismiss();
        }
    };

    SearchResultListener ageResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedAgeTxt.setText(item.getTitle());
            viewModel.getFilterObject().setCaregiverAgeValue(item.getTitle());
            viewModel.getFilterObject().setCaregiver_age(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener languageResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedLanguageTxt.setText(item.getTitle());
            viewModel.getFilterObject().setCaregiver_language_id(item.getId());
            viewModel.getFilterObject().setCaregiverLanguageValue(item.getTitle());
            dialog.dismiss();
        }
    };

    @Override
    public SpecialRequestViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SpecialRequestViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_special_request;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        addToolbar(R.id.toolbar, getString(R.string.special_request), true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        //get args
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT)) {
            getViewModel().setFilterObject(bundle.getParcelable(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT));

            if (!WeCareUtils.isNullOrEmpty(getViewModel().getFilterObject().getCaregiver_language_id())) {
                for (LookUpModel lookUpModel : getViewModel().getDataManager().getLookupsModel().getLanguageArrayList()) {
                    if (lookUpModel.getId().equals(getViewModel().getFilterObject().getCaregiver_language_id())) {
                        binding.selectedLanguageTxt.setText(lookUpModel.getTitle());
                    }
                }
            }

            if (!WeCareUtils.isNullOrEmpty(getViewModel().getFilterObject().getCaregiver_gender_id())) {
                for (LookUpModel lookUpModel : getViewModel().getDataManager().getLookupsModel().getGenderArrayList()) {
                    if (lookUpModel.getId().equals(getViewModel().getFilterObject().getCaregiver_gender_id())) {
                        binding.selectedGenderTxt.setText(lookUpModel.getTitle());
                    }
                }
            }

            if (!WeCareUtils.isNullOrEmpty(getViewModel().getFilterObject().getCaregiver_age())) {
                for (LookUpModel lookUpModel : getViewModel().getDataManager().getLookupsModel().getCaregiverAgeArrayList()) {
                    if (lookUpModel.getId().equals(getViewModel().getFilterObject().getCaregiver_age())) {
                        binding.selectedAgeTxt.setText(lookUpModel.getTitle());
                    }
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onSaveFilterSelection() {
        if (viewModel.getFilterObject() != null) {
            Intent data = new Intent();
            data.putExtra(AppConstants.ARGS_KEY_EDITED_FILTER_GIVER_OBJECT, viewModel.getFilterObject());
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public void goBack() {

    }

    @Override
    public void doneClicked() {
        onSaveFilterSelection();
    }

    @Override
    public void ageClicked() {
        PickerDialogFactory.showGiverAge(mContext, getViewModel().getDataManager(), ageResultListener);
//        showNumberPickerDialog();
    }

    @Override
    public void genderClicked() {
        PickerDialogFactory.showProviderGender(mContext, getViewModel().getDataManager(), genderResultListener);
    }

    @Override
    public void languageClicked() {
        PickerDialogFactory.showLanguages(mContext, getViewModel().getDataManager(), languageResultListener);
    }

    private void showNumberPickerDialog() {
        RelativeLayout linearLayout = new RelativeLayout(mContext);
        final NumberPicker aNumberPicker = new NumberPicker(mContext);
        aNumberPicker.setMaxValue(60);
        aNumberPicker.setMinValue(20);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker, numPicerParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(getString(R.string.preferred_caregiver_age));
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String agePre = String.format("%s", aNumberPicker.getValue());
                        binding.selectedAgeTxt.setText(agePre);
                        viewModel.getFilterObject().setCaregiver_age(agePre);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
