package com.wecare.android.ui.main.order.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.data.model.api.models.RequiredServiceModel;
import com.wecare.android.databinding.ActivityOrderDetailsBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.main.order.FinishOrderActivity;
import com.wecare.android.ui.order_info.InformationAttachmentAdapter;
import com.wecare.android.ui.profile.UserShowProfileActivity;
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

public class OrderDetailsActivity extends BaseActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel> implements OrderDetailsNavigator {


    @Inject
    ViewModelProviderFactory factory;
    OrderDetailsViewModel viewModel;

    ActivityOrderDetailsBinding binding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    InformationAttachmentAdapter attachmentAdapter;

    OrderModel orderModel;


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
        return R.layout.activity_order_details;
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

        if (getIntent().hasExtra(AppConstants.KEY_ORDER_OBJECT)) {
            orderModel = getIntent().getParcelableExtra(AppConstants.KEY_ORDER_OBJECT);
            setUpData();
        } else {
            viewModel.getOrderDetails(getIntent().getStringExtra(AppConstants.ARGS_KEY_ORDER_ID));
        }

    }

    private void setUpData() {
        if (orderModel == null)
            return;

        switch (orderModel.getOrderStatusModel().getId()) {
            case AppConstants.ORDER_STATUS_PENDING:
                binding.cancelBtn.setText(getResources().getString(R.string.reject_order));
                binding.acceptBtn.setText(getResources().getString(R.string.accept));
                binding.orderStatusTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_button_orange));

                break;
            case AppConstants.ORDER_STATUS_ACCEPTED:
                binding.acceptBtn.setText(getResources().getString(R.string.start));
                binding.cancelBtn.setText(getResources().getString(R.string.cancel_order));
                binding.orderStatusTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_button_green));

                break;
            case AppConstants.ORDER_STATUS_CARING:
                binding.acceptBtn.setText(getResources().getString(R.string.finish_order));
                binding.cancelBtn.setText(getResources().getString(R.string.cancel_order));
                binding.orderStatusTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_button_green));
                break;
            case AppConstants.ORDER_STATUS_COMPLETED:

                break;

            case AppConstants.ORDER_STATUS_CANCELED:
                binding.orderStatusTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_button_red));
                binding.cancelBtn.setVisibility(View.GONE);
                binding.acceptBtn.setVisibility(View.GONE);
                break;
            case AppConstants.ORDER_STATUS_SEEKER_CANCELED:
                binding.orderStatusTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_button_red));

                break;
            case AppConstants.ORDER_STATUS_FULFILLED:
                binding.bottomLayout.setVisibility(View.GONE);
                binding.orderStatusTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_button_coloraccent));

                break;
            case AppConstants.ORDER_STATUS_COMPLETED_UNPAID:

                break;
        }


        binding.seekerNameTxt.setText(String.format("%s %s", orderModel.getCareseeker().getFirstName(), orderModel.getCareseeker().getLastName()));
        viewModel.setOrderUserImg(orderModel.getCareseeker().getAvatar());
        binding.genderTxt.setText(orderModel.getCaregiver().getGender().getTitle());
        binding.dateTV.setText(orderModel.getCreatedAt());
        binding.orderStatusTxt.setText(orderModel.getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING ? getString(R.string.active_order) : orderModel.getOrderStatusModel().getEnglishName());
        binding.orderTotalTxt.setText(String.format("%s %s", orderModel.getEstimatedTotalAmount(), orderModel.getCountry().getCurrencyCode()));
        binding.ageTxt.setText(String.format("%s %s", orderModel.getCaregiverAge(), getString(R.string.years)));


        //location information
        if (orderModel.getLocation() == null) {
            binding.locationParentLayout.setVisibility(View.GONE);
        }

        //relative information

        else {
            binding.countryTV.setText(orderModel.getLocation().getCountry().getTitle());
            binding.cityTV.setText(orderModel.getLocation().getCity().getTitle());
            binding.streetNameTV.setText(orderModel.getLocation().getStreet_name());
            binding.buildingNumberTV.setText(orderModel.getLocation().getBuilding_number());
            binding.floorNumberTV.setText(orderModel.getLocation().getFloor_number());
        }

        //payment method
        if (viewModel.getDataManager().getLookupsModel().getPaymentMethodTypeArrayList()!=null && orderModel.getPaymentMethod()!=null)
        binding.paymentMethodTV.setText(CommonUtils.getLookUpDescById(viewModel.getDataManager().getLookupsModel().getPaymentMethodTypeArrayList(), orderModel.getPaymentMethod() + ""));

        //schedule
        binding.dayOfServiceTV.setText(String.format("%s %s", DateUtils.getDayNameByDate(orderModel.getDate()), orderModel.getDate()));
        binding.timeOfServiceTV.setText(orderModel.getStartTime());

        //relative information
        if (orderModel.getProfile() != null) {
            binding.relativeAgeTxt.setText(orderModel.getProfile().getAge());
            binding.relativeGenderTxt.setText(orderModel.getProfile().getGender().getTitle());
            binding.relativeMobileTxt.setText(orderModel.getPhoneNumber());
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

        if (orderModel.getNeedMaterials()!=null)
        binding.needSomeMaterialsTV.setText(orderModel.getNeedMaterials() == AppConstants.PHP_TRUE_RAW ? getString(R.string.yes) : getString(R.string.no));
        else
            binding.needSomeMaterialsTV.setText(getString(R.string.no));


        if (orderModel.getServices().size() > 0) {
            addNeededServiceViews();
            binding.totalServicesAmountTV.setText(String.format("%s %s", orderModel.getEstimatedTotalAmount(), orderModel.getCountry().getCurrencyCode()));
        } else {
            binding.servicesLayoutHeader.setVisibility(View.GONE);
            binding.totalServicesLayout.setVisibility(View.GONE);
        }

    }

    private void setUpAttachmentAdapter() {
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.attachmentRecycler.setLayoutManager(mLayoutManager);
        binding.attachmentRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.attachmentRecycler.setAdapter(attachmentAdapter);
    }

    @Override
    public void callSeekerClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + orderModel.getPhoneNumber()));
        startActivity(intent);
    }

    @Override
    public void showSeekerProfile() {
        startActivity(UserShowProfileActivity.newIntent(this).putExtra(AppConstants.KEY_USER_OBJECT, orderModel.getCareseeker()).putExtra(AppConstants.KEY_USER_TYPE, AppConstants.KEY_USER_CARE_SEEKER));
    }

    @Override
    public void positiveButtonClicked() {

        switch (orderModel.getOrderStatusModel().getId()) {
            case AppConstants.ORDER_STATUS_PENDING:
                viewModel.changeOrderStatus(orderModel.getId() + "", AppConstants.ORDER_STATUS_ACCEPTED);
                break;
            case AppConstants.ORDER_STATUS_ACCEPTED:
                viewModel.changeOrderStatus(orderModel.getId() + "", AppConstants.ORDER_STATUS_CARING);
                break;
            case AppConstants.ORDER_STATUS_CARING:
                DialogFactory.createInputDialog(this, getString(R.string.order_report), getString(R.string.order_report_disclaimer), "",
                        getString(R.string.save_finish_order), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                openFinishOrderActivity(input.toString());
                            }
                        });
                break;
            case AppConstants.ORDER_STATUS_COMPLETED:

                break;
            case AppConstants.ORDER_STATUS_FULFILLED:

                break;
            case AppConstants.ORDER_STATUS_COMPLETED_UNPAID:

                break;
        }

    }

    @Override
    public void negativeButtonClicked() {

        switch (orderModel.getOrderStatusModel().getId()) {
            case AppConstants.ORDER_STATUS_PENDING:
                rejectClicked();
                break;
            default:
                cancelClicked();
                break;
        }

    }

    private void rejectClicked() {
        DialogFactory.createRadioGroupPicker(this, getString(R.string.reject_order), getString(R.string.reject_order_disclaimer), getString(R.string.yes), new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (which == -1) {
                  showToast(getString(R.string.error_select_reason));
                    return false;
                }

                if (which == viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons().size() - 1) {
                    DialogFactory.createInputDialog(OrderDetailsActivity.this, getString(R.string.reject_order), getString(R.string.reject_order_other_disclaimer), getString(R.string.your_reason), getString(R.string.send),
                            new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons().get(which).getId());
                                    viewModel.rejectOrder(orderModel.getId() + "", reasonID, input.toString());
                                }
                            });
                } else {
                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons().get(which).getId());
                    viewModel.rejectOrder(orderModel.getId() + "", reasonID, "");

                }
                return false;
            }
        }, new ArrayList<LookUpModel>(viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons()));

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
    public void orderRejectedSuccessFully() {
        DialogFactory.createFeedBackDialog(this, getString(R.string.order_rejected_message), getString(R.string.we_hope_you_doing_well),
                getString(R.string.ok), getResources().getDrawable(R.drawable.ic_success_big), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        finish();
                    }
                });
    }

    @Override
    public void orderCanceledSuccessFully() {
        DialogFactory.createFeedBackDialog(this, getString(R.string.order_canceled_message), getString(R.string.we_hope_you_doing_well),
                getString(R.string.ok), getResources().getDrawable(R.drawable.ic_success_big), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                    }
                });
    }

    @Override
    public void orderStatusChangedSuccessFully(OrderModel Model, int status) {
        if (status == AppConstants.ORDER_STATUS_ACCEPTED) {
            finish();
        } else if (status == AppConstants.ORDER_STATUS_CARING) {
            finish();
        }
    }

    @Override
    public void orderDetailsFetchedSuccessfully(OrderModel orderModel) {
        this.orderModel = orderModel;
        setUpData();
    }

    @Override
    public void getDirectionsClicked() {
        if (orderModel.getLocation()!=null){
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr="+orderModel.getLocation().getLatitude()+","+orderModel.getLocation().getLongitude()));
            startActivity(intent);
        }
        else
        {
            showToast(getString(R.string.location_not_available));
        }

    }

    private void addNeededServiceViews() {
        for (int i = 0; i < orderModel.getServices().size(); i++) {
            inflateNeededServiceView(orderModel.getServices().get(i), i);
        }
    }

    private void openFinishOrderActivity(String orderReport) {
        startActivity(FinishOrderActivity.newIntent(this).putExtra(AppConstants.KEY_ORDER_OBJECT, orderModel).putExtra(AppConstants.KEY_ORDER_REPORT, orderReport));
    }


    private void inflateNeededServiceView(RequiredServiceModel item, int position) {
        LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
        View view = inflater.inflate(R.layout.layout_needed_service, null);

        AppCompatTextView serviceNameTV = (AppCompatTextView) view.findViewById(R.id.serviceNameTV);
        AppCompatTextView priceTV = (AppCompatTextView) view.findViewById(R.id.priceTV);
        AppCompatTextView durationTV = (AppCompatTextView) view.findViewById(R.id.durationTV);
        AppCompatTextView totalTV = (AppCompatTextView) view.findViewById(R.id.totalTV);

        serviceNameTV.setText(item.getSubService().getServiceName());
        priceTV.setText(String.format("%s %s %s", getString(R.string.price), item.getPricePerHour(), orderModel.getCountry().getCurrencyCode()));
        durationTV.setText(item.getHours() != null ? item.getHours()  + " " +  getString(R.string.hours) : getString(R.string.one_time));
        totalTV.setText(String.format("%s %s", item.getTotalAmount(), orderModel.getCountry().getCurrencyCode()));


        binding.servicesLayout.addView(view, (position), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


}
