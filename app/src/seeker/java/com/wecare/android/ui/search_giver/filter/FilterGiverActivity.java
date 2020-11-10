package com.wecare.android.ui.search_giver.filter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.databinding.ActivityFilterGiverBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.PickerDialogFactory;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

import javax.inject.Inject;
import java.util.ArrayList;

public class FilterGiverActivity extends BaseActivity<ActivityFilterGiverBinding, FilterGiverViewModel> implements FilterNavigator {

    public static final String TAG = FilterGiverActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    /**/
    FilterGiverViewModel viewModel;
    ActivityFilterGiverBinding binding;

    ArrayList<LookUpModel> filterSortingList;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, FilterGiverActivity.class);
    }

    SearchResultListener yearsOfExperienceResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedYearsExperienceTxt.setText(item.getTitle());
            viewModel.getFilterObject().setSortYearsOfExperience(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener ratingResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedRateTxt.setText(item.getTitle());
            viewModel.getFilterObject().setSortRating(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener priceResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedPriceTxt.setText(item.getTitle());
            viewModel.getFilterObject().setSortPrice(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener ageResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedAgeTxt.setText(item.getTitle());
            viewModel.getFilterObject().setSortAge(item.getId());
            dialog.dismiss();
        }
    };

    SearchResultListener genderResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            binding.selectedGenderTxt.setText(item.getTitle());
            viewModel.getFilterObject().setSortGender(item.getId());
            dialog.dismiss();
        }
    };

    @Override
    public FilterGiverViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(FilterGiverViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_filter_giver;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        addToolbar(R.id.toolbar, getString(R.string.search_results), true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        buildSortingItems();

        //get args
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT)) {
            getViewModel().setFilterObject(bundle.getParcelable(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT));

            binding.selectedYearsExperienceTxt.setText(viewModel.getFilterObject().getSortYearsOfExperience());
            binding.selectedRateTxt.setText(viewModel.getFilterObject().getSortRating());
            binding.selectedPriceTxt.setText(viewModel.getFilterObject().getSortPrice());
            binding.selectedAgeTxt.setText(viewModel.getFilterObject().getSortAge());
            binding.selectedGenderTxt.setText(viewModel.getFilterObject().getSortGender());
        }
    }

    private void buildSortingItems() {
        filterSortingList = new ArrayList<>();
        LookUpModel ascendingItem = new LookUpModel();
        ascendingItem.setId("asc");
        ascendingItem.setArabicName(getString(R.string.ascending));
        ascendingItem.setEnglishName(getString(R.string.ascending));
        filterSortingList.add(ascendingItem);

        LookUpModel descendingItem = new LookUpModel();
        descendingItem.setId("desc");
        descendingItem.setArabicName(getString(R.string.descending));
        descendingItem.setEnglishName(getString(R.string.descending));
        filterSortingList.add(descendingItem);

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
    public void doneSaveClicked() {
        onSaveFilterSelection();
    }

    @Override
    public void resetAllClicked() {

        binding.selectedYearsExperienceTxt.setText("");
        viewModel.getFilterObject().setSortYearsOfExperience(null);

        binding.selectedRateTxt.setText("");
        viewModel.getFilterObject().setSortRating(null);

        binding.selectedPriceTxt.setText("");
        viewModel.getFilterObject().setSortPrice(null);

        binding.selectedAgeTxt.setText("");
        viewModel.getFilterObject().setSortAge(null);

        binding.selectedGenderTxt.setText("");
        viewModel.getFilterObject().setSortGender(null);

    }

    @Override
    public void ageClicked() {
        PickerDialogFactory.showSortingType(mContext, filterSortingList, ageResultListener);
    }

    @Override
    public void genderClicked() {
        PickerDialogFactory.showSortingType(mContext, filterSortingList, genderResultListener);
    }

    @Override
    public void priceClicked() {
        PickerDialogFactory.showSortingType(mContext, filterSortingList, priceResultListener);
    }

    @Override
    public void rateClicked() {
        PickerDialogFactory.showSortingType(mContext, filterSortingList, ratingResultListener);

    }

    @Override
    public void yearsOfExperienceClicked() {
        PickerDialogFactory.showSortingType(mContext, filterSortingList, yearsOfExperienceResultListener);
    }

}
