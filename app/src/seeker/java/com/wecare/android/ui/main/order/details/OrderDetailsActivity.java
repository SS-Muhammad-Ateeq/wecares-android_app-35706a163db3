package com.wecare.android.ui.main.order.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.RequiredServiceModel;
import com.wecare.android.databinding.OrderDetailsActivityBinding;
import com.wecare.android.payment.IPaymentRequestCallBack;
import com.wecare.android.payment.PayFortData;
import com.wecare.android.payment.PayFortPayment;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.main.rating.RatingMainActivity;
import com.wecare.android.ui.order_info.InformationAttachmentAdapter;
import com.wecare.android.ui.profile.CaregiverShowProfileActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.CommonUtils;
import com.wecare.android.utils.DateUtils;
import com.wecare.android.utils.DialogFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailsActivity extends BaseActivity<OrderDetailsActivityBinding, OrderDetailsViewModel> implements OrderDetailsNavigator, IPaymentRequestCallBack {


    @Inject
    ViewModelProviderFactory factory;
    OrderDetailsViewModel viewModel;

    OrderDetailsActivityBinding binding;

    OrderModel orderModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    InformationAttachmentAdapter attachmentAdapter;

    int precision = 100;

    @Override
    public OrderDetailsViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(OrderDetailsViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.order_details_activity;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        addToolbar(R.id.toolbar, getString(R.string.order_details), true);
        viewModel.setNavigator(this);
        attachmentAdapter.setSummaryView(true);
        setUpAttachmentAdapter();
        initPayFortSDK();


        if (getIntent().hasExtra(AppConstants.KEY_ORDER_OBJECT)){
            orderModel = getIntent().getParcelableExtra(AppConstants.KEY_ORDER_OBJECT);
            setUpData();
        }
        else
        {
          viewModel.getOrderDetails(getIntent().getStringExtra(AppConstants.ARGS_KEY_ORDER_ID));
        }

    }

    private void requestForPayfortPayment() {
        PayFortData payFortData = new PayFortData();

        payFortData.amount = String.valueOf((int) (Double.parseDouble(orderModel.getActualTotalAmount()) * precision));// Multiplying with 100, bcz amount should not be in decimal format
        payFortData.command = PayFortPayment.PURCHASE;
        payFortData.currency = PayFortPayment.CURRENCY_TYPE;
        payFortData.customerEmail = viewModel.getDataManager().getCurrentUserEmail();
        payFortData.language = PayFortPayment.LANGUAGE_TYPE;
        payFortData.merchantReference = String.valueOf(orderModel.getId());

        PayFortPayment payFortPayment = new PayFortPayment(OrderDetailsActivity.this, this.fortCallback, this);
        payFortPayment.requestForPayment(payFortData);

    }

    private void setUpAttachmentAdapter() {
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.attachmentRecycler.setLayoutManager(mLayoutManager);
        binding.attachmentRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.attachmentRecycler.setAdapter(attachmentAdapter);
    }

    private void setUpData() {
        if (orderModel == null)
            return;

        if (orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_COMPLETED) {
            binding.acceptBtn.setText(getString(R.string.pay));
            if (orderModel.getPaymentStatus().getId().equals(AppConstants.PAYMENT_STATUS_PAID+"")){
               showStartRatingDialog();
                binding.cancelBtn.setText(getString(R.string.rate));
            }
            else {
                binding.cancelBtn.setVisibility(View.GONE);
            }
        } else if (orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_FULFILLED) {
            binding.cancelBtn.setVisibility(View.GONE);
            binding.acceptBtn.setText(getString(R.string.reorder));
        } else {
            binding.cancelBtn.setText(getResources().getString(R.string.cancel_order));
            binding.acceptBtn.setText(getString(R.string.create_same_order));
        }

        binding.giverNameTxt.setText(String.format("%s %s", orderModel.getCaregiver().getFirstName(), orderModel.getCaregiver().getLastName()));
        viewModel.setOrderUserImg(orderModel.getCaregiver().getAvatar());
        viewModel.isFavoriteGiver(orderModel.getCaregiver().getIsFavorite() == AppConstants.PHP_TRUE_RAW);
        viewModel.setGiverRating((float) orderModel.getCaregiver().getRating());
        binding.dateTV.setText(orderModel.getCreatedAt());
        binding.orderStatusTxt.setText(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName());
 /*       if ((String.valueOf(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName())).equals("Pending")){
            binding.orderStatusTxt.setBackgroundColor(Color.rgb(255,127,80));
        }
        else if ((String.valueOf(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName())).equals("Rejected")){
            binding.orderStatusTxt.setBackgroundColor(Color.rgb(255,0,0));
        }
        else if ((String.valueOf(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName())).equals("Caring")){

            binding.orderStatusTxt.setBackgroundColor(Color.rgb(0,0,139));

        }
        else if ((String.valueOf(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName())).equals("Canceled")){
            binding.orderStatusTxt.setBackgroundColor(Color.rgb(0,0,139));
        }
        else if ((String.valueOf(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName())).equals("Fulfilled")){
            binding.orderStatusTxt.setBackgroundColor(Color.rgb(34,139,34));
        }else if ((String.valueOf(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName())).equals("Accepted")){
            binding.orderStatusTxt.setBackgroundColor(Color.rgb(173,216,230));
        }*/
        binding.orderTotalTxt.setText(orderModel.getEstimatedTotalAmount());
        if (orderModel.getServices().size() > 0)
            binding.orderDescriptionTxt.setText(orderModel.getServices().get(0).getSubService().getServiceName());


        //location information
        if (orderModel.getLocation() == null) {
            binding.locationParentLayout.setVisibility(View.GONE);
        }

        else {
            binding.countryTV.setText(orderModel.getLocation().getCountry().getTitle());
            binding.cityTV.setText(orderModel.getLocation().getCity().getTitle());
            binding.streetNameTV.setText(orderModel.getLocation().getStreet_name());
            binding.buildingNumberTV.setText(String.format("%s", orderModel.getLocation().getBuilding_number()));
            binding.floorNumberTV.setText(String.format("%s", orderModel.getLocation().getFloor_number()));
        }

        if (orderModel.getCountry() != null && orderModel.getCountry().getIso2() != null) {
            //number of decimals to multiple amount for payfort request
            precision = orderModel.getCountry().getIso2().equals(PayFortPayment.JORDAN_ISO) ? 1000 : 100;
        }
        //relative information



        //payment method
        binding.paymentMethodTV.setText(CommonUtils.getLookUpDescById(viewModel.getDataManager().getLookupsModel().getPaymentMethodTypeArrayList(), orderModel.getPaymentMethod() + ""));

        //schedule
        binding.dayOfServiceTV.setText(String.format("%s %s", DateUtils.getDayNameByDate(orderModel.getDate()), orderModel.getDate()));
        binding.timeOfServiceTV.setText(orderModel.getStartTime());

        //relative information
        if (orderModel.getProfile() != null && orderModel.getId() != null) {
            binding.relativeAgeTxt.setText(orderModel.getProfile().getAge());
            binding.relativeGenderTxt.setText(orderModel.getProfile().getGender().getTitle());
            binding.relativeMobileTxt.setText(orderModel.getProfile().getMobile_number());
            binding.relativeNameTxt.setText(orderModel.getProfile().getName());

            if (orderModel.getImages() != null && orderModel.getImages().size()>0) {
                getViewModel().setSelectedInformationAttachmentList(new ArrayList<>(orderModel.getImages()));
                attachmentAdapter.addItems(new ArrayList<>(orderModel.getImages()));
            }
            else {
                binding.imageSeperator.setVisibility(View.GONE);
                binding.attachmentRecycler.setVisibility(View.GONE);
            }

        } else {
            binding.relativeParentLayout.setVisibility(View.GONE);
        }

        if (orderModel.getNeedMaterials() != null)
            binding.needSomeMaterialsTV.setText(orderModel.getNeedMaterials() == AppConstants.PHP_TRUE_RAW ? getString(R.string.yes) : getString(R.string.no));


        if (orderModel.getServices().size() > 0) {
            addNeededServiceViews();
            binding.totalServicesAmountTV.setText(orderModel.getEstimatedTotalAmount());
        } else {
            binding.servicesLayoutHeader.setVisibility(View.GONE);
            binding.totalServicesLayout.setVisibility(View.GONE);
        }

    }


    private void initPayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayFortPayment.RESPONSE_PURCHASE) {
            fortCallback.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void showGiverProfile() {
        startActivity(CaregiverShowProfileActivity.newIntent(this).putExtra(AppConstants.ARGS_KEY_CAREGIVER_ID, orderModel.getCaregiver().getId() + ""));
    }

    @Override
    public void positiveButtonClicked() {

        if (orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_COMPLETED) {
            requestForPayfortPayment();
        } else {
            openCreateOrderActivity();

        }

    }

    @Override
    public void negativeButtonClicked() {
        cancelClicked();

        if (orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_COMPLETED && orderModel.getPaymentStatus().getId().equals(AppConstants.PAYMENT_STATUS_PAID+"")) {
            showStartRatingDialog();
        } else {
         cancelClicked();
        }
    }

    private void cancelClicked() {

        DialogFactory.createRadioGroupPicker(this, getString(R.string.cancel_order), getString(R.string.cancel_order_disclaimer), getString(R.string.yes), new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (which == -1) {
                    showToast(getString(R.string.error_select_reason));
                    return false;
                }

                if (which == viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons().size() - 1) {
                    DialogFactory.createInputDialog(OrderDetailsActivity.this, getString(R.string.cancel_order), getString(R.string.reject_order_other_disclaimer), getString(R.string.your_reason), getString(R.string.send),
                            new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons().get(which).getId());
                                    viewModel.cancelOrder(orderModel.getId() + "", reasonID, input.toString());
                                }
                            });
                } else {
                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons().get(which).getId());
                    viewModel.cancelOrder(orderModel.getId() + "", reasonID, "");
                }
                return false;
            }
        }, new ArrayList<LookUpModel>(viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons()));

    }


    @Override
    public void orderCanceledSuccessFully() {
        DialogFactory.createFeedBackDialog(this, getString(R.string.order_canceled_message), getString(R.string.we_hope_you_doing_well),
                getString(R.string.ok), getResources().getDrawable(R.drawable.ic_success_big), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        finish();
                    }
                });
    }

    @Override
    public void orderDetailsFetchedSuccessfully(OrderModel orderModel) {
        this.orderModel = orderModel;
        setUpData();
    }


    private void addNeededServiceViews() {
        for (int i = 0; i < orderModel.getServices().size(); i++) {
            inflateNeededServiceView(orderModel.getServices().get(i), i);
        }
    }

    private void openFinishOrderActivity(String orderReport) {
    }


    private void inflateNeededServiceView(RequiredServiceModel item, int position) {
        LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        View view = inflater.inflate(R.layout.layout_needed_service, null);

        AppCompatTextView serviceNameTV = (AppCompatTextView) view.findViewById(R.id.serviceNameTV);
        AppCompatTextView priceTV = (AppCompatTextView) view.findViewById(R.id.priceTV);
        AppCompatTextView durationTV = (AppCompatTextView) view.findViewById(R.id.durationTV);
        AppCompatTextView totalTV = (AppCompatTextView) view.findViewById(R.id.totalTV);

        serviceNameTV.setText(item.getSubService().getServiceName());
        priceTV.setText(getString(R.string.price) + " " + item.getPricePerHour());
        durationTV.setText(item.getHours() != null ? item.getHours() + "" : getString(R.string.one_time));
        totalTV.setText(item.getTotalAmount());


        binding.servicesLayout.addView(view, (position), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void openCreateOrderActivity() {
        Intent intent = CreateOrderActivity.getStartIntent(mContext);
        intent.putExtra(AppConstants.KEY_ORDER_OBJECT, orderModel);
        startActivity(intent);
    }


    @Override
    public void onPaymentRequestResponse(int responseType, PayFortData responseData) {
        if (responseType == PayFortPayment.RESPONSE_GET_TOKEN) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Token not generated");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_CANCEL) {
            Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment cancelled");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_FAILURE) {
            Toast.makeText(this, responseData.responseMessage, Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment failed");
        } else {
            showPaymentCompleteDialog();
        }
    }

    private void showPaymentCompleteDialog() {
        DialogFactory.createSuccessDialog(this, "", getString(R.string.payment_completed_successfully), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                     showStartRatingDialog();
            }


        });
    }
    private void showStartRatingDialog(){
    DialogFactory.createReactDialog(this, getString(R.string.order_completed), getString(R.string.order_completed_disclaimer), getString(R.string.rate_giver), getString(R.string.not_now), null, new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            startActivity(RatingMainActivity.newIntent(OrderDetailsActivity.this).putExtra(AppConstants.ARGS_KEY_ORDER_ID, orderModel.getId() + ""));
            finish();
        }
    },null);
    }
}
