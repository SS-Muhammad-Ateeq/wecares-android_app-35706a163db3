package com.wecare.android.ui.search_giver.common;

import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class SelectorItemDetail extends ItemDetailsLookup.ItemDetails<Long> {

    long position;

    SelectorItemDetail() {
    }

    @Override
    public int getPosition() {
        return (int) position;
    }

    @Nullable
    @Override
    public Long getSelectionKey() {
        return position;
    }

    @Override
    public boolean inSelectionHotspot(@NonNull MotionEvent e) {
        return true;
    }
}
