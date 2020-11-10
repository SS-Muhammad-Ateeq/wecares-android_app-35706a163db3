//package com.wecare.android.ui.social;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.mizmar.souqmizmar.ApiResponse.RegisterResponse;
//import com.mizmar.souqmizmar.Base.BasePresenter;
//import com.mizmar.souqmizmar.R;
//import com.mizmar.souqmizmar.Realm.RealmUtils;
//import com.mizmar.souqmizmar.Utili.ApiHelper;
//import com.mizmar.souqmizmar.listeners.ApiHelperCallback;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import okhttp3.ResponseBody;
//
///**
// * Created by HP on 20/09/2018.
// */
//
//public class LoginPresenter extends BasePresenter<LoginPresenter.View> {
//
//    public LoginPresenter(View view) {
//        setView(view);
//    }
//
//    public void login(String email, String password) {
//        getView().showProgress();
//        Map<String, String> fields = new HashMap<>();
//        fields.put("email", email);
//        fields.put("password", password);
//        fields.put("fcm_token", FirebaseInstanceId.getInstance().getToken());
//        fields.put("device", "android");
//        ApiHelper.getInstance()
//                .loginByEmailAPI(fields, new ApiHelperCallback<RegisterResponse>() {
//                    @Override
//                    public void onSuccess(RegisterResponse response) {
//                        RealmUtils.getInstance().createNewUser(response.getData().getAccessToken(), response.getData().getUser());
//                        getView().onLoggedInSuccessfully();
//                    }
//
//                    @Override
//                    public void onProcessFail(ResponseBody errorBody) {
//                        try {
//                            JSONObject objErrorBody = new JSONObject(errorBody.string());
//                            if (objErrorBody.has("errors")) {
//                                JSONObject objErrors = new JSONObject(objErrorBody.get("errors").toString());
//                                if (objErrors.has("email")) {
//                                    getView().showMessage(objErrors.getJSONArray("email").get(0).toString());
//                                } else if (objErrors.has("password")) {
//                                    getView().showMessage(objErrors.getJSONArray("password").get(0).toString());
//                                } else {
//                                    getView().showMessage("فشل في التسجيل");
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail() {
//                        getView().hideProgress();
//                        getView().showMessage(getView().context().getString(R.string.message_wrong_email_or_pass));
//                    }
//
//                    @Override
//                    public void onNetworkError() {
//                        getView().hideProgress();
//                        getView().showMessage(getView().context().getString(R.string.message_check_internet));
//
//                    }
//                });
//    }
//
//    public void socialLogin(String provider, String accessToken, String secret) {
//        getView().showProgress();
//        Map<String, String> fields = new HashMap<>();
//        fields.put("provider", provider);
//        fields.put("access_token", accessToken);
//        if (secret != null) {
//            fields.put("secret", secret);
//        }
//        fields.put("fcm_token", FirebaseInstanceId.getInstance().getToken());
//        fields.put("device", "android");
//        ApiHelper.getInstance()
//                .socialLoginAPI(fields, new ApiHelperCallback<RegisterResponse>() {
//                    @Override
//                    public void onSuccess(RegisterResponse response) {
//                        RealmUtils.getInstance().createNewUser(response.getData().getAccessToken(), response.getData().getUser());
//                        getView().onLoggedInSuccessfully();
//                    }
//
//                    @Override
//                    public void onFail() {
//                        getView().hideProgress();
//                        getView().showMessage(getView().context().getString(R.string.message_login_failed));
//                    }
//
//                    @Override
//                    public void onNetworkError() {
//                        getView().hideProgress();
//                        getView().showMessage(getView().context().getString(R.string.message_check_internet));
//
//                    }
//                });
//    }
//
//
//    public interface View extends BasePresenter.View {
//        void onLoggedInSuccessfully();
//
//        void showMessage(String message);
//    }
//}
