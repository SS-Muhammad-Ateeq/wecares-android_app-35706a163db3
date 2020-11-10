package com.wecare.android.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.CaregiverUser;
import com.wecare.android.data.model.api.models.CountryModel;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.databinding.ActivityUserShowProfileBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class UserShowProfileActivity extends BaseActivity<ActivityUserShowProfileBinding, UserShowProfileViewModel> implements UserShowProfileActivityNavigator {

    UserModel userModel;
    String type;
    ArrayList<CountryModel> countries;

    private Menu menu;


    ActivityUserShowProfileBinding binding;

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
        return R.layout.activity_user_show_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        userModel = getIntent().getParcelableExtra(AppConstants.KEY_USER_OBJECT);
        addToolbar(R.id.toolbar, new StringBuilder().append(userModel.getFirstName()).append(" ").append(getString(R.string.profile)).toString(), true);
        setupData();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, UserShowProfileActivity.class);
        return intent;
    }

    private void setupData() {
        if (userModel == null)
            return;
        binding.usernameTV.setText(new StringBuilder().append(userModel.getFirstName()).append(" ").append(userModel.getMiddleName()).append(" ").append(userModel.getLastName()).toString());
        binding.userNumberTV.setText(userModel.getPhoneNumber());
        binding.genderTV.setText(userModel.getGender().getTitle());
        binding.birtheDateTV.setText(userModel.getBirthDate());
        viewModel.setRating(userModel.getRating());
        viewModel.setURL(userModel.getAvatar());
        binding.nationalityTV.setText(userModel.getNationality().getTitle());
        binding.countryOfServiceTV.setText(userModel.getCountry().getTitle());
    }

    @Override
    public void countriesFinishedSuccessfully(ArrayList<CountryModel> countryModels) {
        this.countries = countryModels;
    }

    @Override
    public void userBlocked() {
        userModel.setIsBlocked(AppConstants.PHP_TRUE_RAW);
        updateBlockMenuItemTitle();
    }

    @Override
    public void userUnBlocked() {
        userModel.setIsBlocked(AppConstants.PHP_TRUE_RAW);
        updateBlockMenuItemTitle();
    }

    @Override
    public void userFavorite() {

    }

    @Override
    public void userUnFavorite() {

    }

    @Override
    public void giverProfileFetchedSuccessfully(CaregiverUser caregiverUser) {

    }

    private String getCountryNameByID(int id) {
        for (CountryModel model : countries) {
            if (model.getId().equals((long) id)) {
                return model.getTitle();
            }
        }
        return "";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_block_report, menu);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_block:
                if (userModel.getIsBlocked()== AppConstants.PHP_TRUE_RAW)
                    viewModel.unBlockSeeker(userModel.getId());
                else
                    showBlockseekerDialog();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private void showBlockseekerDialog(){
        DialogFactory.createInputDialog(this, getString(R.string.block_care_seeker), getString(R.string.block_care_seeker_disclaimer), getString(R.string.explanation),getString(R.string.ok),
                new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        viewModel.blockSeeker(userModel.getId(),input.toString());
                    }
                });
    }



    private void updateBlockMenuItemTitle() {
        MenuItem item = menu.findItem(R.id.action_block);
        item.setTitle(userModel.getIsBlocked().equals(AppConstants.PHP_TRUE_RAW) ? getString(R.string.unblock) : getString(R.string.block));
    }

//    private void createDeleteDialog(SubServiceResponse serviceResponse){
//        DialogFactory.createReactDialog(this, null, getString(R.string.delete_service_msg), getString(R.string.yes),getString(R.string.no), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
//            @Override
//            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                viewModel.deleteService(new UserServiceRequestModel(serviceResponse.getCaregiverServiceID(),serviceResponse.getId()+""),serviceResponse);
//            }
//        },null);
//    }
}
