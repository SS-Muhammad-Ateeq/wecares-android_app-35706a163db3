//package com.wecare.android.ui.social;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.net.Uri;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.mizmar.souqmizmar.ApiResponse.RegisterResponse;
//import com.mizmar.souqmizmar.ApiResponse.UpdateProfileResponse;
//import com.mizmar.souqmizmar.Base.BasePresenter;
//import com.mizmar.souqmizmar.R;
//import com.mizmar.souqmizmar.Realm.RealmUtils;
//import com.mizmar.souqmizmar.Utili.ApiHelper;
//import com.mizmar.souqmizmar.Utili.CameraHelper;
//import com.mizmar.souqmizmar.listeners.ApiHelperCallback;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//
///**
// * Created by HP on 20/09/2018.
// */
//
//public class RegistrationPresenter extends BasePresenter<RegistrationPresenter.View> {
//
//    public RegistrationPresenter(View view) {
//        setView(view);
//    }
//
//    public void registerNewUser(String name, String email, String password, String phone, Uri imagePathURI) {
//
//        getView().showProgress();
//        Map<String, RequestBody> fields = new HashMap<>();
//        fields.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
//        fields.put("password", RequestBody.create(MediaType.parse("text/plain"), password));
//        fields.put("email", RequestBody.create(MediaType.parse("text/plain"), email));
//        fields.put("telephone", RequestBody.create(MediaType.parse("text/plain"), phone));
//        fields.put("fcm_token", RequestBody.create(MediaType.parse("text/plain"), FirebaseInstanceId.getInstance().getToken()));
//        fields.put("device", RequestBody.create(MediaType.parse("text/plain"), "android"));
//
//        MultipartBody.Part image = null;
//        if (imagePathURI != null) {
//            String imagePath = CameraHelper.getRealPath(imagePathURI, (Activity) getView().context());
//            Bitmap bitmap = CameraHelper.compressImage(imagePath, 400);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] imageByteArray = stream.toByteArray();
//
//            RequestBody requestFile =
//                    RequestBody.create(
//                            MediaType.parse("image/*"),
//                            imageByteArray
//                    );
//            //MultipartBody.Part is used to send also the actual file name
//            String extension = imagePath.substring(imagePath.lastIndexOf("."));
//            image = MultipartBody.Part.createFormData("image", "ProfilePicture" + extension, requestFile);
//            // MultipartBody.Part is used to send also the actual file name
//        }
//
//        ApiHelper.getInstance()
//                .registerNewUserAPI(image, fields, new ApiHelperCallback<RegisterResponse>() {
//                    @Override
//                    public void onSuccess(RegisterResponse response) {
//                        RealmUtils.getInstance().createNewUser(response.getData().getAccessToken(), response.getData().getUser());
//                        getView().onUserCreatedSuccessfully();
//                    }
//
//                    @Override
//                    public void onFail() {
//                        getView().hideProgress();
//                        getView().showMessage(getView().context().getString(R.string.message_somthing_wrong));
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
//    public void updateUser(String name, String email, String phone, Uri imagePathURI) {
//        getView().showProgress();
//        Map<String, RequestBody> fields = new HashMap<>();
//        fields.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
//        fields.put("email", RequestBody.create(MediaType.parse("text/plain"), email));
//        fields.put("telephone", RequestBody.create(MediaType.parse("text/plain"), phone));
//
//        MultipartBody.Part image = null;
//        if (imagePathURI != null) {
//            String imagePath = CameraHelper.getRealPath(imagePathURI, (Activity) getView().context());
//            Bitmap bitmap = CameraHelper.compressImage(imagePath, 400);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] imageByteArray = stream.toByteArray();
//
//            RequestBody requestFile =
//                    RequestBody.create(
//                            MediaType.parse("image/*"),
//                            imageByteArray
//                    );
//            //MultipartBody.Part is used to send also the actual file name
//            String extension = imagePath.substring(imagePath.lastIndexOf("."));
//            image = MultipartBody.Part.createFormData("image", "ProfilePicture" + extension, requestFile);
//            // MultipartBody.Part is used to send also the actual file name
//        }
//
//        ApiHelper.getInstance()
//                .updateUserAPI(image, fields, new ApiHelperCallback<UpdateProfileResponse>() {
//                    @Override
//                    public void onSuccess(UpdateProfileResponse response) {
//                        RealmUtils.getInstance().updateUserInfo(response.getUser());
//                        getView().onProfileUpdatedSuccessfully();
//                    }
//
//                    @Override
//                    public void onProcessFail(ResponseBody errorBody) {
//                        try {
//                            JSONObject objErrorBody = new JSONObject(errorBody.string());
//                            if (objErrorBody.has("errors")) {
//                                JSONObject objErrors = new JSONObject(objErrorBody.get("errors").toString());
//                                if (objErrors.has("email")) {
//                                    getView().showMessage(objErrors.get("email").toString());
//                                } else if (objErrors.has("password")) {
//                                    getView().showMessage(objErrors.get("password").toString());
//                                } else {
//                                    getView().showMessage("فشل في تعديل البيانات");
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
//                        getView().showMessage(getView().context().getString(R.string.message_somthing_wrong));
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
//        void onUserCreatedSuccessfully();
//
//        void showMessage(String message);
//
//        void onProfileUpdatedSuccessfully();
//    }
//}
