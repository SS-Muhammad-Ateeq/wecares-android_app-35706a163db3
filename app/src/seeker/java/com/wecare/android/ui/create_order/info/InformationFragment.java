package com.wecare.android.ui.create_order.info;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.responses.RelativeProfileResponse;
import com.wecare.android.databinding.FragmentInformationBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.create_order.CreateOrderActivity;
import com.wecare.android.ui.create_order.relative.RelativeProfileActivity;
import com.wecare.android.ui.main.order.special.SpecialRequestActivity;
import com.wecare.android.ui.order_info.InformationAttachmentAdapter;
import com.wecare.android.ui.order_info.InformationNavigator;
import com.wecare.android.ui.order_info.InformationViewModel;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.PickerDialogFactory;
import com.wecare.android.utils.WeCareUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ilhasoft.support.validation.Validator;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class InformationFragment extends BaseFragment<FragmentInformationBinding, InformationViewModel> implements InformationNavigator, InformationAttachmentAdapter.AttachmentAdapterListener {

    public static final String TAG = InformationFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    InformationAttachmentAdapter attachmentAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private InformationViewModel viewModel;
    private FragmentInformationBinding binding;

    private SearchResultListener needSomeMaterialResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
//            if (viewModel.getProfileResponse() != null) {
            viewModel.getProfileResponse().setNeedSomeMaterial(item.getId());
            binding.infoNeedMoreMaterialTxt.setText(item.getTitle());
            binding.infoMaterialDescriptionEdt.setVisibility(item.getId().equals("1") ? View.VISIBLE : View.GONE);
//            } else {
//                getBaseActivity().showToast(getString(R.string.please_select_relative_profile_first));
//            }
            dialog.dismiss();
        }
    };

    public static InformationFragment newInstance() {
        Bundle args = new Bundle();
        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        //validation
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();

        setUp();

        binding.countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                getViewModel().getProfileResponse().setCountry_code(selectedCountry.getPhoneCode());
            }
        });

        if (getActivity() != null && ((CreateOrderActivity) getActivity()).isReOrderFlow) {
            binding.addNewImageTxt.setVisibility(View.GONE);
            binding.infoMoreDescriptionEdt.setEnabled(false);
            binding.countryCodePicker.setClickable(false);
            binding.infoRelativeMobileNumberEdt.setEnabled(false);
            attachmentAdapter.setSummaryView(true);

            if (((CreateOrderActivity) getActivity()).getReOrderModel() != null) {
                if (((CreateOrderActivity) getActivity()).getReOrderModel().getImages() != null)
                    attachmentAdapter.addItems(((CreateOrderActivity) getActivity()).getReOrderModel().getImages());

//                if (((CreateOrderActivity) getActivity()).getReOrderModel().getProfile() != null)
                setSelectedRelativeProfile(((CreateOrderActivity) getActivity()).getReOrderModel().getProfile());

                if (((CreateOrderActivity) getActivity()).getReOrderModel().getNeedMaterials() != null) {
                    String material = String.valueOf(((CreateOrderActivity) getActivity()).getReOrderModel().getNeedMaterials());
                    binding.infoNeedMoreMaterialTxt.setText(material);
                    viewModel.getProfileResponse().setNeedSomeMaterial(material);
                }

                if (((CreateOrderActivity) getActivity()).getReOrderModel().getDescription() != null) {
                    String description = String.valueOf(((CreateOrderActivity) getActivity()).getReOrderModel().getDescription());
                    binding.infoMoreDescriptionEdt.setText(description);
                    viewModel.getProfileResponse().setNeedSomeMaterial(description);
                }
            }
        } else {
            setSelectedRelativeProfile(getViewModel().getDataManager().getCurrentUserModel().getUser_profile());
//            binding.infoRelativeMobileNumberEdt.setText(getViewModel().getDataManager().getCurrentUserModel().getPhoneNumber());
//            binding.countryCodePicker.setCountryForPhoneCode(Integer.parseInt(getViewModel().getDataManager().getCurrentUserModel().getCountryCode()));
        }
    }

    @Override
    public void onValidationSuccess() {
        //if (isDropDownsValid()) {
        if (getViewModel().getProfileResponse().getCountry_code() == null) {
            binding.countryCodeLayout.setError(getString(R.string.error_field_required));
            return;
        }
        binding.countryCodeLayout.setError(null);

//            viewModel.getDataManager().sdf.setValue(WeCareUtils.getEditTextString(binding.edt));
    }

    @Override
    public void onValidationError() {
        super.onValidationError();
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.attachmentRecycler.setLayoutManager(mLayoutManager);
        binding.attachmentRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.attachmentRecycler.setAdapter(attachmentAdapter);
        attachmentAdapter.setListener(this);
    }

    @Override
    public InformationViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(InformationViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onImagePickerClicked() {
        EasyImage.openChooserWithGallery(this, getString(R.string.app_name), AppConstants.REQ_CODE_ORDER_INFORMATION_PIN_IMAGE);
    }

    @Override
    public void onRelativeProfileClicked() {
        if (getActivity() != null && !((CreateOrderActivity) getActivity()).isReOrderFlow) {
            Intent i = RelativeProfileActivity.getStartIntent(getBaseActivity());
            startActivityForResult(i, AppConstants.REQ_CODE_RELATIVE_PROFILE);
        }
    }

    @Override
    public void onNeedSomeMaterialClicked() {
        if (getActivity() != null && !((CreateOrderActivity) getActivity()).isReOrderFlow) {
            PickerDialogFactory.showYesNoSelection(getBaseActivity(), needSomeMaterialResultListener);
        }
    }

    @Override
    public void onSpecialRequestClicked() {
        if (getActivity() != null && ((CreateOrderActivity) getActivity()).getFilterObject() != null) {
            Intent i = SpecialRequestActivity.getStartIntent(getBaseActivity());
            i.putExtra(AppConstants.ARGS_KEY_FILTER_GIVER_OBJECT, ((CreateOrderActivity) getActivity()).getFilterObject());
            startActivityForResult(i, AppConstants.REQ_CODE_EDITED_FILTER_GIVER_OBJECT_LIST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.REQ_CODE_RELATIVE_PROFILE && data != null) {
                RelativeProfileResponse relativeProfileResponse = data.getParcelableExtra(AppConstants.ARGS_SELECTED_RELATIVE_PROFILE);
                setSelectedRelativeProfile(relativeProfileResponse);
                //maybe we need to set the filter gender to be same as the selected profile.
                //((CreateOrderActivity) Objects.requireNonNull(getActivity())).getFilterObject().setCaregiver_gender_id(relativeProfileResponse.getGender().getId());
            }

            if (requestCode == AppConstants.REQ_CODE_EDITED_FILTER_GIVER_OBJECT_LIST && data != null && data.hasExtra(AppConstants.ARGS_KEY_EDITED_FILTER_GIVER_OBJECT)) {
                if (getActivity() != null && ((CreateOrderActivity) getActivity()).getFilterObject() != null) {
                    ((CreateOrderActivity) getActivity()).setFilterObject(data.getParcelableExtra(AppConstants.ARGS_KEY_EDITED_FILTER_GIVER_OBJECT));

                    ArrayList<String> specialArray = new ArrayList<>();
                    if (!WeCareUtils.isNullOrEmpty(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverGenderValue())) {
                        specialArray.add(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverGenderValue());
                    }

                    if (!WeCareUtils.isNullOrEmpty(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverAgeValue())) {
                        specialArray.add(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverAgeValue());
                    }

                    if (!WeCareUtils.isNullOrEmpty(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverLanguageValue())) {
                        specialArray.add(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverLanguageValue());
                    }
                    setTag(specialArray);
                }
            }
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, getBaseActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
//                getBaseActivity().showToast(imageFiles.toString());

                if (imageFiles.size() != 0) {
                    InformationAttachmentObj attachmentObj = new InformationAttachmentObj();
                    attachmentObj.setUrl(imageFiles.get(0).getAbsolutePath());
                    attachmentObj.setImage(imageFiles.get(0).getName());
                    attachmentAdapter.addItem(attachmentObj);
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                getBaseActivity().showToast(e.getMessage());
            }
        });
    }

    public void setSelectedRelativeProfile(RelativeProfileResponse relativeProfileResponse) {
        //in case we select te need some material first and select the relative profile we need to save te select value.
        relativeProfileResponse.setNeedSomeMaterial(getViewModel().getProfileResponse().getNeedSomeMaterial());

        getViewModel().setProfileResponse(relativeProfileResponse);
        binding.infoRelativeNameTxt.setText(relativeProfileResponse.getName());
        if (relativeProfileResponse.getCountry_code() != null) {
            binding.countryCodePicker.setCountryForPhoneCode(Integer.parseInt(relativeProfileResponse.getCountry_code()));
        }
        binding.infoRelativeMobileNumberEdt.setText(relativeProfileResponse.getMobile_number());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getBaseActivity().setTitle(getString(R.string.information));
    }

    @Override
    public void onRemoveClicked(InformationAttachmentObj informationAttachmentObj) {
        attachmentAdapter.removeItem(informationAttachmentObj);
    }

    @Override
    public void onNoItemClick() {
        onImagePickerClicked();
    }

    private void setTag(final List<String> tagList) {
        final ChipGroup chipGroup = binding.tagGroup;
        chipGroup.removeAllViews();
        for (int index = 0; index < tagList.size(); index++) {
            final String tagName = tagList.get(index);
            final Chip chip = new Chip(getBaseActivity());
            chip.setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);
            int paddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            chip.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.text_blue));
            chip.setCloseIconVisible(true);
            chip.setCloseIconResource(R.drawable.ic_cancel_grey);
            chip.setCloseIconTint(null);//without this will not work.
