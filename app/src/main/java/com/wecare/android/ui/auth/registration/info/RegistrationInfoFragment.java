package com.wecare.android.ui.auth.registration.info;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.annotation.Subscribe;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.CityModel;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.MembershipType;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.databinding.FragmentCreatNewAccountBinding;
import com.wecare.android.interfaces.AccountTypeCallBack;
import com.wecare.android.interfaces.DateTimePickerSelectedValueListener;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DateUtils;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.WeCareUtils;
import com.wecare.android.utils.events.RegistrationStepperEvent;
import com.wecare.android.utils.events.TermsAcceptClickedEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


public class RegistrationInfoFragment extends BaseFragment<FragmentCreatNewAccountBinding, RegistrationInfoViewModel> implements RegistrationInfoFragmentNavigator {

    FragmentCreatNewAccountBinding binding;
//    boolean isTermsViewed = false;
    boolean isTermsAccepted = false;

    ArrayList<MembershipType> membershipTypes;

    //events logging
    private FirebaseAnalytics mFirebaseAnalytics;
    private AppEventsLogger logger;

    int regType;

    private int accountType = AppConstants.ACCOUNT_TYPE_INDIVIDUAL;

    ArrayList<CountryModel> activeCountries;
    ArrayList<CityModel> cities;

    @Inject
    ViewModelProviderFactory factory;
    private RegistrationInfoViewModel viewModel;

