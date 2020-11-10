package com.wecare.android.ui.main.profile.userProfile.paymentmethod;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.databinding.ActivityPaymentMethodBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class PaymentMethodActivity extends BaseActivity<ActivityPaymentMethodBinding,PaymentMethodViewModel> implements PaymentMethodNavigator {


    @Inject
    ViewModelProviderFactory factory;

    ActivityPaymentMethodBinding binding;
    private PaymentMethodViewModel viewModel;

    private MenuItem menuItem;

    boolean isCreditPayment = true;



    @Override
    public PaymentMethodViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(PaymentMethodViewModel.class);
        return viewModel;    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_method;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        addToolbar(R.id.toolbar, getString(R.string.payment_method), true);
        setUpData();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, PaymentMethodActivity.class);
    }

    private void setUpData() {
        getViewModel().enableFieldsEditMode.set(false);

        isCreditPayment = Integer.parseInt(viewModel.getDataManager().getCurrentUserModel().getPaymentMethod().getId()) == AppConstants.PAYMENT_METHOD_CREDIT;
        if (isCreditPayment)
            onCreditClicked();
        else
            onCashClicked();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        menuItem = menu.findItem(R.id.action_save);

        updateMenuItemTitle();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (!getViewModel().enableFieldsEditMode.get()) {
                    getViewModel().enableFieldsEditMode.set(true);
                    menuItem.setTitle(R.string.general_save);
                    return true;
                } else {
                    viewModel.updatePaymentMethod(isCreditPayment ? AppConstants.PAYMENT_METHOD_CREDIT : AppConstants.PAYMENT_METHOD_CASH);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void paymentMethodUpdatedSuccessfully(int method) {
        getViewModel().enableFieldsEditMode.set(false);
        updateMenuItemTitle();
        viewModel.getDataManager().getCurrentUserModel().getPaymentMethod().setId(method+"");
        DialogFactory.createFeedBackDialog(this, getString(R.string.saved_successfully), getString(R.string.payment_update_success), getString(R.string.ok), getResources().getDrawable(R.drawable.success_img), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                finish();
            }
        });

    }

    @Override
    public void onCashClicked() {
        isCreditPayment = false;
        binding.creditCardTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_un_checked_gray), null, null, null);
        binding.cashTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_check_green), null, null, null);
    }

    @Override
    public void onCreditClicked() {
        isCreditPayment = true;
        binding.creditCardTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_check_green), null, null, null);
        binding.cashTV.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_un_checked_gray), null, null, null);

    }

    private void updateMenuItemTitle(){
        if (!getViewModel().isEditMode) {
            menuItem.setTitle(R.string.edit);
        }
        else {
            menuItem.setTitle(R.string.general_save);

        }
    }

}
