package com.wecare.android.ui.main.order.current;

import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wecare.android.BR;
import com.wecare.android.R;
import com.wecare.android.ViewModelProviderFactory;
import com.wecare.android.data.model.api.models.LookUpModel;
import com.wecare.android.data.model.api.models.OrderModel;
import com.wecare.android.databinding.FragmentCurrentOrderBinding;
import com.wecare.android.ui.base.BaseFragment;
import com.wecare.android.ui.main.order.FinishOrderActivity;
import com.wecare.android.ui.main.order.details.OrderDetailsActivity;
import com.wecare.android.utils.AppConstants;
import com.wecare.android.utils.DialogFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentFragment extends BaseFragment<FragmentCurrentOrderBinding, CurrentViewModel> implements CurrentNavigator, CurrentAdapter.CurrentAdapterListener {

    public static final String TAG = CurrentFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    CurrentAdapter currentAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    CurrentViewModel viewModel;

    private OnFragmentInteractionListener mListener;

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    String ordersType;

    //pagination
    int pageNumber = 0;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    FragmentCurrentOrderBinding binding;

    public static CurrentFragment newInstance(String ordersType, OnFragmentInteractionListener mListener) {
        Bundle args = new Bundle();
        args.putString(AppConstants.KEY_ORDERS_STATUS, ordersType);
        CurrentFragment fragment = new CurrentFragment();
        fragment.setmListener(mListener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CurrentViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CurrentViewModel.class);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_current_order;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        ordersType = getArguments().getString(AppConstants.KEY_ORDERS_STATUS, AppConstants.ORDERS_STATUS_PENDING);
        currentAdapter.setListener(this);
        currentAdapter.setOrdersType(ordersType);
        currentAdapter.setContext(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNumber = 0;
        viewModel.fetchOrders(ordersType, pageNumber);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.serviceRecycler.setLayoutManager(layoutManager);
        binding.serviceRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.serviceRecycler.setAdapter(currentAdapter);

        //handling pagination
        binding.serviceRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            //Do pagination.. i.e. fetch new data
                            pageNumber = pageNumber + 1;
                            viewModel.loadModeOrders(ordersType, pageNumber);

                        }
                    }
                }
            }
        });
    }

    private void subscribeToLiveData() {
        viewModel.getOrdersListLiveData().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(@Nullable List<OrderModel> ordersResponseList) {
                viewModel.addOrdersItemsToList(ordersResponseList);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void updateCurrentOrderList(List<OrderModel> ordersResponseList) {
        if (ordersResponseList != null) {
            currentAdapter.addItems(ordersResponseList);
            loading = true;
        }
    }

    @Override
    public void orderRejectedSuccessFully(int position) {
        DialogFactory.createFeedBackDialog(getActivity(), getString(R.string.order_rejected_message), getString(R.string.we_hope_you_doing_well),
                getString(R.string.ok), getResources().getDrawable(R.drawable.ic_success_big), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                    }
                });

        viewModel.fetchOrders(ordersType, 0);
    }

    @Override
    public void orderCanceledSuccessFully(int position) {
        DialogFactory.createFeedBackDialog(getActivity(), getString(R.string.order_canceled_message), getString(R.string.we_hope_you_doing_well),
                getString(R.string.ok), getResources().getDrawable(R.drawable.ic_success_big), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                    }
                });
        viewModel.fetchOrders(ordersType, 0);
    }

    @Override
    public void orderStatusChangedSuccessFully(OrderModel orderModel, int status, int position) {
        if (status == AppConstants.ORDER_STATUS_ACCEPTED) {
            if (mListener != null) {
                mListener.moveToFragment(1);
            }

        } else if (status == AppConstants.ORDER_STATUS_CARING) {
            startActivity(OrderDetailsActivity.newIntent(getActivity()).putExtra(AppConstants.KEY_ORDER_OBJECT, orderModel));
        }
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onItemClicked(OrderModel ordersResponse) {
        startActivity(OrderDetailsActivity.newIntent(getActivity()).putExtra(AppConstants.KEY_ORDER_OBJECT, ordersResponse));
    }

    @Override
    public void onCancelClicked(String orderID, int position) {

        DialogFactory.createRadioGroupPicker(getActivity(), getString(R.string.cancel_order), getString(R.string.cancel_order_disclaimer), getString(R.string.yes), new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (which == -1) {
                    getBaseActivity().showToast(getString(R.string.error_select_reason));
                    return false;
                }

                if (which == viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons().size() - 1) {
                    DialogFactory.createInputDialog(getActivity(), getString(R.string.cancel_order), getString(R.string.reject_order_other_disclaimer), getString(R.string.your_reason),getString(R.string.send),
                            new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons().get(which).getId());
                                    viewModel.cancelOrder(viewModel.getOrdersResponseObservableArrayList().get(position).getId() + "", reasonID, input.toString(), position);
                                }
                            });
                } else {
                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons().get(which).getId());
                    viewModel.cancelOrder(viewModel.getOrdersResponseObservableArrayList().get(position).getId() + "", reasonID, "", position);
                }
                return false;
            }
        }, new ArrayList<LookUpModel>(viewModel.getDataManager().getLookupsModel().getCaregiver_order_cancel_reasons()));
    }

    @Override
    public void onRejectClicked(String orderID, int position) {

        DialogFactory.createRadioGroupPicker(getActivity(), getString(R.string.reject_order), getString(R.string.reject_order_disclaimer), getString(R.string.yes), new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (which == -1) {
                    getBaseActivity().showToast(getString(R.string.error_select_reason));
                    return false;
                }

                if (which == viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons().size() - 1) {
                    DialogFactory.createInputDialog(getActivity(), getString(R.string.reject_order), getString(R.string.reject_order_other_disclaimer), getString(R.string.your_reason),getString(R.string.send),
                            new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons().get(which).getId());
                                    viewModel.rejectOrder(viewModel.getOrdersResponseObservableArrayList().get(position).getId() + "", reasonID, input.toString(), position);
                                }
                            });
                } else {
                    int reasonID = Integer.parseInt(viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons().get(which).getId());
                    viewModel.rejectOrder(viewModel.getOrdersResponseObservableArrayList().get(position).getId() + "", reasonID, "", position);

                }
                return false;
            }
        }, new ArrayList<LookUpModel>(viewModel.getDataManager().getLookupsModel().getCaregiver_order_rejected_reasons()));
    }

    @Override
    public void onStartClicked(String orderID, int position) {
        if (viewModel.getOrdersResponseObservableArrayList().get(position).getOrderStatusModel().getId() == AppConstants.ORDER_STATUS_CARING){
            DialogFactory.createInputDialog(getActivity(), getString(R.string.order_report), getString(R.string.order_report_disclaimer),"",
                    getString(R.string.save_finish_order), new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            startActivity(FinishOrderActivity.newIntent(getActivity()).putExtra(AppConstants.KEY_ORDER_OBJECT, viewModel.getOrdersResponseObservableArrayList().get(position)).putExtra(AppConstants.KEY_ORDER_REPORT,input.toString()));
                        }
                    });
        }
        else
            viewModel.changeOrderStatus(orderID, AppConstants.ORDER_STATUS_CARING, position);


    }

    @Override
    public void onAcceptClicked(String orderID, int position) {
        viewModel.changeOrderStatus(orderID, AppConstants.ORDER_STATUS_ACCEPTED, position);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            if (viewModel != null && ordersType != null)
                viewModel.fetchOrders(ordersType, 0);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void moveToFragment(int position);
    }


}