    @Override
    public RegistrationInfoViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RegistrationInfoViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_creat_new_account;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Objects.requireNonNull(getActivity()));
        logger = AppEventsLogger.newLogger(Objects.requireNonNull(getActivity()));
        viewModel.getCountries("");
        viewModel.initRegModel();

        if (getArguments() != null) {
            //type seeker or giver.
            regType = getArguments().getInt(AppConstants.KEY_REGISTRATION_TYPE);

            if (getArguments().containsKey(AppConstants.ARGS_KEY_IS_REGISTRATION_USING_SOCIAL)) {
                //get social value.
                if (getArguments().containsKey(AppConstants.ARGS_KEY_SOCIAL_FACEBOOK_HASH)) {
                    getViewModel().setSelectedSocialType(AppConstants.FACEBOOK);
                    getViewModel().getDataManager().getRegistrationModel().setFacebookId(getArguments().getString(AppConstants.ARGS_KEY_SOCIAL_FACEBOOK_HASH));
                } else if (getArguments().containsKey(AppConstants.ARGS_KEY_SOCIAL_TWITTER_HASH)) {
                    getViewModel().setSelectedSocialType(AppConstants.TWITTER);
                    getViewModel().getDataManager().getRegistrationModel().setTwitterId(getArguments().getString(AppConstants.ARGS_KEY_SOCIAL_TWITTER_HASH));
                } else if (getArguments().containsKey(AppConstants.ARGS_KEY_SOCIAL_GOOGLE_HASH)) {
                    getViewModel().setSelectedSocialType(AppConstants.GOOGLE);
                    getViewModel().getDataManager().getRegistrationModel().setGoogleId(getArguments().getString(AppConstants.ARGS_KEY_SOCIAL_GOOGLE_HASH));
                }
            }
        }
        NYBus.get().register(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();
        if (regType == AppConstants.REGISTRATION_TYPE_SEEKER)
            setUpSeekerRegistrationForm();

        //by default
        individualSelected();
        binding.countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                getViewModel().getDataManager().getRegistrationModel().setCountryCode((selectedCountry.getPhoneCode()));
            }
        });

        if (binding.countryCodePicker.getSelectedCountryCode() != null) {
            getViewModel().getDataManager().getRegistrationModel().setCountryCode(binding.countryCodePicker.getSelectedCountryCode());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NYBus.get().unregister(this);
    }

    @Subscribe
    public void onEvent(TermsAcceptClickedEvent event) {
    isTermsAccepted =event.isTermsAccepted();
    binding.termsCheckedImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_terms_checked));
    }

    private void setUpSeekerRegistrationForm() {
        binding.registrationNationalityLayout.setVisibility(View.GONE);
        binding.registrationAccountTypeTv.setVisibility(View.GONE);
       // binding.registrationMembershipTypeTv.setVisibility(View.GONE);
      //  binding.registrationMembershipTypeLayout.setVisibility(View.GONE);
        binding.membershipCodeEdt.setVisibility(View.GONE);
        validator.disableValidation(binding.membershipCodeEdt);
        validator.disableValidation(binding.registrationNationalityTv);
    }

    public static RegistrationInfoFragment newInstance(Bundle args) {
        RegistrationInfoFragment fragment = new RegistrationInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void showCountryOfService() {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
                regType == AppConstants.REGISTRATION_TYPE_GIVER ? getString(R.string.country_of_service) : getString(R.string.country),
                getString(R.string.general_search),
                null, activeCountries, countriesResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showNationality() {

        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
                getString(R.string.nationality),
                getString(R.string.general_search),
                null, activeCountries, nationalityResultListener);

        simpleSearchDialogCompat.show();
    }


    private void showCities() {
        if (cities == null)
            return;
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
                getString(R.string.city),
                getString(R.string.general_search),
                null, cities, citiesResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showGender() {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
                getString(R.string.gender),
                getString(R.string.general_search),
                null, viewModel.getDataManager().getLookupsModel().getGenderArrayList(), genderResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showBirthDatePicker() {
        DialogFactory.createDatePickerDialog(getActivity(), binding.registrationBirthdateTv, datePickerDialogListener, null, null, false);

    }

    @Override
    public void onValidationSuccess() {
        if (isDropDownsValid()) {
            viewModel.getDataManager().getRegistrationModel().setFirstName(WeCareUtils.getEditTextString(binding.firstNameEdt));
            viewModel.getDataManager().getRegistrationModel().setMiddleName(WeCareUtils.getEditTextString(binding.middleNameEdt));
            viewModel.getDataManager().getRegistrationModel().setLastName(WeCareUtils.getEditTextString(binding.lastNameEdt));
            viewModel.getDataManager().getRegistrationModel().setEmail(WeCareUtils.getEditTextString(binding.registrationEmailEdt));
            viewModel.getDataManager().getRegistrationModel().setPassword(WeCareUtils.getEditTextString(binding.registrationPasswordEdt));
//            viewModel.getDataManager().getRegistrationModel().setCountryCode(WeCareUtils.getEditTextString(binding.registrationCountryCodeEdt));
            viewModel.getDataManager().getRegistrationModel().setPhoneNumber(WeCareUtils.getEditTextString(binding.registrationMobileNumberEdt));
            if (accountType == AppConstants.ACCOUNT_TYPE_ORGANIZATION)
                viewModel.getDataManager().getRegistrationModel().setOrganizationCode(WeCareUtils.getEditTextString(binding.membershipCodeEdt));
            showConfirmPhoneNumberDialog();
        }
    }

    private void showConfirmPhoneNumberDialog() {
        DialogFactory.createReactDialog(getActivity(), getString(R.string.conform_phone_number), getString(R.string.confirm_phone_number_disclaimer) + " " + viewModel.getDataManager().getRegistrationModel().getCountryCode() + viewModel.getDataManager().getRegistrationModel().getPhoneNumber(),
                getString(R.string.yes), getString(R.string.no), null, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.registerCaregiver();
                    }
                }, null);
    }

    DateTimePickerSelectedValueListener datePickerDialogListener = new DateTimePickerSelectedValueListener() {
        @Override
        public void onValueSelected(Calendar date) {
            viewModel.getDataManager().getRegistrationModel().setBirthDate(DateUtils.formatDateServer(date));

        }
    };

    SearchResultListener countriesResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat searchDialog, CountryModel item, int position) {
            binding.registrationCountryOfServiceTv.setText(item.getTitle());
            binding.countryCodePicker.setCountryForPhoneCode(Integer.parseInt(item.getPhoneCode()));
            //Toast.makeText(getActivity(),item.getTitle(),Toast.LENGTH_SHORT).show();
            viewModel.getMembershipTypes(item.getId());
            viewModel.getCities(item.getId() + "");

            if (item.getActive() == AppConstants.PHP_FALSE_RAW) {
                DialogFactory.createReactDialog(getActivity(), "", getString(R.string.service_not_available_disclaimer), getString(R.string.cont), getString(R.string.choose_deferent_country), null,
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (regType == AppConstants.REGISTRATION_TYPE_GIVER) {
                                    viewModel.getDataManager().getRegistrationModel().setCountryOfService(item.getId());
                                } else
                                    viewModel.getDataManager().getRegistrationModel().setCountryID(item.getId() + "");
                                searchDialog.dismiss();
                            }
                        }, null);
                return;
            }

            if (regType == AppConstants.REGISTRATION_TYPE_GIVER)
                viewModel.getDataManager().getRegistrationModel().setCountryOfService(item.getId() + "");
            else
                viewModel.getDataManager().getRegistrationModel().setCountryID(item.getId() + "");

            getViewDataBinding().countryCodePicker.setDefaultCountryUsingNameCode(item.getIso2());

            searchDialog.dismiss();
        }
    };
    SearchResultListener nationalityResultListener = new SearchResultListener<CountryModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CountryModel item, int position) {
            binding.registrationNationalityTv.setText(item.getTitle());
            viewModel.getDataManager().getRegistrationModel().setNationality(item.getId() + "");
            dialog.dismiss();
        }
    };
    SearchResultListener citiesResultListener = new SearchResultListener<CityModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, CityModel item, int position) {
            binding.registrationCityTv.setText(item.getTitle());
            viewModel.getDataManager().getRegistrationModel().setCity(item.getId() + "");
            dialog.dismiss();
        }
    };

    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.genderMembershipTv.setText(item.getTitle());
            viewModel.getDataManager().getRegistrationModel().setGender(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener membershipResultListener = new SearchResultListener<MembershipType>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, MembershipType item, int position) {
          //  binding.registrationMembershipTypeTv.setText(item.getTitle());
            viewModel.getDataManager().getRegistrationModel().setMembershipType(item.getId());
            dialog.dismiss();
        }
    };

    @Override
    public void nextClicked() {
        validator.toValidate();
    }

    @Override
    public void showAccountTypes() {
        DialogFactory.showMenuOrganizationTypeSheet(getActivity(), new AccountTypeCallBack() {
            @Override
            public void accountTypeSelected(int accountType) {
                RegistrationInfoFragment.this.accountType = accountType;
                handleAccountType(accountType);
            }
        }, accountType);
    }


    @Override
    public void countriesFinishedSuccessfully(String status, ArrayList<CountryModel> countryModels) {

//       Collections.sort(countryModels, Comparator.comparing(CountryModel::getTitle));
//        countryModels.stream().sorted(Comparator.comparing(CountryModel::getTitle)).forEach(p -> System.out.printf("%s%n", p));
//
//        countryModels.stream().sorted((p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));
        if(countryModels!=null&&countryModels.size()>0) {
            Collections.sort(countryModels, (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
        }

        activeCountries = countryModels;
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
    public void citiesClicked() {
        showCities();
    }

    @Override
    public void genderClicked() {
        showGender();
    }

    @Override
    public void nationalityClicked() {
        showNationality();
    }

    @Override
    public void membershipTypeClicked() {

        if (membershipTypes == null)
            return;
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
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
    public void termsConditionsClicked() {
        //remove later bazeg lazeg as per abu zaid requested to consider terms read once their opened
        isTermsAccepted = true;
        binding.termsCheckedImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_terms_checked));

        NYBus.get().post(new RegistrationStepperEvent(AppConstants.REGISTRATION_TERMS_CONDITIONS));

    }

//    @Override
//    public void termsConditionsChecked(boolean isChecked) {
//        if (!isTermsViewed) {
//            getViewModel().setIsTermsAccebted(false);
////            DialogFactory.createErrorDialog(getActivity(), "", getString(R.string.error_read_terms_and_conditions));
//            // if the user didn't review terms and conditions navigate the user to terms conditions screen
//            isTermsViewed = true;
//            NYBus.get().post(new RegistrationStepperEvent(AppConstants.REGISTRATION_TERMS_CONDITIONS));
//        }
//    }
//
    @Override
    public void userRegisteredSuccessfully(UserModel userModel) {
        DialogFactory.createFeedBackDialog(getActivity(), getString(R.string.new_account_created_successfully), getString(R.string.dialog_please_activate_your_account_by_entering_the_code_sent_to_you_via_message), getString(R.string.activate), null, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                //log registration event on facebook dashboard
                logFacebookRegistrationEvent(AppConstants.FACEBOOK_EVENT_REGISTRATION);
                //log registration event on firebase dashboard
                logRegistrationEvent(userModel);
                NYBus.get().post(new RegistrationStepperEvent(AppConstants.REGISTRATION_SECOND_STEP));
            }
        },false);
    }

    @Override
    public void memberShipTypesFetchedSuccessfully(ArrayList<MembershipType> membershipTypes) {
        this.membershipTypes = membershipTypes;

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

    private void individualSelected() {
        binding.registrationAccountTypeTv.setText(getString(R.string.individual));
//        binding.registrationMembershipTypeTv.setVisibility(View.VISIBLE);
        binding.membershipCodeEdt.setVisibility(View.GONE);
        accountType = AppConstants.ACCOUNT_TYPE_INDIVIDUAL;
        validator.disableValidation(binding.membershipCodeEdt);

    }

    private void organizationSelected() {
        binding.registrationAccountTypeTv.setText(getString(R.string.organization));
//        binding.registrationMembershipTypeTv.setVisibility(View.GONE);
        binding.membershipCodeEdt.setVisibility(View.VISIBLE);
        accountType = AppConstants.ACCOUNT_TYPE_ORGANIZATION;
        validator.enableValidation(binding.membershipCodeEdt);
    }

    private boolean isDropDownsValid() {

        if (!isTermsAccepted) {
            DialogFactory.createErrorDialog(getActivity(), "", getString(R.string.error_accept_terms));
            return false;
        }
        if ((regType == AppConstants.REGISTRATION_TYPE_GIVER ? viewModel.getDataManager().getRegistrationModel().getCountryOfService() : viewModel.getDataManager().getRegistrationModel().getCountryID()) == null) {
            binding.registrationCountryOfServiceLayout.setError(getString(R.string.error_field_required));
            return false;
        }

        if (viewModel.getDataManager().getRegistrationModel().getCountryCode() == null) {
            binding.countryCodeLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.countryCodeLayout.setError(null);

        binding.registrationCountryOfServiceLayout.setError(null);

        // as city is optional now on
//        if (viewModel.getDataManager().getRegistrationModel().getCity() == null) {
//            binding.registrationCityLayout.setError(getString(R.string.error_field_required));
//            return false;
//        }
//        binding.registrationCityLayout.setError(null);

        if (viewModel.getDataManager().getRegistrationModel().getGender() == null) {
            binding.genderMembershipLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.genderMembershipLayout.setError(null);

        if (viewModel.getDataManager().getRegistrationModel().getBirthDate() == null) {
            binding.registrationBirthdateLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        binding.registrationBirthdateLayout.setError(null);

//        if (binding.registrationNationalityLayout.getVisibility() == View.VISIBLE && viewModel.getDataManager().getRegistrationModel().getNationality() == null) {
//            binding.registrationNationalityLayout.setError(getString(R.string.error_field_required));
//            return false;
//        }
        binding.registrationNationalityLayout.setError(null);

        if (regType == AppConstants.REGISTRATION_TYPE_GIVER && viewModel.getDataManager().getRegistrationModel().getMembershipType() == null) {
          //  binding.registrationMembershipTypeLayout.setError(getString(R.string.error_field_required));
            return false;
        }
       // binding.registrationMembershipTypeLayout.setError(null);


        return true;
    }

    //events logging
    private void logRegistrationEvent(UserModel userModel){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, userModel.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, userModel.getFirstName() +" "+userModel.getLastName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

    public void logFacebookRegistrationEvent (String registrationMethod) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD, registrationMethod);
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, params);
    }

}

