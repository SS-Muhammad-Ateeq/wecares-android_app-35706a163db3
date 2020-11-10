
package com.wecare.android.ui.order_info;

import androidx.databinding.ObservableField;
import com.wecare.android.data.local.InformationAttachmentObj;
import com.wecare.android.data.model.api.responses.OrdersResponse;


public class AttachmentItemViewModel {

    private InformationAttachmentObj informationAttachmentObj;

    public ObservableField<String> attachmentImageUrl;
//    public ObservableField<String> orderNameTxt;

    private AttachmentItemViewModelListener mListener;

    public AttachmentItemViewModel(InformationAttachmentObj informationAttachmentObj, AttachmentItemViewModelListener listener) {
        this.informationAttachmentObj = informationAttachmentObj;
        this.mListener = listener;

        attachmentImageUrl = new ObservableField<String>(informationAttachmentObj.getUrl());
    }

    public void onRemoveClick() {
        mListener.onRemoveClick(informationAttachmentObj);
    }

    public void onNoItemClick() {
        mListener.onNoItemClick();
    }

    public interface AttachmentItemViewModelListener {
        void onRemoveClick(InformationAttachmentObj informationAttachmentObj);

        void onNoItemClick();
    }
}
