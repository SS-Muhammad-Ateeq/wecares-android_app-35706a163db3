
package com.wecare.android.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;

import com.wecare.android.R;
import com.wecare.android.data.model.api.models.InsuranceCompanyLookup;
import com.wecare.android.data.model.api.models.LookUpModel;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getLookUpDescById(ArrayList<LookUpModel> models,String id){
        for (LookUpModel lookUpModel:models){
            if (lookUpModel.getId().equals(id))
                return lookUpModel.getTitle();
        }
        return "";
    }

    public static String getInsuranceLookUpDescById(ArrayList<InsuranceCompanyLookup> models, String id){
        for (InsuranceCompanyLookup lookUpModel:models){
            if (lookUpModel.getId().equals(id))
                return lookUpModel.getTitle();
        }
        return "";
    }

    public static String getOrderStateID(ArrayList<LookUpModel> models,String descToMatch){
        for (LookUpModel lookUpModel:models){
            if (lookUpModel.getEnglishName().equals(descToMatch))
                return lookUpModel.getId();
        }
        return "";
    }
    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }
}
