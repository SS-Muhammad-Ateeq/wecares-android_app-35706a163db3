
package com.wecare.android.ui.create_order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.badoualy.stepperindicator.StepperIndicator;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.ActivityCreateOrderBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.base.BaseViewPagerAdapter;
import com.wecare.android.ui.create_order.done.DoneFragment;
import com.wecare.android.ui.create_order.info.InformationFragment;
import com.wecare.android.ui.create_order.location.LocationFragment;
import com.wecare.android.ui.create_order.schedule.ScheduleFragment;
import com.wecare.android.ui.create_order.services.OrderServicesFragment;
import com.wecare.android.ui.main.order.sub.OrderSubServicesActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.utils.WeCareUtils;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import javax.inject.Inject;

public class CreateOrderActivity extends BaseActivity<ActivityCreateOrderBinding, CreateOrderViewModel> implements CreateOrderNavigator, HasSupportFragmentInjector, DoneFragment.OnDoneFragmentListener {

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    BaseViewPagerAdapter viewPagerAdapter;

    /**/
    private CreateOrderViewModel viewModel;
    /**/
    private StepperIndicator stepperIndicator;

    /**/
    private ViewPager mViewPager;

    /**/
    ActivityCreateOrderBinding binding;

    private FilterObject filterObject;

    /**
     * order model used in reorder flow.
     */
    private OrderModel reOrderModel;
    public boolean isReOrderFlow = false;

