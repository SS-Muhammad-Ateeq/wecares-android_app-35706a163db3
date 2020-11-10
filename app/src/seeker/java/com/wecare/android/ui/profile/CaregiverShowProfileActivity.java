package com.wecare.android.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.AttachmentModel;
import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.EducationModel;
import com.wecare.android.data.model.api.models.ExperienceModel;
import com.wecare.android.data.model.api.requests.LanguageRequest;
import com.wecare.android.databinding.ActivityCaregiverShowProfileBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

public class CaregiverShowProfileActivity extends BaseActivity<ActivityCaregiverShowProfileBinding, UserShowProfileViewModel> implements UserShowProfileActivityNavigator {

    ActivityCaregiverShowProfileBinding binding;
    CaregiverUser caregiverUser;

    private Menu menu;


    @Inject
    ViewModelProviderFactory factory;
    private UserShowProfileViewModel viewModel;

    @Override
    public UserShowProfileViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(UserShowProfileViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_caregiver_show_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.profile), true);
        viewModel.getCaregiverProfile(getIntent().getStringExtra(AppConstants.ARGS_KEY_CAREGIVER_ID));
        viewModel.setNavigator(this);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CaregiverShowProfileActivity.class);
        return intent;
    }

    private void setUpData() {
        if (caregiverUser == null)
            return;
        viewModel.setURL(caregiverUser.getAvatar());
        binding.nameTV.setText(caregiverUser.getFirstName() + " " + caregiverUser.getLastName());
        binding.caregiverID.setText(caregiverUser.getWecareID());
        viewModel.setRating(caregiverUser.getRating());
        binding.ageTV.setText(caregiverUser.getAge() + "");
        binding.genderTV.setText(caregiverUser.getGender() != null ? caregiverUser.getGender().getEnglishName() : "");
        binding.bioTV.setText(caregiverUser.getBio());

        addGiverEducations();
        addGiverExperiences();
        addGiverLanguages();
        addGiverCertificates();
//        addGiverAttachments();

        updateBlockMenuItemTitle();
        updateFavoriteMenuItemIcon();

    }


    private void addGiverEducations() {
        if (caregiverUser.getEducationModels() == null || caregiverUser.getEducationModels().size() < 1)
            return;
        for (int i = 0; i < caregiverUser.getEducationModels().size(); i++) {
            addEducationView(caregiverUser.getEducationModels().get(i), i);
        }
    }

    private void addGiverExperiences() {
        if (caregiverUser.getExperienceModels() == null || caregiverUser.getExperienceModels().size() < 1)
            return;
        for (int i = 0; i < caregiverUser.getExperienceModels().size(); i++) {
            addExperienceView(caregiverUser.getExperienceModels().get(i), i);
        }
    }

    private void addGiverLanguages() {
        if (caregiverUser.getLanguageModels() == null || caregiverUser.getLanguageModels().size() < 1)
            return;
        for (int i = 0; i < caregiverUser.getLanguageModels().size(); i++) {
            addLanguageView(caregiverUser.getLanguageModels().get(i), i);
        }
    }

    private void addGiverCertificates() {
        if (caregiverUser.getCertificateModels() == null || caregiverUser.getCertificateModels().size() < 1)
            return;
        for (int i = 0; i < caregiverUser.getCertificateModels().size(); i++) {
            addAttachmentView(caregiverUser.getCertificateModels().get(i), i, true);
        }
    }

    private void addGiverAttachments() {
        if (caregiverUser.getAttachmentModels() == null || caregiverUser.getAttachmentModels().size() < 1)
            return;
        for (int i = 0; i < caregiverUser.getAttachmentModels().size(); i++) {
            addAttachmentView(caregiverUser.getAttachmentModels().get(i), i, false);
        }
    }


    @Override
    public void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels) {

    }

    @Override
    public void userBlocked() {
        caregiverUser.setIsBlocked(AppConstants.PHP_TRUE_RAW);
        updateBlockMenuItemTitle();

    }

    @Override
    public void userUnBlocked() {
        caregiverUser.setIsBlocked(AppConstants.PHP_FALSE_RAW);
        updateBlockMenuItemTitle();
    }

    @Override
    public void userFavorite() {
        caregiverUser.setIsFavorite(AppConstants.PHP_TRUE_RAW);
        updateFavoriteMenuItemIcon();
    }

    @Override
    public void userUnFavorite() {
        caregiverUser.setIsFavorite(AppConstants.PHP_FALSE_RAW);
        updateFavoriteMenuItemIcon();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_favorite_block_report, menu);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                if (caregiverUser.getIsFavorite()== AppConstants.PHP_TRUE_RAW)
                    viewModel.unFavoriteGiver(caregiverUser.getId());
                else
                    viewModel.favoriteGiver(caregiverUser.getId());

                return true;
            case R.id.action_block:
                if (caregiverUser.getIsBlocked()== AppConstants.PHP_TRUE_RAW)
                    viewModel.unblockGiver(caregiverUser.getId());
                else
                    showBlockGiverDialog();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private void showBlockGiverDialog(){
        DialogFactory.createInputDialog(this, getString(R.string.block_caregiver), getString(R.string.block_caregiver_disclaimer), getString(R.string.explanation),getString(R.string.ok),
                new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                    viewModel.blockGiver(caregiverUser.getId(),input.toString());
                    }
                });
    }


    private void updateBlockMenuItemTitle() {
        MenuItem item = menu.findItem(R.id.action_block);
        item.setTitle(caregiverUser.getIsBlocked().equals(AppConstants.PHP_TRUE_RAW) ? getString(R.string.unblock) : getString(R.string.block));
    }

    private void updateFavoriteMenuItemIcon() {
        MenuItem item = menu.findItem(R.id.action_favorite);
        item.setIcon(caregiverUser.getIsFavorite().equals(AppConstants.PHP_TRUE_RAW) ? R.drawable.ic_unfavorite_giver : R.drawable.ic_favorite_giver);

    }


    @Override
    public void giverProfileFetchedSuccessfully(CaregiverUser caregiverUser) {
        this.caregiverUser = caregiverUser;
        setUpData();
    }

    public void addEducationView(EducationModel item, int position) {
        LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        View view = inflater.inflate(R.layout.layout_view_education, null);


        AppCompatTextView collageNameTV = (AppCompatTextView) view.findViewById(R.id.collageNameTV);
        collageNameTV.setText(item.getUniversityOrCollegeName());

        AppCompatTextView educationDegreeTV = (AppCompatTextView) view.findViewById(R.id.educationDegreeTV);
        educationDegreeTV.setText(item.getEducationDegree());

        AppCompatTextView specializationTV = (AppCompatTextView) view.findViewById(R.id.specializationTV);
        specializationTV.setText(item.getSpecialization());

        binding.educationLayout.addView(view, (position), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addExperienceView(ExperienceModel item, int position) {
        LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        View view = inflater.inflate(R.layout.layout_view_experience, null);


        AppCompatTextView companyNameTV = (AppCompatTextView) view.findViewById(R.id.companyNameTV);
        companyNameTV.setText(item.getCompanyOrHospitalName());

        AppCompatTextView durationTV = (AppCompatTextView) view.findViewById(R.id.durationTV);
        durationTV.setText(item.getStartDate() + " " + item.getEndDate());

        AppCompatTextView jobTitleTV = (AppCompatTextView) view.findViewById(R.id.jobTitleTV);
        jobTitleTV.setText(item.getJobTitle());

        AppCompatTextView jobDescriptionTV = (AppCompatTextView) view.findViewById(R.id.jobDescriptionTV);
        jobDescriptionTV.setText(item.getJobDescription());

        binding.experienceLayout.addView(view, (position), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public void addLanguageView(LanguageRequest item, int position) {
        LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        View view = inflater.inflate(R.layout.layout_add_language, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteLanguage);
        deleteImg.setVisibility(View.GONE);
        AppCompatTextView textView = (AppCompatTextView) view.findViewById(R.id.languageDesc);
        textView.setText(item.getEnglishName());
        binding.languageLayout.addView(view, (position), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addAttachmentView(AttachmentModel model, int position, boolean isCertificates) {
        LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        View view = inflater.inflate(R.layout.layout_attachment, null);

        AppCompatImageView deleteImg = (AppCompatImageView) view.findViewById(R.id.deleteAttachment);
        deleteImg.setVisibility(View.GONE);
        AppCompatImageView compatImageView = (AppCompatImageView) view.findViewById(R.id.attachment_img);
        Context context = compatImageView.getContext();
        Glide.with(context).load(model.getUrl()).into(compatImageView);

//        if (isCertificates)
            binding.certificatesLayout.addView(view, position, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        else
//            binding.attachmentsLayout.addView(view, position, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


}
