package com.wecare.android.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.Gson;
import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.sdk.android.dependancies.base.FortInterfaces;
import com.payfort.sdk.android.dependancies.models.FortRequest;
import com.wecare.android.utils.NoSSLv3SocketFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class PayFortPayment {

    //Request key for response
    public static final int RESPONSE_GET_TOKEN = 111;
    public static final int RESPONSE_PURCHASE = 222;
    public static final int RESPONSE_PURCHASE_CANCEL = 333;
    public static final int RESPONSE_PURCHASE_SUCCESS = 444;
    public static final int RESPONSE_PURCHASE_FAILURE = 555;

    //WS params
    private final static String KEY_MERCHANT_IDENTIFIER = "merchant_identifier";
    private final static String KEY_SERVICE_COMMAND = "service_command";
    private final static String KEY_COMMAND = "command";
    private final static String KEY_DEVICE_ID = "device_id";
    private final static String KEY_LANGUAGE = "language";
    private final static String KEY_ACCESS_CODE = "access_code";
    private final static String KEY_SIGNATURE = "signature";
    private final static String KEY_AMOUNT = "amount";
    private final static String KEY_CURRENCY = "currency";
    private final static String KEY_CUSTOMER_EMAIL = "customer_email";
    private final static String KEY_MERCHANT_REFERENCE = "merchant_reference";

    //Commands
    public final static String AUTHORIZATION = "AUTHORIZATION";
    public final static String PURCHASE = "PURCHASE";
    private final static String SDK_TOKEN = "SDK_TOKEN";
    private final static String KEY_SDK_TOKEN = "sdk_token";


    //Test token url
    private final static String TEST_TOKEN_URL = "https://sbpaymentservices.payfort.com/FortAPI/paymentApi";
    //Live token url
    public final static String LIVE_TOKEN_URL = "https://paymentservices.payfort.com/FortAPI/paymentApi";
    //Make a change for live or test token url from WS_GET_TOKEN variable
    private final static String WS_GET_TOKEN = LIVE_TOKEN_URL;

    //Statics
    private final static String MERCHANT_IDENTIFIER = "JchaDhkP";
    private final static String ACCESS_CODE = "QoYfRxJqkyteuMXu4wGp";
    private final static String SHA_TYPE = "SHA-256";
    private final static String SHA_REQUEST_PHRASE = "$2y$10$9LAJLSdBR";
    public final static String SHA_RESPONSE_PHRASE = "$2y$10$8nleswf18";
    public final static String CURRENCY_TYPE = "JOD";
    public final static String CURRENCY_JOD = "JOD";
    public final static String JORDAN_ISO = "JO";
    public final static String LANGUAGE_TYPE = "en";//Arabic - ar //English - en

    private final Gson gson;
    private Activity context;
    private IPaymentRequestCallBack iPaymentRequestCallBack;
    private FortCallBackManager fortCallback = null;
    private ProgressDialog progressDialog;
    private String sdkToken;
    private PayFortData payFortData;

    public PayFortPayment(Activity context, FortCallBackManager fortCallback, IPaymentRequestCallBack iPaymentRequestCallBack) {
        this.context = context;
        this.fortCallback = fortCallback;
        this.iPaymentRequestCallBack = iPaymentRequestCallBack;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Processing your payment\nPlease wait...");
        progressDialog.setCancelable(false);
        sdkToken = "";
        gson = new Gson();
    }

    public void requestForPayment(PayFortData payFortData) {
        this.payFortData = payFortData;
        new GetTokenFromServer().execute(WS_GET_TOKEN);
    }

    private void requestPurchase() {
        try {
            FortSdk.getInstance().registerCallback(context, getPurchaseFortRequest(), FortSdk.ENVIRONMENT.PRODUCTION, RESPONSE_PURCHASE,
                    fortCallback, true, new FortInterfaces.OnTnxProcessed() {
                        @Override
                        public void onCancel(Map<String, Object> requestMap, Map<String, Object> responseMap) {
                            JSONObject response = new JSONObject(responseMap);
                            PayFortData payFortData = gson.fromJson(response.toString(), PayFortData.class);
                            payFortData.paymentResponse = response.toString();
                            Log.e("Cancel Response", response.toString());
                            if (iPaymentRequestCallBack != null) {
                                iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_PURCHASE_CANCEL, payFortData);
                            }
                        }

                        @Override
                        public void onSuccess(Map<String, Object> requestParamsMap, Map<String, Object> fortResponseMap) {
                            JSONObject response = new JSONObject(fortResponseMap);
                            PayFortData payFortData = gson.fromJson(response.toString(), PayFortData.class);
                            payFortData.paymentResponse = response.toString();
                            Log.e("Success Response", response.toString());
                            if (iPaymentRequestCallBack != null) {
                                iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_PURCHASE_SUCCESS, payFortData);
                            }
                        }

                        @Override
                        public void onFailure(Map<String, Object> map, Map<String, Object> responseMap) {
                            JSONObject response = new JSONObject(responseMap);
                            PayFortData payFortData = gson.fromJson(response.toString(), PayFortData.class);
                            payFortData.paymentResponse = response.toString();
                            Log.e("Failure Response", response.toString());
                            if (iPaymentRequestCallBack != null) {
                                iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_PURCHASE_FAILURE, payFortData);
                            }
                        }




                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FortRequest getPurchaseFortRequest() {
        FortRequest fortRequest = new FortRequest();
        if (payFortData != null) {
            HashMap<String, Object> requestMap = new HashMap<>();
//            requestMap.put(KEY_AMOUNT, payFortData.amount);
//            requestMap.put(KEY_COMMAND, PURCHASE);
//            requestMap.put(KEY_CURRENCY, payFortData.currency);
//            requestMap.put(KEY_CUSTOMER_EMAIL, payFortData.customerEmail);
//            requestMap.put(KEY_LANGUAGE, LANGUAGE_TYPE);
//            requestMap.put(KEY_MERCHANT_REFERENCE, payFortData.merchantReference);
//            requestMap.put(KEY_SDK_TOKEN, sdkToken);

            requestMap.put("command", PURCHASE);
            requestMap.put("customer_email", payFortData.customerEmail);
            requestMap.put("currency", payFortData.currency);
            requestMap.put("amount", payFortData.amount);
            requestMap.put("language", payFortData.language);
            requestMap.put("merchant_reference", payFortData.merchantReference);
            requestMap.put("sdk_token", sdkToken);

            

            fortRequest.setRequestMap(requestMap);
            fortRequest.setShowResponsePage(true);
        }
        return fortRequest;
    }

    private class GetTokenFromServer extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... postParams) {
            String response = "";
            try {
                ProviderInstaller.installIfNeeded(context);

//                SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

                SSLContext sslcontext = SSLContext.getInstance("TLSv1");

                sslcontext.init(null,
                        null,
                        null);
                SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());

                HttpsURLConnection conn;
                URL url = new URL(postParams[0].replace(" ", "%20"));
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("content-type", "application/json");
                conn.setSSLSocketFactory(NoSSLv3Factory);

                String str = getTokenParams();
                byte[] outputInBytes = str.getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(outputInBytes);
                os.close();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    response = convertStreamToString(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialog.hide();
            Log.e("Response", response + "");
            try {
                PayFortData payFortData = gson.fromJson(response, PayFortData.class);
                if (!TextUtils.isEmpty(payFortData.sdkToken)) {
                    sdkToken = payFortData.sdkToken;
                    requestPurchase();
                } else {
                    payFortData.paymentResponse = response;
                    iPaymentRequestCallBack.onPaymentRequestResponse(RESPONSE_GET_TOKEN, payFortData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getTokenParams() {
        JSONObject jsonObject = new JSONObject();
        try {
            String device_id = FortSdk.getDeviceId(context);
            String concatenatedString = SHA_REQUEST_PHRASE +
                    KEY_ACCESS_CODE + "=" + ACCESS_CODE +
                    KEY_DEVICE_ID + "=" + device_id +
                    KEY_LANGUAGE + "=" + LANGUAGE_TYPE +
                    KEY_MERCHANT_IDENTIFIER + "=" + MERCHANT_IDENTIFIER +
                    KEY_SERVICE_COMMAND + "=" + SDK_TOKEN +
                    SHA_REQUEST_PHRASE;

            jsonObject.put(KEY_SERVICE_COMMAND, SDK_TOKEN);
            jsonObject.put(KEY_MERCHANT_IDENTIFIER, MERCHANT_IDENTIFIER);
            jsonObject.put(KEY_ACCESS_CODE, ACCESS_CODE);
            String signature = getSignatureSHA256(concatenatedString);
            jsonObject.put(KEY_SIGNATURE, signature);
            jsonObject.put(KEY_DEVICE_ID, device_id);
            jsonObject.put(KEY_LANGUAGE, LANGUAGE_TYPE);

            Log.e("concatenatedString", concatenatedString);
            Log.e("signature", signature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JsonString", String.valueOf(jsonObject));
        return String.valueOf(jsonObject);
    }

    private static String convertStreamToString(InputStream inputStream) {
        if (inputStream == null)
            return null;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream), 1024);
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static String getSignatureSHA256(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(SHA_TYPE);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            return String.format("%0" + (messageDigest.length * 2) + 'x', new BigInteger(1, messageDigest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}