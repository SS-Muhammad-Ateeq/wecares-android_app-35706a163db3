package com.wecare.android.ui.auth.registration.verification;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.UserModel;
import com.wecare.android.databinding.FragmentRegistrationVerficationCodeBinding;
import com.wecare.android.interfaces.ResendVerificationCodeCallBack;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;
import com.wecare.android.utils.WeCareUtils;

import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import br.com.ilhasoft.support.validation.Validator;

public class RegistrationVerificationCodeFragment extends BaseFragment<FragmentRegistrationVerficationCodeBinding, RegistrationVerificationCodeViewModel> implements RegistrationVerificationCodeNavigator {


    private long countDownValue = 0;
    private final long MINUTE_VALUE = 60000;

    private CountDownTimer countDownTimer;

    boolean isGiverRegistration = true;
    boolean isLoginVerifyUser = false;

    boolean isPaused = false;

    //events logging
    private FirebaseAnalytics mFirebaseAnalytics;
    private AppEventsLogger logger;



    @Inject
    ViewModelProviderFactory factory;
    private RegistrationVerificationCodeViewModel viewModel;

    FragmentRegistrationVerficationCodeBinding binding;


    @Override
    public void nextClicked() {
        validator.toValidate();
    }

    @Override
    public RegistrationVerificationCodeViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RegistrationVerificationCodeViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_registration_verfication_code;
    }

    public static RegistrationVerificationCodeFragment newInstance(Bundle args) {
        RegistrationVerificationCodeFragment fragment = new RegistrationVerificationCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Objects.requireNonNull(getActivity()));
        logger = AppEventsLogger.newLogger(Objects.requireNonNull(getActivity()));

        isGiverRegistration = getArguments().getInt(AppConstants.KEY_REGISTRATION_TYPE) == AppConstants.REGISTRATION_TYPE_GIVER;
        isLoginVerifyUser = getArguments().containsKey(AppConstants.ARGS_VERIFY_USER);
        if (isLoginVerifyUser)
            viewModel.resendVerificationCode(viewModel.getDataManager().getCurrentUserModel().getCountryCode(), viewModel.getDataManager().getCurrentUserModel().getPhoneNumber());

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        validator = new Validator(binding);
        validator.setValidationListener(this);
        validator.enableFormValidationMode();
        String number = String.format(Locale.ENGLISH,"%s%s", isLoginVerifyUser ? viewModel.getDataManager().getCurrentUserModel().getCountryCode() : viewModel.getDataManager().getRegistrationModel().getCountryCode(), isLoginVerifyUser ? viewModel.getDataManager().getCurrentUserModel().getPhoneNumber() : viewModel.getDataManager().getRegistrationModel().getPhoneNumber());
        binding.verificationCodeNumberTxt.setText(number);
//        binding.verificationCodeEdt.setText(viewModel.getDataManager().getVerificationCode());

    }

    @Override
    public void onValidationSuccess() {
        super.onValidationSuccess();
        viewModel.sendVerificationCode(WeCareUtils.getEditTextString(binding.verificationCodeEdt));
    }

    @Override
    public void resendClicked() {
        ResendVerificationCodeFragment fragment = new ResendVerificationCodeFragment();
        fragment.setCallBack(new ResendVerificationCodeCallBack() {
            @Override
            public void sendClicked(String countryCode, String mobileNumber) {
                binding.verificationCodeNumberTxt.setText(String.format("%s%s", countryCode, mobileNumber));
                viewModel.getDataManager().getRegistrationModel().setCountryCode(countryCode);
                viewModel.getDataManager().getRegistrationModel().setPhoneNumber(mobileNumber);
                viewModel.resendVerificationCode(countryCode, mobileNumber);
            }
        });
        fragment.show(getActivity().getSupportFragmentManager(), "");
    }


    @Override
    public void accountActivatedSuccessfully(UserModel userModel) {

        DialogFactory.createFeedBackDialog(getActivity(), getString(R.string.thank_you), getString(R.string.account_activated), getString(R.string.ok), getActivity().getResources().getDrawable(R.drawable.success_img), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
//                NYBus.get().post(new RegistrationStepperEvent(AppConstants.EXIT_STEPPER));

                //log verification success on firebase
                logVerificationSuccessEvent(userModel);
                //log verification success on facebook
                logFacebookVerificationSuccessEvent(userModel);

                getBaseActivity().openMainActivity(getActivity());
                getActivity().finish();
            }
        });
    }

    @Override
    public void codeResentSuccessfully() {
        countDownValue = MINUTE_VALUE;
        setUpAndStartCountDownTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        //counting to send the sms.
        if (countDownValue == 0)
            countDownValue = MINUTE_VALUE;

        isPaused = false;
        setUpAndStartCountDownTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        countDownTimer.cancel();
        isPaused = true;
    }

    private void setUpAndStartCountDownTimer() {
        countDownTimer = new CountDownTimer(countDownValue, 1000) {
            @Override
            public void onTick(long l) {

                if (isPaused)
                    return;

                long currentTick = l / 1000;
                binding.verificationResendBtn.setText(String.format("%s %s \n%s", getString(R.string.general_PleaseWait), String.valueOf(currentTick), getString(R.string.to_resend_activation_code)));

                countDownValue = l;
            }

            @Override
            public void onFinish() {
                if (isPaused)
                    return;

                binding.verificationResendBtn.setEnabled(true);
                binding.verificationResendBtn.setText(getString(R.string.resend_activation_code));
            }

        };

        countDownTimer.start();
        binding.verificationResendBtn.setEnabled(false);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void logVerificationSuccessEvent(UserModel userModel){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, userModel.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, userModel.getFirstName() +" "+userModel.getLastName());
        mFirebaseAnalytics.logEvent(AppConstants.FIREBASE_EVENT_VERIFICATION, bundle);
    }

    public void logFacebookVerificationSuccessEvent (UserModel userModel) {
        Bundle params = new Bundle();
        params.putString("user_id", userModel.getId());
        params.putString("username", userModel.getFirstName()+" "+userModel.getLastName());
        logger.logEvent(AppConstants.FIREBASE_EVENT_VERIFICATION, params);
    }

}