    /**/
    private MenuItem nextItem;
    private MenuItem doneItem;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CreateOrderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        getFilterObject();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.KEY_ORDER_OBJECT)) {
            reOrderModel = bundle.getParcelable(AppConstants.KEY_ORDER_OBJECT);
            isReOrderFlow = true;
        }
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.next_menu, menu);
        nextItem = menu.findItem(R.id.action_next);
        doneItem = menu.findItem(R.id.action_done);
        doneItem.setTitle(R.string.general_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Drawable drawable = item.getIcon();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//        }
        switch (item.getItemId()) {
            case R.id.action_next:
                navigateToNextStep();
                return true;

            case R.id.action_done:
                DoneFragment doneFragment = (DoneFragment) viewPagerAdapter.getFragmentForPosition(4);
                doneFragment.onSubmitOrderClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

private void  navigateToPreviousService(){

            OrderServicesFragment servicesFragment = (OrderServicesFragment) viewPagerAdapter.getFragmentForPosition(0);
            if (servicesFragment.validator.validate() && servicesFragment.getSelectedSubServices() != null && servicesFragment.getSelectedSubServices().size() != 0) {

                servicesFragment.goToSubServices(servicesFragment.getServiceResponse());
               /* //servicesFragment.goToSubServices(servicesFragment.getSelectedSubServices());
                Intent intent = OrderSubServicesActivity.getStartIntent(servicesFragment.getBaseActivity());
                intent.getExtras(AppConstants.ARGS_KEY_SERVICES,ser);
                startActivityForResult(intent, AppConstants.REQ_CODE_ORDER_SUB_SERVICE_LIST);
                Toast.makeText(getApplicationContext(),"Previous fragment"+servicesFragment,Toast.LENGTH_SHORT).show();*/

            } else {
                showToast(getString(R.string.validation_select_order_sub_service));
            }

}
    private void navigateToNextStep() {
        //if (stepperIndicator.getStepCount() != stepperIndicator.getCurrentStep()) {
        if (validateTabFields()) {
            //get  summary data for done view
            setSelectedSummaryDataForDoneView(viewPagerAdapter.getCurrentFragmentIndex() - 1);

            viewPagerAdapter.switchViewPagerPage(viewPagerAdapter.getCurrentFragmentIndex() + 1);
//                    stepperIndicator.setCurrentStep(stepperIndicator.getCurrentStep() + 1);
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void showDoneMenuItem(boolean showDone) {
        if (showDone) {
//            doneItem.setVisible(true);
//            nextItem.setVisible(false);
            getViewDataBinding().nextBtn.setVisibility(View.GONE);
        } else {
//            doneItem.setVisible(false);
//            nextItem.setVisible(true);
            getViewDataBinding().nextBtn.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateTabFields() {
        boolean isValid = false;
        switch (viewPagerAdapter.getCurrentFragmentIndex()) {
            case 0:
                OrderServicesFragment servicesFragment = (OrderServicesFragment) viewPagerAdapter.getFragmentForPosition(0);
                if (servicesFragment.validator.validate() && servicesFragment.getSelectedSubServices() != null && servicesFragment.getSelectedSubServices().size() != 0) {
                    isValid = true;
                    Toast.makeText(getApplicationContext(),"Previous fragment"+servicesFragment,Toast.LENGTH_SHORT).show();

                } else {
                    showToast(getString(R.string.validation_select_order_sub_service));
                }
                break;

            case 1:
                InformationFragment informationFragment = (InformationFragment) viewPagerAdapter.getFragmentForPosition(1);
                if (informationFragment.validator.validate() && informationFragment.getSelectedProfileObject() != null) {
                    isValid = true;
                } else {
                    showToast(getString(R.string.validation_select_order_relative_prifile_and_need_material));
                }
                break;

            case 2:
                LocationFragment locationFragment = (LocationFragment) viewPagerAdapter.getFragmentForPosition(2);
                if (locationFragment.validator.validate() && locationFragment.getSelectedLocation() != null) {
                    isValid = true;
                } else {
                    showToast(getString(R.string.validation_select_order_location));
                }
                break;

            case 3:
                ScheduleFragment scheduleFragment = (ScheduleFragment) viewPagerAdapter.getFragmentForPosition(3);
                if (scheduleFragment.validator.validate() && !WeCareUtils.isNullOrEmpty(scheduleFragment.getSelectedDate())
                        && !WeCareUtils.isNullOrEmpty(scheduleFragment.getSelectedTime())) {
                    isValid = true;
                } else {
                    showToast(getString(R.string.validation_select_order_date_time));
                }
                break;

            case 4:
                DoneFragment doneFragment = (DoneFragment) viewPagerAdapter.getFragmentForPosition(4);
                isValid = doneFragment.validator.validate();
                break;
        }

        return isValid;
    }

    private void setSelectedSummaryDataForDoneView(int fragmentPosition) {
        if (viewPagerAdapter.getFragmentForPosition(fragmentPosition) instanceof DoneFragment) {
            OrderServicesFragment servicesFragment = (OrderServicesFragment) viewPagerAdapter.getFragmentForPosition(0);
            InformationFragment informationFragment = (InformationFragment) viewPagerAdapter.getFragmentForPosition(1);
            LocationFragment locationFragment = (LocationFragment) viewPagerAdapter.getFragmentForPosition(2);
            ScheduleFragment scheduleFragment = (ScheduleFragment) viewPagerAdapter.getFragmentForPosition(3);
            DoneFragment doneFragment = (DoneFragment) viewPagerAdapter.getFragmentForPosition(4);
            //get data
            if (validateTabFields()) {
                doneFragment.setSelectedData(servicesFragment.getSelectedSubServices(),
                        informationFragment.getAttachmentPhotosList(), informationFragment.getSelectedProfileObject(),
                        locationFragment.getSelectedLocation(),
                        scheduleFragment.getSelectedDate(), scheduleFragment.getSelectedTime());
            }

            //when done view selected show done menu item
            showDoneMenuItem(true);
        } else {
            DoneFragment doneFragment = (DoneFragment) viewPagerAdapter.getFragmentForPosition(4);
            doneFragment.onTabChangeRemoveSelected();
        }
    }

    private void setUp() {
        addToolbar(R.id.toolbar, getString(R.string.services), true);

        mViewPager = binding.createViewPager;

        viewPagerAdapter.addFrag(OrderServicesFragment.newInstance(getIntent().getExtras()), getString(R.string.services));
        viewPagerAdapter.addFrag(InformationFragment.newInstance(), getString(R.string.information));
        viewPagerAdapter.addFrag(LocationFragment.newInstance(), getString(R.string.location));
        viewPagerAdapter.addFrag(ScheduleFragment.newInstance(), getString(R.string.appointment));
        viewPagerAdapter.addFrag(DoneFragment.newInstance(), getString(R.string.summry));

        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());

        stepperIndicator = binding.createStepperIndicator;
        stepperIndicator.setTextAlign(AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? Paint.Align.CENTER : Paint.Align.RIGHT);
        stepperIndicator.setLabelLayoutAlign(AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_OPPOSITE);

        // We keep last page for a "finishing" page
        stepperIndicator.setViewPager(mViewPager, false);

        if (isReOrderFlow) {
            viewPagerAdapter.setupAdapter(mViewPager, 3);
            stepperIndicator.setCurrentStep(3);

        } else {
            viewPagerAdapter.setupAdapter(mViewPager);
        }


        stepperIndicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                //reset menu item from selected to un selected.
                showDoneMenuItem(false);

                //enable click only for previous selected tab.
                if (viewPagerAdapter.getCurrentFragmentIndex() >= step) {
                    //check if done to set its data
                    setSelectedSummaryDataForDoneView(step);
                    //selecte view.
                    mViewPager.setCurrentItem(step, true);

//                stepperIndicator.setDoneIcon(getResources().getDrawable(R.drawable.ic_order));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DialogFactory.createReactDialog(mContext, getString(R.string.order_cancellation),
                getString(R.string.are_you_sure_you_want_cancel_this_order), getString(R.string.yes), getString(R.string.no), null,
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                }, null);
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onNextClicked() {
        navigateToNextStep();
    }

    // go to previous service
    @Override
    public void goToPreviousService() {
        navigateToPreviousService();
    }

    @Override
    public CreateOrderViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CreateOrderViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_order;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public OrderModel getReOrderModel() {
        return reOrderModel;
    }

    public FilterObject getFilterObject() {
        if (filterObject == null) {
            filterObject = new FilterObject();
        }
        return filterObject;
    }

    public CreateOrderActivity setFilterObject(FilterObject filterObject) {
        if (filterObject != null)
            this.filterObject = filterObject;
        return this;
    }

    @Override
    public void OnGiverSelectListener() {
//        doneItem.setTitle(R.string.submit_order);
    }
}
