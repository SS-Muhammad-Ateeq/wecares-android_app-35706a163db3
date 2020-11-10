package com.wecare.android.ui.main.profile.userProfile.personalInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.databinding.ActivityCaregiverPersonalInfoBinding;
import com.wecare.android.interfaces.AccountTypeCallBack;
import com.wecare.android.interfaces.DateTimePickerSelectedValueListener;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DateUtils;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.WeCareUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CaregiverPersonalInformationActivity extends BaseActivity<ActivityCaregiverPersonalInfoBinding, CaregiverPersonalInformationViewModel> implements CaregiverPersonalInformationActivityNavigator {

    ArrayList<CountryModel> countries;
    ArrayList<MembershipType> membershipTypes;
    ArrayList<CountryModel> nationalities;
    ArrayList<CityModel> cities;
    ActivityCaregiverPersonalInfoBinding binding;
    private int accountType = AppConstants.ACCOUNT_TYPE_INDIVIDUAL;
    boolean isEditMode = false;
    private Menu menu;
    private CountryModel item;
    @Inject
    ViewModelProviderFactory factory;
    private CaregiverPersonalInformationViewModel viewModel;

    @Override
    public CaregiverPersonalInformationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CaregiverPersonalInformationViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_caregiver_personal_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        viewModel.getActiveCountries();
        viewModel.getpersonalCities("1");
        viewModel.getNationalities();
        addToolbar(R.id.toolbar, getString(R.string.personal_information), true);
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();
        loadData();
        setUpEditMode();
        if (viewModel.getDataManager().getCurrentUserModel().getCountryOfService() != null)
            viewModel.getMembershipTypes(viewModel.getDataManager().getCurrentUserModel().getCountryOfService() + "");
    }

    private void loadData() {
        if (viewModel.getDataManager().getCurrentUserModel() != null)
            binding.firstNameEdt.setText(viewModel.getDataManager().getCurrentUserModel().getFirstName());
        binding.middleNameEdt.setText(viewModel.getDataManager().getCurrentUserModel().getMiddleName());
        binding.lastNameEdt.setText(viewModel.getDataManager().getCurrentUserModel().getLastName());
        binding.mobileEdt.setText(String.format("%s%s", viewModel.getDataManager().getCurrentUserModel().getCountryCode(), viewModel.getDataManager().getCurrentUserModel().getPhoneNumber()));
        binding.emailEdt.setText(viewModel.getDataManager().getCurrentUserModel().getEmail());
        binding.AddressEdt.setText(viewModel.getDataManager().getCurrentUserModel().getStreetAddress());
       binding.provinceEdt.setText(viewModel.getDataManager().getCurrentUserModel().getProvinceState());
        binding.postalcodeEdt.setText(viewModel.getDataManager().getCurrentUserModel().getPostalCode());
        binding.registrationBirthdateTv.setText(viewModel.getDataManager().getCurrentUserModel().getBirthDate());
        viewModel.getPersonalInfoRequestModel().setBirthDate(viewModel.getDataManager().getCurrentUserModel().getBirthDate());

        if (viewModel.getDataManager().getCurrentUserModel().getOrganizationCode() != null) {
            binding.membershipCodeEdt.setText(viewModel.getDataManager().getCurrentUserModel().getOrganizationCode());
            organizationSelected();
        } else {
            individualSelected();
        }

        binding.genderMembershipTv.setText(viewModel.getDataManager().getCurrentUserModel().getGender().getTitle());
        viewModel.getPersonalInfoRequestModel().setGender(Integer.parseInt(viewModel.getDataManager().getCurrentUserModel().getGender().getId()));

        if (viewModel.getDataManager().getCurrentUserModel().getNationality() != null) {
            binding.nationalityTv.setText(viewModel.getDataManager().getCurrentUserModel().getNationality().getTitle());
            viewModel.getPersonalInfoRequestModel().setNationality(Integer.valueOf(viewModel.getDataManager().getCurrentUserModel().getNationality().getId()));
        }
        if (viewModel.getDataManager().getCurrentUserModel().getCity() != null) {
            binding.personalprofilecityTv.setText(viewModel.getDataManager().getCurrentUserModel().getCity().getTitle());
            viewModel.getPersonalInfoRequestModel().setCity(Integer.valueOf(viewModel.getDataManager().getCurrentUserModel().getCity().getId()));
        }

        if (viewModel.getDataManager().getCurrentUserModel().getNationalId() != null) {
            binding.nationalityNumberEdt.setText(viewModel.getDataManager().getCurrentUserModel().getNationalId());
            binding.nationalityNumberEdt.setEnabled(false);
            viewModel.getPersonalInfoRequestModel().setNationalId(viewModel.getDataManager().getCurrentUserModel().getNationalId());
        }



        binding.membershipTypeTv.setText(viewModel.getDataManager().getCurrentUserModel().getMembershipType().getAmount());
        viewModel.getPersonalInfoRequestModel().setMembershipType(viewModel.getDataManager().getCurrentUserModel().getMembershipType().getId() + "");


        viewModel.getPersonalInfoRequestModel().setCountryOfService(viewModel.getDataManager().getCurrentUserModel().getCountryOfService());

    }

    private void individualSelected() {
        binding.accountTypeTv.setText(getString(R.string.individual));
        binding.membershipCodeLayout.setVisibility(View.GONE);
        accountType = AppConstants.ACCOUNT_TYPE_INDIVIDUAL;
        validator.disableValidation(binding.membershipCodeEdt);

    }

    private void organizationSelected() {
        binding.accountTypeTv.setText(getString(R.string.organization));
        binding.membershipCodeLayout.setVisibility(View.VISIBLE);
        accountType = AppConstants.ACCOUNT_TYPE_ORGANIZATION;
        validator.enableValidation(binding.membershipCodeEdt);
    }

    private void handleAccountType(int accountType) {
        switch (accountType) {
            case AppConstants.ACCOUNT_TYPE_INDIVIDUAL:
                individualSelected();
                break;
            case AppConstants.ACCOUNT_TYPE_ORGANIZATION:
                organizationSelected();
                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CaregiverPersonalInformationActivity.class);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editItem:
                if (isEditMode) {
                    validator.toValidate();
                    break;
                }

                isEditMode = !isEditMode;
                //prepare for edit mode
                setUpEditMode();
                updateMenuTitles();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private void setUpEditMode() {
        binding.firstNameEdt.setEnabled(isEditMode);
        binding.middleNameEdt.setEnabled(isEditMode);
        binding.lastNameEdt.setEnabled(isEditMode);
        binding.AddressEdt.setEnabled(isEditMode);
        binding.postalcodeEdt.setEnabled(isEditMode);
        binding.provinceEdt.setEnabled(isEditMode);
        //email address should'nt be modified
        binding.emailEdt.setEnabled(false);
        binding.mobileEdt.setEnabled(false);
        binding.genderMembershipTv.setEnabled(isEditMode);
        binding.registrationBirthdateTv.setEnabled(isEditMode);
        binding.countryOfServiceTv.setEnabled(isEditMode);
        if ((viewModel.getDataManager().getCurrentUserModel().getNationalId() != null && !viewModel.getDataManager().getCurrentUserModel().getNationalId().isEmpty())){
            binding.nationalityNumberEdt.setEnabled(false);
        }
        else {
            binding.nationalityNumberEdt.setEnabled(isEditMode);
        }
        binding.nationalityTv.setEnabled(isEditMode);
        binding.personalprofilecityTv.setEnabled(isEditMode);
        binding.membershipCodeEdt.setEnabled(isEditMode);
        binding.membershipTypeTv.setEnabled(isEditMode);
        binding.accountTypeTv.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);
        binding.genderMembershipTv.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);
        binding.registrationBirthdateTv.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);
       // country of service disabled
        binding.countryOfServiceTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        binding.countryOfServiceTv.setEnabled(false);

        binding.nationalityTv.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);
        binding.personalprofilecityTv.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);

        // membership type disabled
        binding.membershipTypeTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        binding.membershipTypeTv.setEnabled(false);


    }

    private void updateMenuTitles() {
        MenuItem item = menu.findItem(R.id.editItem);
        item.setTitle(isEditMode ? getString(R.string.general_save) : getString(R.string.edit));
    }

    @Override
    public void showAccountTypes() {
        DialogFactory.showMenuOrganizationTypeSheet(this, new AccountTypeCallBack() {
            @Override
            public void accountTypeSelected(int accountType) {
                CaregiverPersonalInformationActivity.this.accountType = accountType;
                handleAccountType(accountType);
            }
        }, accountType);
    }

    @Override
    public void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels) {
        countries = countryModels;
        if (viewModel.getDataManager().getCurrentUserModel().getCountryOfService() != null)
            binding.countryOfServiceTv.setText(getCountryNameByID());
    }


    @Override
    public void nationalitiesFinishedSuccessfully(ArrayList<CountryModel> lookUpModels) {
        this.nationalities =lookUpModels;
    }

    @Override
    public void memberShipTypesFetchedSuccessfully(ArrayList<MembershipType> membershipTypes) {
        this.membershipTypes = membershipTypes;
    }

    @Override
    public void countriesClicked() {
        showCountryOfService();
    }



    @Override
    public void citiesFinishedSuccessfully(ArrayList<CityModel> cities) {
        this.cities = cities;
    }


    @Override
    public void genderClicked() {
        showGender();
    }

    @Override
    public void personalcitiesClicked() {
        showCities();
    }
    @Override
    public void nationalityClicked() {
        showNationality();
    }

    @Override
    public void membershipTypeClicked() {
        if (membershipTypes == null)
            return;
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.membership_type),
                getString(R.string.general_search),
                null, membershipTypes, membershipResultListener);

        simpleSearchDialogCompat.show();
    }

    @Override
    public void birthDateClicked() {
        showBirthDatePicker();
    }

    @Override
    public void informationUpdatedSuccessfully() {
        DialogFactory.createFeedBackDialog(this, "", getString(R.string.saved_successfully), getString(R.string.ok), null, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
            }
        });
        isEditMode = false;

        setUpEditMode();
        updateMenuTitles();
    }

    @Override
    public void UserProfileClicked() {
        EasyImage.openChooserWithGallery(this, getString(R.string.replace_photo), AppConstants.PROFILE_IMAGE);
    }

    @Override
    public void identityDocumentClicked() {
        EasyImage.openChooserWithGallery(this, getString(R.string.upload_identity_document), AppConstants.IDENTITY_IMAGE);
    }

    @Override
    public void identityDocumentUploaded() {
        DialogFactory.createSuccessDialog(this, "", getString(R.string.identity_doc_uploaded_succussfully), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
            }
        });
    }

    private void showCities() {
        if (cities == null)
            return;
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.city),
                getString(R.string.general_search),
                null, cities, citiesResultListener);
        simpleSearchDialogCompat.show();
    }
    private void showCountryOfService() {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.country_of_service),
                getString(R.string.general_search),
                null, countries, countriesResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showNationality() {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.nationality),
                getString(R.string.general_search),
                null, nationalities, nationalityResultListener);

        simpleSearchDialogCompat.show();
    }


    private void showGender() {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.gender),
                getString(R.string.general_search),
                null, viewModel.getDataManager().getLookupsModel().getGenderArrayList(), genderResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showBirthDatePicker() {
        DialogFactory.createDatePickerDialog(this, binding.registrationBirthdateTv, datePickerDialogListener, null, null, false);
    }

    DateTimePickerSelectedValueListener datePickerDialogListener = new DateTimePickerSelectedValueListener() {
        @Override
        public void onValueSelected(Calendar date) {
            viewModel.getPersonalInfoRequestModel().setBirthDate(DateUtils.formatDateServer(date));

        }
    };


    SearchResultListener countriesResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
            binding.countryOfServiceTv.setText(item.getTitle());
            viewModel.getPersonalInfoRequestModel().setCountryOfService(item.getId());
            viewModel.getMembershipTypes(item.getId() + "");
            dialog.dismiss();
        }
    };
    SearchResultListener citiesResultListener = new SearchResultListener<CityModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CityModel item, int position) {
            binding.personalprofilecityTv.setText(item.getTitle());
            viewModel.getPersonalInfoRequestModel().setCity( item.getId());
            dialog.dismiss();
        }
    };
    SearchResultListener nationalityResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
            binding.nationalityTv.setText(item.getTitle());
            viewModel.getPersonalInfoRequestModel().setNationality(Integer.valueOf(item.getId()));
            dialog.dismiss();
        }
    };

    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.genderMembershipTv.setText(item.getTitle());
            viewModel.getPersonalInfoRequestModel().setGender(Integer.parseInt(item.getId()));
            dialog.dismiss();
        }
    };

    SearchResultListener membershipResultListener = new SearchResultListener<MembershipType>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, MembershipType item, int position) {
            binding.membershipTypeTv.setText(item.getTitle());
            viewModel.getPersonalInfoRequestModel().setMembershipType(item.getId() + "");
            dialog.dismiss();
        }
    };

    private String getCountryNameByID() {
        for (CountryModel model : countries) {
            if (model.getId().equals(viewModel.getDataManager().getCurrentUserModel().getCountryOfService())) {
                return model.getTitle();
            }
        }
        return "";
    }
    private String getCountryByID(){
        for (CountryModel model : countries) {
            if (model.getId().equals(viewModel.getDataManager().getCurrentUserModel().getCountryOfService())) {
                return model.getId();
            }
        }
        return "";
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
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (type == AppConstants.PROFILE_IMAGE) {
                    viewModel.uploadProfilePicture(file);
                }
                else if (type == AppConstants.IDENTITY_IMAGE){
                    viewModel.uploadAttachment(file.getName(),file);
                }
            }
        });
    }
