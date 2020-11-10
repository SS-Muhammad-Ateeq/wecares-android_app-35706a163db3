//package com.wecare.android.ui.social;
//
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.PopupMenu;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.mizmar.souqmizmar.Activities.MainActivity;
//import com.mizmar.souqmizmar.Base.BaseActivity;
//import com.mizmar.souqmizmar.Base.BaseFragment;
//import com.mizmar.souqmizmar.DataModel.UserDataUpdatedEvent;
//import com.mizmar.souqmizmar.Presenters.RegistrationPresenter;
//import com.mizmar.souqmizmar.R;
//import com.mizmar.souqmizmar.Realm.CurrentUser;
//import com.mizmar.souqmizmar.Realm.RealmUtils;
//import com.mizmar.souqmizmar.Utili.AppUtil;
//import com.mizmar.souqmizmar.Utili.CameraHelper;
//import com.mizmar.souqmizmar.Utili.Constants;
//import com.mizmar.souqmizmar.listeners.FragmentInteractionListener;
//import com.wang.avi.AVLoadingIndicatorView;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static android.app.Activity.RESULT_OK;
//import static com.mizmar.souqmizmar.Utili.CameraHelper.GALLARY_REQUEST_CODE;
//import static com.mizmar.souqmizmar.Utili.CameraHelper.REQUEST_CAMERA;
//
//public class RegistrationFragment extends BaseFragment implements RegistrationPresenter.View {
//    @BindView(R.id.editText_registration_username)
//    EditText txtUserName;
//    @BindView(R.id.editText_registration_password)
//    EditText txtPassword;
//    @BindView(R.id.editText_registration_confirm_password)
//    EditText txtConfirmPassword;
//    @BindView(R.id.editText_registration_email)
//    EditText txtEmail;
//    @BindView(R.id.editText_registration_phone)
//    EditText txtPhone;
//    @BindView(R.id.button_registration_register)
//    Button btnRegister;
//    @BindView(R.id.progressBar_registration)
//    AVLoadingIndicatorView progress;
//    @BindView(R.id.imageView_toolbar_menu)
//    ImageView imgMenu;
//    @BindView(R.id.circleImageView_registration_profile_image)
//    CircleImageView profileImage;
//    Uri selectedImageUri = null;
//    RegistrationPresenter presenter;
//    boolean editMode = false;
//    private FragmentInteractionListener listener;
//    private boolean isImageUpdated = false;
//
//    public RegistrationFragment() {
//        // Required empty public constructor
//    }
//
//    // TODO: Rename and change types and number of parameters
//    public static RegistrationFragment newInstance() {
//        RegistrationFragment fragment = new RegistrationFragment();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        presenter = new RegistrationPresenter(this);
//        if (getArguments() != null && getArguments().getBoolean(Constants.EDIT_PROFILE, false)) {
//            editMode = true;
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_registration, container, false);
//        ButterKnife.bind(this, view);
//        if (editMode) {
//            imgMenu.setVisibility(View.VISIBLE);
//            CurrentUser currentUser = RealmUtils.getInstance().getCurrentUser();
//            txtEmail.setText(currentUser.getEmail());
//            txtEmail.setEnabled(false);
//            txtUserName.setText(currentUser.getName());
//            txtPhone.setText(currentUser.getPhone());
//            Glide.with(this)
//                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_user).error(R.drawable.ic_user))
//                    .load(currentUser.getImageUrl()).into(profileImage);
//            txtPassword.setVisibility(View.GONE);
//            txtConfirmPassword.setVisibility(View.GONE);
//            btnRegister.setText(R.string.edit_profile);
//        } else {
//            imgMenu.setVisibility(View.GONE);
//        }
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        if (context instanceof FragmentInteractionListener)
//            listener = (FragmentInteractionListener) context;
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
////            Bundle extras = data.getExtras();
////            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            isImageUpdated = true;
//            selectedImageUri = CameraHelper.capturedImageUri;
//            Bitmap image = CameraHelper.compressImage(CameraHelper.getRealPath(selectedImageUri, getActivity()), 400);
//            Glide.with(this).load(image).into(profileImage);
//        } else if (requestCode == GALLARY_REQUEST_CODE && resultCode == RESULT_OK) {
//            isImageUpdated = true;
//            Uri selectedImage = data.getData();
//            selectedImageUri = selectedImage;
//            Bitmap image = CameraHelper.compressImage(CameraHelper.getRealPath(selectedImageUri, getActivity()), 400);
//            Glide.with(this).load(image).into(profileImage);
//
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CAMERA) {
//            if (grantResults.length == 2
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                CameraHelper.openCamera((BaseActivity) getActivity());
//            }
//        } else if (requestCode == GALLARY_REQUEST_CODE) {
//            if (grantResults.length == 1
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                CameraHelper.openGallary(false);
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @OnClick(R.id.imageView_toolbar_back)
//    public void onBackImageClick() {
//        getActivity().onBackPressed();
//    }
//
//    @OnClick(R.id.button_registration_register)
//    public void onRegisterClick() {
//        if (TextUtils.isEmpty(txtUserName.getText().toString().trim())) {
//            txtUserName.setError(getString(R.string.message_fill_username));
//            return;
//        }
//        if (!editMode) {
//            if (TextUtils.isEmpty(txtEmail.getText().toString().trim())) {
//                txtEmail.setError(getString(R.string.message_fill_email));
//                return;
//            }
//            if (TextUtils.isEmpty(txtPassword.getText().toString().trim())) {
//                txtPassword.setError(getString(R.string.message_fill_password));
//                return;
//            }
//            if (TextUtils.isEmpty(txtConfirmPassword.getText().toString().trim())) {
//                txtConfirmPassword.setError(getString(R.string.message_fill_password));
//                return;
//            }
//            if (!txtPassword.getText().toString().trim().equals(txtConfirmPassword.getText().toString().trim())) {
//                txtConfirmPassword.setError(getString(R.string.message_password_not_match));
//                return;
//            }
//        }
//        if (TextUtils.isEmpty(txtPhone.getText().toString().trim())) {
//            txtPhone.setError(getString(R.string.message_fill_phone));
//            return;
//        }
//        btnRegister.setEnabled(false);
//        if (!editMode) {
//            presenter.registerNewUser(txtUserName.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString(), txtPhone.getText().toString(), selectedImageUri);
//        } else {
//            presenter.updateUser(txtUserName.getText().toString(), txtEmail.getText().toString(), txtPhone.getText().toString(), selectedImageUri);
//
//        }
//    }
//
//    @OnClick(R.id.frame_image)
//    void selectImage() {
//        //Creating the instance of PopupMenu
//        PopupMenu popup = new PopupMenu(getActivity(), profileImage);
//        //Inflating the Popup using xml file
//        popup.getMenuInflater().inflate(R.menu.photo_menu, popup.getMenu());
//
//        //registering popup with OnMenuItemClickListener
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.item_camera:
//                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
//                                == PackageManager.PERMISSION_GRANTED
//                                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                == PackageManager.PERMISSION_GRANTED) {
//                            CameraHelper.openCamera((BaseActivity) getActivity());
//                        } else {
//                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
//                                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                                Snackbar.make(profileImage, R.string.message_enable_permission,
//                                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.enable,
//                                        new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                ActivityCompat.requestPermissions(getActivity(),
//                                                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
//                                            }
//                                        }).show();
//                            } else {
//                                ActivityCompat.requestPermissions(getActivity(),
//                                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
//                            }
//                        }
//                        break;
//                    case R.id.item_gallary:
//                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                                == PackageManager.PERMISSION_GRANTED) {
//                            CameraHelper.openGallary(false);
//                        } else {
//                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                                Snackbar.make(profileImage, R.string.message_enable_permission,
//                                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.enable,
//                                        new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                ActivityCompat.requestPermissions(getActivity(),
//                                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLARY_REQUEST_CODE);
//                                            }
//                                        }).show();
//                            } else {
//                                ActivityCompat.requestPermissions(getActivity(),
//                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLARY_REQUEST_CODE);
//                            }
//                        }
//                        break;
//                }
//                return true;
//            }
//        });
//
//        popup.show();//showing popup menu
//    }
//
//    @Override
//    public void onUserCreatedSuccessfully() {
//        AppUtil.showToastMessage(R.string.message_user_created_successfully, true);
//        List<Integer> flages = new ArrayList<>();
//        flages.add(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        flages.add(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        AppUtil.startActivity(MainActivity.class.getName(), null, flages);
//        getActivity().finish();
//
//    }
//
//    @Override
//    public void showMessage(String message) {
//        hideProgress();
//        AppUtil.showToastMessage(message, true);
//    }
//
//    @Override
//    public void onProfileUpdatedSuccessfully() {
//        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                .setTitleText(getString(R.string.message_user_updated_successfully))
//                .setConfirmText(getString(R.string.okay));
//        sweetAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                EventBus.getDefault().post(new UserDataUpdatedEvent());
//                getActivity().onBackPressed();
//
//            }
//        });
//        sweetAlertDialog.show();
//
//    }
//
//    @Override
//    public void showProgress() {
//        progress.smoothToShow();
//    }
//
//    @Override
//    public void hideProgress() {
//        btnRegister.setEnabled(true);
//        progress.smoothToHide();
//    }
//
//    @Override
//    public Context context() {
//        return getActivity();
//    }
//
//    @OnClick(R.id.imageView_toolbar_menu)
//    public void onMenuClick() {
//        listener.showDrawer();
//    }
//}
//
//
