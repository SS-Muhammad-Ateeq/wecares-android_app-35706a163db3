package com.wecare.android.ui.create_order.relative.add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import br.com.ilhasoft.support.validation.Validator;

import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.InsuranceCompanyModel;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.databinding.ActivityAddRelativeProfileBinding;
import com.wecare.android.interfaces.DateTimePickerSelectedValueListener;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.create_order.relative.RelativeProfileNavigator;
import com.wecare.android.ui.create_order.relative.RelativeProfileViewModel;
import com.wecare.android.utils.*;

import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import javax.inject.Inject;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class AddEditRelativeProfileActivity extends BaseActivity<ActivityAddRelativeProfileBinding, RelativeProfileViewModel>
        implements RelativeProfileNavigator {

    public static final String TAG = AddEditRelativeProfileActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    RelativeProfileViewModel viewModel;

    private MenuItem menuItem;
    ActivityAddRelativeProfileBinding binding;

    public static Intent getStartIntent(Context context, String profileID) {
        Intent intent = new Intent(context, AddEditRelativeProfileActivity.class);
        if (!WeCareUtils.isNullOrEmpty(profileID)) {
            intent.putExtra(AppConstants.ARGS_KEY_PROFILE_ID, profileID);
        }
        return intent;
    }

    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedGenderTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setGender(item);
            dialog.dismiss();
        }
    };

    SearchResultListener relationshipResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.RelationshipTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setSelectedNewRelationShip(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener nationalityResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
            binding.selectedNationalityTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setNationalityID(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener countriesOfServiceResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
            binding.selectedCountryOfServiceTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setCountryOfServiceID(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener typeOfInsuranceResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedTypeOfInsuranceTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setSelectedTypeOfInsurance(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener insuranceCompanyResultListener = new SearchResultListener<InsuranceCompanyModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, InsuranceCompanyModel item, int position) {
            binding.selectedInsuranceCompanyNameTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setSelectedInsuranceCompany(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener bloodTypeResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedBloodTypeTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setBlood_type(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener chronicDiseasesResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedChronicDiseasesTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setChronic_diseases((item.getEnglishName()));
            dialog.dismiss();
        }
    };

    SearchResultListener bloodPressureResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            viewModel.getToBeFilledRelativeProfileResponse().setBlood_pressure(binding.selectedBloodPressureTxt.getText().toString());

            binding.selectedBloodPressureTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setBlood_pressure((item.getEnglishName()));
            dialog.dismiss();
        }
    };

    SearchResultListener selectedIsThereHealthResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedIsThereHealthTxt.setText(item.getTitle());
            viewModel.getToBeFilledRelativeProfileResponse().setHealth_insurance(item.getId());

            if (item.getId().equals("1")) {
                //1 = yes
                enableDisableInsuranceFields(View.VISIBLE);
            } else {
                // 0 = no
                enableDisableInsuranceFields(View.GONE);
            }
            dialog.dismiss();
        }
    };

    DateTimePickerSelectedValueListener datePickerDialogListener = new DateTimePickerSelectedValueListener() {
        @Override
        public void onValueSelected(Calendar date) {
            viewModel.getToBeFilledRelativeProfileResponse().setInsurance_expiration_date(DateUtils.formatDateServer(date));

        }
    };

    DateTimePickerSelectedValueListener dateBirthDatePickerDialogListener = new DateTimePickerSelectedValueListener() {
        @Override
        public void onValueSelected(Calendar date) {
            viewModel.getToBeFilledRelativeProfileResponse().setBirthDate(DateUtils.formatDateServer(date));

        }
    };


    @Override
    public RelativeProfileViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RelativeProfileViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_relative_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        //update toolbar title
        addToolbar(R.id.toolbar, getString(R.string.wecare), true);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(AppConstants.ARGS_KEY_PROFILE_ID)) {
            getViewModel().isEditMode = true;
            getViewModel().enableFieldsEditMode.set(false);

            getViewModel().setProfileID(getIntent().getExtras().getString(AppConstants.ARGS_KEY_PROFILE_ID));

            if (getViewModel().getProfileID().equals(getViewModel().getDataManager().getCurrentUserId())) {
                getViewModel().isMyProfileEditMode = true;
            }
            //edit other relative profile // if this feature enabled later then this is the api.
//            getViewModel().fetchMyRelativeProfiles();

            if (getViewModel().isMyProfileEditMode) {
                //fetch my user profile to edit
                getViewModel().fetchUserProfileByID();
            } else {
                //fetch relative profile to edit
                getViewModel().fetchMyRelativeProfilesByID();
            }
        } else {
            getViewModel().enableFieldsEditMode.set(true);
            binding.countryCodePicker.setClickable(true);
            viewModel.getToBeFilledRelativeProfileResponse().setCountry_code(binding.countryCodePicker.getFullNumber());
        }

        //request data
        viewModel.getCountries(AppConstants.ACTIVE_COUNTRIES);
        viewModel.getCountries(AppConstants.NON_ACTIVE_COUNTRIES);
        viewModel.getInsuranceCompany();

        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();

        if (getViewModel().isMyProfileEditMode) {
            enableDisableMyProfileFields(View.VISIBLE);
        } else {
            enableDisableMyProfileFields(View.GONE);
        }

        //disable by default.
        enableDisableInsuranceFields(View.GONE);

        binding.countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                viewModel.getToBeFilledRelativeProfileResponse().setCountry_code(selectedCountry.getPhoneCode());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        menuItem = menu.findItem(R.id.action_save);

        if (getViewModel().isEditMode) {
            menuItem.setTitle(R.string.edit);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (!getViewModel().enableFieldsEditMode.get()) {

                    getViewModel().enableFieldsEditMode.set(true);
                    if (getViewModel().isMyProfileEditMode) {
                        enableDisableMyProfileFields(View.VISIBLE);
                    } else {
                        enableDisableMyProfileFields(View.GONE);
                    }

                    menuItem.setTitle(R.string.general_save);
                    return true;

                } else {
                    validator.toValidate();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }


            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                File file = imageFiles.get(0);
                if (type == AppConstants.PROFILE_IMAGE) {
                    getViewModel().picURL.set(file.getAbsolutePath());
                    viewModel.uploadProfilePicture(file);
                }
            }
        });
    }

    @Override
    public void onValidationSuccess() {
        super.onValidationSuccess();

        if (isDropDownsValid()) {
            if (binding.myProfileParentLinear.getVisibility() == View.VISIBLE) {
                viewModel.getToBeFilledRelativeProfileResponse().setFirstName(WeCareUtils.getEditTextString(binding.firstNameEdt));
                viewModel.getToBeFilledRelativeProfileResponse().setMiddleName(WeCareUtils.getEditTextString(binding.middleNameEdt));
                viewModel.getToBeFilledRelativeProfileResponse().setLastName(WeCareUtils.getEditTextString(binding.lastNameEdt));
                viewModel.getToBeFilledRelativeProfileResponse().setNationalityNumberID(WeCareUtils.getEditTextString(binding.selectedNationalityNumberEdt));

            } else {
                viewModel.getToBeFilledRelativeProfileResponse().setName(WeCareUtils.getEditTextString(binding.nameEdt));
                viewModel.getToBeFilledRelativeProfileResponse().setAge(WeCareUtils.getEditTextString(binding.selectedAgeEdt));
            }


            viewModel.getToBeFilledRelativeProfileResponse().setMobile_number(WeCareUtils.getEditTextString(binding.mobileNumberEdt));
            viewModel.getToBeFilledRelativeProfileResponse().setWeight(WeCareUtils.getEditTextString(binding.selectedWeightEdt));
            viewModel.getToBeFilledRelativeProfileResponse().setLength(WeCareUtils.getEditTextString(binding.selectedLengthEdt));
//            viewModel.getToBeFilledRelativeProfileResponse().setCountry_code(binding.countryCodePicker.getFullNumber());
//            viewModel.getToBeFilledRelativeProfileResponse().setBlood_pressure(WeCareUtils.getEditTextString(binding.selectedBloodPressureEdt));

            if (binding.insuranceParentLinear.getVisibility() == View.VISIBLE) {
                viewModel.getToBeFilledRelativeProfileResponse().setInsurance_number(WeCareUtils.getEditTextString(binding.insuranceNumberEdt));
            }

            if (getViewModel().isEditMode) {
                if (getViewModel().isMyProfileEditMode) {
                    //update my user profile
                    viewModel.updateMyProfiles();
                } else {
                    //update relative
                    viewModel.updateRelativeProfiles();
                }
                menuItem.setTitle(R.string.edit);
            } else {
                //add
                viewModel.createRelativeProfiles();
            }
        } else {
            showToast(getString(R.string.please_fill_all_required_fields));
        }
    }

    @Override
    public void onValidationError() {
        super.onValidationError();

        showToast(getString(R.string.please_fill_all_required_fields));
    }


    private boolean isDropDownsValid() {

        if (viewModel.getToBeFilledRelativeProfileResponse().getCountry_code() == null) {
            binding.countryCodeLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.countryCodeLayout.setError(null);

        if (viewModel.getToBeFilledRelativeProfileResponse().getGender() == null) {
            binding.genderLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.genderLayout.setError(null);


        if (!getViewModel().isMyProfileEditMode && viewModel.getToBeFilledRelativeProfileResponse().getSelectedNewRelationShip() == null) {
            binding.relationshipLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.relationshipLayout.setError(null);

//        if (viewModel.getToBeFilledRelativeProfileResponse().getBlood_type() == null) {
//            binding.bloodTypeLayout.setError(getString(R.string.error_field_required));
//            return false;
//        }
//        binding.bloodTypeLayout.setError(null);
//
//        if (viewModel.getToBeFilledRelativeProfileResponse().getBlood_pressure() == null) {
//            binding.BloodPressureLayout.setError(getString(R.string.error_field_required));
//            return false;
//        }
//        binding.BloodPressureLayout.setError(null);
//
//        if (viewModel.getToBeFilledRelativeProfileResponse().getChronic_diseases() == null) {
//            binding.chronicDiseasesLayout.setError(getString(R.string.error_field_required));
//            return false;
//        }
//        binding.chronicDiseasesLayout.setError(null);

        if (viewModel.getToBeFilledRelativeProfileResponse().getHealth_insurance() == null) {
            binding.isThereHealthLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.isThereHealthLayout.setError(null);

        if (binding.myProfileParentLinear.getVisibility() == View.VISIBLE) {
            if (viewModel.getToBeFilledRelativeProfileResponse().getBirthDate() == null) {
                binding.selectedBirthDateTxt.setError(getString(R.string.error_field_required));
                return false;
            }
            binding.selectedBirthDateTxt.setError(null);

            if (viewModel.getToBeFilledRelativeProfileResponse().getCountryOfServiceID() == null) {
                binding.selectedCountryOfServiceTxt.setError(getString(R.string.error_field_required));
                return false;
            }
            binding.selectedCountryOfServiceTxt.setError(null);

//            if (viewModel.getToBeFilledRelativeProfileResponse().getNationalityID() == null) {
//                binding.selectedNationalityTxt.setError(getString(R.string.error_field_required));
//                return false;
//            }
//            binding.selectedNationalityTxt.setError(null);
        }

        if (binding.insuranceParentLinear.getVisibility() == View.VISIBLE) {
            if (viewModel.getToBeFilledRelativeProfileResponse().getSelectedTypeOfInsurance() == null) {
                binding.typeOfInsuranceLayout.setError(getString(R.string.error_field_required));
                return false;
            }
            binding.typeOfInsuranceLayout.setError(null);

            if (viewModel.getToBeFilledRelativeProfileResponse().getInsurance_expiration_date() == null) {
                binding.insuranceExpirationDateLayout.setError(getString(R.string.error_field_required));
                return false;
            }
            binding.insuranceExpirationDateLayout.setError(null);

            if (viewModel.getToBeFilledRelativeProfileResponse().getSelectedInsuranceCompany() == null) {
                binding.insuranceCompanyNameLayout.setError(getString(R.string.error_field_required));
                return false;
            }
            binding.insuranceCompanyNameLayout.setError(null);
        }


        return true;
    }

    void enableDisableMyProfileFields(int isVisible) {
        if (isVisible == View.GONE) {//hide my profile fields
            //relative profile has only name.
            validator.enableValidation(binding.nameEdt);
            validator.enableValidation(binding.selectedAgeEdt);

            binding.selectedAgeEdt.setEnabled(true);
            binding.mobileNumberEdt.setEnabled(true);
            binding.countryCodePicker.setClickable(true);
//            binding.countryOfServiceTxt.setClickable(true);
//            binding.selectedCountryOfServiceTxt.setClickable(true);

            binding.nameEdt.setVisibility(View.VISIBLE);
            binding.relationshipLayout.setVisibility(View.VISIBLE);
            binding.profileImageParentLinear.setVisibility(View.GONE);
            binding.contactWeCareToChangeLabelTxt.setVisibility(View.GONE);

            //my profile has fir last mid name.
            validator.disableValidation(binding.firstNameEdt);
            validator.disableValidation(binding.middleNameEdt);
            validator.disableValidation(binding.lastNameEdt);
            validator.disableValidation(binding.selectedNationalityNumberEdt);
        } else {//show my profile fields
            //relative profile has only name.
            validator.disableValidation(binding.nameEdt);
            validator.disableValidation(binding.selectedAgeEdt);

            binding.selectedAgeEdt.setEnabled(false);
//            binding.countryOfServiceLayout.setEnabled(false);
            binding.mobileNumberEdt.setEnabled(false);
            binding.countryCodePicker.setClickable(false);
//            binding.countryOfServiceTxt.setClickable(true);
//            binding.selectedCountryOfServiceTxt.setClickable(true);

            binding.nameEdt.setVisibility(View.GONE);
            binding.relationshipLayout.setVisibility(View.GONE);
            binding.profileImageParentLinear.setVisibility(View.VISIBLE);
            binding.contactWeCareToChangeLabelTxt.setVisibility(View.VISIBLE);

            //my profile has fir last mid name.
            validator.enableValidation(binding.firstNameEdt);
            validator.enableValidation(binding.middleNameEdt);
            validator.enableValidation(binding.lastNameEdt);
//            validator.enableValidation(binding.selectedNationalityNumberEdt);as requested make it optional fields
            validator.disableValidation(binding.selectedNationalityNumberEdt);
        }
        binding.myProfileParentLinear.setVisibility(isVisible);
    }

    void enableDisableInsuranceFields(int isVisible) {
        if (isVisible == View.GONE) {
//            validator.disableValidation(binding.insuranceCompanyNameEdt);
            validator.disableValidation(binding.insuranceNumberEdt);
        } else {
//            validator.enableValidation(binding.insuranceCompanyNameEdt);
            validator.enableValidation(binding.insuranceNumberEdt);
        }
        binding.insuranceParentLinear.setVisibility(isVisible);
    }


    @Override
    public void goBack() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void updateRelativeProfilesList(List<RelativeProfileResponse> responseList) {

    }

    @Override
    public void onDeletedSuccessfully(RelativeProfileResponse profileResponse) {
        //not used
    }

    @Override
    public void genderClicked() {
        PickerDialogFactory.showGender(mContext, getViewModel().getDataManager(), genderResultListener);
    }

    @Override
    public void relationshipClicked() {
        PickerDialogFactory.showRelationship(mContext, getViewModel().getDataManager().getLookupsModel().getRelationshipsArrayList(), relationshipResultListener);
    }

    @Override
    public void ageClicked() {

    }

    @Override
    public void bloodTypeClicked() {
        PickerDialogFactory.showBloodTypeGender(mContext, getViewModel().getDataManager().getLookupsModel().getBloodTypesArrayList(), bloodTypeResultListener);
    }

    @Override
    public void weightClicked() {
        //not used
    }

    @Override
    public void lengthClicked() {
        //not used
    }

    @Override
    public void bloodPressureClicked() {
        PickerDialogFactory.showYesNoSelection(mContext, bloodPressureResultListener);
    }

    @Override
    public void chronicDiseasesClicked() {
        PickerDialogFactory.showYesNoSelection(mContext, chronicDiseasesResultListener);
    }

    @Override
    public void isThereHealthInsuranceClicked() {
        PickerDialogFactory.showYesNoSelection(mContext, selectedIsThereHealthResultListener);
    }

    @Override
    public void typeOfInsuranceClicked() {
        PickerDialogFactory.showTypeOfInsurance(mContext, getViewModel().getDataManager().getLookupsModel().getInsuranceTypesArrayList(), typeOfInsuranceResultListener);
    }

    @Override
    public void insuranceExpirationDateClicked() {
        DialogFactory.createDatePickerDialog(mContext, binding.selectedInsuranceExpirationDateTxt, datePickerDialogListener, null, null, false);
    }

    @Override
    public void insuranceCompanyNameClicked() {
        PickerDialogFactory.showInsuranceCompany(mContext, getViewModel().getInsuranceCompanyList(), insuranceCompanyResultListener);
    }

    @Override
    public void countriesOfServiceClicked() {
        PickerDialogFactory.showCountryOfService(mContext, getViewModel().getActiveCountries(), countriesOfServiceResultListener);
    }

    @Override
    public void birthDateClicked() {
        DialogFactory.createDatePickerDialog(mContext, binding.selectedBirthDateTxt, dateBirthDatePickerDialogListener, null, null, false);
    }

    @Override
    public void nationalityClicked() {
        PickerDialogFactory.showNationality(mContext, getViewModel().getAllCountries(), nationalityResultListener);

    }


    @Override
    public void fillUserInfoForEdit() {
        if (getViewModel().getToBeFilledRelativeProfileResponse() == null)
            return;

        //fill local parameter to skip create validation.
        getViewModel().getToBeFilledRelativeProfileResponse().setSelectedNewRelationShip(getViewModel().getToBeFilledRelativeProfileResponse().getRelationship().getId());
        //fill data to be edit.
        if (getViewModel().isMyProfileEditMode && getViewModel().getUserModel() != null) {

            getViewModel().getToBeFilledRelativeProfileResponse().setBirthDate(getViewModel().getUserModel().getBirthDate());
            getViewModel().getToBeFilledRelativeProfileResponse().setFirstName(getViewModel().getUserModel().getFirstName());
            getViewModel().getToBeFilledRelativeProfileResponse().setMiddleName(getViewModel().getUserModel().getMiddleName());
            getViewModel().getToBeFilledRelativeProfileResponse().setLastName(getViewModel().getUserModel().getLastName());
            getViewModel().getToBeFilledRelativeProfileResponse().setNationalityNumberID(getViewModel().getUserModel().getNationalId());
            getViewModel().getToBeFilledRelativeProfileResponse().setCountryOfServiceID(getViewModel().getUserModel().getCountryID());

            if (getViewModel().getUserModel().getNationality() != null) {
                getViewModel().getToBeFilledRelativeProfileResponse().setNationalityID(getViewModel().getUserModel().getNationality().getId());
                binding.selectedNationalityTxt.setText(getViewModel().getUserModel().getNationality().getTitle());
            }
            binding.firstNameEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getFirstName());
            binding.middleNameEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getMiddleName());
            binding.lastNameEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getLastName());
            binding.selectedNationalityNumberEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getNationalityNumberID());
            //drop down menu value.
            binding.selectedBirthDateTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getBirthDate());

            binding.selectedCountryOfServiceTxt.setText(String.format("%s", getViewModel().getUserModel().getCountry().getTitle()));
            //or use on of those
//            binding.selectedCountryOfServiceTxt.setText(String.format("%s", getViewModel().getToBeFilledRelativeProfileResponse().getCountryOfServiceID()));
//            if (!WeCareUtils.isNullOrEmpty(getViewModel().getUserModel().getCountryID())) {
//                for (CountryModel lookUpModel : getViewModel().getActiveCountries()) {
//                    if (lookUpModel.getId().equals(getViewModel().getUserModel().getCountryID())) {
//                        binding.selectedCountryOfServiceTxt.setText(lookUpModel.getTitle());
//                    }
//                }
//            }

        } else {
            binding.nameEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getName());
        }

        binding.selectedAgeEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getAge());
        binding.selectedGenderTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getGender().getTitle());
        binding.RelationshipTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getRelationship().getTitle());
        if (getViewModel().getToBeFilledRelativeProfileResponse().getCountry_code() != null) {
            binding.countryCodePicker.setCountryForPhoneCode(Integer.parseInt(getViewModel().getToBeFilledRelativeProfileResponse().getCountry_code()));
        }
        binding.mobileNumberEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getMobile_number());

