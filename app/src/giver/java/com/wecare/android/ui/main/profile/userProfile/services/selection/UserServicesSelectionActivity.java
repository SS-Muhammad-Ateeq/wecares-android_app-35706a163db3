package com.wecare.android.ui.main.profile.userProfile.services.selection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.requests.UserServiceRequestModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ActivityUserServicesSelectionBinding;
import com.wecare.android.interfaces.UserServiceCallBack;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserServicesSelectionActivity extends BaseActivity<ActivityUserServicesSelectionBinding, UserServicesSelectionViewModel> implements UserServicesSelectionNavigator {

    ActivityUserServicesSelectionBinding binding;

    MainServiceModel selectedMainServiceModel;

    int selectedServiceMaxIndex;
    ArrayList<Integer> integers=new ArrayList<>();

    ArrayList<SubServiceResponse> subServiceResponseArrayList = new ArrayList<>();

    @Inject
    ViewModelProviderFactory factory;
    private UserServicesSelectionViewModel viewModel;

    @Inject
    UserSubServicesAdapter mServicesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Override
    public UserServicesSelectionViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(UserServicesSelectionViewModel.class);
        return viewModel;    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_services_selection;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        setUpAdapter();

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(AppConstants.ARGS_KEY_SERVICES)) {
            selectedMainServiceModel = getIntent().getExtras().getParcelable(AppConstants.ARGS_KEY_SERVICES);

            if (selectedMainServiceModel != null) {

                //set the index of the last selected item so it draw a gray seperator
                selectedServiceMaxIndex = selectedMainServiceModel.getSelectedServicesNumber()== 0 ? -1 : selectedMainServiceModel.getSelectedServicesNumber()-1;
                mServicesAdapter.setSelectedServiceMaxIndex(selectedServiceMaxIndex);
                subServiceResponseArrayList.addAll(selectedMainServiceModel.getSubServiceResponseList());
                //update recycler list.
                mServicesAdapter.addItems(subServiceResponseArrayList);
            }
        }
        addToolbar(R.id.toolbar, selectedMainServiceModel.getServiceName(), true);
        binding.activateWarningTV.setText(selectedServiceMaxIndex==-1 ? getString(R.string.choose_services):getString(R.string.your_active_services));

    }

    private void setUpAdapter() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.subServiceRecycler.setLayoutManager(mLayoutManager);
        binding.subServiceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.subServiceRecycler.setAdapter(mServicesAdapter);
        mServicesAdapter.setContext(this);
        mServicesAdapter.setSelectionListener(new ServicesSelectionItemViewModel.serviceSelectionListener() {
            @Override
            public void onEditClicked(int position, SubServiceResponse serviceResponse) {
                if(!integers.contains(serviceResponse.getId())) {
                    UserServiceDialogFragment fragment = new UserServiceDialogFragment();
                    fragment.setSubServiceResponse(serviceResponse);
                    if (viewModel.getDataManager().getCurrentUserModel() != null && viewModel.getDataManager().getCurrentUserModel().getCountry() != null)
                        fragment.setCurrencyCode(viewModel.getDataManager().getCurrentUserModel().getCountry().getCurrencyCode());
                    fragment.setGenderArrayList(viewModel.getDataManager().getLookupsModel().getProviderServiceGendersArrayList());
                    fragment.setEdit(true);
                    fragment.setCallBack(new UserServiceCallBack() {
                        @Override
                        public void doneClicked(SubServiceResponse selectedObject, UserServiceRequestModel requestModel) {
                            viewModel.editService(requestModel, selectedObject);
                        }
                    });
                    fragment.show(getSupportFragmentManager(), "");
                }

            }

            @Override
            public void onDeleteClicked(int position, SubServiceResponse serviceResponse) {
                createDeleteDialog(serviceResponse);
            }

            @Override
            public void onAddClicked(int position, SubServiceResponse serviceResponse) {
                UserServiceDialogFragment fragment = new UserServiceDialogFragment();
                fragment.setSubServiceResponse(serviceResponse);
                if (viewModel.getDataManager().getCurrentUserModel()!=null && viewModel.getDataManager().getCurrentUserModel().getCountry()!=null)
                    fragment.setCurrencyCode(viewModel.getDataManager().getCurrentUserModel().getCountry().getCurrencyCode());
                fragment.setGenderArrayList(viewModel.getDataManager().getLookupsModel().getProviderServiceGendersArrayList());
                fragment.setCallBack(new UserServiceCallBack() {
                    @Override
                    public void doneClicked(SubServiceResponse selectedObject,UserServiceRequestModel requestModel) {
                        viewModel.addService(requestModel,selectedObject);
                    }
                });
                fragment.show(getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editItem:
                mServicesAdapter.setEditMode(!mServicesAdapter.getIsEditMode());
                item.setTitle(mServicesAdapter.getIsEditMode() ? getString(R.string.done) : getString(R.string.edit));
                setUpAdapter();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, UserServicesSelectionActivity.class);
    }

    @Override
    public void serviceAddedSuccessfully(SubServiceResponse response) {
        Log.e("selected fkd", String.valueOf(selectedServiceMaxIndex));
        selectedServiceMaxIndex = selectedServiceMaxIndex + 1;
        integers.add(response.getId());
        Log.e("selected fkd", String.valueOf(selectedServiceMaxIndex));

        //modify the object locally until its returned by api
        response.setSelected(AppConstants.PHP_TRUE);
        response.setStatus(AppConstants.PHP_FALSE);

        mServicesAdapter.setSelectedServiceMaxIndex(selectedServiceMaxIndex);

        //remove it from its previous location
        subServiceResponseArrayList.remove(response.getPosition());

        //add it to the bottom of the selected list
        subServiceResponseArrayList.add(selectedServiceMaxIndex,response);


        mServicesAdapter.clearItems();
        mServicesAdapter.addItems(subServiceResponseArrayList);

        // reload adapter
        binding.activateWarningTV.setText(selectedServiceMaxIndex==-1 ? getString(R.string.choose_services):getString(R.string.your_active_services));
        setUpAdapter();

    }


    @Override
    public void serviceEditedSuccessfully(UserServiceRequestModel model, SubServiceResponse response) {

        //change object values locally
        response.setPrice(model.getPrice());
        response.setGender(model.getGenderID());

        mServicesAdapter.clearItems();
        mServicesAdapter.addItems(subServiceResponseArrayList);

        // reload adapter
        binding.activateWarningTV.setText(selectedServiceMaxIndex==-1 ? getString(R.string.choose_services):getString(R.string.your_active_services));
        setUpAdapter();
    }

    @Override
    public void serviceDeletedSuccessfully(SubServiceResponse response) {

        selectedServiceMaxIndex = selectedServiceMaxIndex - 1;



        mServicesAdapter.setSelectedServiceMaxIndex(selectedServiceMaxIndex);

        //remove it from its previous location
        subServiceResponseArrayList.remove(response.getPosition());


        mServicesAdapter.clearItems();
        mServicesAdapter.addItems(subServiceResponseArrayList);

        // reload adapter
        binding.activateWarningTV.setText(selectedServiceMaxIndex==-1 ? getString(R.string.choose_services):getString(R.string.your_active_services));
        setUpAdapter();
    }

    private void createDeleteDialog(SubServiceResponse serviceResponse){
        DialogFactory.createReactDialog(this, null, getString(R.string.delete_service_msg), getString(R.string.yes),getString(R.string.no), getResources().getDrawable(R.drawable.ic_delete), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                viewModel.deleteService(new UserServiceRequestModel(serviceResponse.getCaregiverServiceID()+"",serviceResponse.getId()+""),serviceResponse);
            }
        },null);
    }
}