//            chip.setCloseIconSizeResource(R.dimen.icon_size_small);
//            chip.setCloseIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_cancel_grey_500_24dp));
//            ChipDrawable chipDrawable = ChipDrawable.createFromResource(getBaseActivity(), R.xml.chip);
//            chip.setCloseIcon(chipDrawable);

            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tagList.remove(tagName);
                    chipGroup.removeView(chip);

                    if (!WeCareUtils.isNullOrEmpty(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverGenderValue())) {
                        if (((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverGenderValue().equals(tagName)) {
                            ((CreateOrderActivity) getActivity()).getFilterObject().setCaregiverGenderValue(null);
                        }
                    } else if (!WeCareUtils.isNullOrEmpty(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverAgeValue())) {
                        if (((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverAgeValue().equals(tagName)) {
                            ((CreateOrderActivity) getActivity()).getFilterObject().setCaregiverAgeValue(null);
                        }
                    } else if (!WeCareUtils.isNullOrEmpty(((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverLanguageValue())) {
                        if (((CreateOrderActivity) getActivity()).getFilterObject().getCaregiverLanguageValue().equals(tagName)) {
                            ((CreateOrderActivity) getActivity()).getFilterObject().setCaregiverAgeValue(null);
                        }
                    }
                }
            });
            chipGroup.addView(chip);
        }
    }

    public RelativeProfileResponse getSelectedProfileObject() {
        RelativeProfileResponse relativeProfileResponse;
//        if (getViewModel().getProfileResponse() != null) {
        relativeProfileResponse = getViewModel().getProfileResponse();
        relativeProfileResponse.setMoreDescription(String.valueOf(binding.infoMoreDescriptionEdt.getText()));
        relativeProfileResponse.setCountry_code(getViewModel().getProfileResponse().getCountry_code());
        relativeProfileResponse.setMobile_number(String.valueOf(binding.infoRelativeMobileNumberEdt.getText()));
        if (!WeCareUtils.isNullOrEmpty(String.valueOf(binding.infoMaterialDescriptionEdt.getText()))){
            relativeProfileResponse.setNeededMaterial(String.valueOf(binding.infoMaterialDescriptionEdt.getText()));
        }
//        }

        return relativeProfileResponse;
    }

    public ArrayList<InformationAttachmentObj> getAttachmentPhotosList() {
        return attachmentAdapter.getInformationAttachmentObjList();
    }

}
