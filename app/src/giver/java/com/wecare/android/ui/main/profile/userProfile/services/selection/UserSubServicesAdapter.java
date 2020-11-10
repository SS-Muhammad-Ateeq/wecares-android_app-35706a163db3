package com.wecare.android.ui.main.profile.userProfile.services.selection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecare.android.R;
import com.wecare.android.data.model.api.responses.SubServiceResponse;
import com.wecare.android.databinding.ItemUserSubServiceBinding;
import com.wecare.android.ui.base.BaseViewHolder;
import com.wecare.android.ui.main.profile.userProfile.services.UserServicesAdapter;
import com.wecare.android.utils.AppConstants;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class UserSubServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    Context context;

    public void setSelectionListener(ServicesSelectionItemViewModel.serviceSelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    ServicesSelectionItemViewModel.serviceSelectionListener selectionListener;

    public void setContext(Context context) {
        this.context = context;
    }

    boolean isEditMode = false;

    public boolean getIsEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public void setSelectedServiceMaxIndex(int selectedServiceMaxIndex) {
        this.selectedServiceMaxIndex = selectedServiceMaxIndex;
    }

    int selectedServiceMaxIndex;

    private List<SubServiceResponse> mServiceResponseList;

    private UserServicesAdapter.ServiceAdapterListener mListener;

    public UserSubServicesAdapter(List<SubServiceResponse> serviceResponseList) {
        this.mServiceResponseList = serviceResponseList;
    }

    public void setListener(UserServicesAdapter.ServiceAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        ItemUserSubServiceBinding serviceMainBinding = ItemUserSubServiceBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new UserSubServicesAdapter.ServiceViewHolder(serviceMainBinding);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;

    }

    @Override
    public int getItemCount() {
        return mServiceResponseList.size();

    }

    public void addItems(List<SubServiceResponse> serviceResponseList) {
        mServiceResponseList.addAll(serviceResponseList);
        notifyDataSetChanged();
    }


    public void clearItems() {
        mServiceResponseList.clear();
    }

    public class ServiceViewHolder extends BaseViewHolder implements ServicesSelectionItemViewModel.serviceSelectionListener {

        private ItemUserSubServiceBinding mBinding;

        private ServicesSelectionItemViewModel mServicesItemViewModel;

        public ServiceViewHolder(ItemUserSubServiceBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {


            final SubServiceResponse service = mServiceResponseList.get(position);
            service.setPosition(position);
            mServicesItemViewModel = new ServicesSelectionItemViewModel(service, this);

            mBinding.setViewModel(mServicesItemViewModel);

            //selected services drawing
            if (service.getSelected().equals(AppConstants.PHP_TRUE)) {
                // for selected services show delete and edit icons
                if (isEditMode) {
                    mBinding.itemMainActionImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_delete_gray));
                    mBinding.itemSubActionImg.setVisibility(View.VISIBLE);
                    mBinding.itemSubActionImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_edit));
                    mBinding.notVerifiedLabel.setVisibility(View.GONE);

                } else {
                    // check if service if verified or not
                    if (service.getStatus().equals(AppConstants.SERVICE_STATUS_VERIFIED)) {
                        mBinding.notVerifiedLabel.setVisibility(View.GONE);
                        mBinding.itemMainActionImg.setVisibility(View.VISIBLE);
                        mBinding.itemMainActionImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checked));
                    } else {
                        mBinding.itemMainActionImg.setVisibility(View.GONE);
                        mBinding.notVerifiedLabel.setVisibility(View.VISIBLE);
                    }
                }

                //check for the last selected item index abd draw a separator between selected and non selected services
                if (selectedServiceMaxIndex != -1 && position == selectedServiceMaxIndex) {
                    mBinding.selectedSeperatorView.setVisibility(View.VISIBLE);
                }
            }
            //non selected services drawing
            else {
                //hide for recycling cases
                mBinding.selectedSeperatorView.setVisibility(View.GONE);
                mBinding.notVerifiedLabel.setVisibility(View.GONE);
                mBinding.itemSubActionImg.setVisibility(View.GONE);

                // if isEditMode draw set Main Action to add
                if (isEditMode) {
                    mBinding.itemMainActionImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
                } else {
                    mBinding.itemMainActionImg.setVisibility(View.GONE);
                }
            }


            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }


        @Override
        public void onEditClicked(int position, SubServiceResponse serviceResponse) {

        selectionListener.onEditClicked(position,serviceResponse);
        }

        @Override
        public void onDeleteClicked(int position, SubServiceResponse serviceResponse) {

            selectionListener.onDeleteClicked(position,serviceResponse);

        }

        @Override
        public void onAddClicked(int position, SubServiceResponse serviceResponse) {

            selectionListener.onAddClicked(position,serviceResponse);

        }
    }



}
