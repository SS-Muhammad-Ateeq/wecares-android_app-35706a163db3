
package com.wecare.android.ui.order_info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.databinding.ItemAttachmentImageViewBinding;
import com.wecare.android.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class InformationAttachmentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_TYPE_EMPTY = 0;
    private final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<InformationAttachmentObj> mInformationAttachmentObjList;

    private boolean isSummaryView;
     AttachmentAdapterListener mListener;

    public InformationAttachmentAdapter(ArrayList<InformationAttachmentObj> informationAttachmentObjList) {
        this.mInformationAttachmentObjList = informationAttachmentObjList;
        isSummaryView = false;
    }

    public void setSummaryView(boolean summaryView) {
        this.isSummaryView = summaryView;
    }

    public void setListener(AttachmentAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                ItemAttachmentMainBinding emptyViewBinding = ItemAttachmentBinding.inflate(LayoutInflater.from(parent.getContext()),
//                        parent, false);
//                return new AttachmentViewHolder(sViewBinding);
//            case VIEW_TYPE_EMPTY:
//            default:
        ItemAttachmentImageViewBinding attachmentImageViewBinding = ItemAttachmentImageViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new AttachmentViewHolder(attachmentImageViewBinding);
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mInformationAttachmentObjList != null && mInformationAttachmentObjList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mInformationAttachmentObjList != null && mInformationAttachmentObjList.size() > 0) {
            return mInformationAttachmentObjList.size();
        } else {
            return 1;
        }
    }

    public void removeItem(InformationAttachmentObj attachmentObj) {
        mInformationAttachmentObjList.remove(attachmentObj);
        notifyDataSetChanged();
    }

    public void addItem(InformationAttachmentObj attachmentObj) {
        mInformationAttachmentObjList.add(attachmentObj);
        notifyDataSetChanged();
    }

    public void addItems(List<InformationAttachmentObj> attachmentObjList) {
        mInformationAttachmentObjList.addAll(attachmentObjList);
        notifyDataSetChanged();
    }

    public ArrayList<InformationAttachmentObj> getInformationAttachmentObjList() {
        return mInformationAttachmentObjList;
    }

    public void clearItems() {
        mInformationAttachmentObjList.clear();
    }

    public class AttachmentViewHolder extends BaseViewHolder implements AttachmentItemViewModel.AttachmentItemViewModelListener {

        private ItemAttachmentImageViewBinding mBinding;

        private AttachmentItemViewModel mAttachmentItemViewModel;

        public AttachmentViewHolder(ItemAttachmentImageViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mInformationAttachmentObjList.size() != 0) {
                if (isSummaryView) {
                    mBinding.itemDeleteImageImg.setVisibility(View.GONE);
                } else {
                    mBinding.itemDeleteImageImg.setVisibility(View.VISIBLE);
                }


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    float curveRadius = 20F;
//                    ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
//                        @Override
//                        public void getOutline(View view, Outline outline) {
//                            outline.setRoundRect(0, 0, view.getWidth(), (int) (view.getHeight() + curveRadius), curveRadius);
//                            outline.setRoundRect();
//                        }
//                    };
//                    mBinding.itemPickedImageImg.setOutlineProvider(viewOutlineProvider); //Notice I have used inheritance
//                    mBinding.itemPickedImageImg.setClipToOutline(true);
//                }

                final InformationAttachmentObj order = mInformationAttachmentObjList.get(position);

                mAttachmentItemViewModel = new AttachmentItemViewModel(order, this);

                mBinding.setViewModel(mAttachmentItemViewModel);

                // Immediate Binding
                // When a variable or observable changes, the binding will be scheduled to change before
                // the next frame. There are times, however, when binding must be executed immediately.
                // To force execution, use the executePendingBindings() method.
                mBinding.executePendingBindings();
            } else {
                mBinding.itemDeleteImageImg.setVisibility(View.GONE);
            }

        }


        @Override
        public void onRemoveClick(InformationAttachmentObj informationAttachmentObj) {
            mListener.onRemoveClicked(informationAttachmentObj);
        }

        @Override
        public void onNoItemClick() {
            if (mListener!=null && mInformationAttachmentObjList.size() != 0) {
                mListener.onNoItemClick();
            }
        }
    }

//    public class EmptyViewHolder extends BaseViewHolder implements servicesEmptyItemViewModel.ServicesEmptyItemViewModelListener {
//
//        private ItemServicesEmptyViewBinding mBinding;
//
//        public EmptyViewHolder(ItemServicesEmptyViewBinding binding) {
//            super(binding.getRoot());
//            this.mBinding = binding;
//        }
//
//        @Override
//        public void onBind(int position) {
//            ServicesEmptyItemViewModel emptyItemViewModel = new ServicesEmptyItemViewModel(this);
//            mBinding.setViewModel(emptyItemViewModel);
//        }
//
//        @Override
//        public void onRetryClick() {
//            mListener.onRetryClick();
//        }
//    }

    public interface AttachmentAdapterListener {
        void onRemoveClicked(InformationAttachmentObj informationAttachmentObj);

        void onNoItemClick();
    }
}