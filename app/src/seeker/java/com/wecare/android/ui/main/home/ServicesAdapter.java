//
//package com.wecare.android.ui.main.home;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import androidx.recyclerview.widget.RecyclerView;
//import com.wecare.android.data.model.api.responses.MainServiceModel;
//import com.wecare.android.databinding.ItemServiceMainBinding;
//import com.wecare.android.ui.base.BaseViewHolder;
//
//import java.util.List;
//
//
//public class ServicesAdapter extends RecyclerView.Adapter<BaseViewHolder> {
//
//    //    private static final int VIEW_TYPE_EMPTY = 0;
//    private static final int VIEW_TYPE_NORMAL = 1;
//
//    private List<MainServiceModel> mServiceResponseList;
//
//    private ServiceAdapterListener mListener;
//
//    public ServicesAdapter(List<MainServiceModel> serviceResponseList) {
//        this.mServiceResponseList = serviceResponseList;
//    }
//
//    public void setListener(ServiceAdapterListener listener) {
//        this.mListener = listener;
//    }
//
//    @Override
//    public void onBindViewHolder(BaseViewHolder holder, int position) {
//        holder.onBind(position);
//    }
//
//    @Override
//    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
////        switch (viewType) {
////            case VIEW_TYPE_NORMAL:
////                ItemServiceMainBinding emptyViewBinding = ItemServiceMainBinding.inflate(LayoutInflater.from(parent.getContext()),
////                        parent, false);
////                return new SubServiceViewHolder(sViewBinding);
////            case VIEW_TYPE_EMPTY:
////            default:
//        ItemServiceMainBinding serviceMainBinding = ItemServiceMainBinding.inflate(LayoutInflater.from(parent.getContext()),
//                parent, false);
//        return new ServiceViewHolder(serviceMainBinding);
////        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
////        if (mServiceResponseList != null && mServiceResponseList.size() > 0) {
//        return VIEW_TYPE_NORMAL;
////        } else {
////            return VIEW_TYPE_EMPTY;
////        }
//    }
//
//    @Override
//    public int getItemCount() {
////        if (mServiceResponseList != null && mServiceResponseList.size() > 0) {
//        return mServiceResponseList.size();
////        } else {
////            return 1;
////        }
//    }
//
//    public void addItems(List<MainServiceModel> serviceResponseList) {
//        mServiceResponseList.addAll(serviceResponseList);
//        notifyDataSetChanged();
//    }
//
//    public void clearItems() {
//        mServiceResponseList.clear();
//    }
//
//    public class ServiceViewHolder extends BaseViewHolder implements ServicesItemViewModel.ServiceItemViewModelListener {
//
//        private ItemServiceMainBinding mBinding;
//
//        private ServicesItemViewModel mServicesItemViewModel;
//
//        public ServiceViewHolder(ItemServiceMainBinding binding) {
//            super(binding.getRoot());
//            this.mBinding = binding;
//        }
//
//        @Override
//        public void onBind(int position) {
//
//            final MainServiceModel service = mServiceResponseList.get(position);
//
//            mServicesItemViewModel = new ServicesItemViewModel(service, this);
//
//            mBinding.setViewModel(mServicesItemViewModel);
//
//            // Immediate Binding
//            // When a variable or observable changes, the binding will be scheduled to change before
//            // the next frame. There are times, however, when binding must be executed immediately.
//            // To force execution, use the executePendingBindings() method.
//            mBinding.executePendingBindings();
//
//        }
//
//        @Override
//        public void onItemClick(MainServiceModel serviceResponse) {
//            //navigate to sub view.
//            mListener.onItemClicked(serviceResponse);
//        }
//    }
//
////    public class EmptyViewHolder extends BaseViewHolder implements servicesEmptyItemViewModel.ServicesEmptyItemViewModelListener {
////
////        private ItemServicesEmptyViewBinding mBinding;
////
////        public EmptyViewHolder(ItemServicesEmptyViewBinding binding) {
////            super(binding.getRoot());
////            this.mBinding = binding;
////        }
////
////        @Override
////        public void onBind(int position) {
////            ServicesEmptyItemViewModel emptyItemViewModel = new ServicesEmptyItemViewModel(this);
////            mBinding.setViewModel(emptyItemViewModel);
////        }
////
////        @Override
////        public void onRetryClick() {
////            mListener.onRetryClick();
////        }
////    }
//
//    public interface ServiceAdapterListener {
//        void onItemClicked(MainServiceModel serviceResponse);
//    }
//}