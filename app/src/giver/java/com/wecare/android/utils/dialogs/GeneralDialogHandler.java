package com.wecare.android.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.wecare.android.R;
import com.wecare.android.interfaces.OnCustomDialogClickListener;
import com.wecare.android.utils.WeCareUtils;


/**
 * Created by Mihyar on 3/15/2019
 * <p>
 * Handler to handle App's Dialogs.
 * <p>
 * - Handling General Confirmation dialogs.
 * - Handling Small messages dialogs
 */

public class GeneralDialogHandler {

    private Context mContext;

    public GeneralDialogHandler(Context mContext) {
        this.mContext = mContext;
    }

    public void showMessageDialog(String title, String msg) {

        showConfirmationDialog(title, msg, mContext.getString(R.string.general_OK), null, null);
    }

    public void showMessageDialog(String title, String msg, String positiveBtnMsg) {

        showConfirmationDialog(title, msg, positiveBtnMsg, null, null);
    }

    public void showConfirmationDialog(String title,
                                       String msg,
                                       String positiveBtnMsg,
                                       String negativeBtnMsg,
                                       final OnCustomDialogClickListener onCustomDialogClickListener) {

        showConfirmationDialog(title, msg, positiveBtnMsg, negativeBtnMsg, null, false, onCustomDialogClickListener);

    }

    public void showConfirmationDialog(String title,
                                       String msg,
                                       String positiveBtnMsg,
                                       String negativeBtnMsg,
                                       String edtFilling,
                                       final boolean showEdit,
                                       final OnCustomDialogClickListener onCustomDialogClickListener) {

        final Dialog mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_msg_confimation);

        TextView txtTitle = (TextView) mDialog.findViewById(R.id.dialog_title_txt);

        TextView txtMessage = (TextView) mDialog.findViewById(R.id.dialog_msg_txt);

        Button btnPositive = (Button) mDialog.findViewById(R.id.dialog_positive_btn);

        Button btnNegative = (Button) mDialog.findViewById(R.id.dialog_negative_btn);

        final EditText edtMessage = (EditText) mDialog.findViewById(R.id.dialog_message_edt);

        if (!WeCareUtils.isNullOrEmpty(positiveBtnMsg))
            btnPositive.setText(positiveBtnMsg);

        if (!WeCareUtils.isNullOrEmpty(negativeBtnMsg)) {
            btnNegative.setText(negativeBtnMsg);
            btnNegative.setVisibility(View.VISIBLE);
        }

        if (!WeCareUtils.isNullOrEmpty(title))
            txtTitle.setText(title);

        if (showEdit) {
            edtMessage.setVisibility(View.VISIBLE);
            txtMessage.setVisibility(View.GONE);

            if (!WeCareUtils.isNullOrEmpty(edtFilling)) {
                edtMessage.setText(edtFilling);
            }

        }

        txtMessage.setText(msg);


        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onCustomDialogClickListener != null) {

                    if (showEdit)
                        onCustomDialogClickListener.onButtonClickedWithData(mDialog, WeCareUtils.getEditTextString(edtMessage));
                    else
                        onCustomDialogClickListener.onPositiveClickListener(mDialog);

                } else
                    mDialog.dismiss();

            }
        });


        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onCustomDialogClickListener != null)
                    onCustomDialogClickListener.onNegativeClickListener(mDialog);
                else
                    mDialog.dismiss();


            }
        });

        mDialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimationStyle;
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.show();

    }

    //here
    public void showDialogWithIcon(String title,
                                   int icon,
                                   String msg,
                                   String btnMsg,
                                   final OnCustomDialogClickListener onCustomDialogClickListener) {

        final Dialog mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_msg_and_icon);

        TextView txtTitle = (TextView) mDialog.findViewById(R.id.dialog_title_txt);

        TextView txtMessage = (TextView) mDialog.findViewById(R.id.dialog_msg_txt);

        ImageView imgIcon = (ImageView) mDialog.findViewById(R.id.dialog_icon_img);

        Button btnDone = (Button) mDialog.findViewById(R.id.dialog_done_btn);


        if (!WeCareUtils.isNullOrEmpty(title))
            txtTitle.setText(title);

        if (!WeCareUtils.isNullOrEmpty(btnMsg))
            btnDone.setText(btnMsg);

        if (icon != -1)
            imgIcon.setImageResource(icon);

        txtMessage.setText(msg);


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onCustomDialogClickListener != null)
                    onCustomDialogClickListener.onPositiveClickListener(mDialog);
                else
                    mDialog.dismiss();

            }
        });


        mDialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimationStyle;
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.show();

    }

}
