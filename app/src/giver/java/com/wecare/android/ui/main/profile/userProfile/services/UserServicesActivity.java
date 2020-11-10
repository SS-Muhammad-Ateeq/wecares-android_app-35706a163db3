package com.wecare.android.ui.main.profile.userProfile.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.ServicesTotalModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.databinding.ActivityUserMainServicesBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.main.order.sub.OrderSubServicesActivity;
import com.wecare.android.ui.main.profile.userProfile.services.selection.UserServicesSelectionActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserServicesActivity extends BaseActivity<ActivityUserMainServicesBinding, UserServicesViewModel> implements UserServicesActivityNavigator, UserServicesAdapter.ServiceAdapterListener {

    @Inject
    ViewModelProviderFactory factory;
    private UserServicesViewModel viewModel;

    @Inject
    UserServicesAdapter mServicesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    ActivityUserMainServicesBinding binding;

    private String servicesType;

    @Override
    public UserServicesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(UserServicesViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_main_services;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();
        servicesType = getIntent().getStringExtra(AppConstants.ARGS_KEY_SERVICES_PICKING_TYPE);
        addToolbar(R.id.toolbar, getString(R.string.services), true);
        setUp();
        subscribeToLiveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (servicesType.equals(AppConstants.ARGS_KEY_ORDER_SERVICES))
            viewModel.fetchOrderServices(getIntent().getStringExtra(AppConstants.ARGS_KEY_ORDER_ID));
        else
            viewModel.fetchServices();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, UserServicesActivity.class);
        return intent;
    }

    private void setUp() {

        mServicesAdapter.setListener(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.serviceRecycler.setLayoutManager(mLayoutManager);
        binding.serviceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.serviceRecycler.setAdapter(mServicesAdapter);
        if(viewModel.getDataManager().getCurrentUserModel()!=null) {
            if (viewModel.getDataManager().getCurrentUserModel().getStatus().equals(AppConstants.CAREGIVER_STATUS_INACTIVE + "") || viewModel.getDataManager().getCurrentUserModel().getStatus().equals(AppConstants.CAREGIVER_STATUS_REJECTED + "")) {
                setNeedActivateUI();
            } else if (viewModel.getDataManager().getCurrentUserModel().getStatus().equals(AppConstants.CAREGIVER_STATUS_UNDER_REVIEW + "")) {
                setUnderReviewUI();
            } else {
                setNoServicesNeedActivateUI();
            }
        }


        if (servicesType.equals(AppConstants.ARGS_KEY_ORDER_SERVICES)) {
            binding.serviceActivationLayout.setVisibility(View.GONE);
            binding.activateWarningTV.setVisibility(View.GONE);
        }
    }

    private void setNeedActivateUI() {
        binding.activateNowTV.setText(getString(R.string.new_services_need_activation_from_wecare));
        binding.activateNowTV.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        binding.btnActivate.setVisibility(View.VISIBLE);
        binding.activateWarningTV.setVisibility(View.VISIBLE);

    }

    private void setUnderReviewUI() {
        binding.activateNowTV.setText(R.string.services_need_admin_approval);
        binding.activateNowTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_smile), null, null, null);
        binding.btnActivate.setVisibility(View.GONE);
        binding.activateWarningTV.setVisibility(View.GONE);

    }

    private void setNoServicesNeedActivateUI() {
        binding.activateNowTV.setText(getString(R.string.there_is_no_services_need_to_activation));
        binding.activateNowTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_smile), null, null, null);
        binding.btnActivate.setVisibility(View.GONE);
        binding.activateWarningTV.setVisibility(View.GONE);

    }

    private void subscribeToLiveData() {
        viewModel.getServiceListLiveData().observe(this, new Observer<List<MainServiceModel>>() {
            @Override
            public void onChanged(@Nullable List<MainServiceModel> mainServiceRespons) {
                viewModel.addServiceItemsToList(mainServiceRespons);
            }
        });
    }

    @Override
    public void activateNowClicked() {
        DialogFactory.createReactDialog(this, getString(R.string.submit_services_review_title), getString(R.string.submit_services_review_disclaimer), getString(R.string.yes), getString(R.string.no), null, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                viewModel.requestToActivate();
            }
        },null);


    }

    @Override
    public void goToSubServices(MainServiceModel mainServiceModel) {
        if (servicesType.equals(AppConstants.ARGS_KEY_CAREGIVER_SERVICES))
            goToCaregiverSubService(mainServiceModel);

        else
            goToOrderSebServices(mainServiceModel);


    }

    @Override
    public void requestToActivateSuccessfully() {
        setUnderReviewUI();
    }

    @Override
    public void serviceNumbersFetched(ServicesTotalModel totalModel) {
        binding.noOfActiveTv.setText(totalModel.getActive());
        binding.noOfActivationTv.setText(totalModel.getUnderReview());
        binding.noOfNotSignedTv.setText(totalModel.getInactive());
    }

    private void goToCaregiverSubService(MainServiceModel mainServiceModel) {
        Intent intent = UserServicesSelectionActivity.getStartIntent(this);
        intent.putExtra(AppConstants.ARGS_KEY_SERVICES, mainServiceModel);
        startActivity(intent);
    }

    private void goToOrderSebServices(MainServiceModel serviceModel) {
        Intent intent = OrderSubServicesActivity.getStartIntent(this);
        intent.putExtra(AppConstants.ARGS_KEY_SERVICES, serviceModel);
        intent.putExtra(AppConstants.ARGS_KEY_SUB_SERVICES_PICKING_TYPE, AppConstants.ARGS_KEY_SUB_SERVICES_EDIT);
        startActivityForResult(intent, AppConstants.REQ_CODE_ORDER_SUB_SERVICE_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQ_CODE_ORDER_SUB_SERVICE_LIST) {
            setResult(resultCode, data);
            finish();
        }
    }

    @Override
    public void onItemClicked(MainServiceModel mainServiceModel) {
        goToSubServices(mainServiceModel);
    }
}
