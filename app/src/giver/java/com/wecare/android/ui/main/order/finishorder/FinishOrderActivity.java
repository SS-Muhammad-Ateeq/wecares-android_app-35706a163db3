package com.wecare.android.ui.main.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.RequiredServiceModel;
import com.wecare.android.data.model.api.requests.SubServiceRequest;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ActivityFinishOrderBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.main.order.finishorder.AddedSubServicesAdapter;
import com.wecare.android.ui.main.profile.userProfile.services.UserServicesActivity;
import com.wecare.android.ui.main.rating.RatingMainActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FinishOrderActivity extends BaseActivity<ActivityFinishOrderBinding, com.wecare.android.ui.main.order.FinishOrderViewModel> implements com.wecare.android.ui.main.order.FinishOrderActivityNavigator {

    OrderModel orderModel;

    ActivityFinishOrderBinding binding;

    ArrayList<SubServiceResponse> subServiceResponseArrayList = new ArrayList<>();

    boolean isCreditPayment = true;

    double totalAmount;



    @Inject
    ViewModelProviderFactory factory;
    private com.wecare.android.ui.main.order.FinishOrderViewModel viewModel;


    @Inject
    AddedSubServicesAdapter addedSubServicesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    LinearLayoutManager subLayoutManager;

    @Override
    public com.wecare.android.ui.main.order.FinishOrderViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(com.wecare.android.ui.main.order.FinishOrderViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_finish_order;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FinishOrderActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        addToolbar(R.id.toolbar, getString(R.string.finish_order), true);
        orderModel = getIntent().getParcelableExtra(AppConstants.KEY_ORDER_OBJECT);
        binding = getViewDataBinding();
        getSubServicesFromOrderModel();
        //setup adapter
        setUpSubServicesAdapter();
        addedSubServicesAdapter.addItems(subServiceResponseArrayList);
        addedSubServicesAdapter.setCurrency(orderModel.getCountry().getCurrencyCode());
        setUpData();
    }

    private void setUpData() {
//        binding.startTimeTV.setText(orderModel.getStartTime());
//        binding.finishTimeTV.setText(orderModel.getEndTime());
        binding.totalServicesAmountTV.setText(orderModel.getEstimatedTotalAmount());
        totalAmount = Double.parseDouble(orderModel.getEstimatedTotalAmount());
        binding.dateTV.setText(orderModel.getDate());
        isCreditPayment = orderModel.getPaymentMethod() == AppConstants.PAYMENT_METHOD_CREDIT;
        if (isCreditPayment)
            onCreditClicked();
        else
            onCashClicked();
    }


    @Override
    public void addNewServiceClicked() {
        startActivityForResult(UserServicesActivity.newIntent(this).putExtra(AppConstants.ARGS_KEY_SERVICES_PICKING_TYPE, AppConstants.ARGS_KEY_ORDER_SERVICES).putExtra(AppConstants.ARGS_KEY_ORDER_ID, orderModel.getId() + ""), AppConstants.REQ_CODE_ORDER_SUB_SERVICE_LIST);
    }

    @Override
    public void onConfirmClicked() {
        finishOrder(true);
    }

    private void finishOrder(boolean didReceive) {
        //incase giver did receive his paid he mjust enter the amount he did received
        if (isValidToFinishOrder(didReceive)) {
            viewModel.getFinishOrderRequest().setOrderId(orderModel.getId());
            viewModel.getFinishOrderRequest().setPaymentMethod(isCreditPayment ? AppConstants.PAYMENT_METHOD_CREDIT : AppConstants.PAYMENT_METHOD_CASH);
            viewModel.getFinishOrderRequest().setServices(prepareSubServicesRequest(subServiceResponseArrayList));
            viewModel.getFinishOrderRequest().setActualTotalAmount(totalAmount);
            viewModel.getFinishOrderRequest().setDetailsOfCareGiven(getIntent().getStringExtra(AppConstants.KEY_ORDER_REPORT));
            if (!isCreditPayment && didReceive)
                viewModel.getFinishOrderRequest().setPaidAmount(Double.parseDouble(binding.amountEdt.getText().toString()));

            viewModel.finishOrder();
//            finishOrderSubmittedSuccessfully(orderModel);
        }

    }

    private ArrayList<SubServiceRequest> prepareSubServicesRequest(ArrayList<SubServiceResponse> subServiceResponses) {
        ArrayList<SubServiceRequest> subServiceRequests = new ArrayList<>();
        for (SubServiceResponse subServiceResponse : subServiceResponses) {
            SubServiceRequest subServiceRequest = new SubServiceRequest();
            subServiceRequest.setId(subServiceResponse.getId());
            subServiceRequest.setCaregiverServiceId(subServiceResponse.getCaregiverServiceID());
            subServiceRequest.setDeleted(false);
            subServiceRequest.setSubServiceType(subServiceResponse.getSub_service_type());
            if (subServiceResponse.getSub_service_type() == AppConstants.SERVICE_TYPE_QUANTITY)
                subServiceRequest.setQuantity(subServiceResponse.getQuantity());
            else
                subServiceRequest.setHours(subServiceResponse.getHours());
            subServiceRequests.add(subServiceRequest);
        }
        return subServiceRequests;
    }

    private boolean isValidToFinishOrder(boolean didReceiveMoney) {

        if (subServiceResponseArrayList.size() == 0) {
            DialogFactory.createErrorDialog(this, getString(R.string.error), getString(R.string.service_error));
            return false;
        }

        if (!isCreditPayment && didReceiveMoney && (binding.amountEdt.getText().toString().isEmpty() || binding.amountEdt.getText().toString().equals(""))) {
            binding.amountEdt.setError(getString(R.string.general_required_field));
            return false;
        }
        binding.amountEdt.setError(null);


        return true;
    }


    @Override
    public void onCashClicked() {
        isCreditPayment = false;
        binding.receivedAmountLayout.setVisibility(View.VISIBLE);
        binding.changeAmountLayout.setVisibility(View.VISIBLE);
        binding.creditCardTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_un_checked_gray), null, null, null);
        binding.cashTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_check_green), null, null, null);
        binding.cancelBtn.setVisibility(View.VISIBLE);
        binding.acceptBtn.setText(getString(R.string.yes_i_received));
        binding.currencyTv.setText(orderModel.getCountry().getCurrencyCode());
        binding.amountEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s!=null && !s.toString().equals("") && !s.toString().equals("."))
                binding.changeTv.setText(String.format("%s %s", calculateChange(Double.parseDouble(s + "")), orderModel.getCountry().getCurrencyCode()));

                else
                    binding.changeTv.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String calculateChange(double receivedAmount) {
        double change = 0;
        change = totalAmount - receivedAmount;
        return String.format("%.2f", change);
    }

    @Override
    public void onCreditClicked() {
        isCreditPayment = true;
        binding.receivedAmountLayout.setVisibility(View.GONE);
        binding.changeAmountLayout.setVisibility(View.GONE);
        binding.creditCardTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_check_green), null, null, null);
        binding.cashTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_un_checked_gray), null, null, null);
        binding.cancelBtn.setVisibility(View.GONE);
        binding.acceptBtn.setText(getString(R.string.confirm));

    }


    @Override
    public void finishOrderSubmittedSuccessfully(OrderModel orderModel) {
        DialogFactory.createFeedBackDialog(this, getString(R.string.great_job) + " " + orderModel.getCaregiver().getFirstName(), getString(R.string.job_finished_successfully),
                getString(R.string.ok), getResources().getDrawable(R.drawable.ic_success_big), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        startActivity(RatingMainActivity.newIntent(FinishOrderActivity.this).putExtra(AppConstants.ARGS_KEY_ORDER_ID, orderModel.getId() + ""));
                        finish();
                    }
                }, false);
    }

    @Override
    public void onNegativeClicked() {
        finishOrder(false);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.REQ_CODE_ORDER_SUB_SERVICE_LIST && data != null && data.hasExtra(AppConstants.ARGS_SELECTED_ORDER_SUB_SERVICE_LIST)) {
                ArrayList<SubServiceResponse> addedSubServiceResponseArrayList = removeAlreadySelectedService(data.getParcelableArrayListExtra(AppConstants.ARGS_SELECTED_ORDER_SUB_SERVICE_LIST));

                subServiceResponseArrayList.addAll(addedSubServiceResponseArrayList);
                //add new services prices to total amount
                addNewServicesPricesTotal(addedSubServiceResponseArrayList);

                addedSubServicesAdapter.addItems(subServiceResponseArrayList);
            }
        }

    }

    private void addNewServicesPricesTotal(ArrayList<SubServiceResponse> addedSubServiceResponseArrayList) {
        double addedPrice = 0;
        for (SubServiceResponse subServiceResponse : addedSubServiceResponseArrayList) {
            addedPrice = addedPrice + Double.parseDouble(subServiceResponse.getPrice());
        }
        totalAmount = totalAmount + addedPrice;
        binding.totalServicesAmountTV.setText(totalAmount + "");
    }

    private void getSubServicesFromOrderModel() {
        for (RequiredServiceModel serviceModel : orderModel.getServices()) {
            serviceModel.getSubService().setPrice(serviceModel.getPricePerHour());
            serviceModel.getSubService().setCaregiverServiceID(serviceModel.getCaregiverServiceId());
            serviceModel.getSubService().setSub_service_type(serviceModel.getSubServiceType());
            if (serviceModel.getSubServiceType() == AppConstants.SERVICE_TYPE_HOURS)
                serviceModel.getSubService().setHours(serviceModel.getHours());
            else
                serviceModel.getSubService().setQuantity(serviceModel.getQuantity());
            subServiceResponseArrayList.add(serviceModel.getSubService());
        }
    }

    //spit and paste solution can be revamped later to remove already selected service
    private ArrayList<SubServiceResponse> removeAlreadySelectedService(ArrayList<SubServiceResponse> addedSubServiceResponses) {

        for (SubServiceResponse subServiceResponse : subServiceResponseArrayList) {
            for (int i = 0; i < addedSubServiceResponses.size(); i++) {
                if (subServiceResponse.getId() == addedSubServiceResponses.get(i).getId())
                    addedSubServiceResponses.remove(i);
            }
        }

        return addedSubServiceResponses;
    }

    private void setUpSubServicesAdapter() {
        subLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.durationSubServiceRecycler.setLayoutManager(subLayoutManager);
        binding.durationSubServiceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.durationSubServiceRecycler.setAdapter(addedSubServicesAdapter);
        addedSubServicesAdapter.setListener(new AddedSubServicesAdapter.SubServiceAdapterListener() {
            @Override
            public void onItemClicked(SubServiceResponse subServiceResponse) {

            }

            @Override
            public void onNoItemLeft() {

            }

            @Override
            public void onPriceChanged(int position, double newValue, boolean isAdd) {

                if (isAdd) {
                    //if the user clicks on add the remove the old price and add the new to the total
                    totalAmount = totalAmount - subServiceResponseArrayList.get(position).getCalculatedPrice();
                    totalAmount = totalAmount + newValue;
                } else {
                    //if the user clicks reduces the hours for this service then reduce the total by hour price
                    totalAmount = totalAmount - Double.parseDouble(subServiceResponseArrayList.get(position).getPrice());

                }

                binding.totalServicesAmountTV.setText(totalAmount + "");

            }
        });
    }
}
