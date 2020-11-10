package com.wecare.android.ui.create_order.done;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.ilhasoft.support.validation.Validator;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.local.FilterObject;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.responses.*;
import com.wecare.android.databinding.FragmentDoneBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.order_info.InformationAttachmentAdapter;
import com.wecare.android.ui.create_order.location.LocationAdapter;
import com.wecare.android.ui.create_order.services.sub_duration.DurationSubServicesAdapter;
import com.wecare.android.ui.search_giver.SearchGiverActivity;
import com.wecare.android.ui.search_giver.common.SuggestedGiverAdapter;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import javax.inject.Inject;

import java.util.ArrayList;

public class DoneFragment extends BaseFragment<FragmentDoneBinding, DoneViewModel> implements DoneNavigator, SuggestedGiverAdapter.SearchGiverAdapterListener {

    public static final String TAG = DoneFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DurationSubServicesAdapter durationSubServicesAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    InformationAttachmentAdapter attachmentAdapter;
    @Inject
    LinearLayoutManager mAttachmentLayoutManager;

    @Inject
    LocationAdapter locationAdapter;
    @Inject
    LinearLayoutManager locationLayoutManager;

    @Inject
    SuggestedGiverAdapter suggestedGiverAdapter;
    @Inject
    LinearLayoutManager suggestedGiverLayoutManager;


    private DoneViewModel viewModel;
    private FragmentDoneBinding binding;

