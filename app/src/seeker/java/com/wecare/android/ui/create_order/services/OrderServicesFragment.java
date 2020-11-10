package com.wecare.android.ui.create_order.services;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.ilhasoft.support.validation.Validator;

import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.RequiredServiceModel;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.data.model.api.responses.StatisticResponse;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.FragmentOrderServiceBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.main.order.sub.OrderSubServicesActivity;
import com.wecare.android.ui.create_order.services.sub_duration.DurationSubServicesAdapter;
import com.wecare.android.ui.main.home.sub.details.SubDetailsFragment;
import com.wecare.android.ui.sub.ServicesNavigator;
import com.wecare.android.ui.sub.ServicesViewModel;
import com.wecare.android.utils.AppConstants;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class OrderServicesFragment extends BaseFragment<FragmentOrderServiceBinding, ServicesViewModel>
        implements ServicesNavigator, OrderServicesAdapter.ServiceAdapterListener, DurationSubServicesAdapter.SubServiceAdapterListener {

    public static final String TAG = SubDetailsFragment.class.getSimpleName();
    private MainServiceModel ServiceResponse;
    @Inject
    OrderServicesAdapter mServicesAdapter;

    @Inject
    DurationSubServicesAdapter durationSubServicesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    LinearLayoutManager subLayoutManager;

    @Inject
    ViewModelProviderFactory factory;
    private ServicesViewModel viewModel;


    private FragmentOrderServiceBinding binding;

    public static OrderServicesFragment newInstance(Bundle extras) {
        Bundle args = extras;
        if (args == null) {
            args = new Bundle();
        }
        OrderServicesFragment fragment = new OrderServicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        mServicesAdapter.setListener(this);
        durationSubServicesAdapter.setListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(AppConstants.ARGS_KEY_SERVICES)) {

                MainServiceModel serviceResponse = bundle.getParcelable(AppConstants.ARGS_KEY_SERVICES);
                Toast.makeText(getContext(),"MainServiceModel: "+serviceResponse,Toast.LENGTH_LONG).show();
                goToSubServices(serviceResponse);
            } else if (bundle.containsKey(AppConstants.KEY_ORDER_OBJECT)) {
                //summary view
                durationSubServicesAdapter.setSummaryView(true);
                //get order model
                OrderModel reOrderModel = bundle.getParcelable(AppConstants.KEY_ORDER_OBJECT);
                //prepare object to show in list
                if (reOrderModel != null && reOrderModel.getServices() != null)
                    if (!reOrderModel.getServices().isEmpty()) {

                        ArrayList<SubServiceResponse> selectedSubServiceResponseList = new ArrayList<>();
                        for (RequiredServiceModel serviceModel : reOrderModel.getServices()) {

//                            serviceModel.getMainService();
//                            serviceModel.getHours()
//                            serviceModel.getQuantity()
//                            serviceModel.getSubServiceType()
//                            serviceModel.getOrderId();
                            SubServiceResponse subServiceResponse = serviceModel.getSubService();
                            MainServiceModel mainServiceModel = serviceModel.getMainService();
                            //service_type : quantity = 1, hours = 2
                            if (subServiceResponse.getService_type().equals("2")) {
                                subServiceResponse.setHourlyDuration(serviceModel.getHours());
                            }

                            selectedSubServiceResponseList.add(subServiceResponse);
                            getViewModel().setSelectedServiceResponse(mainServiceModel);//main services
                        }
                        getViewModel().setSelectedSubServiceResponseList(selectedSubServiceResponseList);
                    }
            }
        }

    }

    @Override
    public void onValidationSuccess() {
//        if (isDropDownsValid()) {
//            viewModel.getDataManager()..setValue(WeCareUtils.getEditTextString(binding.edt));
        //your action here;
//        }
    }

    @Override
    public void onValidationError() {
        super.onValidationError();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        //validation
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();

        setUpServices();
        subscribeToLiveData();

        if (getSelectedSubServices() != null && !getSelectedSubServices().isEmpty()) {
            handleArgsForSubService(getSelectedSubServices());
        } else {
            //request
            getViewModel().fetchServices(String.valueOf(getViewModel().getDataManager().getCurrentUserModel().getCountryID()));
        }

    }

    @Override
    public ServicesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ServicesViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_service;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    public MainServiceModel  getServiceResponse(){
       return ServiceResponse;
    }
    public void setServiceResponse(MainServiceModel response){
        this.ServiceResponse=response;
    }
    @Override
    public void goToSubServices(MainServiceModel serviceResponse) {
        if (getActivity() != null && !((CreateOrderActivity) getActivity()).isReOrderFlow) {
            //set selected main services
            setServiceResponse(serviceResponse);
            getViewModel().setSelectedServiceResponse(serviceResponse);
            //navigate
            Intent intent = OrderSubServicesActivity.getStartIntent(getBaseActivity());
            intent.putExtra(AppConstants.ARGS_KEY_SERVICES, serviceResponse);
            intent.putExtra(AppConstants.ARGS_KEY_SUB_SERVICES_PICKING_TYPE, AppConstants.ARGS_KEY_SUB_SERVICES_CREATE);
            startActivityForResult(intent, AppConstants.REQ_CODE_ORDER_SUB_SERVICE_LIST);
        }
    }

    @Override
    public void statisticsFetched(StatisticResponse statisticResponse) {
        // not used
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.REQ_CODE_ORDER_SUB_SERVICE_LIST && data != null && data.hasExtra(AppConstants.ARGS_SELECTED_ORDER_SUB_SERVICE_LIST)) {
                handleArgsForSubService(data.getParcelableArrayListExtra(AppConstants.ARGS_SELECTED_ORDER_SUB_SERVICE_LIST));
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            onNoItemLeft();
        }
    }

    private void handleArgsForSubService(ArrayList<SubServiceResponse> selectedSubServiceResponseList) {
        getViewModel().setSelectedSubServiceResponseList(selectedSubServiceResponseList);
//                binding.durationSubServicesParentLinear.setVisibility(View.VISIBLE);

        //update services list to show only the selected one.
        ArrayList<MainServiceModel> filteredArrayList = new ArrayList<>();
        filteredArrayList.add(getViewModel().getSelectedServiceResponse());
        getViewModel().setServiceListLiveDataValue(filteredArrayList);

        //setup adapter
        setUpSubServicesDuration();
        getViewModel().isSubServicesSelected.set(Boolean.TRUE);
        durationSubServicesAdapter.addItems(getViewModel().getSelectedSubServiceResponseList());
    }

    private void setUpServices() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.serviceRecycler.setLayoutManager(mLayoutManager);
        binding.serviceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.serviceRecycler.setAdapter(mServicesAdapter);
    }

    private void setUpSubServicesDuration() {
        subLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.durationSubServiceRecycler.setLayoutManager(subLayoutManager);
        binding.durationSubServiceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.durationSubServiceRecycler.setAdapter(durationSubServicesAdapter);
    }

    private void subscribeToLiveData() {
        viewModel.getServiceListLiveData().observe(this, new Observer<List<MainServiceModel>>() {
            @Override
            public void onChanged(@Nullable List<MainServiceModel> serviceResponses) {
                viewModel.addServiceItemsToList(serviceResponses);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void updateServicesList(List<MainServiceModel> serviceResponseList) {
        mServicesAdapter.addItems(serviceResponseList);
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().setTitle(getString(R.string.services));
    }

    @Override
    public void onItemClicked(MainServiceModel serviceResponse) {
        if (getActivity() != null && !((CreateOrderActivity) getActivity()).isReOrderFlow) {
            goToSubServices(serviceResponse);
        }
    }

    @Override
    public void onItemClicked(SubServiceResponse subServiceResponse) {

    }

    @Override
    public void onNoItemLeft() {
        // be careful the method is being used.
        getViewModel().isSubServicesSelected.set(Boolean.FALSE);
        getViewModel().setServiceListLiveDataValue(new ArrayList<>());
        getViewModel().setSelectedServiceResponse(null);
        getViewModel().fetchServices(String.valueOf(getViewModel().getDataManager().getCurrentUserModel().getCountryID()) );
    }

    public ArrayList<SubServiceResponse> getSelectedSubServices() {
        return getViewModel().getSelectedSubServiceResponseList();
    }
}
