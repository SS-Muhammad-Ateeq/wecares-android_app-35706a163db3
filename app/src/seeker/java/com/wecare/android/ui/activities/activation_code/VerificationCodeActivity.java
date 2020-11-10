package com.wecare.android.ui.activities.activation_code;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.wecare.android.R;
import com.wecare.android.databinding.FragmentRegistrationVerficationCodeBinding;
import com.wecare.android.ui.base.BaseActivity;
import com.wecare.android.ui.base.BaseViewModel;

import androidx.appcompat.widget.AppCompatTextView;

public class VerificationCodeActivity extends BaseActivity<FragmentRegistrationVerficationCodeBinding, BaseViewModel> implements View.OnClickListener {

    /**/
    private long countDownValue = 0;
    private final long MINUTE_VALUE = 50000;
    /**/
    private String userPhoneNumber = "";
    /**/
    private AppCompatTextView resendBtn;
    /**/
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration_verfication_code);

        //get views
        initViews();
    }

    @Override
    public BaseViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }


    @Override
    protected void onResume() {
        super.onResume();

        //counting to send the sms.
        if (countDownValue == 0)
            countDownValue = MINUTE_VALUE;

        setUpAndStartCountDownTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        countDownTimer.cancel();
    }

    /**
     * get viewa
     */
    private void initViews() {
        resendBtn = findViewById(R.id.verification_resend_btn);

        //set phone number.
        String number = "\u200E" + userPhoneNumber;
        TextView textView = findViewById(R.id.verificationCode_number_txt);
        textView.setText(number);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.verification_resend_btn:
                countDownValue = MINUTE_VALUE;
                setUpAndStartCountDownTimer();
//                request();
                break;

        }
    }

    /**
     * start stop counter
     */
    private void setUpAndStartCountDownTimer() {
        countDownTimer = new CountDownTimer(countDownValue, 1000) {
            @Override
            public void onTick(long l) {
                long currentTick = l / 1000;
                resendBtn.setText("Please wait" + " " + String.valueOf(currentTick) + " " + "to send activation code again");

                countDownValue = l;
            }

            @Override
            public void onFinish() {
                resendBtn.setEnabled(true);
                resendBtn.setText(getString(R.string.resend_activation_code));
            }
        };

        countDownTimer.start();
        resendBtn.setEnabled(false);
    }

}
