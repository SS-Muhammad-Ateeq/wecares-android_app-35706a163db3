package com.wecare.android.ui.main.profile.userProfile.educationcertificates;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.AttachmentModel;
import com.wecare.android.data.model.api.models.EducationModel;
import com.wecare.android.data.model.api.models.ExperienceModel;
import com.wecare.android.data.model.api.models.InsuranceCompanyLookup;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.MalInsuranceModel;
import com.wecare.android.data.model.api.requests.LanguageRequest;
import com.wecare.android.databinding.ActivityEducationCertificatesBinding;
import com.wecare.android.interfaces.DateTimePickerSelectedValueListener;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.CommonUtils;
import com.wecare.android.utils.DateUtils;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.LocalizedContextWrapper;
import com.wecare.android.utils.WeCareUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class EducationCertificatesActivity extends BaseActivity<ActivityEducationCertificatesBinding, EducationCertificatesViewModel> implements EducationCertificatesActivityNavigator, View.OnClickListener {

    @Inject
    ViewModelProviderFactory factory;
    private EducationCertificatesViewModel viewModel;

    ActivityEducationCertificatesBinding binding;
    Context localizedContext;
    int current_job;
    ArrayList<EducationModel> educationModels = new ArrayList<>();
    ArrayList<ExperienceModel> experienceModels = new ArrayList<>();
    ArrayList<MalInsuranceModel> malInsuranceModels = new ArrayList<>();
    ArrayList<InsuranceCompanyLookup> insuranceCompanies = new ArrayList<>();
    ArrayList<LanguageRequest> languageModels = new ArrayList<>();
    ArrayList<AttachmentModel> attachmentModels = new ArrayList<>();
    ArrayList<AttachmentModel> certificatesModels = new ArrayList<>();
    boolean isEditMode = false;
    private Menu menu;

    private String categoryID;


    @Override
    public EducationCertificatesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(EducationCertificatesViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_education_certificates;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EducationCertificatesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.education_ceritification), true);
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();
        loadData();
        localizedContext = LocalizedContextWrapper.wrap(this, getResources().getConfiguration().locale);
    }

    private void loadData() {
        if (viewModel.getDataManager().getCurrentUserModel().getBio() != null) {
            binding.bioEdt.setText(viewModel.getDataManager().getCurrentUserModel().getBio());
        }
        if (viewModel.getDataManager().getCurrentUserModel().getProfessionalLicenseNumber() != null) {
            binding.licenseNumberEdt.setText(viewModel.getDataManager().getCurrentUserModel().getProfessionalLicenseNumber());
        }
        if (viewModel.getDataManager().getCurrentUserModel().getIssuername() != null) {
            binding.issuerNameedt.setText(viewModel.getDataManager().getCurrentUserModel().getIssuername());
        }

        if (viewModel.getDataManager().getCurrentUserModel().getYearsOfExperience() != null) {
            binding.yearsExperienceEdt.setText(viewModel.getDataManager().getCurrentUserModel().getYearsOfExperience());
        }
        if (viewModel.getDataManager().getCurrentUserModel().getIssuerOfCertificate() != null) {
            binding.placeOfIssueTv.setText(viewModel.getDataManager().getCurrentUserModel().getIssuerOfCertificate().getLabel());
            viewModel.getUserModel().setIssuerOfCertificate(Integer.parseInt(viewModel.getDataManager().getCurrentUserModel().getIssuerOfCertificate().getId()));
        }
        viewModel.fetchUserEducation();
        viewModel.fetchUserExperience();
        viewModel.fetchUserLanguages();
        viewModel.fetchUserAttachments();
        viewModel.fetchUserCertificates();
        viewModel.fetchّInsuranceCompanies();

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
        binding.addEducationImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        binding.addExperienceImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        binding.addLanguageImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        binding.addCertificateImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        binding.addAttachmentImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        binding.bioEdt.setEnabled(isEditMode);
        binding.licenseNumberEdt.setEnabled(isEditMode);
        binding.issuerNameedt.setEnabled(isEditMode);
        binding.placeOfIssueTv.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);
        binding.placeOfIssueTv.setEnabled(isEditMode);
        binding.yearsExperienceEdt.setEnabled(isEditMode);
        binding.addMalpracticeInsuranceImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        enableDisableEducation();
        enableDisableExperience();
        enableDisableMalInsurance();
    }

    // disable enable education
    private void enableDisableEducation() {
        for (int i = 0; i < educationModels.size(); i++) {

            AppCompatImageView deleteImageView = (AppCompatImageView) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(0);
            deleteImageView.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

            TextInputLayout universityInputLayout = (TextInputLayout) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(1);
            TextInputEditText universityET = (TextInputEditText) universityInputLayout.findViewById(R.id.university_edt);
            universityET.setEnabled(isEditMode);

            TextInputLayout specializationInputLayout = (TextInputLayout) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(3);
            TextInputEditText specializationET = (TextInputEditText) specializationInputLayout.findViewById(R.id.specialization_edt);
            specializationET.setEnabled(isEditMode);

            TextInputLayout textInputLayout = (TextInputLayout) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(2);
            AppCompatTextView compatTextView = (AppCompatTextView) textInputLayout.findViewById(R.id.education_degree_TV);
            compatTextView.setEnabled(isEditMode);
            compatTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);

            View view = (View) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(4);
            view.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        }
    }

    //disable enable experience
    private void enableDisableExperience() {

        for (int i = 0; i < experienceModels.size(); i++) {

            AppCompatImageView deleteImageView = (AppCompatImageView) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(0);
            deleteImageView.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

            TextInputLayout hospitalInputLayout = (TextInputLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(1);
            TextInputEditText hospitalET = (TextInputEditText) hospitalInputLayout.findViewById(R.id.hospital_edt);
            hospitalET.setEnabled(isEditMode);

            TextInputLayout jobInputLayout = (TextInputLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(3);
            TextInputEditText jobET = (TextInputEditText) jobInputLayout.findViewById(R.id.job_title_edt);
            jobET.setEnabled(isEditMode);

            TextInputLayout job_descriptionInputLayout = (TextInputLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(4);
            TextInputEditText job_descriptionET = (TextInputEditText) job_descriptionInputLayout.findViewById(R.id.job_description_edt);
            job_descriptionET.setEnabled(isEditMode);

            RelativeLayout relativeLayout = (RelativeLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(2);
            TextInputLayout fromDateLayout = (TextInputLayout) relativeLayout.findViewById(R.id.from_date_layout);
            AppCompatTextView from = (AppCompatTextView) fromDateLayout.findViewById(R.id.fromTV);

            from.setEnabled(isEditMode);
            from.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_right) : null, null);



            RelativeLayout toRelativeLayout = (RelativeLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(2);
            TextInputLayout toDateLayout = (TextInputLayout) toRelativeLayout.findViewById(R.id.to_date_layout);
            AppCompatTextView toTV = (AppCompatTextView) toDateLayout.findViewById(R.id.toTV);
            AppCompatCheckBox checkBox= (AppCompatCheckBox) findViewById(R.id.currently_working);
            checkBox.setEnabled(isEditMode);
            toTV.setEnabled(isEditMode);
            toTV.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_right) : null, null);

            checkBox.setOnCheckedChangeListener(new AppCompatCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Toast.makeText(getApplicationContext(),"checked",Toast.LENGTH_SHORT).show();
                        current_job=1;
                        toTV.setEnabled(false);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"unchecked",Toast.LENGTH_SHORT).show();
                         current_job=0;
                    toTV.setEnabled(isEditMode);
                }
                                                }
            );
            View view = (View) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(5);
            view.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        }

    }

    //disable enable malInsurance
    private void enableDisableMalInsurance() {

        for (int i = 0; i < malInsuranceModels.size(); i++) {

            AppCompatImageView deleteImageView = (AppCompatImageView) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(0);
            deleteImageView.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

            TextInputLayout textInputLayout = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(1);
            AppCompatTextView compatTextView = (AppCompatTextView) textInputLayout.findViewById(R.id.insurance_company_TV);
            compatTextView.setEnabled(isEditMode);
            compatTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);

            TextInputLayout textInputLayout_insurance_type = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(2);
            TextInputEditText insurance_type_edt = textInputLayout_insurance_type.findViewById(R.id.insurance_type_edt);
            insurance_type_edt.setEnabled(true);
            TextInputLayout textInputLayout_issuer = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(3);
            TextInputEditText issuer_name_edt = textInputLayout_issuer.findViewById(R.id.inssuar_name_edt);
            issuer_name_edt.setEnabled(true);

            TextInputLayout hospitalInputLayout = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(4);
            TextInputEditText hospitalET = (TextInputEditText) hospitalInputLayout.findViewById(R.id.insurance_number_edt);
            hospitalET.setEnabled(isEditMode);

            RelativeLayout relativeLayout = (RelativeLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(5);
            TextInputLayout fromDateLayout = (TextInputLayout) relativeLayout.findViewById(R.id.expiration_date_layout);
            AppCompatTextView from = (AppCompatTextView) fromDateLayout.findViewById(R.id.selectExpDateTV);
            from.setEnabled(isEditMode);
            from.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_right) : null, null);

            View view = (View) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(6);
            view.setVisibility(isEditMode ? View.GONE : View.VISIBLE);


        }

    }

    private void validateSubmitEducationCertificate() {
        if (educationModels.size() > 0) {
            isEditMode = !validateSubmitEducation();

        }
        if (experienceModels.size() > 0) {
            isEditMode = !validateSubmitExperience();
        }

    }

    public void addMalInsuranceView() {

        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_add_malpractice_insurance, null);
        MalInsuranceModel malInsuranceModel = new MalInsuranceModel();
        malInsuranceModel.setNew(true);
        malInsuranceModels.add(malInsuranceModel);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteInsurance);
        deleteImg.setOnClickListener(this);
        ((AppCompatTextView) view.findViewById(R.id.insurance_company_TV)).setOnClickListener(this);
        ((AppCompatTextView) view.findViewById(R.id.selectExpDateTV)).setOnClickListener(this);
        binding.malpracticeInsuranceLayout.addView(view, (0), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //for existing insurance listing
    public void addMalInsuranceView(int position) {
        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_add_malpractice_insurance, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteInsurance);
        deleteImg.setOnClickListener(this);
        deleteImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

        TextInputEditText insuranceNumber = (TextInputEditText) view.findViewById(R.id.insurance_number_edt);
        insuranceNumber.setEnabled(isEditMode ? true : false);

        TextInputEditText insurance_type_edt =  view.findViewById(R.id.insurance_type_edt);
        insurance_type_edt.setEnabled(false);
        TextInputEditText inssuar_name_edt =  view.findViewById(R.id.inssuar_name_edt);
        inssuar_name_edt.setEnabled(false);

        AppCompatTextView insuranceCompany = (AppCompatTextView) view.findViewById(R.id.insurance_company_TV);
        insuranceCompany.setOnClickListener(this);
        insuranceCompany.setEnabled(isEditMode ? true : false);
        insuranceCompany.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);

        AppCompatTextView selectExpDateTV = (AppCompatTextView) view.findViewById(R.id.selectExpDateTV);
        selectExpDateTV.setOnClickListener(this);
        selectExpDateTV.setEnabled(isEditMode ? true : false);
        selectExpDateTV.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);
        inssuar_name_edt.setText(malInsuranceModels.get(position).getIssuer_name());
        insurance_type_edt.setText(malInsuranceModels.get(position).getInsurance_type());
        insuranceNumber.setText(malInsuranceModels.get(position).getInsuranceNumber());
        insuranceCompany.setText(CommonUtils.getInsuranceLookUpDescById(insuranceCompanies, malInsuranceModels.get(position).getCompanyId()));
        selectExpDateTV.setText(DateUtils.formatDateServer(malInsuranceModels.get(position).getExpirationDate()));


        binding.malpracticeInsuranceLayout.addView(view, position, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void addEducationView() {

        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_add_education, null);
        EducationModel educationModel = new EducationModel();
        educationModel.setNew(true);
        educationModels.add(educationModel);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteEducation);
        deleteImg.setOnClickListener(this);
        ((AppCompatTextView) view.findViewById(R.id.education_degree_TV)).setOnClickListener(this);
        binding.educationLayout.addView(view, (0), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    //for existing education
    public void addEducationView(int position) {
        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_add_education, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteEducation);
        deleteImg.setOnClickListener(this);
        deleteImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

        TextInputEditText universityEdt = (TextInputEditText) view.findViewById(R.id.university_edt);
        universityEdt.setEnabled(isEditMode ? true : false);

        AppCompatTextView educationDegreeTV = (AppCompatTextView) view.findViewById(R.id.education_degree_TV);
        educationDegreeTV.setOnClickListener(this);
        educationDegreeTV.setEnabled(isEditMode ? true : false);
        educationDegreeTV.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_spinner) : null, null);


        TextInputEditText specializationEdt = (TextInputEditText) view.findViewById(R.id.specialization_edt);
        specializationEdt.setEnabled(isEditMode ? true : false);

        universityEdt.setText(educationModels.get(position).getUniversityOrCollegeName());
        educationDegreeTV.setText(educationModels.get(position).getEducationDegreeEnglish());
        specializationEdt.setText(educationModels.get(position).getSpecialization());

        binding.educationLayout.addView(view, position, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void addExperienceView() {
        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_add_experience, null);
        ExperienceModel experienceModel = new ExperienceModel();
        experienceModel.setNew(true);
        experienceModels.add(experienceModel);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteExperience);
        deleteImg.setOnClickListener(this);
        ((AppCompatTextView) view.findViewById(R.id.fromTV)).setOnClickListener(this);
        ((AppCompatTextView) view.findViewById(R.id.toTV)).setOnClickListener(this);
        ((AppCompatCheckBox) view.findViewById(R.id.currently_working)).setOnClickListener(this);
        binding.experienceLayout.addView(view, (0), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        focusOnView(view);
    }

    //for existing experiences
    public void addExperienceView(int position) {
        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_add_experience, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteExperience);
        deleteImg.setOnClickListener(this);
        deleteImg.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

        TextInputEditText hospitalEdt = (TextInputEditText) view.findViewById(R.id.hospital_edt);
        hospitalEdt.setEnabled(isEditMode);

        TextInputEditText jobTitleEdt = (TextInputEditText) view.findViewById(R.id.job_title_edt);
        jobTitleEdt.setEnabled(isEditMode);


        TextInputEditText jobDescriptionEdt = (TextInputEditText) view.findViewById(R.id.job_description_edt);
        jobDescriptionEdt.setEnabled(isEditMode);

        hospitalEdt.setText(experienceModels.get(position).getCompanyOrHospitalName());
        jobTitleEdt.setText(experienceModels.get(position).getJobTitle());
        jobDescriptionEdt.setText(experienceModels.get(position).getJobDescription());


        AppCompatTextView fromTV = ((AppCompatTextView) view.findViewById(R.id.fromTV));
        fromTV.setOnClickListener(this);
        fromTV.setEnabled(isEditMode);
        fromTV.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_right) : null, null);


        AppCompatTextView toTV = ((AppCompatTextView) view.findViewById(R.id.toTV));
        toTV.setOnClickListener(this);
        if((experienceModels.get(position).getIsCurretnt())==1){
         toTV.setEnabled(false);
        }
        else{
        toTV.setEnabled(isEditMode);
        toTV.setText(experienceModels.get(position).getEndDate());
        }
        toTV.setCompoundDrawablesWithIntrinsicBounds(null, null, isEditMode ? getResources().getDrawable(R.drawable.ic_arrow_right) : null, null);

        AppCompatCheckBox CurrentlyWorking = ((AppCompatCheckBox) view.findViewById(R.id.currently_working));
        CurrentlyWorking.setOnClickListener(this);
        CurrentlyWorking.setEnabled(isEditMode);


        fromTV.setText(experienceModels.get(position).getStartDate());


        binding.experienceLayout.addView(view, position, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        focusOnView(view);
    }

    public void addLanguageView(LanguageRequest item, int position) {
        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_add_language, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteLanguage);
        AppCompatTextView textView = (AppCompatTextView) view.findViewById(R.id.languageDesc);
        textView.setText(item.getEnglishName());
        deleteImg.setOnClickListener(this);
        binding.languageLayout.addView(view, (position), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addAttachmentView(int position) {
        LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        View view = inflater.inflate(R.layout.layout_attachment, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteAttachment);
        deleteImg.setOnClickListener(this);

        AppCompatImageView compatImageView = (AppCompatImageView) view.findViewById(R.id.attachment_img);
        Context context = compatImageView.getContext();
        Glide.with(context).load(attachmentModels.get(position).getUrl()).into(compatImageView);

        binding.attachmentLayout.addView(view, position, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addCertificateView(int position) {
        LayoutInflater inflater = LayoutInflater.from(localizedContext);
        View view = inflater.inflate(R.layout.layout_certificate, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteCertificate);
        deleteImg.setOnClickListener(this);

        AppCompatImageView compatImageView = (AppCompatImageView) view.findViewById(R.id.attachment_img);
        Context context = compatImageView.getContext();
        Glide.with(context).load(certificatesModels.get(position).getUrl()).into(compatImageView);

        binding.certificateLayout.addView(view, position, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    @Override
    public void addEducation() {
        addEducationView();
    }

    @Override
    public void addMalInsurance() {
    addMalInsuranceView();
    }

    @Override
    public void addExperience() {
        addExperienceView();
    }

    @Override
    public void addLanguage() {
        showLanguagePicker();
    }

    @Override
    public void addCertificate() {
        showPickingBS(true);
    }

    @Override
    public void addAttachment() {
        showAttachmentTypesPicker();
    }

    @Override
    public void eductionInstructions() {
        showeducationistructionDialog();
    }
    private void showeducationistructionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_education_instruction);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    @Override
    public void languageAdded(LookUpModel model) {
        viewModel.fetchUserLanguages();
//        addLanguageView(model);
    }

    @Override
    public void placeOfIssueClicked() {
        showPlaceOfIssuePicker();
    }

    @Override
    public void userDataUpdatedSuccessfully() {
//        if (educationModels.size() > 0 || experienceModels.size() > 0)
//            validateSubmitEducationCertificate();
//        else
            showSuccessDialog();
    }

    @Override
    public void educationAddedSuccessfully(boolean isLastOne) {
//        if (isLastOne)
//            showSuccessDialog();
    }

    @Override
    public void experienceAddedSuccessfully(boolean isLastOne) {
//        if (isLastOne)
//            showSuccessDialog();
    }

    @Override
    public void malpracticeInsuranceAddedSuccessfully(boolean isLastOne) {

    }

    @Override
    public void userEducationFetched(ArrayList<EducationModel> educationModels) {
        this.educationModels = educationModels;
        for (int i = 0; i < educationModels.size(); i++) {
            addEducationView(i);
        }
    }

    @Override
    public void userExperienceFetched(ArrayList<ExperienceModel> experienceModels) {
        this.experienceModels = experienceModels;
        for (int i = 0; i < experienceModels.size(); i++) {
            addExperienceView(i);
        }
    }

    @Override
    public void malInsurancesFetched(ArrayList<MalInsuranceModel> malInsuranceModels) {
        this.malInsuranceModels = malInsuranceModels;
        for (int i = 0; i < malInsuranceModels.size(); i++) {
            addMalInsuranceView(i);
        }
    }

    @Override
    public void languageDeletedSuccessfully(int position) {
        binding.languageLayout.removeViewAt(position);
        languageModels.remove(position);
    }

    @Override
    public void educationDeletedSuccessfully(int position) {
        binding.educationLayout.removeViewAt(position);
        educationModels.remove(position);
    }

    @Override
    public void experienceDeletedSuccessfully(int position) {
        binding.experienceLayout.removeViewAt(position);
        experienceModels.remove(position);
    }

    @Override
    public void certificateDeletedSuccessfully(int position) {
        binding.certificateLayout.removeViewAt(position);
        certificatesModels.remove(position);
    }

    @Override
    public void malInsuranceDeletedSuccessfully(int position) {
        binding.malpracticeInsuranceLayout.removeViewAt(position);
        malInsuranceModels.remove(position);
    }

    @Override
    public void attachmentDeletedSuccessfully(int position) {
        binding.attachmentLayout.removeViewAt(position);
        attachmentModels.remove(position);
    }

    @Override
    public void languageFetched(ArrayList<LanguageRequest> models) {
        this.languageModels = models;
        for (int i = 0; i < languageModels.size(); i++) {
            addLanguageView(languageModels.get(i), i);
        }
    }

    @Override
    public void attachmentUploadedSuccessfully(AttachmentModel attachmentModel) {
        attachmentModels.add(attachmentModel);
        addAttachmentView(attachmentModels.size() - 1);
    }

    @Override
    public void certificateUploadedSuccessfully(AttachmentModel attachmentModel) {
        certificatesModels.add(attachmentModel);
        addCertificateView(certificatesModels.size() - 1);
    }

    @Override
    public void attachmentsFetched(ArrayList<AttachmentModel> attachmentModels) {
        this.attachmentModels = attachmentModels;
        for (int i = 0; i < attachmentModels.size(); i++) {
            addAttachmentView(i);
        }
    }

    @Override
    public void certificatesFetched(ArrayList<AttachmentModel> attachmentModels) {
        this.certificatesModels = attachmentModels;
        for (int i = 0; i < certificatesModels.size(); i++) {
            addCertificateView(i);
        }
    }

    @Override
    public void insuranceCompaniesFetched(ArrayList<InsuranceCompanyLookup> insuranceCompanies) {
        this.insuranceCompanies = insuranceCompanies;
        viewModel.fetchUserMalInsurance();
    }

    @Override
    public void educationUpdatedSuccessfully(int position, EducationModel educationModel, boolean isLastOne) {
        educationModels.set(position, educationModel);
//        if (isLastOne)
//            showSuccessDialog();

    }

    @Override
    public void malInsuranceUpdatedSuccessfully(int position, MalInsuranceModel malInsuranceModel, boolean isLastOne) {
        malInsuranceModels.set(position,malInsuranceModel);
    }

    @Override
    public void experienceUpdatedSuccessfully(int position, ExperienceModel experienceModel, boolean isLastOne) {
        experienceModels.set(position, experienceModel);
//        if (isLastOne)
//            showSuccessDialog();

    }

    public void removeEducation(View view) {
        int index = binding.educationLayout.indexOfChild((LinearLayout) view.getParent());
        DialogFactory.createReactDialog(this, null, getString(R.string.delete_item_confirmation), getString(R.string.delete), getString(R.string.cancel), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (educationModels.get(index).getId() != null) {
                    viewModel.deleteEducation(educationModels.get(index).getId() + "", index);
                } else {
                    binding.educationLayout.removeViewAt(index);
                    educationModels.remove(index);
                }
            }
        }, null);

    }

    public void removeExperience(View view) {
        int index = binding.experienceLayout.indexOfChild((LinearLayout) view.getParent());

        DialogFactory.createReactDialog(this, null, getString(R.string.delete_item_confirmation), getString(R.string.delete), getString(R.string.cancel), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (experienceModels.get(index).getId() != null) {
                    viewModel.deleteExperience(experienceModels.get(index).getId() + "", index);
                } else {
                    binding.experienceLayout.removeViewAt(index);
                    experienceModels.remove(index);
                }
            }
        }, null);
    }

    public void removeInsurance(View view) {
        int index = binding.malpracticeInsuranceLayout.indexOfChild((LinearLayout) view.getParent());

        DialogFactory.createReactDialog(this, null, getString(R.string.delete_item_confirmation), getString(R.string.delete), getString(R.string.cancel), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (malInsuranceModels.get(index).getId() != null) {
                    viewModel.deleteMalInsurance(malInsuranceModels.get(index).getId() + "", index);
                } else {
                    binding.malpracticeInsuranceLayout.removeViewAt(index);
                    malInsuranceModels.remove(index);
                }
            }
        }, null);
    }

    public void removeLanguage(View view) {
        int index = binding.languageLayout.indexOfChild((RelativeLayout) view.getParent());

        DialogFactory.createReactDialog(this, null, getString(R.string.delete_item_confirmation), getString(R.string.delete), getString(R.string.cancel), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                viewModel.deleteLanguage(languageModels.get(index).getId() + "", index);
            }
        }, null);
    }

    public void removeCertificate(View view) {
        int index = binding.certificateLayout.indexOfChild((LinearLayout) view.getParent());

        DialogFactory.createReactDialog(this, null, getString(R.string.delete_item_confirmation), getString(R.string.delete), getString(R.string.cancel), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                viewModel.deleteCertificate(certificatesModels.get(index).getId() + "", index);
            }
        }, null);
    }

    public void removeAttachment(View view) {
        int index = binding.attachmentLayout.indexOfChild((LinearLayout) view.getParent());

        DialogFactory.createReactDialog(this, null, getString(R.string.delete_item_confirmation), getString(R.string.delete), getString(R.string.cancel), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                viewModel.deleteAttachment(attachmentModels.get(index).getId() + "", index);
            }
        }, null);
    }

    private void setIsCurrent(AppCompatCheckBox checkBox){
        int index = binding.experienceLayout.indexOfChild((LinearLayout) ((RelativeLayout) ((LinearLayout) checkBox.getParent()).getParent()).getParent());

    }
    private void setExperienceFromDate(AppCompatTextView fromDateTv) {
        int index = binding.experienceLayout.indexOfChild((LinearLayout) ((RelativeLayout) ((TextInputLayout) fromDateTv.getParent()).getParent()).getParent());



        DialogFactory.createDatePickerDialog(this, fromDateTv, new DateTimePickerSelectedValueListener() {
            @Override
            public void onValueSelected(Calendar date) {
                fromDateTv.setText(DateUtils.formatDateServer(date));
                experienceModels.get(index).setStartDate(DateUtils.formatDateServer(date));
            }
        }, null, null, false);

    }

    private void setExperienceToDate(AppCompatTextView toDateTV) {
        int index = binding.experienceLayout.indexOfChild((LinearLayout) ((RelativeLayout) ((TextInputLayout) toDateTV.getParent()).getParent()).getParent());

        DialogFactory.createDatePickerDialog(this, toDateTV, new DateTimePickerSelectedValueListener() {
            @Override
            public void onValueSelected(Calendar date) {
                toDateTV.setText(DateUtils.formatDateServer(date));
                experienceModels.get(index).setEndDate(DateUtils.formatDateServer(date));

            }
        }, null, null, false);

    }

    private void setInsuranceExpDate(AppCompatTextView toDateTV) {
        int index = binding.malpracticeInsuranceLayout.indexOfChild((LinearLayout) ((RelativeLayout) ((TextInputLayout) toDateTV.getParent()).getParent()).getParent());

        DialogFactory.createDatePickerDialog(this, toDateTV, new DateTimePickerSelectedValueListener() {
            @Override
            public void onValueSelected(Calendar date) {
                toDateTV.setText(DateUtils.formatDateServer(date));
                malInsuranceModels.get(index).setExpirationDate(DateUtils.formatDateServer(date));

            }
        }, null, null, false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteEducation:
                removeEducation(v);
                break;
            case R.id.deleteExperience:
                removeExperience(v);
                break;
            case R.id.deleteLanguage:
                removeLanguage(v);
                break;
            case R.id.fromTV:

                setExperienceFromDate((AppCompatTextView) v);
                break;
            case R.id.toTV:
                if(current_job==1){

                }
                setExperienceToDate((AppCompatTextView) v);
                break;
            case R.id.education_degree_TV:
                showEducationDegreePicker((AppCompatTextView) v);
                break;
            case R.id.deleteAttachment:
                removeAttachment(v);
                break;
            case R.id.deleteCertificate:
                removeCertificate(v);
                break;
            case R.id.deleteInsurance:
                removeInsurance(v);
                break;
            case R.id.insurance_company_TV:
                showCompaniesNamesPicker((AppCompatTextView) v);
                break;
            case R.id.selectExpDateTV:
                setInsuranceExpDate((AppCompatTextView) v);
                break;

        }
    }

    private void showLanguagePicker() {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.language),
                getString(R.string.general_search),
                null, viewModel.getDataManager().getLookupsModel().getLanguageArrayList(), languageResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showPlaceOfIssuePicker() {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.place_of_issue),
                getString(R.string.general_search),
                null, viewModel.getDataManager().getLookupsModel().getCaregiverIssuersOfCertificates(), placeOfIssueResultListener);

        simpleSearchDialogCompat.show();
    }

    private void showEducationDegreePicker(AppCompatTextView compatTextView) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.education_degree),
                getString(R.string.general_search),
                null, viewModel.getDataManager().getLookupsModel().getEducationDegreeArrayList(), new SearchResultListener<LookUpModel>() {
            @Override
            public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
                int index = binding.educationLayout.indexOfChild((LinearLayout) ((TextInputLayout) compatTextView.getParent()).getParent());
                compatTextView.setText(item.getTitle());
                educationModels.get(index).setEducationDegree(item.getId());
                dialog.dismiss();
            }
        });

        simpleSearchDialogCompat.show();
    }

    private void showCompaniesNamesPicker(AppCompatTextView compatTextView) {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.insurance_company_name),
                getString(R.string.general_search),
                null, insuranceCompanies, new SearchResultListener<LookUpModel>() {
            @Override
            public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
                int index = binding.malpracticeInsuranceLayout.indexOfChild((LinearLayout) ((TextInputLayout) compatTextView.getParent()).getParent());
                compatTextView.setText(item.getTitle());
                malInsuranceModels.get(index).setCompanyId(item.getId());
                dialog.dismiss();
            }
        });

        simpleSearchDialogCompat.show();
    }

    private void showAttachmentTypesPicker() {
        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(this,
                getString(R.string.attachment_category),
                getString(R.string.general_search),
                null, viewModel.getDataManager().getLookupsModel().getAttachmentCategories(), new SearchResultListener<LookUpModel>() {
            @Override
            public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
                categoryID = item.getId();
                showPickingBS(false);
                dialog.dismiss();
            }
        });

        simpleSearchDialogCompat.show();
    }


    private void showPickingBS(boolean isCertificate) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_attachment, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        LinearLayout layoutTakePicture = (LinearLayout) view.findViewById(R.id.takePicture);
        layoutTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openCameraForImage(EducationCertificatesActivity.this, isCertificate ? AppConstants.CERTIFICATE_CAMERA : AppConstants.ATTACHMENT_CAMERA);
                dialog.hide();
            }
        });
        LinearLayout layoutPickPicture = (LinearLayout) view.findViewById(R.id.pickImage);
        layoutPickPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openGallery(EducationCertificatesActivity.this, isCertificate ? AppConstants.CERTIFICATE_GALLERY : AppConstants.ATTACHMENT_GALLERY);
                dialog.hide();
            }
        });


        dialog.show();
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
                if (type == AppConstants.CERTIFICATE_CAMERA || type == AppConstants.CERTIFICATE_GALLERY) {
                    viewModel.uploadCertificates(file.getName(), file);
                } else {
                    viewModel.uploadAttachment(file.getName(), categoryID, file);
                }
            }
        });
    }


    SearchResultListener languageResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            viewModel.addLanguageRemote(item);
            dialog.dismiss();
        }
    };
    SearchResultListener placeOfIssueResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            viewModel.getUserModel().setIssuerOfCertificate(Integer.parseInt(item.getId()));
            binding.placeOfIssueTv.setText(item.getTitle());
            dialog.dismiss();
        }
    };

    private boolean validateSubmitEducation() {
        boolean valid = true;
        for (int i = 0; i < educationModels.size(); i++) {
            //uni field validation
            TextInputLayout universityInputLayout = (TextInputLayout) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(1);
            TextInputEditText universityET = (TextInputEditText) universityInputLayout.findViewById(R.id.university_edt);
            if (universityET.getText().toString().trim().isEmpty()) {
                universityET.setError(getString(R.string.general_required_field));
                focusOnView(universityInputLayout);
                valid = false;
                return valid;
            } else {
                educationModels.get(i).setUniversityOrCollegeName(universityET.getText().toString().trim());
            }


            //uni field validation
            TextInputLayout specializationInputLayout = (TextInputLayout) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(3);
            TextInputEditText specializationET = (TextInputEditText) specializationInputLayout.findViewById(R.id.specialization_edt);
            if (specializationET.getText().toString().trim().isEmpty()) {
                specializationET.setError(getString(R.string.general_required_field));
                focusOnView(specializationInputLayout);
                valid = false;
                return valid;
            } else {
                educationModels.get(i).setSpecialization(universityET.getText().toString().trim());
            }

            // education_degree validation
            if (educationModels.get(i).getEducationDegree() == null || educationModels.get(i).getEducationDegree().isEmpty()) {
                TextInputLayout textInputLayout = (TextInputLayout) ((LinearLayout) binding.educationLayout.getChildAt(i)).getChildAt(2);
                textInputLayout.setError(getString(R.string.error_field_required));
                focusOnView(textInputLayout);
                valid = false;
                return valid;
            }

            if (valid) {
                if (educationModels.get(i).isNew())
                    viewModel.addEducationRemote(educationModels.get(i), i == educationModels.size() - 1 );
                else
                    viewModel.updateEducation(i, educationModels.get(i), i == educationModels.size() - 1);

            }

        }
        return valid;
    }

    private boolean validateSubmitMalInsurance() {
        boolean valid = true;
        for (int i = 0; i < malInsuranceModels.size(); i++) {

            // Company name validation
            if (malInsuranceModels.get(i).getCompanyId() == null || malInsuranceModels.get(i).getCompanyId().isEmpty()) {
                TextInputLayout textInputLayout = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(1);
                textInputLayout.setError(getString(R.string.error_field_required));
                focusOnView(textInputLayout);
                valid = false;
                return valid;
            }


            //insuranceNo field validation
            TextInputLayout insuranceNoInputLayout = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(4);
            TextInputEditText insuranceNoET = (TextInputEditText) insuranceNoInputLayout.findViewById(R.id.insurance_number_edt);
            if (insuranceNoET.getText().toString().trim().isEmpty()) {
                insuranceNoET.setError(getString(R.string.general_required_field));
                focusOnView(insuranceNoInputLayout);
                valid = false;
                return valid;
            } else {
                malInsuranceModels.get(i).setInsuranceNumber(insuranceNoET.getText().toString().trim());
            }

            //exp date field validation

            if (malInsuranceModels.get(i).getExpirationDate() == null || malInsuranceModels.get(i).getExpirationDate().isEmpty()) {
                RelativeLayout relativeLayout = (RelativeLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(5);
                TextInputLayout toDateLayout = (TextInputLayout) relativeLayout.findViewById(R.id.expiration_date_layout);
                toDateLayout.setError(getString(R.string.error_select_end_date));
                focusOnView(toDateLayout);
                valid = false;
                return valid;
            }


            if (valid) {
                TextInputLayout insuranceNoInputLayout1 = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(2);
                TextInputEditText insurance_type_edt = (TextInputEditText) insuranceNoInputLayout1.findViewById(R.id.insurance_type_edt);
                malInsuranceModels.get(i).setInsurance_type(insurance_type_edt.getText().toString().trim());
                TextInputLayout insuranceNoInputLayout2 = (TextInputLayout) ((LinearLayout) binding.malpracticeInsuranceLayout.getChildAt(i)).getChildAt(3);
                TextInputEditText inssuar_name_edt = (TextInputEditText) insuranceNoInputLayout2.findViewById(R.id.inssuar_name_edt);
                malInsuranceModels.get(i).setIssuer_name(inssuar_name_edt.getText().toString().trim());
                if (malInsuranceModels.get(i).isNew())
                    viewModel.addMalInsuranceRemote(malInsuranceModels.get(i), i == malInsuranceModels.size() - 1 );
                else
                    viewModel.updateMalInsurance(i, malInsuranceModels.get(i), i == malInsuranceModels.size() - 1);

            }

        }
        return valid;
    }


    private boolean validateSubmitExperience() {
        boolean valid = true;
        for (int i = 0; i < experienceModels.size(); i++) {
            //hospital field validation
            TextInputLayout hospitalInputLayout = (TextInputLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(1);
            TextInputEditText hospitalET = (TextInputEditText) hospitalInputLayout.findViewById(R.id.hospital_edt);
            if (Objects.requireNonNull(hospitalET.getText()).toString().trim().isEmpty()) {
                hospitalET.setError(getString(R.string.general_required_field));
                focusOnView(hospitalET);
                valid = false;
                return valid;
            } else {
                experienceModels.get(i).setCompanyOrHospitalName(hospitalET.getText().toString().trim());
            }

            //job field validation
            TextInputLayout jobInputLayout = (TextInputLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(3);
            TextInputEditText jobET = (TextInputEditText) jobInputLayout.findViewById(R.id.job_title_edt);
            if (Objects.requireNonNull(jobET.getText()).toString().trim().isEmpty()) {
                jobET.setError(getString(R.string.general_required_field));
                focusOnView(jobET);
                valid = false;
                return valid;
            } else {
                experienceModels.get(i).setJobTitle(jobET.getText().toString().trim());
            }

            //job_description field validation
            TextInputLayout job_descriptionInputLayout = (TextInputLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(4);
            TextInputEditText job_descriptionET = (TextInputEditText) job_descriptionInputLayout.findViewById(R.id.job_description_edt);
            if (Objects.requireNonNull(job_descriptionET.getText()).toString().trim().isEmpty()) {
                job_descriptionET.setError(getString(R.string.general_required_field));
                focusOnView(job_descriptionET);
                valid = false;
                return valid;
            } else {
                experienceModels.get(i).setJobDescription(job_descriptionET.getText().toString().trim());
            }
            //from date field validation

            if (experienceModels.get(i).getStartDate() == null || experienceModels.get(i).getStartDate().isEmpty()) {
                RelativeLayout relativeLayout = (RelativeLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(2);
                TextInputLayout fromDateLayout = (TextInputLayout) relativeLayout.findViewById(R.id.from_date_layout);
                fromDateLayout.setError(getString(R.string.error_select_start_date));
                focusOnView(fromDateLayout);
                valid = false;
                return valid;
            }
            //from date field validation

            if (experienceModels.get(i).getStartDate() == null || experienceModels.get(i).getStartDate().isEmpty()) {
                RelativeLayout relativeLayout = (RelativeLayout) ((LinearLayout) binding.experienceLayout.getChildAt(i)).getChildAt(2);
                TextInputLayout toDateLayout = (TextInputLayout) relativeLayout.findViewById(R.id.to_date_layout);
                toDateLayout.setError(getString(R.string.error_select_end_date));
                focusOnView(toDateLayout);
                valid = false;
                return valid;
            }
            if (valid) {
                if (experienceModels.get(i).isNew())
                    viewModel.addExperienceRemote(experienceModels.get(i), i == experienceModels.size() - 1);
                else
                    viewModel.updateExperience(i, experienceModels.get(i), i == experienceModels.size() - 1);
            }

        }
        return valid;
    }

    @Override
    public void onValidationSuccess() {
        super.onValidationSuccess();
        if (educationModels.size() > 0) {
            if (!validateSubmitEducation())
                return;
        }
        if (experienceModels.size() > 0) {
           if (!validateSubmitExperience())
               return;
        }
        if (malInsuranceModels.size() > 0) {
            if (!validateSubmitMalInsurance())
                return;
        }
        if (viewModel.getUserModel().getIssuerOfCertificate() != null) {
            binding.placeOfIssueLayout.setError(null);
            viewModel.getUserModel().setBio(WeCareUtils.getEditTextString(binding.bioEdt));
            viewModel.getUserModel().setProfessionalLicenseNumber(WeCareUtils.getEditTextString(binding.licenseNumberEdt));
            viewModel.getUserModel().setIssuername(WeCareUtils.getEditTextString(binding.issuerNameedt));
            viewModel.getUserModel().setYearsOfExperience(WeCareUtils.getEditTextString(binding.yearsExperienceEdt));

            viewModel.updateUser();
        } else {
            binding.placeOfIssueLayout.setError(getString(R.string.general_required_field));
            isEditMode = true;
        }

    }

    @Override
    public void onValidationError() {
        super.onValidationError();
        isEditMode = true;
    }

    private void showSuccessDialog() {
        DialogFactory.createFeedBackDialog(this, "", getString(R.string.saved_successfully), getString(R.string.ok), getResources().getDrawable(R.drawable.success_img), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                isEditMode = false;

                setUpEditMode();
                updateMenuTitles();
            }
        });
    }

    @Override
    public void handleError(String errorMessage) {
        DialogFactory.createErrorDialog(this, getString(R.string.error), errorMessage, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                isEditMode = false;

                setUpEditMode();
                updateMenuTitles();
            }
        });
    }

    private void updateMenuTitles() {
        MenuItem item = menu.findItem(R.id.editItem);
        item.setTitle(isEditMode ? getString(R.string.general_save) : getString(R.string.edit));
    }

    private final void focusOnView(View view){
        binding.scrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.scrollTo(0, view.getBottom());
            }
        });
    }
    //can be used to check if required
    //  if (binding.educationLayout.getChildAt(i).getTag() != null && layout.getChildAt(i).getTag().toString().contains("required")) {

}
