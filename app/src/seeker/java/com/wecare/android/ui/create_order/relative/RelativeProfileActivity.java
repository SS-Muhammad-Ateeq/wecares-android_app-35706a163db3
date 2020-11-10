package com.wecare.android.ui.create_order.relative;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.databinding.ActivityRelativeProfileBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.create_order.relative.add.AddEditRelativeProfileActivity;

import com.wecare.android.utils.AppConstants;

import com.wecare.android.utils.DialogFactory;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import javax.inject.Inject;

import java.util.List;

public class RelativeProfileActivity extends BaseActivity<ActivityRelativeProfileBinding, RelativeProfileViewModel>
        implements RelativeProfileNavigator, HasSupportFragmentInjector, RelativeProfileAdapter.RelativeProfileAdapterListener {

    public static final String TAG = RelativeProfileActivity.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    RelativeProfileAdapter relativeProfileAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    RelativeProfileViewModel viewModel;

    ActivityRelativeProfileBinding binding;

    private int selectedPosition = -1;
    private SelectionTracker<Long> selectionTracker;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, RelativeProfileActivity.class);
    }


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
        return R.layout.activity_relative_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        //update toolbar title
        addToolbar(R.id.toolbar, getString(R.string.my_profile), true);

        setUp(savedInstanceState);
        subscribeToLiveData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.fetchRelativeProfiles();
    }

    private void setUp(Bundle savedInstanceState) {
        relativeProfileAdapter.setListener(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.relativeProfileRecycler.setLayoutManager(mLayoutManager);
        binding.relativeProfileRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.relativeProfileRecycler.setAdapter(relativeProfileAdapter);

        selectionTracker = new SelectionTracker.Builder<>("mySelectionPosition",
                binding.relativeProfileRecycler,
                new RelativeProfileAdapter.KeyProvider(binding.relativeProfileRecycler.getAdapter()),
                new RelativeProfileAdapter.RelativeItemDetailsLookup(binding.relativeProfileRecycler),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectSingleAnything()).build();
//                .withSelectionPredicate(new RelativeProfileAdapter.DetailsPredicate()).build();

        relativeProfileAdapter.setSelectionTracker(selectionTracker);

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onItemStateChanged(@NonNull Object key, boolean selected) {
                super.onItemStateChanged(key, selected);
                if (selected) {
                    selectedPosition = ((Long) key).intValue();
                } else {
                    selectedPosition = -1;
                }
            }
        });

        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        selectionTracker.onSaveInstanceState(outState);
    }

    private void subscribeToLiveData() {
        viewModel.getRelativeProfileListLiveData().observe(this, new Observer<List<RelativeProfileResponse>>() {
            @Override
            public void onChanged(@Nullable List<RelativeProfileResponse> profileResponseList) {
                viewModel.addRelativeProfileItemsToList(profileResponseList);
            }
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void updateRelativeProfilesList(List<RelativeProfileResponse> responseList) {
        relativeProfileAdapter.addItems(responseList);

    }

    @Override
    public void onDeletedSuccessfully(RelativeProfileResponse profileResponse) {
        getViewModel().getRelativeProfileObservableArrayList().remove(profileResponse);
        relativeProfileAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
//                validator.toValidate();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (selectionTracker != null && selectedPosition != -1) {
            RelativeProfileResponse profileResponse = relativeProfileAdapter.getItemAtPosition(selectedPosition);
            Intent data = new Intent();
            data.putExtra(AppConstants.ARGS_SELECTED_RELATIVE_PROFILE, profileResponse);
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public void addNewRelativeClicked() {
        Intent intent = AddEditRelativeProfileActivity.getStartIntent(mContext, "");
        startActivity(intent);
    }

    @Override
    public void userProfileClicked() {
        //not used here
    }

    @Override
    public void genderClicked() {
        //not used here
    }

    @Override
    public void relationshipClicked() {

    }

    @Override
    public void ageClicked() {
        //not used here
    }

    @Override
    public void bloodTypeClicked() {
        //not used here
    }

    @Override
    public void weightClicked() {
        //not used here
    }

    @Override
    public void lengthClicked() {
        //not used here
    }

    @Override
    public void chronicDiseasesClicked() {
        //not used here
    }

    @Override
    public void bloodPressureClicked() {
        //not used here
    }

    @Override
    public void isThereHealthInsuranceClicked() {
        //not used here
    }

    @Override
    public void typeOfInsuranceClicked() {
        //not used here
    }

    @Override
    public void insuranceExpirationDateClicked() {
        //not used here
    }

    @Override
    public void fillUserInfoForEdit() {
        //not used here
    }

    @Override
    public void insuranceCompanyNameClicked() {
        //not used
    }

    @Override
    public void countriesOfServiceClicked() {
        //not used here
    }

    @Override
    public void birthDateClicked() {
        //not used here
    }

    @Override
    public void nationalityClicked() {
        //not used here
    }

    @Override
    public void onItemClicked(RelativeProfileResponse relativeProfileResponse) {
        Intent intent = AddEditRelativeProfileActivity.getStartIntent(mContext, relativeProfileResponse.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(RelativeProfileResponse relativeProfileResponse) {
        DialogFactory.createReactDialog(mContext, getString(R.string.delete), getString(R.string.delete_item_confirmation),
                getString(R.string.delete), getString(R.string.cancel), null,
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //request delete
                        viewModel.deleteRelativeProfiles(relativeProfileResponse);
                    }
                }, null);
    }
}
