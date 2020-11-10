
package com.wecare.android.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecare.android.utils.AppConstants;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import br.com.ilhasoft.support.validation.Validator;
import dagger.android.support.AndroidSupportInjection;


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements Validator.ValidationListener {

    private BaseActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;
    private View mRootView;
    private String fragmentIdentifier;
    public Validator validator;


    @AppConstants.animationType
    private int animationDefaultType;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public void openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity.openActivityOnTokenExpire();
        }
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    public String getFragmentIdentifier() {
        return fragmentIdentifier;
    }

    public int getTransitionType() {
        return animationDefaultType;
    }

    public void setTransitionType(int animationDefaultType) {
        this.animationDefaultType = animationDefaultType;
    }

    public void setFragmentIdentifier(String fragmentIdentifier) {
        this.fragmentIdentifier = fragmentIdentifier;
    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void onValidationError() {

    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /***
     * this method is called when the Fragment is used with @BaseViewPagerAdapter.
     * when this Fragment is visible this method is called.
     * Please note that this method will not be called the first time, it must be handled using OnResume (Only for the
     * first Time)
     */
    public void OnUpdateView() {
    }

    /***
     * this method is called when the Fragment is used with @BaseViewPagerAdapter.
     * when this Fragment is not visible this method is called.
     */
    protected void OnViewRemoved() {
    }

    /***
     * this method is called when the Fragment is used with @BaseViewPagerAdapter.
     * when the tab of this Fragment is re-selected this method is called.
     */
    protected void OnTabReselected() {
    }

    public void handleError(String errorMessage) {
        getBaseActivity().handleError(errorMessage);
    }

    public void showCommonError() {
        getBaseActivity().showCommonError();
    }

}
