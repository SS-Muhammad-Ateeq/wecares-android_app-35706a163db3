package com.wecare.android.ui.main.profile.userProfile.personalInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.databinding.ActivityEditProfileInformationBinding;
import com.wecare.android.ui.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

//not used
public class EditProfileInformationActivity extends BaseActivity<ActivityEditProfileInformationBinding, EditPersonalInformationViewModel>
        implements EditPersonalInformationActivityNavigator {

    ArrayList<CountryModel> countries;

    @Inject
    ViewModelProviderFactory factory;

    ActivityEditProfileInformationBinding binding;
    private EditPersonalInformationViewModel viewModel;

    @Override
    public EditPersonalInformationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(EditPersonalInformationViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_profile_information;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        viewModel.getActiveCountries();
        addToolbar(R.id.toolbar, getString(R.string.personal_information), true);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, EditProfileInformationActivity.class);
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
            case R.id.action_save:
//           startActivity(FreeLancerProfileActivity.newIntent(this));
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @Override
    public void showAccountTypes() {

    }

    @Override
    public void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels) {

    }

    @Override
    public void countriesClicked() {

    }

    @Override
    public void citiesFinishedSuccessfully(ArrayList<CityModel> countryModels) {

    }

    @Override
    public void citiesClicked() {

    }

    @Override
    public void genderClicked() {

    }

    @Override
    public void nationalityClicked() {

    }

    @Override
    public void membershipTypeClicked() {

    }

    @Override
    public void birthDateClicked() {

    }

    @Override
    public void informationUpdatedSuccessfully() {

    }


//    SearchResultListener countriesResultListener = new SearchResultListener<CountryModel>() {
//        @Override
//        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
//            binding.registrationCountryOfServiceTv.setText(item.getTitle());
//            viewModel.getDataManager().getRegistrationModel().setCountryOfService(item.getId() + "");
//            dialog.dismiss();
//        }
//    };
//    SearchResultListener nationalityResultListener = new SearchResultListener<CountryModel>() {
//        @Override
//        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
//            binding.registrationNationalityTv.setText(item.getTitle());
//            viewModel.getDataManager().getRegistrationModel().setNationality(item.getId() + "");
//            dialog.dismiss();
//        }
//    };
//
//    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
//        @Override
//        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
//            binding.genderMembershipTv.setText(item.getTitle());
//            viewModel.getDataManager().getRegistrationModel().setGender(item.getId());
//            dialog.dismiss();
//        }
//    };

}