//        binding.selectedBloodTypeTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getBlood_type());
        if (!WeCareUtils.isNullOrEmpty(getViewModel().getToBeFilledRelativeProfileResponse().getBlood_type())) {
            for (LookUpModel lookUpModel : getViewModel().getDataManager().getLookupsModel().getBloodTypesArrayList()) {
                if (lookUpModel.getId().equals(getViewModel().getToBeFilledRelativeProfileResponse().getBlood_type())) {
                    binding.selectedBloodTypeTxt.setText(lookUpModel.getTitle());
                }
            }
        }

        binding.selectedWeightEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getWeight());
        binding.selectedLengthEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getLength());
        binding.selectedChronicDiseasesTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getChronic_diseases());

        if (getViewModel().getToBeFilledRelativeProfileResponse().getBlood_pressure() != null) {
            if (getViewModel().getToBeFilledRelativeProfileResponse().getBlood_pressure().equals("0")) {
                // 0 = no
                binding.selectedBloodPressureTxt.setText(getString(R.string.no));
            } else {
                //1 = yes
                binding.selectedBloodPressureTxt.setText(getString(R.string.yes));
            }
        }

        if (getViewModel().getToBeFilledRelativeProfileResponse().getHealth_insurance() != null) {
            if (getViewModel().getToBeFilledRelativeProfileResponse().getHealth_insurance().equals("0")) {
                binding.selectedIsThereHealthTxt.setText(getString(R.string.no));
                enableDisableInsuranceFields(View.GONE);
                // 0 = no
            } else {
                //1 = yes
                binding.selectedIsThereHealthTxt.setText(getString(R.string.yes));
                enableDisableInsuranceFields(View.VISIBLE);

                if (getViewModel().getToBeFilledRelativeProfileResponse().getInsurance_company() != null) {
                    getViewModel().getToBeFilledRelativeProfileResponse().setSelectedInsuranceCompany(getViewModel().getToBeFilledRelativeProfileResponse().getInsurance_company().getId());
                    binding.selectedInsuranceCompanyNameTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getInsurance_company().getCompany());
                }
                if (getViewModel().getToBeFilledRelativeProfileResponse().getType_of_insurance() != null) {
                    getViewModel().getToBeFilledRelativeProfileResponse().setSelectedTypeOfInsurance(getViewModel().getToBeFilledRelativeProfileResponse().getType_of_insurance().getId());
                    binding.selectedTypeOfInsuranceTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getType_of_insurance().getmName());
                }
                binding.insuranceNumberEdt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getInsurance_number());

                binding.selectedInsuranceExpirationDateTxt.setText(getViewModel().getToBeFilledRelativeProfileResponse().getInsurance_expiration_date());
            }
        }
    }

    @Override
    public void addNewRelativeClicked() {

    }

    @Override
    public void userProfileClicked() {
        EasyImage.openChooserWithGallery(this, getString(R.string.app_name), AppConstants.PROFILE_IMAGE);
    }

}