//Dropdown for downoptions Viewmodel
    private boolean isDropDownsValid() {

        if (viewModel.getPersonalInfoRequestModel().getGender() == null) {
            binding.genderMembershipLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.genderMembershipLayout.setError(null);

        if (viewModel.getPersonalInfoRequestModel().getBirthDate() == null) {
            binding.registrationBirthdateLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.registrationBirthdateLayout.setError(null);

        if (viewModel.getPersonalInfoRequestModel().getCountryOfService() == null) {
            binding.countryOfServiceLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.countryOfServiceLayout.setError(null);

        if (viewModel.getPersonalInfoRequestModel().getNationality() == null) {
            binding.nationalityLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.nationalityLayout.setError(null);

        if (viewModel.getPersonalInfoRequestModel().getMembershipType() == null) {
            binding.membershipTypeLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.membershipTypeLayout.setError(null);


        return true;
    }

    @Override
    public void onValidationSuccess() {
        super.onValidationSuccess();
        if (isDropDownsValid()) {
            viewModel.getPersonalInfoRequestModel().setFirstName(WeCareUtils.getEditTextString(binding.firstNameEdt));
            viewModel.getPersonalInfoRequestModel().setMiddleName(WeCareUtils.getEditTextString(binding.middleNameEdt));
            viewModel.getPersonalInfoRequestModel().setLastName(WeCareUtils.getEditTextString(binding.lastNameEdt));
            viewModel.getPersonalInfoRequestModel().setStreetAddress(WeCareUtils.getEditTextString(binding.AddressEdt));
            viewModel.getPersonalInfoRequestModel().setProvinceState(WeCareUtils.getEditTextString(binding.provinceEdt));
            viewModel.getPersonalInfoRequestModel().setPostalCode(WeCareUtils.getEditTextString(binding.postalcodeEdt));
            viewModel.getPersonalInfoRequestModel().setEmail(WeCareUtils.getEditTextString(binding.emailEdt));
            viewModel.getPersonalInfoRequestModel().setNationalId(WeCareUtils.getEditTextString(binding.nationalityNumberEdt));
            viewModel.updateUser();
        }
    }
}
