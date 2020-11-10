
package com.wecare.android.ui.sub;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecare.android.data.model.api.responses.MainServiceModel;
import com.wecare.android.databinding.ItemServiceMainBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class ServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    //    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private List<MainServiceModel> mMainServiceModelList;

    private ServiceAdapterListener mListener;

    public ServicesAdapter(List<MainServiceModel> mainServiceModelList) {
        this.mMainServiceModelList = mainServiceModelList;
    }

    public void setListener(ServiceAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        ItemServiceMainBinding serviceMainBinding = ItemServiceMainBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ServiceViewHolder(serviceMainBinding);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;

    }

    @Override
    public int getItemCount() {
        return mMainServiceModelList.size();

    }

    public void addItems(List<MainServiceModel> mainServiceModelList) {
        mMainServiceModelList.addAll(mainServiceModelList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mMainServiceModelList.clear();
    }

    public class ServiceViewHolder extends BaseViewHolder implements ServicesItemViewModel.ServiceItemViewModelListener {

        private ItemServiceMainBinding mBinding;

        private ServicesItemViewModel mServicesItemViewModel;

        public ServiceViewHolder(ItemServiceMainBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            final MainServiceModel service = mMainServiceModelList.get(position);

            mServicesItemViewModel = new ServicesItemViewModel(service, this);

            mBinding.setViewModel(mServicesItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();

        }

        @Override
        public void onItemClick(MainServiceModel mainServiceModel) {
            //navigate to sub view.
            mListener.onItemClicked(mainServiceModel);
        }
    }

    public interface ServiceAdapterListener {
        void onItemClicked(MainServiceModel mainServiceModel);
    }
}