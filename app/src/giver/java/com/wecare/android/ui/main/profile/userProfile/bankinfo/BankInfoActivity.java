package com.wecare.android.ui.main.profile.userProfile.bankinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.BankModel;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.databinding.ActivityBankInfoBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.WeCareUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class BankInfoActivity extends BaseActivity<ActivityBankInfoBinding, BankInfoViewModel> implements BankInfoActivityNavigator {

    ArrayList<CountryModel> countryModels;
    ArrayList<CityModel> cityModels;

    @Inject
    ViewModelProviderFactory factory;
    BankInfoViewModel viewModel;

    ActivityBankInfoBinding binding;

    @Override
    public BankInfoViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(BankInfoViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_info;

    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BankInfoActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.bank_info), true);
        viewModel.setNavigator(this);
        viewModel.getActiveCountries();
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();
        viewModel.getBankInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
                validator.toValidate();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @Override
    public void countriesClicked() {
        showCountries();
    }

    @Override
    public void citiesClicked() {
        showCities();
    }

    @Override
    public void bankinformationClicked() {
       showDialogBankInformation();
    }

    private void showDialogBankInformation() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_bank_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void showCountries() {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.country),
                getString(R.string.general_search),
                null, countryModels, countriesResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showCities() {
        if (cityModels == null)
            return;
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.city),
                getString(R.string.general_search),
                null, cityModels, citiesResultListener);

        simpleSearchDialogCompat.show();
    }

    SearchResultListener countriesResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
            binding.countryTv.setText(item.getTitle());
            viewModel.getCities(item.getId() + "");
            viewModel.getBankInfoRequest().setCountry(item.getId() + "");
            dialog.dismiss();
        }
    };

    SearchResultListener citiesResultListener = new SearchResultListener<CityModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CityModel item, int position) {
            binding.cityTV.setText(item.getTitle());
            viewModel.getBankInfoRequest().setCity(item.getId() + "");
            dialog.dismiss();
        }
    };


    @Override
    public void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels) {
        this.countryModels = countryModels;

    }

    @Override
    public void citiesFinishedSuccessfully(ArrayList<CityModel> cityModels) {
        this.cityModels = cityModels;

    }

    @Override
    public void bankInfoFetched(BankModel model) {
        viewModel.getBankInfoRequest().setCountry(model.getCountry().getId() + "");
        viewModel.getBankInfoRequest().setCity(model.getCity().getId() + "");
        binding.countryTv.setText(model.getCountry().getTitle());
        binding.cityTV.setText(model.getCity().getTitle());

        binding.accountNameEdt.setText(model.getHolderName());
        binding.bankNameEdt.setText(model.getBankName());
        binding.accountNumberEdt.setText(model.getAccountNumber());
        binding.transitnoEdt.setText(model.getTransitno());

        if (model.getCountry().getId()!=null)
            viewModel.getCities(model.getCountry().getId());

    }

    @Override
    public void bankInfoUpdatedSuccessfully() {
        DialogFactory.createFeedBackDialog(this, "", getString(R.string.saved_successfully), getString(R.string.ok), null, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                finish();
            }
        });
    }

    @Override
    public void onValidationSuccess() {
        super.onValidationSuccess();
        if (isDropDownsValid()){
            viewModel.getBankInfoRequest().setHolderName(WeCareUtils.getEditTextString(binding.accountNameEdt));
            viewModel.getBankInfoRequest().setBankName(WeCareUtils.getEditTextString(binding.bankNameEdt));
            viewModel.getBankInfoRequest().setAccountNumber(WeCareUtils.getEditTextString(binding.accountNumberEdt));
            viewModel.getBankInfoRequest().setTransitno(WeCareUtils.getEditTextString(binding.transitnoEdt));

            viewModel.updateBankInfo();
        }

    }

    private boolean isDropDownsValid() {
        if (viewModel.getBankInfoRequest().getCountry() == null) {
            binding.countryLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.countryLayout.setError(null);
        if (viewModel.getBankInfoRequest().getCountry() == null) {
            binding.cityLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.cityLayout.setError(null);
        return true;
    }
}