    private OnDoneFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDoneFragmentListener) {
            mListener = (OnDoneFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDoneFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDoneFragmentListener {
        void OnGiverSelectListener();
    }

    public void setSelectedData(ArrayList<SubServiceResponse> subServiceResponseArrayList, ArrayList<InformationAttachmentObj> attachmentPhotosList,
                                RelativeProfileResponse selectedProfileObject, UserLocationResponse selectedLocation, String selectedDate, String selectedTime) {
        if (subServiceResponseArrayList != null) {
            getViewModel().setSelectedSubServiceResponseList(subServiceResponseArrayList);
            durationSubServicesAdapter.addItems(getViewModel().getSelectedSubServiceResponseList());
        }

        if (attachmentPhotosList != null) {
            getViewModel().setSelectedInformationAttachmentList(attachmentPhotosList);
            attachmentAdapter.addItems(attachmentPhotosList);
        }

        if (selectedProfileObject != null) {
            getViewModel().setSelectedRelativeResponse(selectedProfileObject);
            updateInformationViewSection();
        }

        if (selectedLocation != null) {
            getViewModel().setSelectedLocation(selectedLocation);
            locationAdapter.clearItems();
            locationAdapter.addOneItem(selectedLocation);
        }

        getViewModel().userScheduleTime.set(selectedTime);
        getViewModel().userScheduleDate.set(selectedDate);
    }

    public static DoneFragment newInstance() {
        Bundle args = new Bundle();
        DoneFragment fragment = new DoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        //set summary view
        attachmentAdapter.setSummaryView(true);
        durationSubServicesAdapter.setSummaryView(true);
        locationAdapter.setSummaryView(true);
        suggestedGiverAdapter.setSummaryView(true);

        if ((getActivity() != null && ((CreateOrderActivity) getActivity()).isReOrderFlow)) {
            getViewModel().setReOrderModel(((CreateOrderActivity) getActivity()).getReOrderModel());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        //validation
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();

        setUpSubServicesDuration();
        setUpAttachmentAdapter();
        setUpSubLocationAdapter();
        setSelectedGiverAdapter();
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

    private void setUpSubServicesDuration() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.durationSubServiceRecycler.setLayoutManager(mLayoutManager);
        binding.durationSubServiceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.durationSubServiceRecycler.setAdapter(durationSubServicesAdapter);
    }

    private void setUpSubLocationAdapter() {
        locationLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.locationRecycler.setLayoutManager(locationLayoutManager);
        binding.locationRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.locationRecycler.setAdapter(locationAdapter);
    }

    private void setSelectedGiverAdapter() {
        suggestedGiverAdapter.setListener(this);
        suggestedGiverLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.giverRecycler.setLayoutManager(suggestedGiverLayoutManager);
        binding.giverRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.giverRecycler.setAdapter(suggestedGiverAdapter);
    }

    private void setUpAttachmentAdapter() {
        mAttachmentLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.attachmentRecycler.setLayoutManager(mAttachmentLayoutManager);
        binding.attachmentRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.attachmentRecycler.setAdapter(attachmentAdapter);
    }

    @Override
    public DoneViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(DoneViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_done;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onSearchForCaregiverClick() {
        if ((getActivity() != null)) {
            //filter object
            getViewModel().setFilterObject(((CreateOrderActivity) getActivity()).getFilterObject());

            FilterObject filterObject = getViewModel().getFilterObject();
            //step 1
            filterObject.setServices(getViewModel().getSelectedSubServiceResponseList());
            //step 2
            if (getViewModel().getSelectedRelativeResponse().getId() != null)
                filterObject.setProfile_id(getViewModel().getSelectedRelativeResponse().getId());
            //step 3
            filterObject.setLocation_id(getViewModel().getSelectedLocation().getId());
            //step 4
            filterObject.setDate(getViewModel().userScheduleDate.get());
            filterObject.setStart_time(getViewModel().userScheduleTime.get());
            //step 5
            filterObject.setPayment_method(getViewModel().getIsCreditCardSelectedValue());

            Intent intent = SearchGiverActivity.getStartIntent(getBaseActivity());
            intent.putExtra(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT, filterObject);
            startActivityForResult(intent, AppConstants.REQ_CODE_SEARCH_GIVER_ACTIVITY_PICK);
        }
    }

    @Override
    public void onSubmitOrderClick() {
        if (getViewModel().isGiverSelected.get()) {
            if ((getActivity() != null && ((CreateOrderActivity) getActivity()).isReOrderFlow)) {
                getViewModel().CreateOrderRequest();
                return;
            }

            if (getViewModel().getSelectedInformationAttachmentList().isEmpty()) {
                getViewModel().CreateOrderRequest();
            } else {
                getViewModel().uploadCreateOrderImages();
            }
        } else {
            getBaseActivity().showToast(getString(R.string.please_select_giver_first));
            onSearchForCaregiverClick();
        }
    }

    @Override
    public void showOrderCreateDialog() {
        DialogFactory.createReactDialog(getBaseActivity(), getString(R.string.general_Success),
                String.format("%s, %s", getString(R.string.new_order_created_successfully), getString(R.string.giver_will_contact_you_soon)), getString(R.string.yes), null, null,
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getBaseActivity().finish();
                    }
                }, null);
    }

    @Override
    public void creditCardClicked() {
        getViewModel().isCreditCardSelected.set(true);
        Drawable imgCheck = getResources().getDrawable(R.drawable.ic_check_green);
        Drawable imgUnCheck = getResources().getDrawable(R.drawable.ic_un_checked_gray);
        binding.creditCardTxt.setCompoundDrawablesWithIntrinsicBounds(imgCheck, null, null, null);
        binding.cashTxt.setCompoundDrawablesWithIntrinsicBounds(imgUnCheck, null, null, null);
    }

    @Override
    public void cashClicked() {
        getViewModel().isCreditCardSelected.set(false);
        Drawable imgCheck = getResources().getDrawable(R.drawable.ic_check_green);
        Drawable imgUnCheck = getResources().getDrawable(R.drawable.ic_un_checked_gray);
        binding.creditCardTxt.setCompoundDrawablesWithIntrinsicBounds(imgUnCheck, null, null, null);
        binding.cashTxt.setCompoundDrawablesWithIntrinsicBounds(imgCheck, null, null, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.REQ_CODE_SEARCH_GIVER_ACTIVITY_PICK && data != null && data.hasExtra(AppConstants.ARGS_SELECTED_GIVER_PROFILE)) {
                //get giver selected item and add it to list.
                SearchGiverResponse searchGiverResponses = data.getParcelableExtra(AppConstants.ARGS_SELECTED_GIVER_PROFILE);

                //set selected item in viewModel.
                getViewModel().setSelectedSearchGiverResponse(searchGiverResponses);
                getViewModel().isGiverSelected.set(true); //binding.giverRecycler.setVisibility(View.VISIBLE);

                //update services list to show only the selected one.
                ArrayList<SearchGiverResponse> searchGiverResponsesList = new ArrayList<>();
                searchGiverResponsesList.add(getViewModel().getSelectedSearchGiverResponse());

                if (searchGiverResponses != null && searchGiverResponses.getMainServiceModelArrayList().size() != 0) {

                    ArrayList<SubServiceResponse> currentSubServiceResponseArrayList = getViewModel().getSelectedSubServiceResponseList();

                    for (MainServiceModel mainServiceModel : searchGiverResponses.getMainServiceModelArrayList()) {
                        for (SubServiceResponse currentSubServiceResponse : currentSubServiceResponseArrayList) {
                            SubServiceResponse subServiceResponse = mainServiceModel.getSubServiceResponse();
                            if (currentSubServiceResponse.getId() == subServiceResponse.getId()) {
                                currentSubServiceResponse.setTotal_amount(mainServiceModel.getTotal_amount());
                                currentSubServiceResponse.setTotal_taxes_percentage(mainServiceModel.getTotal_taxes_percentage());
                                currentSubServiceResponse.setTotal_amount(mainServiceModel.getTotal_amount());
                                currentSubServiceResponse.setGrand_total_amount(mainServiceModel.getGrand_total_amount());
                                currentSubServiceResponse.setPrice_per_hour(mainServiceModel.getPrice_per_hour());
                                currentSubServiceResponse.setPrice(subServiceResponse.getPrice());

                                currentSubServiceResponse.setColor(currentSubServiceResponse.getColor());
                                currentSubServiceResponse.setHourlyDuration(currentSubServiceResponse.getHourlyDuration());
                                currentSubServiceResponse.setPosition(currentSubServiceResponse.getPosition());
                                currentSubServiceResponse.setCalculatedPrice(currentSubServiceResponse.getCalculatedPrice());
                            }
                        }
                    }

                    getViewModel().setSelectedSubServiceResponseList(currentSubServiceResponseArrayList);
                    durationSubServicesAdapter.addItems(getViewModel().getSelectedSubServiceResponseList());
                    binding.subServiceTotalTxt.setVisibility(View.VISIBLE);
                }

                //setup adapter
                suggestedGiverAdapter.clearItems();
                suggestedGiverAdapter.addItems(searchGiverResponsesList);

                binding.parentScroll.fullScroll(View.FOCUS_UP);

                mListener.OnGiverSelectListener();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
//            onNoItemLeft();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getBaseActivity().setTitle(getString(R.string.summry));
    }

    private void updateInformationViewSection() {
        if (getViewModel().getSelectedRelativeResponse().getId() != null) {// in case user did not select relative profile and enter oly mobile number
            getViewModel().userInfoOrderFor.set(String.format("%s %s", getString(R.string.order_for), getViewModel().getSelectedRelativeResponse().getName()));
            getViewModel().userInfoYears.set(String.format("%s %s", getViewModel().getSelectedRelativeResponse().getAge(), getString(R.string.years)));
            getViewModel().userInfoGender.set(String.format("%s", getViewModel().getSelectedRelativeResponse().getGender().getTitle()));
        }
        getViewModel().userInfoMobile.set(String.format("%s", getViewModel().getSelectedRelativeResponse().getMobile_number()));

        if (getViewModel().getSelectedRelativeResponse().getNeedSomeMaterial() != null) {
            if (getViewModel().getSelectedRelativeResponse().getNeedSomeMaterial().equals("0")) {
                getViewModel().userInfoNeedSomeMaterial.set(getString(R.string.no));
            } else {
                getViewModel().userInfoNeedSomeMaterial.set(getString(R.string.yes));
            }
        }
    }

    public void onTabChangeRemoveSelected() {
        //set selected item in viewModel.
        getViewModel().setSelectedSearchGiverResponse(null);
        getViewModel().isGiverSelected.set(false);
        suggestedGiverAdapter.clearItems();
    }

    @Override
    public void onItemClicked(SearchGiverResponse searchGiverResponse) {
        onSearchForCaregiverClick();
    }

    @Override
    public void onLoadMore() {
        //not used
    }
}
