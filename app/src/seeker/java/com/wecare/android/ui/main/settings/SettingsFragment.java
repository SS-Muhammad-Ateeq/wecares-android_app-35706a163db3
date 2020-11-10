package com.wecare.android.ui.main.settings;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.BuildConfig;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.databinding.FragmentSettingsBinding;
import com.wecare.android.interfaces.PickerSelectedValueListener;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.webview.activity.WebViewActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.FileUtil;
import com.wecare.android.utils.ServerKeys;
import com.wecare.android.utils.WeCareUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import de.cketti.mailto.EmailIntentBuilder;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class SettingsFragment extends BaseFragment<FragmentSettingsBinding, SettingsViewModel> implements SettingsNavigator {

    public static final String TAG = SettingsFragment.class.getSimpleName();
    private ArrayAdapter<String> languageAdapter;


    @Inject
    ViewModelProviderFactory factory;
    private SettingsViewModel viewModel;

    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
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
        binding.settingsNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.enableNotifications(isChecked ? AppConstants.PHP_TRUE : AppConstants.PHP_FALSE);
            }
        });

        if (getViewModel().getDataManager().getCurrentUserEmail() == null) {
            binding.settingsLogoutParentLinear.setVisibility(View.GONE);
            binding.settingsPasswordParentLinear.setVisibility(View.GONE);
        }
    }

    private void setUp() {
        if (viewModel.getDataManager().getCurrentUserModel() == null || viewModel.getDataManager().getCurrentUserModel().getSettings() == null)
            return;

        binding.settingsNotificationSwitch.setChecked(viewModel.getDataManager().getCurrentUserModel().getSettings().getEnableNotifications() == AppConstants.PHP_TRUE_RAW);
        binding.settingsSelectedLanguageTxt.setText(viewModel.getDataManager().getAppLocale().equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? getString(R.string.english) : getString(R.string.arabic));
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            setUp();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUp();
    }

    @Override
    public SettingsViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SettingsViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void logoutClicked() {
        createLogoutDialog();
    }

    @Override
    public void logoutUserSuccessfully() {
        getBaseActivity().openLoginActivity(getActivity());
    }

    @Override
    public void aboutUsClicked() {
//        FileUtil.openWebPage(getActivity(), ServerKeys.KEY_ABOUT_US_URL);
        String aboutURL = WeCareUtils.getPageURL(AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? AppConstants.PAGE_ABOUT_US_ENGLISH : AppConstants.PAGE_ABOUT_US_ARABIC, viewModel.getDataManager().getPages());
        startActivity(WebViewActivity.newIntent(getActivity()).putExtra(AppConstants.ARGS_KEY_URL, aboutURL).putExtra(AppConstants.ARGS_KEY_TITLE, getString(R.string.about_the_app)));

    }

    @Override
    public void privacyPolicy() {
//        FileUtil.openWebPage(getActivity(), ServerKeys.KEY_PRIVACY_POLICY_URL);
        String privacyURL = WeCareUtils.getPageURL(AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? AppConstants.PAGE_PRIVACY_POLICY_ENGLISH : AppConstants.PAGE_PRIVACY_POLICY_ARABIC, viewModel.getDataManager().getPages());
        startActivity(WebViewActivity.newIntent(getActivity()).putExtra(AppConstants.ARGS_KEY_URL, privacyURL).putExtra(AppConstants.ARGS_KEY_TITLE, getString(R.string.privacy_police)));
    }

    @Override
    public void termsConditions() {
//        FileUtil.openWebPage(getActivity(), ServerKeys.KEY_TERMS_CONDITIONS_URL);
        String termsURL = WeCareUtils.getPageURL(AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? AppConstants.SEEKER_TERMS_SERVICE_ENGLISH : AppConstants.SEEKER_TERMS_SERVICE_ARABIC, viewModel.getDataManager().getPages());
        startActivity(WebViewActivity.newIntent(getActivity()).putExtra(AppConstants.ARGS_KEY_URL, termsURL).putExtra(AppConstants.ARGS_KEY_TITLE, getString(R.string.terms_and_conditions)));

    }

    @Override
    public void onChangeLanguageClicked() {
        List<String> sortList = new ArrayList<String>();
        sortList.add(getString(R.string.arabic));
        sortList.add(getString(R.string.english));

        String[] sortArr = new String[sortList.size()];
        sortArr = sortList.toArray(sortArr);

        languageAdapter = new ArrayAdapter<>(getBaseActivity(), R.layout.simple_spinner_item, sortArr);

        Dialog dialog = DialogFactory.createListPickerDialog(getActivity(), true, languageAdapter, getString(R.string.change_language), new PickerSelectedValueListener() {
            @Override
            public void onValueSelected(int selectedIndex) {
                switch (selectedIndex) {
                    case 0:
                        if (!viewModel.getDataManager().getAppLocale().equals(AppConstants.LANGUAGE_LOCALE_ARABIC)) {
                            WeCareUtils.setLocale(AppConstants.LANGUAGE_LOCALE_ARABIC, getBaseActivity(), true);
                            viewModel.getDataManager().setAppLocale(AppConstants.LANGUAGE_LOCALE_ARABIC);
                            binding.settingsSelectedLanguageTxt.setText(getString(R.string.arabic));
                        }
                        break;
                    case 1:
                        if (!viewModel.getDataManager().getAppLocale().equals(AppConstants.LANGUAGE_LOCALE_ENGLISH)) {
                            WeCareUtils.setLocale(AppConstants.LANGUAGE_LOCALE_ENGLISH, getBaseActivity(), true);
                            viewModel.getDataManager().setAppLocale(AppConstants.LANGUAGE_LOCALE_ENGLISH);
                            binding.settingsSelectedLanguageTxt.setText(getString(R.string.english));
                        }
                        break;
                }
            }
        });
        dialog.show();
    }

    private void showTypesOfContactUs() {

        sendEmailIntent(getString(R.string.app_name));

//        SimpleSearchDialogCompat simpleSearchDialogCompat = new SimpleSearchDialogCompat(getActivity(),
//                getString(R.string.contact_type),
//                getString(R.string.general_search),
//                null, viewModel.getDataManager().getLookupsModel().getContactUsTypesTypeArrayList(), contactUsResultListener);
//
//        simpleSearchDialogCompat.show();
    }

    SearchResultListener contactUsResultListener = new SearchResultListener<LookUpModel>() {
        @Override
        public void onSelected(BaseSearchDialogCompat dialog, LookUpModel item, int position) {
            sendEmailIntent(item.getTitle());
            dialog.dismiss();
        }
    };

    private void sendEmailIntent(String subject) {
        Intent emailIntent = EmailIntentBuilder.from(getActivity())
                .to(getString(R.string.support_email))
                .subject(subject)
                .build();
        startActivity(emailIntent);
    }

    @Override
    public void shareAppClicked() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=com.wecare.android");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void contactUsClicked() {
        showTypesOfContactUs();
    }

    @Override
    public void rateUsClicked() {
        FileUtil.openWebPage(getActivity(), "https://play.google.com/store/apps/details?id=com.wecare.android.seeker");

    }

    @Override
    public void changePasswordClicked() {
        startActivity(ChangePasswordActivity.getIntent(getActivity()));
    }

    private void createLogoutDialog() {
        DialogFactory.createReactDialog(getActivity(), getString(R.string.logout), getString(R.string.logout_confirmation), getString(R.string.yes), getString(R.string.no), null, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                viewModel.doLogoutUser();
            }
        }, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void OnUpdateView() {
        super.OnUpdateView();
        getBaseActivity().setTitle(getString(R.string.settings));
    }

    @Override
    public void notificationEnableSuccessfully(boolean enabled) {
        viewModel.getDataManager().getCurrentUserModel().getSettings().setEnableNotifications(enabled ? AppConstants.PHP_TRUE_RAW : AppConstants.PHP_FALSE_RAW);
    }

    @Override
    public void notificationEnableFailed(boolean enabled) {
//    binding.settingsNotificationSwitch.setChecked(!enabled);
    }

    @Override
    public void onContactSupportClicked() {
        if (viewModel.getDataManager().getCurrentUserModel().getCountry() != null && viewModel.getDataManager().getCurrentUserModel().getCountry().getCustomerSupportNumber() != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + viewModel.getDataManager().getCurrentUserModel().getCountry().getCustomerSupportNumber()));
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), getString(R.string.customer_number_not_available), Toast.LENGTH_SHORT).show();
        }
    }


}
